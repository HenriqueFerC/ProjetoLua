package ProjetoLua.dto.carrinho;

import ProjetoLua.dto.produto.DetalhesProdutoDto;
import ProjetoLua.model.Carrinho;

import java.util.List;
import java.util.stream.Collectors;

public record DetalhesCarrinhoDto(int quantidade, double valor) {
    public DetalhesCarrinhoDto(Carrinho carrinho){
        this(carrinho.getQuantidade(), carrinho.getValor());
    }
}
