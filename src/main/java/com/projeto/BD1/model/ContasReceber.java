package com.projeto.BD1.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contas_receber")
@Data
public class ContasReceber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "valor_receber", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorReceber;

    @ManyToOne
    @JoinColumn(name = "cod_pedido", referencedColumnName = "codigo", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "cod_cliente", referencedColumnName = "codigo", nullable = false)
    private Cliente cliente;

    @Column(name = "valor_pedido", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPedido;
}