package ProjetoLua.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor

@Entity
@Table(name = "ID_PRODUTO")
public class Produto {

    @Id
    @GeneratedValue
    @Column(name = "ID_PRODUTO")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 20)
    private String nome;

    @Column(name = "VALOR", nullable = false)
    private double valor;

    @Column(name = "DESCRICAO")
    private String descricao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_HISTORICO")
    private Historico historico;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CARRINHO")
    private Carrinho carrinho;
}
