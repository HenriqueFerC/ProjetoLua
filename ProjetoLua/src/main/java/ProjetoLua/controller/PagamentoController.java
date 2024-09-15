package ProjetoLua.controller;

import ProjetoLua.dto.pagamento.DetalhesPagamentoDto;
import ProjetoLua.dto.pagamento.ListagemPagamentoDto;
import ProjetoLua.dto.produto.DetalhesProdutoDto;
import ProjetoLua.dto.produto.ListagemProdutoDto;
import ProjetoLua.repository.PagamentoRepository;
import ProjetoLua.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pagamento")
@Tag(name = "pagamento", description = "Operações relacionadas ao pagamento, como GET e DELETE")
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<ListagemPagamentoDto>> listarPagamento(Pageable pageable){
        var lista = pagamentoRepository.findAll(pageable).stream().map(ListagemPagamentoDto::new).toList();
        if(lista.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesPagamentoDto> buscarPorId(@PathVariable("id") Long id) {
        try {
            var pagamento = pagamentoRepository.getReferenceById(id);
            return ResponseEntity.ok(new DetalhesPagamentoDto(pagamento));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}/usuario/{idUsuario}")
    @Operation(summary = "Deleta Forma de Pagamento",
            description = "Deleta a Forma de Pagamento associada ao Usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Forma de Pagamento deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado ou Token Inválido"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (id ou id do usuario)")
    })
    @Transactional
    public ResponseEntity<Void> deletarPagamento(@PathVariable("id") Long id, @PathVariable("idUsuario") Long idUsuario){
        try {
            var usuario = usuarioRepository.getReferenceById(id);
            var pagamento = pagamentoRepository.getReferenceById(id);
            usuario.removerPagamento(pagamento);
            pagamentoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }
}
