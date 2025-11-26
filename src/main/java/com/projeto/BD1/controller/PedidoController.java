package com.projeto.BD1.controller;

import com.projeto.BD1.dto.pedido.PedidoCreateDto;
import com.projeto.BD1.dto.pedido.PedidoResponseDto;
import com.projeto.BD1.exception.ResourceNotFoundException;
import com.projeto.BD1.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> buscarTodos() {
        List<PedidoResponseDto> pedidos = pedidoService.buscarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> buscarPorId(@PathVariable Integer id) {
        PedidoResponseDto dto = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDto> criar(@RequestBody PedidoCreateDto pedido) {
        PedidoResponseDto pedidoSalvo = pedidoService.salvarPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> atualizar(
            @PathVariable Integer id,
            @RequestBody PedidoCreateDto pedido
    ) {
        PedidoResponseDto pedidoAtualizado = pedidoService.atualizarValorPedido(id, pedido);
        if (pedidoAtualizado != null) {
            return ResponseEntity.ok(pedidoAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        if (pedidoService.deletarPedido(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}