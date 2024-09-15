package ProjetoLua.model;


import ProjetoLua.dto.produto.AtualizarProdutoDto;
import ProjetoLua.dto.produto.CadastrarProdutoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @ManyToMany(mappedBy = "produtos",cascade = CascadeType.ALL)
    private List<Historico> historicos;

    public void adicionarHistorico(Historico historico){
        historicos.add(historico);
    }

    public Produto(CadastrarProdutoDto produtoDto){
        nome = produtoDto.nome();
        valor = produtoDto.valor();
        descricao = produtoDto.descricao();
    }

    public void atualizarProduto(AtualizarProdutoDto produtoDto){
        nome = produtoDto.nome();
        valor = produtoDto.valor();
        descricao = produtoDto.descricao();
    }

    public void adicionarHistProd(Historico historico){
        historicos.add(historico);
    }
}
