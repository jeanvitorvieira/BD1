package com.projeto.BD1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "cliente")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(name = "cpf", nullable = false, length = 14)
    private String cpf;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "telefone", nullable = false)
    private Integer telefone;

    @Column(name = "endereco", nullable = false, length = 50)
    private String endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContasReceber> contasReceber;
}
