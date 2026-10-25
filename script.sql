-- DDL das tabelas (Azure SQL / SQL Server)
-- Relacionamento: produto (N) -> categoria (1) por chave estrangeira

IF OBJECT_ID('dbo.produto', 'U') IS NOT NULL DROP TABLE dbo.produto;
IF OBJECT_ID('dbo.categoria', 'U') IS NOT NULL DROP TABLE dbo.categoria;

CREATE TABLE dbo.categoria (
    id   BIGINT IDENTITY(1,1) NOT NULL,
    nome VARCHAR(100)         NOT NULL,
    CONSTRAINT pk_categoria PRIMARY KEY (id)
);

CREATE TABLE dbo.produto (
    id           BIGINT IDENTITY(1,1) NOT NULL,
    nome         VARCHAR(150)         NOT NULL,
    preco        DECIMAL(10,2)        NOT NULL,
    categoria_id BIGINT               NOT NULL,
    CONSTRAINT pk_produto PRIMARY KEY (id),
    CONSTRAINT fk_produto_categoria FOREIGN KEY (categoria_id) REFERENCES dbo.categoria (id)
);
