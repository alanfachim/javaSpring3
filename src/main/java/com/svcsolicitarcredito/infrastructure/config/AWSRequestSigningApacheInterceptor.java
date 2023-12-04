package com.svcsolicitarcredito.infrastructure.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 *        Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *        Permission is hereby granted, free of charge, to any person obtaining a copy of this
 *        software and associated documentation files (the "Software"), to deal in the Software
 *        without restriction, including without limitation the rights to use, copy, modify,
 *        merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 *        permit persons to whom the Software is furnished to do so.
 *
 *        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *        INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 *        PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *        HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *        OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 *        SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import com.amazonaws.DefaultRequest;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.Signer;
import com.amazonaws.http.HttpMethodName;
import org.apache.http.*;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.apache.http.protocol.HttpCoreContext.HTTP_TARGET_HOST;

public class AWSRequestSigningApacheInterceptor implements HttpRequestInterceptor {
    /**
     * The service that we're connecting to. Technically not necessary.
     * Could be used by a future Signer, though.
     */
    private final String service;

    /**
     * The particular signer implementation.
     */
    private final Signer signer;

    /**
     * The source of AWS credentials for signing.
     */
    private final AWSCredentialsProvider awsCredentialsProvider;
    private final List<String> PATHS;
    private final Map<String, String> PARAMETERS;

    /**
     * @param service                service that we're connecting to
     * @param signer                 particular signer implementation
     * @param awsCredentialsProvider source of AWS credentials for signing
     */
    public AWSRequestSigningApacheInterceptor(final String service,
                                              final Signer signer,
                                              final AWSCredentialsProvider awsCredentialsProvider,
                                              final List<String> paths,
                                              final Map<String, String> parameters) {
        this.service = service;
        this.signer = signer;
        this.PATHS = paths;
        this.PARAMETERS = parameters;
        this.awsCredentialsProvider = awsCredentialsProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(final HttpRequest request, final HttpContext context)
            throws IOException {
        DefaultRequest<?> signableRequest = null;
        try {
            signableRequest = convertRequest(request,context);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        if (request instanceof HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
            if (httpEntityEnclosingRequest.getEntity() == null) {
                signableRequest.setContent(new ByteArrayInputStream(new byte[0]));
            } else {
                signableRequest.setContent(httpEntityEnclosingRequest.getEntity().getContent());
            }
        }

        // Sign it
        signer.sign(signableRequest, awsCredentialsProvider.getCredentials());

        // Now copy everything back
        request.setHeaders(mapToHeaderArray(signableRequest.getHeaders()));
        if (request instanceof HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
            if (httpEntityEnclosingRequest.getEntity() != null) {
                BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
                basicHttpEntity.setContent(signableRequest.getContent());
                httpEntityEnclosingRequest.setEntity(basicHttpEntity);
            }
        }
    }


    // Define the fixed lists for each attribute
    private static final List<String> METHODS = List.of("GET", "POST", "PUT", "DELETE", "HEAD");
    private static final List<String> SCHEMES = List.of("http", "https");

    // Define a method that takes a HttpRequest and returns a DefaultRequest
    public DefaultRequest<?> convertRequest(HttpRequest input,final HttpContext context) throws IOException, URISyntaxException {
        // Get the input HttpRequest attributes
        String method = input.getRequestLine().getMethod();
        URI uri = new URI(input.getRequestLine().getUri());

        // Set the output DefaultRequest attributes based on the fixed lists
        DefaultRequest<?> output = new DefaultRequest<>("se");

        if (!METHODS.contains(method))
            throw new IOException("Metodo não permitido para requisição ao opensearch");
        if (!PATHS.contains(uri.getPath()))
            throw new IOException("Rota não permitida para requisição ao opensearch");
        HttpHost host = (HttpHost) context.getAttribute(HTTP_TARGET_HOST);
        if (host != null) {
            output.setEndpoint(URI.create(host.toURI()));
        }

        output.setHttpMethod(HttpMethodName.valueOf(METHODS.get(METHODS.indexOf(method))));
        output.setResourcePath(PATHS.get(PATHS.indexOf(uri.getPath())));
        output.setParameters(new HashMap<>());
        if (uri.getQuery() != null) {
            String[] params = uri.getQuery().split("&");
            for (String param : params) {
                String key = param.split("=")[0];
                String value = PARAMETERS.get(key);
                if (value != null) {
                    // Sanitize the parameter string by replacing all non-alphanumeric or underscore characters with the replacement string
                    String sanitized = param.split("=")[1].replaceAll(value, "");
                    output.addParameter(key, sanitized);
                }
            }
        }
        output.setHeaders(headerArrayToMap(input.getAllHeaders()));
        return output;
    }


    /**
     * @param headers modeled Header objects
     * @return a Map of header entries
     */
    private static Map<String, String> headerArrayToMap(final Header[] headers) {
        Map<String, String> headersMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Header header : headers) {
            if (!skipHeader(header)) {
                headersMap.put(header.getName(), header.getValue());
            }
        }
        return headersMap;
    }

    /**
     * @param header header line to check
     * @return true if the given header should be excluded when signing
     */
    private static boolean skipHeader(final Header header) {
        return ("content-length".equalsIgnoreCase(header.getName())
                && "0".equals(header.getValue())) // Strip Content-Length: 0
                || "host".equalsIgnoreCase(header.getName()); // Host comes from endpoint
    }

    /**
     * @param mapHeaders Map of header entries
     * @return modeled Header objects
     */
    private static Header[] mapToHeaderArray(final Map<String, String> mapHeaders) {
        Header[] headers = new Header[mapHeaders.size()];
        int i = 0;
        for (Map.Entry<String, String> headerEntry : mapHeaders.entrySet()) {
            headers[i++] = new BasicHeader(headerEntry.getKey(), headerEntry.getValue());
        }
        return headers;
    }
}
