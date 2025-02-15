
# API REST de Biblioteca - ADA

API REST para gerenciamente de uma biblioteca. Gerencia usuários, livros e empréstimos dos livros


## Stack utilizada

**Back-end:** Java 21, Spring Boot 3.4.2, Spring Web, Spring Data JPA, Banco de Dados H2, JUnit 5 e Mockito, Swagger


## Pré-requisitos

Antes de rodar o projeto, certifique-se de ter instalado:

Java 21

```bash
  java -version
```

Maven

```bash
  mvn -version
```

## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/gabrielvf64/library-api
```

Entre no diretório do projeto

```bash
  cd library-api
```

Instale as dependências

```bash
  mvn clean install
```

Inicie o servidor

```bash
  mvn spring-boot:run
```

A API estará disponível em: http://localhost:8080

## Documentação
http://localhost:8080/swagger-ui/index.html

## Configuração do Banco de dados H2

O projeto utiliza o banco de dados H2 em memória. A configuração está definida no arquivo application.properties

Para acessar o console web do H2, após rodar a aplicação, vá até: http://localhost:8080/h2-console e use os seguintes dados:

JDBC URL: jdbc:h2:mem:testdb

User: sa

Password: (deixe em branco)


## Rodando os testes

Para rodar os testes, rode o seguinte comando

```bash
  mvn test
```
Isso executará todos os testes unitários e de integração do projeto.