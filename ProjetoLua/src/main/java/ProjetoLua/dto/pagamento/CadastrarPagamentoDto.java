package ProjetoLua.dto.pagamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastrarPagamentoDto(
        @NotNull(message = "Número do cartão não pode ser nulo!")
        @Size(min = 16, max = 16, message = "Numero da Cartão precisa ter 16 dígitos!")
        Long numeroCartao,
        @NotNull(message = "Senha do cartão não pode ser nula!")
        @Size(min = 4, max = 4, message = "Senha do cartão precisa ter 4 dígitos!")
        int senha,
        String validade
) {
}
