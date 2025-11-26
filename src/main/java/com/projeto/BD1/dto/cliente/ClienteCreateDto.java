package com.projeto.BD1.dto.cliente;

public record ClienteCreateDto (
        String cpf,
        String nome,
        Integer telefone,
        String endereco
){
}
