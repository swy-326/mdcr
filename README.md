# Desafio Matriz de Dados do Crédito Rural - MDCR IBM - C

Aplicação que lê, salva e apresenta dados da lista da Matriz de Dados do Crédito Rural - MDCR do Banco Central do Brasil sobre Contratos de Investimento por Município e Produto.

- Fonte de dados: [Matriz de Dados do Crédito Rural - MDCR - Conjuntos de dados - Portal de Dados Abertos do Banco Central do Brasil (bcb.gov.br)](https://dadosabertos.bcb.gov.br/dataset/matrizdadoscreditorural)

- API: [Matriz de Dados do Crédito Rural - MDCR - v2 (bcb.gov.br)](https://olinda.bcb.gov.br/olinda/servico/SICOR/versao/v2/aplicacao#!/recursos/InvestMunicipioProduto)

- Documentação: [Matriz de Dados do Crédito Rural - MDCR - v2 (bcb.gov.br)](https://olinda.bcb.gov.br/olinda/servico/SICOR/versao/v2/documentacao)


## Requisitos

- Java 11+
- Maven 3+
- Mysql 8+

## Setup

### Clonar o aplicativo

```
https://github.ibm.com/sungwon-yoon/mdcr-c.git
```

### Configurar MySql

No arquivo `src\main\resources\application.properties`, configure o username e password

```
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/contratos?useSSL=false&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=GMT
spring.datasource.username=root
spring.datasource.password=0000
```

### Build e Run

```
mvn clean spring-boot:run
```

ou

```
mvn package
java -jar target/mdcr-0.0.1-SNAPSHOT.jar
```

O servidor será iniciado em http://localhost:8080

## REST API

A documentação de APIs podem ser encontrados em <http://localhost:8080/swagger-ui.html> após o aplicativo ser executado

ou na documentação abaixo

| Método | URL | Descrição |
| --- | --- | --- |
| GET | /api/contratos/onboarding | Busca os dados daAPI de MDCR e salva no banco de dados |
| GET | /api/contratos | Busca todos os registros de banco de dados, podendo ser consultado por paginação |
| GET | /api/contratos/id/{id} | Busca um registro por seu ID |
| GET | /api/contratos/ano/{ano} | Retorna a soma de investimentos dos produtos |
| GET | /api/contratos/search | Busca todos os registros por parâmetros fornecidos |
| POST | /api/contratos | Salva um novo registro |
| PUT | /api/contratos/id/{id} | Atualiza o registro do ID fornecido |
| DELETE | /api/contratos/id/{id} | Remove o registro do ID fornecido |