package com.projeto.BD1.dto.cliente;


public record ClienteResponseDto(
        Integer codigo,
        String cpf,
        String nome,
        String telefone,
        String endereco
) { }