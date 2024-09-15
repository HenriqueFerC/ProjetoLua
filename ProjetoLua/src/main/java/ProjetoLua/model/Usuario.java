package ProjetoLua.model;

import ProjetoLua.dto.usuario.AtualizarUsuarioDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @Column(name = "CPF", nullable = false, length = 14)
    private String cpf;

    @Column(name = "SENHA", nullable = false, length = 15)
    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CARRINHO")
    private Carrinho carrinho;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_HISTORICO")
    private Historico historico;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos;

    public void adicionarPagamento(Pagamento pagamento){
        pagamentos.add(pagamento);
    }

    public void removerPagamento(Pagamento pagamento){
        pagamentos.remove(pagamento);
    }

    public Usuario(String nome, String login, String cpf, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.cpf = cpf;
    }

    public void atualizarUsuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }
}
