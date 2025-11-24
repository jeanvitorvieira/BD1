package com.projeto.BD1.service;

import com.projeto.BD1.model.Cliente;
import com.projeto.BD1.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente atualizarCliente(UUID codigo, Cliente clienteAtualizado) {
        return clienteRepository.findById(codigo)
                .map(clienteExistente -> {
                    clienteExistente.setCpf(clienteAtualizado.getCpf());
                    clienteExistente.setNome(clienteAtualizado.getNome());
                    clienteExistente.setTelefone(clienteAtualizado.getTelefone());
                    clienteExistente.setEndereco(clienteAtualizado.getEndereco());
                    return clienteRepository.save(clienteExistente);
                })
                .orElse(null);
    }

    @Transactional
    public boolean deletarCliente(UUID clienteId) {
        if (clienteRepository.existsById(clienteId)) {
            clienteRepository.deleteById(clienteId);
            return true;
        }
        return false;
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(UUID clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }
}
