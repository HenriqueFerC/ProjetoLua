package ProjetoLua.dto.produto;

import ProjetoLua.model.Produto;

public record ListagemProdutoDto(String nome, double valor, String descricao) {
    public ListagemProdutoDto(Produto produto){
        this(produto.getNome(), produto.getValor(), produto.getDescricao());
    }

}
