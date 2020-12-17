package br.com.caelum.tarefas.seguranca;

import java.util.Date;

import br.com.caelum.tarefas.modelo.Usuario;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GerenciadorDeToken {

	private static final String CHAVE_SECRETA = "segredo-secreto";

	public String geraToken(Usuario usuario) {
		return Jwts.builder().setIssuer("Alura F�rum API").setSubject(usuario.getLogin()).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, CHAVE_SECRETA).compact();
	}

	public boolean isValido(String jwt) {
		try {
			Jwts.parser()
				.setSigningKey(CHAVE_SECRETA)
				.parseClaimsJws(jwt);
			
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

}
