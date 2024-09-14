package ProjetoLua.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Column(name = "DATA", nullable = false)
    private LocalDateTime data;

    @OneToOne(mappedBy = "historico", cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PAGAMENTO")
    private Pagamento pagamento;

    @OneToMany(mappedBy = "historico",cascade = CascadeType.ALL)
    private List<Produto> produtos;

}
