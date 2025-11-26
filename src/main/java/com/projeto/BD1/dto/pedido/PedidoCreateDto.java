package com.projeto.BD1.dto.pedido;

import java.math.BigDecimal;

public record PedidoCreateDto (
        Integer codCliente,
        Integer codProduto,
        BigDecimal valorPedido
) { }