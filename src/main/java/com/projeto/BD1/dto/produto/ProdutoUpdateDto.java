package com.projeto.BD1.dto.produto;

import java.math.BigDecimal;

public record ProdutoUpdateDto (
        String nome,
        BigDecimal valor,
        Integer quantidade,
        String descricao
) { }