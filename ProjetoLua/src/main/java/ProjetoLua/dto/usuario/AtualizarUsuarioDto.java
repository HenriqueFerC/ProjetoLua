package ProjetoLua.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizarUsuarioDto(
        @NotBlank(message = "Nome não pode estar em branco!")
        @Size(min = 3, max = 20, message = "Nome precisa ter no mínimo 3 e no máximo 20 letras!")
        String nome,
        @NotBlank(message = "Senha não pode estar em branco!")
        @Size(min = 8, max = 15, message = "Senha precisa ter no mínimo 8 e no máximo 15 caractéres!")
        String senha
) {

}
