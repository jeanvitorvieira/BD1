package com.projeto.BD1.service;

import com.projeto.BD1.dto.contas_receber.ContasReceberCreateDto;
import com.projeto.BD1.dto.contas_receber.ContasReceberResponseDto;
import com.projeto.BD1.exception.ResourceNotFoundException;
import com.projeto.BD1.model.Cliente;
import com.projeto.BD1.model.ContasReceber;
import com.projeto.BD1.model.Pedido;
import com.projeto.BD1.repository.ClienteRepository;
import com.projeto.BD1.repository.ContasReceberRepository;
import com.projeto.BD1.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContasReceberService {

    private final ContasReceberRepository contasReceberRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;

    private ContasReceberResponseDto toResponseDto(ContasReceber conta) {
        return new ContasReceberResponseDto(
                conta.getCodigo(),
                conta.getCliente().getCodigo(),
                conta.getPedido().getCodigo(),
                conta.getValorReceber(),
                conta.getDataVencimento(),
                conta.getValorPedido()
        );
    }
    
    @Transactional
    public ContasReceberResponseDto salvar(ContasReceberCreateDto dto) {

        Cliente cliente = clienteRepository.findById(dto.codCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + dto.codCliente()));

        Pedido pedido = pedidoRepository.findById(dto.codPedido())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + dto.codPedido()));

        ContasReceber novaConta = new ContasReceber();
        novaConta.setCliente(cliente);
        novaConta.setPedido(pedido);
        novaConta.setValorReceber(dto.valorReceber());
        novaConta.setDataVencimento(dto.dataVencimento());
        novaConta.setValorPedido(dto.valorPedido());

        ContasReceber salvo = contasReceberRepository.save(novaConta);

        return toResponseDto(salvo);
    }

    public List<ContasReceberResponseDto> buscarTodos() {
        return contasReceberRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public ContasReceberResponseDto buscarPorId(Integer id) {
        return contasReceberRepository.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Conta a Receber não encontrada com o id: " + id));
    }

    @Transactional
    public boolean deletar(Integer id) {
        if (contasReceberRepository.existsById(id)) {
            contasReceberRepository.deleteById(id);
            return true;
        }
        return false;
    }
}