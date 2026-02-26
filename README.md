# 🚀 Project Webhook — Spring Boot REST API

API REST com CRUD completo, Webhooks e documentação Swagger, desenvolvida com Spring Boot.

---

## 📁 Estrutura do Projeto

```
├project-webhook\
├── src/
│   └── main/
│       ├── java/
│       │   └── com/webhook/web/
│       │       ├── config/
│       │       │   └── OpenApiConfig.java
│       │       ├── controller/
│       │       │   └── ProductController.java
│       │       ├── dto/
│       │       │   ├── ProductRequest.java
│       │       │   └── ProductResponse.java
│       │       ├── entity/
│       │       │   └── Product.java
│       │       ├── exception/
│       │       │   ├── GlobalExceptionHandler.java
│       │       │   └── ResourceNotFoundException.java
│       │       ├── repository/
│       │       │   └── ProductRepository.java
│       │       ├── service/
│       │       │   └── ProductService.java
│       │       ├── webhook/
│       │       │   └── WebhookService.java
│       │       └── WebApplication.java
│       └── resources/
│           └── application.yml
├── docker-compose.yml
└── pom.xml
```

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring WebFlux** (para envio assíncrono de Webhooks)
- **PostgreSQL** (banco de dados principal)
- **H2** (banco em memória para testes)
- **SpringDoc OpenAPI (Swagger UI)**
- **Lombok**
- **Docker & Docker Compose**

---

## ⚙️ Pré-requisitos

Antes de rodar o projeto, certifique-se de ter instalado:

- [Java 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/) (ou use o `mvnw` incluído no projeto)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) *(somente se usar PostgreSQL)*

---

## ▶️ Como Executar

### Opção 1 — Com H2 (banco em memória, sem Docker)

Ideal para testes rápidos, sem precisar instalar nada além do Java.

**1. Certifique-se que o `application.yml` está configurado com H2:**

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:demo_db
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    database-platform: org.hibernate.dialect.H2Dialect

server:
  port: 8080

webhook:
  url: https://webhook.site/seu-uuid-aqui

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    operations-sorter: method
    tags-sorter: alpha
```

**2. Rode o projeto no terminal a partir da raiz do projeto:**

```bash
cd C:\Users\Usuário\Documents\Projects\project-webhook

mvnw.cmd spring-boot:run
```

---

### Opção 2 — Com PostgreSQL via Docker

**1. Certifique-se que o `application.yml` está configurado com PostgreSQL:**

```yaml
spring:
  application:
    name: products-api
  datasource:
    url: jdbc:postgresql://localhost:5432/demo_db
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 9080

webhook:
  url: https://webhook.site/seu-uuid-aqui

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    operations-sorter: method
    tags-sorter: alpha
```

**2. Suba o banco com Docker:**

```bash
cd C:\Users\Usuário\Documents\Projects\project-webhook

docker-compose up -d
```

**3. Rode a aplicação:**

```bash
mvnw.cmd spring-boot:run
```

---

## 🌐 URLs Disponíveis

| URL | Descrição |
|-----|-----------|
| `http://localhost:8080/swagger-ui.html` | Swagger UI — documentação e testes |
| `http://localhost:8080/api-docs` | Spec OpenAPI em JSON |
| `http://localhost:8080/api-docs.yaml` | Spec OpenAPI em YAML |
| `http://localhost:8080/h2-console` | Console H2 *(somente ao usar H2)* |

### Credenciais do H2 Console

| Campo | Valor |
|-------|-------|
| JDBC URL | `jdbc:h2:mem:demo_db` |
| Username | `sa` |
| Password | *(deixe vazio)* |

---

## 📦 Endpoints da API

Base URL: `http://localhost:8080/api/v1/products`

| Método | Endpoint | Descrição | Webhook disparado |
|--------|----------|-----------|-------------------|
| `POST` | `/` | Criar produto | `PRODUCT_CREATED` |
| `GET` | `/` | Listar todos os produtos | — |
| `GET` | `/{id}` | Buscar produto por ID | — |
| `PUT` | `/{id}` | Atualizar produto | `PRODUCT_UPDATED` |
| `DELETE` | `/{id}` | Deletar produto | `PRODUCT_DELETED` |

### Exemplo de Body (POST / PUT)

```json
{
  "name": "Notebook Dell",
  "price": 3499.90,
  "description": "Notebook Dell Inspiron 15, 16GB RAM, SSD 512GB"
}
```

### Exemplo de Resposta

```json
{
  "id": 1,
  "name": "Notebook Dell",
  "price": 3499.90,
  "description": "Notebook Dell Inspiron 15, 16GB RAM, SSD 512GB",
  "createdAt": "2026-02-26T10:00:00"
}
```

---

## 🔔 Testando os Webhooks

1. Acesse [https://webhook.site](https://webhook.site)
2. Copie a URL única gerada (ex: `https://webhook.site/xxxx-xxxx-xxxx`)
3. Cole no `application.yml` no campo `webhook.url`
4. Crie, atualize ou delete um produto via Swagger ou Postman
5. O webhook.site exibirá o payload recebido em tempo real

### Exemplo de payload enviado pelo webhook

```json
{
  "event": "PRODUCT_CREATED",
  "timestamp": "2026-02-26T10:00:00",
  "data": {
    "id": 1,
    "name": "Notebook Dell",
    "price": 3499.90
  }
}
```

---

## 🧪 Testando com o Swagger

1. Acesse `http://localhost:8080/swagger-ui.html`
2. Clique no endpoint desejado (ex: `POST /api/v1/products`)
3. Clique em **Try it out**
4. Preencha o body com os dados do produto
5. Clique em **Execute**
6. Verifique a resposta na seção **Responses**

---

## 📋 Pacote base do projeto

O pacote base Java do projeto está em:

```
com.webhook.web
```

Certifique-se que a classe principal `WebApplication.java` possui a anotação `@SpringBootApplication` e está neste pacote, pois o Spring vai escanear os componentes a partir daí.

```java
package com.webhook.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
```

---

## ❓ Problemas Comuns

**Erro ao conectar no PostgreSQL:**
Verifique se o Docker está rodando e se o container `products-db` está ativo:
```bash
docker ps
```

**Porta 8080 em uso:**
Altere a porta no `application.yml`:
```yaml
server:
  port: 9080
```

**Swagger não abre:**
Confirme que a dependência `springdoc-openapi-starter-webmvc-ui` está no `pom.xml` e que a aplicação subiu sem erros.

---

## 📄 Licença

Este projeto é de uso livre para fins de estudo e laboratório.
