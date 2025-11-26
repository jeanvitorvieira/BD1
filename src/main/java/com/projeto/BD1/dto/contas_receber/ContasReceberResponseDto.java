package com.projeto.BD1.dto.contas_receber;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContasReceberResponseDto(
        Integer codigo,
        Integer codCliente,
        Integer codPedido,
        BigDecimal valorReceber,
        LocalDate dataVencimento,
        BigDecimal valorPedido
) { }