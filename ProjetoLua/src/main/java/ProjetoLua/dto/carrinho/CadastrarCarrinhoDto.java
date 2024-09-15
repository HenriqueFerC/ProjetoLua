package ProjetoLua.dto.carrinho;

import jakarta.validation.constraints.NotNull;

public record CadastrarCarrinhoDto(
        @NotNull(message = "Quantidade não pode ser nulo!")
        int quantidade,
        @NotNull(message = "Valor não pode ser nulo!")
        double valor
) {
}
