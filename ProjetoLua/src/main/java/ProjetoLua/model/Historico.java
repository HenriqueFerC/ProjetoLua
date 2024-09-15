package ProjetoLua.model;

import ProjetoLua.dto.historico.AtualizarHistoricoDto;
import ProjetoLua.dto.historico.CadastrarHistoricoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter@Setter
@NoArgsConstructor

@Entity
@Table(name = "TB_HISTORICO")
@EntityListeners(AuditingEntityListener.class)
public class Historico {

    @Id
    @GeneratedValue
    @Column(name = "ID_HISTORICO")
    private Long id;

    @Column(name = "VALOR_TOTAL", nullable = false)
    private double valorTotal;

    @CreatedDate
    @Column(name = "DATA", nullable = false)
    private LocalDateTime data;

    @OneToOne(mappedBy = "historico", cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PAGAMENTO")
    private Pagamento pagamento;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="TB_HISTORICO_PRODUTO", joinColumns =@JoinColumn(name="ID_PRODUTO"),
            inverseJoinColumns = @JoinColumn(name="ID_HISTORICO"))
    private List<Produto> produtos;


    public void atualizarHistorico(AtualizarHistoricoDto historicoDto){
        valorTotal = historicoDto.valorTotal();
    }
    public Historico(CadastrarHistoricoDto historicoDto, Usuario usuario) {
        valorTotal = historicoDto.valorTotal();
        this.usuario = usuario;
    }

    public void adicionarProduto(Produto produto){
        produtos.add(produto);
    }

}
