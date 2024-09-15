package ProjetoLua.dto.historico;

import jakarta.validation.constraints.NotNull;

public record CadastrarHistoricoDto(
        @NotNull(message = "Valor Total não pode ser nulo!")
        double valorTotal

) {
}
