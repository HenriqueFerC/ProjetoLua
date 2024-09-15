package ProjetoLua.model;

import ProjetoLua.dto.carrinho.AtualizarCarrinhoDto;
import ProjetoLua.dto.carrinho.CadastrarCarrinhoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter
@NoArgsConstructor

@Entity
@Table(name = "TB_CARRINHO")
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

    public void atualizarCarrinho(AtualizarCarrinhoDto carrinhoDto){
        quantidade = carrinhoDto.quantidade();
        valor = carrinhoDto.valor();
    }

    public Carrinho(CadastrarCarrinhoDto carrinhoDto, Usuario usuario){
        quantidade = carrinhoDto.quantidade();
        valor = carrinhoDto.valor();
        this.usuario = usuario;
    }

}
