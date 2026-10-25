# loja-api

API REST simples (Java 17, Spring Boot 3, Maven) usada no 3o Checkpoint de Pipelines CI/CD (Azure DevOps).

- **Tabelas:** `categoria` (1) -> `produto` (N), relacionadas por chave estrangeira (`produto.categoria_id`).
- **Endpoints:** CRUD completo em `/categorias` e `/produtos` (exemplos em `requests/`).
- **Banco:** Azure SQL (PaaS). Localmente usa H2 em memoria.
- **Variaveis de ambiente:** `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`.

## Estrutura

| Caminho                  | Conteudo                                       |
|--------------------------|------------------------------------------------|
| `script.sql`             | DDL das tabelas                                |
| `infra/criar-infra.sh`   | Cria Resource Group, Azure SQL e Web App (az CLI) |
| `requests/`              | JSONs e exemplos do CRUD                       |
| `src/test`               | Testes unitarios (JUnit + Mockito)             |

## Rodar localmente

```bash
mvn spring-boot:run      # http://localhost:8080
mvn package              # compila e roda os testes
```
