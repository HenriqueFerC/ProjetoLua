package ProjetoLua.controller;

import ProjetoLua.dto.produto.AtualizarProdutoDto;
import ProjetoLua.dto.produto.CadastrarProdutoDto;
import ProjetoLua.dto.produto.DetalhesProdutoDto;
import ProjetoLua.dto.produto.ListagemProdutoDto;
import ProjetoLua.dto.usuario.CadastrarUsuarioDto;
import ProjetoLua.dto.usuario.DetalhesUsuarioDto;
import ProjetoLua.model.Produto;
import ProjetoLua.model.Usuario;
import ProjetoLua.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("produto")
@Tag(name = "Produto", description = "Operações relacionadas aos produtos da loja")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping("registrar")
    @Operation(summary = "Cadastro de Produto",
            description = "Cadastra a Produto no Sistema da Loja")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com Sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @Transactional
    public ResponseEntity<DetalhesProdutoDto> cadastrarProduto(@RequestBody @Valid CadastrarProdutoDto produtoDto, UriComponentsBuilder uriBuilder){
        var produto = new Produto(produtoDto);
        produtoRepository.save(produto);
        var url = uriBuilder.path("produto/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(url).body(new DetalhesProdutoDto(produto));
    }

    @GetMapping
    public ResponseEntity<List<ListagemProdutoDto>> listarProduto(Pageable pageable){
        var lista = produtoRepository.findAll(pageable).stream().map(ListagemProdutoDto::new).toList();
        if(lista.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesProdutoDto> buscarPorId(@PathVariable("id") Long id){
        try {
            var produto = produtoRepository.getReferenceById(id);
            return ResponseEntity.ok(new DetalhesProdutoDto(produto));
        } catch (EmptyResultDataAccessException  e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("por-valor-maior")
    public ResponseEntity<Page<DetalhesProdutoDto>> buscarPorValorMaior(@RequestParam("valor") double valor, Pageable pageable){
        var produtos = produtoRepository.findByValorGreaterThan(valor, pageable).map(DetalhesProdutoDto::new);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("por-valor-menor")
    public ResponseEntity<Page<DetalhesProdutoDto>> buscarPorValorMenor(@RequestParam("valor") double valor, Pageable pageable){
        var produtos = produtoRepository.findByValorLessThan(valor, pageable).map(DetalhesProdutoDto::new);
        return ResponseEntity.ok(produtos);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Remoção de Produto",
            description = "Deleta o Produto do sistema da Loja")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado ou Token Inválido"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (id)")
    })
    @Transactional
    public ResponseEntity<Void> deletarProduto(@PathVariable("id") Long id){
        try {
            produtoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DetalhesProdutoDto> atualizarProduto(@PathVariable("id") Long id, @RequestBody @Valid AtualizarProdutoDto produtoDto){
        var produto = produtoRepository.getReferenceById(id);
        produto.atualizarProduto(produtoDto);
        return ResponseEntity.ok(new DetalhesProdutoDto(produto));
    }
}
