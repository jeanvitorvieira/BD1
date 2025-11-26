package com.projeto.BD1.dto.pedido;

import java.math.BigDecimal;

public record PedidoResponseDto(
        Integer codigo,
        Integer codCliente,
        Integer codProduto,
        BigDecimal valorPedido
) { }