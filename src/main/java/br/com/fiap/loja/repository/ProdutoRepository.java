package br.com.fiap.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.loja.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
