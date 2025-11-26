package com.projeto.BD1.controller;

import com.projeto.BD1.dto.contas_receber.ContasReceberCreateDto;
import com.projeto.BD1.dto.contas_receber.ContasReceberResponseDto;
import com.projeto.BD1.exception.ResourceNotFoundException;
import com.projeto.BD1.service.ContasReceberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contasreceber")
public class ContasReceberController {

    @Autowired
    private ContasReceberService contasReceberService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<ContasReceberResponseDto>> buscarTodos() {
        List<ContasReceberResponseDto> contas = contasReceberService.buscarTodos();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ContasReceberResponseDto> buscarPorCodigo(@PathVariable Integer codigo) {
        ContasReceberResponseDto dto = contasReceberService.buscarPorId(codigo);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ContasReceberResponseDto> criar(@RequestBody ContasReceberCreateDto conta) {
        ContasReceberResponseDto contaSalva = contasReceberService.salvar(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaSalva);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluir(@PathVariable Integer codigo) {
        if (contasReceberService.deletar(codigo)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}