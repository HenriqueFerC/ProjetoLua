package ProjetoLua.controller;

import ProjetoLua.dto.historico.AtualizarHistoricoDto;
import ProjetoLua.dto.historico.DetalhesHistoricoDto;
import ProjetoLua.dto.pagamento.DetalhesPagamentoDto;
import ProjetoLua.dto.pagamento.ListagemPagamentoDto;
import ProjetoLua.dto.produto.DetalhesProdutoDto;
import ProjetoLua.dto.usuario.DetalhesUsuarioDto;
import ProjetoLua.repository.HistoricoRepository;
import ProjetoLua.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("historico")
@Tag(name = "historico", description = "Operações relacionadas ao historico, como GET e PUT")
public class HistoricoController {

    @Autowired
    private HistoricoRepository historicoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping("{idHistorico}/produto/{idProduto}")
    @Operation(summary = "Cadastro de Historico relacionado a Pedido",
            description = "Cadastra um id do historico com um id de pedido")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Cadastro com Sucesso", content =
    @Content(schema = @Schema(implementation = DetalhesHistoricoDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Não Autorizado ou Token Inválido", content =
                    { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (id do historico ou id do produto)")})
    @Transactional
    public ResponseEntity<DetalhesHistoricoDto> casastrarFuncCliente(@PathVariable("idHistorico") Long idHistorico, @PathVariable("idProduto") Long idProduto, UriComponentsBuilder uriBuilder){
        var historico = historicoRepository.getReferenceById(idHistorico);
        var produto = produtoRepository.getReferenceById(idProduto);
        historico.adicionarProdHist(produto);
        produto.adicionarHistProd(historico);
        var url = uriBuilder.path("{idHistorico}/produto/{idProduto}").buildAndExpand(historico.getId(), produto.getId()).toUri();
        return ResponseEntity.created(url).body(new DetalhesHistoricoDto(historico));
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesHistoricoDto> buscarPorId(@PathVariable("id") Long id) {
        try {
            var historico = historicoRepository.getReferenceById(id);
            return ResponseEntity.ok(new DetalhesHistoricoDto(historico));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("por-data-entre")
    public ResponseEntity<Page<DetalhesHistoricoDto>> buscarPorData(@RequestParam("dataInicial") LocalDateTime dataInicial, @RequestParam("dataFinal")LocalDateTime dataFinal, Pageable pageable){
        var historico = historicoRepository.findByDataBetween(dataInicial, dataFinal, pageable).map(DetalhesHistoricoDto::new);
        return ResponseEntity.ok(historico);
    }


    @PutMapping("{id}")
    @Operation(summary = "Atualização do Historico",
            description = "Atualiza o Historico do Usuario")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Atualização feita com Sucesso", content =
    @Content(schema = @Schema(implementation = DetalhesHistoricoDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Não Autorizado ou Token Inválido", content =
                    { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @Transactional
    public ResponseEntity<DetalhesHistoricoDto> atualizarHistorico(@PathVariable("id") Long id, @RequestBody @Valid AtualizarHistoricoDto atualizarHistoricoDto){
        var historico = historicoRepository.getReferenceById(id);
        historico.atualizarHistorico(atualizarHistoricoDto);
        return ResponseEntity.ok(new DetalhesHistoricoDto(historico));
    }

}
