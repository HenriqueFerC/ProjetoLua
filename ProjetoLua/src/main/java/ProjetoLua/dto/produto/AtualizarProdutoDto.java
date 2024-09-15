package ProjetoLua.dto.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AtualizarProdutoDto(
        @NotBlank(message = "Nome do produto não pode estar em branco!")
        @Size(min = 3, max = 20, message = "Nome precisa ter no mínimo 3 e no máximo 20 letras!")
        String nome,
        @NotNull(message = "Valor não pode ser nulo!")
        double valor,
        String descricao
) {
}
