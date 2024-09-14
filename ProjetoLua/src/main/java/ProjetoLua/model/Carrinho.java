package ProjetoLua.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter
@NoArgsConstructor

@Entity
@Table(name = "TB_HISTORICO")
public class Carrinho {

    @Id
    @Column(name = "ID_CARRINHO")
    @GeneratedValue
    private Long id;

    @Column(name = "QUANTIDADE", nullable = false)
    private int quantidade;

    @Column(name = "VALOR", nullable = false)
    private double valor;

    @OneToOne(mappedBy = "carrinho", cascade = CascadeType.ALL)
    private Usuario usuario;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
    private List<Produto> produtos;
}
