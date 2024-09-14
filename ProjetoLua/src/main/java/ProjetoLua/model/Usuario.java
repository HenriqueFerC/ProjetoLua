package ProjetoLua.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor

@Entity
@Table(name = "TB_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 20)
    private String nome;

    @Column(name = "LOGIN", nullable = false, length = 30)
    private String login;

    @Column(name = "SENHA", nullable = false, length = 15)
    private String senha;

    @Column(name = "CPF", nullable = false, length = 14)
    private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CARRINHO")
    private Carrinho carrinho;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_HISTORICO")
    private Historico historico;
}
