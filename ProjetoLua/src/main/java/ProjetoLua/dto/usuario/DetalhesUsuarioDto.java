package ProjetoLua.dto.usuario;

import ProjetoLua.model.Usuario;

public record DetalhesUsuarioDto(String nome, String login) {
    public DetalhesUsuarioDto(Usuario usuario){
        this(usuario.getNome(), usuario.getLogin());
    }
}
