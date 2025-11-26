package com.projeto.BD1.repository;

import com.projeto.BD1.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByCpf(String cpf);
}
