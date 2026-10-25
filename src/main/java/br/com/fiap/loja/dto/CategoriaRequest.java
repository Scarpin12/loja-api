package br.com.fiap.loja.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
        @NotBlank(message = "nome e obrigatorio")
        @Size(max = 100, message = "nome deve ter no maximo 100 caracteres")
        String nome) {
}
