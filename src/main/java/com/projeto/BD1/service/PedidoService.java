package com.projeto.BD1.service;

import com.projeto.BD1.dto.pedido.PedidoCreateDto;
import com.projeto.BD1.dto.pedido.PedidoResponseDto;
import com.projeto.BD1.exception.ResourceNotFoundException;
import com.projeto.BD1.model.Cliente;
import com.projeto.BD1.model.Pedido;
import com.projeto.BD1.model.Produto;
import com.projeto.BD1.repository.ClienteRepository;
import com.projeto.BD1.repository.PedidoRepository;
import com.projeto.BD1.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    private PedidoResponseDto toResponseDto(Pedido pedido) {
        return new PedidoResponseDto(
                pedido.getCodigo(),
                pedido.getCliente().getCodigo(),
                pedido.getProduto().getCodigo(),
                pedido.getValorPedido()
        );
    }

    @Transactional
    public PedidoResponseDto salvarPedido(PedidoCreateDto pedidoDto) {

        Cliente cliente = clienteRepository.findById(pedidoDto.codCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + pedidoDto.codCliente()));

        Produto produto = produtoRepository.findById(pedidoDto.codProduto())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + pedidoDto.codProduto()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        pedido.setValorPedido(pedidoDto.valorPedido());

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return toResponseDto(pedidoSalvo);
    }

    public List<PedidoResponseDto> buscarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public PedidoResponseDto buscarPedidoPorId(Integer id) {
        return pedidoRepository.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));
    }

    @Transactional
    public PedidoResponseDto atualizarValorPedido(Integer id, PedidoCreateDto pedidoAtualizado) {
        return pedidoRepository.findById(id)
                .map(pedidoExistente -> {
                    pedidoExistente.setValorPedido(pedidoAtualizado.valorPedido());

                    Pedido salvo = pedidoRepository.save(pedidoExistente);
                    return toResponseDto(salvo);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado para atualização com o id: " + id));
    }

    @Transactional
    public boolean deletarPedido(Integer id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}