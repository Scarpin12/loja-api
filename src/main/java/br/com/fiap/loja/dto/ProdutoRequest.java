package br.com.fiap.loja.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProdutoRequest(
        @NotBlank(message = "nome e obrigatorio")
        @Size(max = 150, message = "nome deve ter no maximo 150 caracteres")
        String nome,

        @NotNull(message = "preco e obrigatorio")
        @Positive(message = "preco deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "categoriaId e obrigatorio")
        Long categoriaId) {
}
