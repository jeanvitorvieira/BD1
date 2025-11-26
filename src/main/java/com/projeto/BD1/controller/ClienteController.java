package com.projeto.BD1.controller;

import com.projeto.BD1.dto.cliente.ClienteCreateDto;
import com.projeto.BD1.dto.cliente.ClienteResponseDto;
import com.projeto.BD1.dto.cliente.ClienteUpdateDto;
import com.projeto.BD1.exception.ResourceNotFoundException;
import com.projeto.BD1.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> buscarTodos() {
        List<ClienteResponseDto> clientes = clienteService.buscarTodos();
        return ResponseEntity.ok(clientes);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> buscarPorId(@PathVariable Integer id) {
        ClienteResponseDto dto = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> criar(@RequestBody ClienteCreateDto cliente) {
        ClienteResponseDto clienteSalvo = clienteService.salvarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> atualizar(
            @PathVariable Integer id,
            @RequestBody ClienteUpdateDto cliente
    ) {
        ClienteResponseDto clienteAtualizado = clienteService.atualizarCliente(id, cliente);
        if (clienteAtualizado != null) {
            return ResponseEntity.ok(clienteAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        if (clienteService.deletarCliente(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}