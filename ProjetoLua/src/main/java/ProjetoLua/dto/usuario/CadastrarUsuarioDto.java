package ProjetoLua.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastrarUsuarioDto(
        @NotBlank(message = "Nome não pode estar em branco!")
        @Size(min = 3, max = 20, message = "Nome precisa ter no mínimo 3 e no máximo 20 letras!")
        String nome,
        @NotBlank(message = "Login não pode estar em branco!")
        @Size(min = 8, max = 30, message = "Login precisa ter no mínimo 8 e no máximo 30 caractéres!")
        String login,
        @NotBlank(message = "Senha não pode estar em branco!")
        @Size(min = 8, max = 15, message = "Senha precisa ter no mínimo 8 e no máximo 15 caractéres!")
        String senha,
        @NotBlank(message = "CPF não pode estar em branco!")
        @Size(min = 14, max = 14, message = "CPF precisa ter 14 caractéres!")
        String cpf
) {
}
