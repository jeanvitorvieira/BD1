package com.projeto.BD1.service;

import com.projeto.BD1.dto.produto.ProdutoCreateDto;
import com.projeto.BD1.dto.produto.ProdutoResponseDto;
import com.projeto.BD1.dto.produto.ProdutoUpdateDto;
import com.projeto.BD1.exception.ConflictException;
import com.projeto.BD1.exception.ResourceNotFoundException;
import com.projeto.BD1.model.Produto;
import com.projeto.BD1.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private ProdutoResponseDto toResponseDto(Produto produto) {
        return new ProdutoResponseDto(
                produto.getCodigo(),
                produto.getNome(),
                produto.getValor(),
                produto.getQuantidade(),
                produto.getDescricao()
        );
    }

    public ProdutoResponseDto salvarProduto(ProdutoCreateDto produtoDto) {
        if (produtoRepository.existsByNome(produtoDto.nome())) {
            throw new ConflictException("Já existe um produto com o nome: " + produtoDto.nome());
        }

        Produto produto = new Produto();
        produto.setNome(produtoDto.nome());
        produto.setValor(produtoDto.valor());
        produto.setQuantidade(produtoDto.quantidade());
        produto.setDescricao(produtoDto.descricao());

        Produto produtoSalvo = produtoRepository.save(produto);
        return toResponseDto(produtoSalvo);
    }

    public List<ProdutoResponseDto> buscarTodos() {
        return produtoRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public ProdutoResponseDto buscarProdutoPorId(Integer id) {
        return produtoRepository.findById(id)
                .map(this::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + id));
    }

    @Transactional
    public ProdutoResponseDto atualizarProduto(Integer id, ProdutoUpdateDto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produtoExistente -> {
                    produtoExistente.setNome(produtoAtualizado.nome());
                    produtoExistente.setValor(produtoAtualizado.valor());
                    produtoExistente.setQuantidade(produtoAtualizado.quantidade());
                    produtoExistente.setDescricao(produtoAtualizado.descricao());

                    Produto salvo = produtoRepository.save(produtoExistente);
                    return toResponseDto(salvo);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + id));
    }

    @Transactional
    public boolean deletarProduto(Integer id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}