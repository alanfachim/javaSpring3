package com.svcsolicitarcredito.application.dto.exceptions;
public class PedidoNotFoundException extends RuntimeException {

    // Construtor que recebe a mensagem de erro
    public PedidoNotFoundException(String message) {
        super(message);
    }

    // Construtor que recebe a causa da exceção
    public PedidoNotFoundException(Throwable cause) {
        super(cause);
    }

    // Construtor que recebe a mensagem e a causa da exceção
    public PedidoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
