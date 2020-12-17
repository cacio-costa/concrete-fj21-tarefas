package br.com.caelum.tarefas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.caelum.tarefas.dao.JdbcUsuarioDao;
import br.com.caelum.tarefas.modelo.Usuario;
import br.com.caelum.tarefas.seguranca.GerenciadorDeToken;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(Usuario usuario) {
		if (new JdbcUsuarioDao().existeUsuario(usuario)) {
			GerenciadorDeToken gerenciadorDeToken = new GerenciadorDeToken();
			String token = gerenciadorDeToken.geraToken(usuario);
			
			return ResponseEntity.ok(token); 
		}
		
		return ResponseEntity.badRequest().build();
	}

}
