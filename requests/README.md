# Requisicoes do CRUD (Postman / Insomnia / curl)

Troque `BASE` pela URL do Web App, ex.: `https://app-loja-rm00000.azurewebsites.net`.
Todos os envios usam o header `Content-Type: application/json`.

| Tabela    | Operacao | Metodo e URL           | Corpo (JSON)                        |
|-----------|----------|------------------------|-------------------------------------|
| categoria | CREATE   | POST   BASE/categorias | categoria-criar.json                |
| categoria | READ     | GET    BASE/categorias     (e BASE/categorias/1) | -              |
| categoria | UPDATE   | PUT    BASE/categorias/1 | categoria-atualizar.json            |
| categoria | DELETE   | DELETE BASE/categorias/1 | -                                   |
| produto   | CREATE   | POST   BASE/produtos   | produto-criar.json                  |
| produto   | READ     | GET    BASE/produtos       (e BASE/produtos/1)   | -              |
| produto   | UPDATE   | PUT    BASE/produtos/1 | produto-atualizar.json              |
| produto   | DELETE   | DELETE BASE/produtos/1 | -                                   |

Ordem sugerida para o video: criar categoria -> criar produto (usa categoriaId = 1) ->
atualizar produto -> atualizar categoria -> excluir produto -> excluir categoria.
(Excluir a categoria antes do produto retorna 409, pois ha relacionamento entre as tabelas.)

## Conferencia direto no banco (apos cada operacao)

```sql
SELECT * FROM dbo.categoria;
SELECT * FROM dbo.produto;
```

## curl

```bash
curl -X POST   $BASE/categorias -H "Content-Type: application/json" -d @categoria-criar.json
curl -X POST   $BASE/produtos   -H "Content-Type: application/json" -d @produto-criar.json
curl -X PUT    $BASE/produtos/1 -H "Content-Type: application/json" -d @produto-atualizar.json
curl           $BASE/produtos
curl -X DELETE $BASE/produtos/1
```
