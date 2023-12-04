Esse terraform é um exemplo de código que eu criei para te mostrar como provisionar um cluster elasticsearch na AWS e uma função lambda para pausar e retomar o cluster de acordo com um cronograma. O código faz o seguinte:

- Cria um módulo elasticsearch usando a fonte clouddrove/elasticsearch/aws, que é um módulo terraform que facilita a criação de um cluster elasticsearch na AWS.
- Define as configurações do IAM, da rede, do ES, do volume, do DNS, do Cognito e dos logs para o cluster elasticsearch, usando os valores que eu encontrei nos resultados da pesquisa web .
- Cria uma função lambda chamada pause_resume_es, que usa o runtime python3.8 e o handler lambda_function.lambda_handler.
- Define o código da função lambda, que usa a API do boto3 para atualizar a configuração do domínio elasticsearch para desabilitar ou habilitar o processamento de cluster, dependendo do tipo de evento (pause ou resume) que é passado como um parâmetro para a função lambda.
- Define as variáveis de ambiente para o nome do domínio elasticsearch e o número de instâncias, usando os valores do módulo elasticsearch.
- Define o tempo limite da função lambda em 30 segundos.
- Cria um papel IAM chamado lambda_role, que permite que a função lambda assuma o papel de um serviço lambda.
- Anexa uma política ao papel lambda, que permite que a função lambda acesse o serviço elasticsearch.
- Cria um evento do CloudWatch chamado pause_es_rule, que aciona a função lambda para pausar o cluster elasticsearch às 18:00, usando uma expressão cron.
- Cria um alvo do CloudWatch chamado pause_es_target, que passa o tipo de evento (pause) como um parâmetro para a função lambda.
- Cria uma permissão para permitir que o evento do CloudWatch invoque a função lambda para pausar o cluster elasticsearch.
- Cria um evento do CloudWatch chamado resume_es_rule, que aciona a função lambda para retomar o cluster elasticsearch às 09:00, usando uma expressão cron.
- Cria um alvo do CloudWatch chamado resume_es_target, que passa o tipo de evento (resume) como um parâmetro para a função lambda.
- Cria uma permissão para permitir que o evento do CloudWatch invoque a função lambda para retomar o cluster elasticsearch.