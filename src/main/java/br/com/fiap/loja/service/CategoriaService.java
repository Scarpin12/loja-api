package br.com.fiap.loja.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.loja.dto.CategoriaRequest;
import br.com.fiap.loja.exception.RecursoNaoEncontradoException;
import br.com.fiap.loja.model.Categoria;
import br.com.fiap.loja.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Categoria> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Categoria buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria nao encontrada: " + id));
    }

    @Transactional
    public Categoria criar(CategoriaRequest request) {
        return repository.save(new Categoria(request.nome()));
    }

    @Transactional
    public Categoria atualizar(Long id, CategoriaRequest request) {
        Categoria categoria = buscar(id);
        categoria.setNome(request.nome());
        return repository.save(categoria);
    }

    @Transactional
    public void excluir(Long id) {
        Categoria categoria = buscar(id);
        repository.delete(categoria);
        repository.flush();
    }
}
