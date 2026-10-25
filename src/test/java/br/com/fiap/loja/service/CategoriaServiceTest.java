package br.com.fiap.loja.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.loja.dto.CategoriaRequest;
import br.com.fiap.loja.exception.RecursoNaoEncontradoException;
import br.com.fiap.loja.model.Categoria;
import br.com.fiap.loja.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaService service;

    @Test
    void criar_salvaCategoriaComONomeInformado() {
        when(repository.save(any(Categoria.class))).thenAnswer(inv -> inv.getArgument(0));

        Categoria resultado = service.criar(new CategoriaRequest("Bebidas"));

        assertEquals("Bebidas", resultado.getNome());
        verify(repository).save(any(Categoria.class));
    }

    @Test
    void buscar_categoriaInexistente_lancaExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.buscar(99L));
    }

    @Test
    void atualizar_alteraONomeDaCategoria() {
        Categoria existente = new Categoria("Antigo");
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Categoria.class))).thenAnswer(inv -> inv.getArgument(0));

        Categoria resultado = service.atualizar(1L, new CategoriaRequest("Novo"));

        assertEquals("Novo", resultado.getNome());
    }
}
