package br.com.fiap.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.loja.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
