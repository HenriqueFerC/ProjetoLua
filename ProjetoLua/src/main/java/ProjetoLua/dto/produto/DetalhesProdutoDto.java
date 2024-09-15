package ProjetoLua.dto.produto;

import ProjetoLua.model.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DetalhesProdutoDto(String nome, double valor, String descricao) {
    public DetalhesProdutoDto(Produto produto){
        this(produto.getNome(), produto.getValor(), produto.getDescricao());
    }
}
