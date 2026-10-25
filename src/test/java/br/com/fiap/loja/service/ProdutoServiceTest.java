package br.com.fiap.loja.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.loja.dto.ProdutoRequest;
import br.com.fiap.loja.exception.RecursoNaoEncontradoException;
import br.com.fiap.loja.model.Categoria;
import br.com.fiap.loja.model.Produto;
import br.com.fiap.loja.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProdutoService service;

    @Test
    void criar_salvaProdutoVinculadoACategoria() {
        Categoria categoria = new Categoria("Eletronicos");
        when(categoriaService.buscar(1L)).thenReturn(categoria);
        when(produtoRepository.save(any(Produto.class))).thenAnswer(inv -> inv.getArgument(0));

        Produto resultado = service.criar(new ProdutoRequest("Notebook", new BigDecimal("3500.00"), 1L));

        assertEquals("Notebook", resultado.getNome());
        assertEquals(new BigDecimal("3500.00"), resultado.getPreco());
        assertSame(categoria, resultado.getCategoria());
    }

    @Test
    void criar_categoriaInexistente_naoSalvaProduto() {
        when(categoriaService.buscar(99L)).thenThrow(new RecursoNaoEncontradoException("Categoria nao encontrada: 99"));

        ProdutoRequest request = new ProdutoRequest("Mouse", new BigDecimal("50.00"), 99L);

        assertThrows(RecursoNaoEncontradoException.class, () -> service.criar(request));
        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void buscar_produtoInexistente_lancaExcecao() {
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.buscar(99L));
    }

    @Test
    void excluir_removeOProdutoExistente() {
        Produto produto = new Produto("Teclado", new BigDecimal("120.00"), new Categoria("Perifericos"));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        service.excluir(1L);

        verify(produtoRepository).delete(produto);
    }
}
