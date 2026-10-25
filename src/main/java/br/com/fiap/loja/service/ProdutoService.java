package br.com.fiap.loja.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.loja.dto.ProdutoRequest;
import br.com.fiap.loja.exception.RecursoNaoEncontradoException;
import br.com.fiap.loja.model.Categoria;
import br.com.fiap.loja.model.Produto;
import br.com.fiap.loja.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    @Transactional(readOnly = true)
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Produto buscar(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto nao encontrado: " + id));
    }

    @Transactional
    public Produto criar(ProdutoRequest request) {
        Categoria categoria = categoriaService.buscar(request.categoriaId());
        return produtoRepository.save(new Produto(request.nome(), request.preco(), categoria));
    }

    @Transactional
    public Produto atualizar(Long id, ProdutoRequest request) {
        Produto produto = buscar(id);
        Categoria categoria = categoriaService.buscar(request.categoriaId());
        produto.setNome(request.nome());
        produto.setPreco(request.preco());
        produto.setCategoria(categoria);
        return produtoRepository.save(produto);
    }

    @Transactional
    public void excluir(Long id) {
        Produto produto = buscar(id);
        produtoRepository.delete(produto);
    }
}
