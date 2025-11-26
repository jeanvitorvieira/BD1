package com.projeto.BD1.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @ManyToOne
    @JoinColumn(name = "cod_cliente", referencedColumnName = "codigo", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "cod_produto", referencedColumnName = "codigo", nullable = false)
    private Produto produto;

    @Column(name = "valor_pedido", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContasReceber> contasReceber;

}