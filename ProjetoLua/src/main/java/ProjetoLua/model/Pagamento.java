package ProjetoLua.model;

import ProjetoLua.dto.pagamento.CadastrarPagamentoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor

@Entity
@Table(name = "TB_PAGAMENTO")
public class Pagamento {

    @Id
    @GeneratedValue
    @Column(name = "ID_PAGAMENTO")
    private Long id;

    @Column(name = "NUMERO_CARTAO", nullable = false)
    private Long numeroCartao;

    @Column(name = "SENHA_CARTAO", nullable = false)
    private int senha;

    @Column(name = "VALIDADE", nullable = false)
    private String validade;

    @OneToOne(mappedBy = "pagamento", cascade = CascadeType.ALL)
    private Historico historico;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    public Pagamento(CadastrarPagamentoDto pagamentoDto, Usuario usuario){
        numeroCartao = pagamentoDto.numeroCartao();
        senha = pagamentoDto.senha();
        validade = pagamentoDto.validade();
        this.usuario = usuario;
    }
}
