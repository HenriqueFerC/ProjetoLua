package ProjetoLua.controller;

import ProjetoLua.dto.carrinho.CadastrarCarrinhoDto;
import ProjetoLua.dto.carrinho.DetalhesCarrinhoDto;
import ProjetoLua.dto.historico.CadastrarHistoricoDto;
import ProjetoLua.dto.historico.DetalhesHistoricoDto;
import ProjetoLua.dto.pagamento.CadastrarPagamentoDto;
import ProjetoLua.dto.pagamento.DetalhesPagamentoDto;
import ProjetoLua.dto.produto.CadastrarProdutoDto;
import ProjetoLua.dto.produto.DetalhesProdutoDto;
import ProjetoLua.dto.usuario.AtualizarUsuarioDto;
import ProjetoLua.dto.usuario.CadastrarUsuarioDto;
import ProjetoLua.dto.usuario.DetalhesUsuarioDto;
import ProjetoLua.model.*;
import ProjetoLua.repository.*;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("usuario")
@Tag(name = "Usuario", description = "Operações relacionadas ao Usuario, através do Usuario, cadastrar Pagamento, Historico, Carrinho")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HistoricoRepository historicoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("registrar")
    @Operation(summary = "Cadastro de Usuario",
            description = "Cadastra o Usuario no Sistema")
    @Transactional
    public ResponseEntity<DetalhesUsuarioDto> cadastrarUsuario(@RequestBody @Valid CadastrarUsuarioDto usuarioDto, UriComponentsBuilder uriBuilder){
        var usuario = new Usuario(usuarioDto.nome(), usuarioDto.login(), usuarioDto.cpf(), passwordEncoder.encode(usuarioDto.senha()));
        usuarioRepository.save(usuario);
        var url = uriBuilder.path("usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(new DetalhesUsuarioDto(usuario));
    }

    @PostMapping("{idusuario}/historicos")
    @Operation(summary = "Cadastro do Historico",
            description = "Cadastra o Historico do Usuario")

    @Transactional
    public ResponseEntity<DetalhesHistoricoDto> cadastrarHistorico(@PathVariable("idUsuario") Long id, @RequestBody @Valid CadastrarHistoricoDto historicoDto, UriComponentsBuilder uriBuilder){
        var usuario = usuarioRepository.getReferenceById(id);
        var historico = new Historico(historicoDto, usuario);
        historicoRepository.save(historico);
        var url = uriBuilder.path("historico/{id}").buildAndExpand(historico.getId()).toUri();
        return ResponseEntity.created(url).body(new DetalhesHistoricoDto(historico));
    }

    @PostMapping("{idusuario}/pagamentos")
    @Operation(summary = "Cadastro de Pagamento",
            description = "Cadastra a Forma de Pagamento do Usuario")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Cadastro com Sucesso", content =
    @Content(schema = @Schema(implementation = DetalhesPagamentoDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Não Autorizado ou Token Inválido", content =
                    { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @Transactional
    public ResponseEntity<DetalhesPagamentoDto> cadastrarPagamento(@PathVariable("idUsuario") Long id, @RequestBody @Valid CadastrarPagamentoDto pagamentoDto, UriComponentsBuilder uriBuilder){
        var usuario = usuarioRepository.getReferenceById(id);
        var pagamento = new Pagamento(pagamentoDto, usuario);
        usuario.adicionarPagamento(pagamento);
        pagamentoRepository.save(pagamento);
        var url = uriBuilder.path("pagamento/{id}").buildAndExpand(pagamento.getId()).toUri();
        return ResponseEntity.created(url).body(new DetalhesPagamentoDto(pagamento));
    }

    @PostMapping("{idUsuario}/carrinhos")
    @Operation(summary = "Cadastro de Carrinho",
            description = "Cadastra um Carrinho do Usuario")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Cadastro com Sucesso", content =
    @Content(schema = @Schema(implementation = DetalhesCarrinhoDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Não Autorizado ou Token Inválido", content =
                    { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @Transactional
    public ResponseEntity<DetalhesCarrinhoDto> cadastrarCarrinho(@PathVariable("idUsuario") Long id, @RequestBody @Valid CadastrarCarrinhoDto carrinhoDto, UriComponentsBuilder uriBuilder){
        var usuario = usuarioRepository.getReferenceById(id);
        var carrinho = new Carrinho(carrinhoDto, usuario);
        carrinhoRepository.save(carrinho);
        var url = uriBuilder.path("carrinho/{id}").buildAndExpand(carrinho.getId()).toUri();
        return ResponseEntity.created(url).body(new DetalhesCarrinhoDto(carrinho));
    }

    @PutMapping("atualizar/{id}")
    @Operation(summary = "Atualizar o Usuario",
            description = "Atualiza os dados do Usuario")
            @ApiResponses({@ApiResponse(responseCode = "200", description = "Atualização feita com Sucesso", content =
    @Content(schema = @Schema(implementation = DetalhesUsuarioDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Não Autorizado ou Token Inválido", content =
                    { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")})
    @Transactional
    public ResponseEntity<DetalhesUsuarioDto> atualizarUsuario(@PathVariable("id") Long id, @RequestBody @Valid AtualizarUsuarioDto usuarioDto) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.atualizarUsuario(usuarioDto.nome(), passwordEncoder.encode(usuarioDto.senha()));
        return ResponseEntity.ok(new DetalhesUsuarioDto(usuario));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar o Usuario",
            description = "Deleta o Usuario do Sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado ou Token Inválido"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (id)")
    })
    @Transactional
    public ResponseEntity<Void> deletarUsuario(@PathVariable("id") Long id){
        try {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<DetalhesUsuarioDto> buscarPorId(@PathVariable("id") Long id){
        try {
            var usuario = usuarioRepository.getReferenceById(id);
            return ResponseEntity.ok(new DetalhesUsuarioDto(usuario));
        } catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }

}
