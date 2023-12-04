# Importando o módulo boto3
import boto3


import shutil

shutil.make_archive("final", "zip", "./lambda")
# Criando um cliente para acessar o serviço DynamoDB
dynamodb = boto3.client(
    "dynamodb", endpoint_url="http://localhost:4566", region_name="us-east-1"
)
# Criando um recurso do tipo aws_lambda_function
lambdafn = boto3.client(
    "lambda", endpoint_url="http://localhost:4566", region_name="us-east-1"
)
iam = boto3.client("iam", endpoint_url="http://localhost:4566", region_name="us-east-1")

try:
    iam.detach_role_policy(
        RoleName="LambdaDynamoDBRole",
        PolicyArn="arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole",
    )
except:
    print("1")

try:
    iam.detach_role_policy(
        RoleName="LambdaDynamoDBRole",
        PolicyArn="arn:aws:iam::aws:policy/AmazonDynamoDBFullAccess",
    )
except:
    print("2")

try:
    iam.delete_role(RoleName="LambdaDynamoDBRole")
except:
    print("3")

try:
    dynamodb.delete_table(TableName="Pedidos")
except:
    print("4")

try:
    lambdafn.delete_function(FunctionName="ProcessDynamoDBRecords")
except Exception as e:
    print("5")

# Criando uma tabela chamada Pedidos
table = dynamodb.create_table(
    # Definindo o nome da tabela
    TableName="Pedidos",
    # Definindo os atributos da tabela
    AttributeDefinitions=[
        {"AttributeName": "codigo_pedido_credito", "AttributeType": "S"},
        {"AttributeName": "chave_ordenacao", "AttributeType": "S"},
        {"AttributeName": "codigo_cliente", "AttributeType": "S"},
        {"AttributeName": "codigo_produto", "AttributeType": "S"},
    ],
    # Definindo a chave primária da tabela
    KeySchema=[
        {
            "AttributeName": "codigo_pedido_credito",
            "KeyType": "HASH",  # Chave de classificação
        },
        {
            "AttributeName": "chave_ordenacao",
            "KeyType": "RANGE",  # Chave de classificação
        },
    ],
    GlobalSecondaryIndexes=[
        {
            "IndexName": "codigo_cliente_codigo_produto_index",
            "KeySchema": [
                {"AttributeName": "codigo_cliente", "KeyType": "HASH"},
                {"AttributeName": "codigo_produto", "KeyType": "RANGE"},
            ],
            "Projection": {
                "ProjectionType": "INCLUDE",
                "NonKeyAttributes": [
                    "codigo_pedido_credito",
                    "data_pedido",
                    "status_pedido",
                ],
            },
            "ProvisionedThroughput": {"ReadCapacityUnits": 5, "WriteCapacityUnits": 5},
        },
    ],
    # Habilitando o streaming da tabela
    StreamSpecification={
        "StreamEnabled": True,
        "StreamViewType": "NEW_AND_OLD_IMAGES",  # Capturando as imagens antes e depois dos itens modificados
    },
    # Definindo o throughput provisionado da tabela
    ProvisionedThroughput={"ReadCapacityUnits": 5, "WriteCapacityUnits": 5},
)


# Criando uma role chamada LambdaDynamoDBRole
role = iam.create_role(
    # Definindo o nome da role
    RoleName="LambdaDynamoDBRole",
    # Definindo a política de confiança da role
    AssumeRolePolicyDocument="""{
        "Version": "2012-10-17",
        "Statement": [
            {
                "Effect": "Allow",
                "Principal": {
                    "Service": "lambda.amazonaws.com"
                },
                "Action": "sts:AssumeRole"
            }
        ]
    }""",
)

# Anexando a política gerenciada AWSLambdaBasicExecutionRole à role
iam.attach_role_policy(
    # Especificando o nome da role
    RoleName="LambdaDynamoDBRole",
    # Especificando a ARN da política gerenciada
    PolicyArn="arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole",
)

# Anexando a política gerenciada AmazonDynamoDBFullAccess à role
iam.attach_role_policy(
    # Especificando o nome da role
    RoleName="LambdaDynamoDBRole",
    # Especificando a ARN da política gerenciada
    PolicyArn="arn:aws:iam::aws:policy/AmazonDynamoDBFullAccess",
)


# Criando uma função do Lambda chamada ProcessDynamoDBRecords
function = lambdafn.create_function(
    # Definindo o nome da função
    FunctionName="ProcessDynamoDBRecords",
    # Definindo o arquivo zip com o código da função
    Code={"ZipFile": open("final.zip", "rb").read()},
    # Definindo a função handler
    Handler="lambda_streaming_elasticsearch.lambda_handler",
    # Definindo o tempo de execução
    Runtime="python3.9",
    # Definindo a função de execução que dá à função permissão para acessar recursos do AWS
    Role="arn:aws:iam::000000000000:role/LambdaDynamoDBRole",
)

# Criando um recurso do tipo aws_lambda_event_source_mapping
mapping = lambdafn.create_event_source_mapping(
    # Definindo a função lambda que será acionada pelo stream
    FunctionName="ProcessDynamoDBRecords",
    StartingPosition="LATEST",
    # Definindo o stream do dynamo que será a fonte do evento
    EventSourceArn=table["TableDescription"]["LatestStreamArn"],
)
print('ok')