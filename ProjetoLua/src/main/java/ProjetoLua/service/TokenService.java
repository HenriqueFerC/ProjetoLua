package ProjetoLua.service;

import ProjetoLua.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    @Value("${api.token.secret}")
    private String senha;

    public String getSubject(String tokenJwt){
        try {
            Algorithm algorithm = Algorithm.HMAC256(senha);
            return JWT.require(algorithm).withIssuer("FIAP").build().verify(tokenJwt).getSubject();
        } catch(JWTVerificationException e){
            throw new RuntimeException("Não foi possível validar o token JWT");
        }
    }

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(senha);
            return JWT.create()
                    .withIssuer("FIAP")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(1)))
                    .sign(algorithm);
        } catch (JWTCreationException e){
            throw new RuntimeException("Erro ao criar o token JWT");
        }
    }

}
