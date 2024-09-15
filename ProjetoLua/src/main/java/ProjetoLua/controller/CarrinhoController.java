package ProjetoLua.controller;

import ProjetoLua.dto.carrinho.AtualizarCarrinhoDto;
import ProjetoLua.dto.carrinho.DetalhesCarrinhoDto;
import ProjetoLua.dto.historico.AtualizarHistoricoDto;
import ProjetoLua.dto.historico.DetalhesHistoricoDto;
import ProjetoLua.dto.pagamento.DetalhesPagamentoDto;
import ProjetoLua.repository.CarrinhoRepository;
import ProjetoLua.repository.UsuarioRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("carrinho")
@Tag(name = "carrinho", description = "Operações relacionadas ao carrinho, como GET e PUT")
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("{id}")
    public ResponseEntity<DetalhesCarrinhoDto> buscarPorId(@PathVariable("id") Long id) {
        try {
            var carrinho = carrinhoRepository.getReferenceById(id);
            return ResponseEntity.ok(new DetalhesCarrinhoDto(carrinho));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualização do Carrinho",
            description = "Atualiza o Carrinho do Usuario")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Atualização feita com Sucesso", content =
    @Content(schema = @Schema(implementation = DetalhesCarrinhoDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Não Autorizado ou Token Inválido", content =
                    { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @Transactional
    public ResponseEntity<DetalhesCarrinhoDto> atualizarCarrinho(@PathVariable("id") Long id, @RequestBody @Valid AtualizarCarrinhoDto carrinhoDto){
        var carrinho = carrinhoRepository.getReferenceById(id);
        carrinho.atualizarCarrinho(carrinhoDto);
        return ResponseEntity.ok(new DetalhesCarrinhoDto(carrinho));
    }
}
