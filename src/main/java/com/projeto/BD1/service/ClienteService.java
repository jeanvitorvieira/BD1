package com.projeto.BD1.service;

import com.projeto.BD1.dto.cliente.ClienteCreateDto;
import com.projeto.BD1.dto.cliente.ClienteResponseDto;
import com.projeto.BD1.dto.cliente.ClienteUpdateDto;
import com.projeto.BD1.exception.ConflictException;
import com.projeto.BD1.exception.ResourceNotFoundException;
import com.projeto.BD1.model.Cliente;
import com.projeto.BD1.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponseDto salvarCliente(ClienteCreateDto clienteDto) {
        if (clienteRepository.existsByCpf(clienteDto.cpf())) {
            throw new ConflictException("Já existe um cliente com esse CPF");
        }

        Cliente cliente = new Cliente();
        cliente.setCpf(clienteDto.cpf());
        cliente.setNome(clienteDto.nome());
        cliente.setEndereco(clienteDto.endereco());
        cliente.setTelefone(clienteDto.telefone());

        Cliente clienteSalvo = clienteRepository.save(cliente);
        return toResponseDto(clienteSalvo);
    }

    @Transactional
    public ClienteResponseDto atualizarCliente(Integer id, ClienteUpdateDto clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    clienteExistente.setNome(clienteAtualizado.nome());
                    clienteExistente.setTelefone(clienteAtualizado.telefone());
                    clienteExistente.setEndereco(clienteAtualizado.endereco());

                    Cliente salvo = clienteRepository.save(clienteExistente);
                    return toResponseDto(salvo);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + id));
    }

    @Transactional
    public boolean deletarCliente(Integer clienteId) {
        if (clienteRepository.existsById(clienteId)) {
            clienteRepository.deleteById(clienteId);
            return true;
        }
        return false;
    }

    public List<ClienteResponseDto> buscarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public ClienteResponseDto buscarClientePorId(Integer clienteId) {
        return clienteRepository.findById(clienteId)
                .map(this::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    private ClienteResponseDto toResponseDto(Cliente cliente) {
        return new ClienteResponseDto(
                cliente.getCodigo(),
                cliente.getCpf(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEndereco()
        );
    }
}
