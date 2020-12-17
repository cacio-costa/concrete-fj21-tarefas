package br.com.caelum.tarefas.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.caelum.tarefas.seguranca.GerenciadorDeToken;

public class AutenticacaoInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
        if(uri.endsWith("/login")) {
        	return true;
        }

		String jwtToken = pegaTokenDaRequisicao(request);
		if (jwtToken == null) {
			response.sendError(401, "Não encontrado token de acesso...");
			return false;
		}
		
		GerenciadorDeToken gerenciadorDeToken = new GerenciadorDeToken();
		if (!gerenciadorDeToken.isValido(jwtToken)) {
			response.sendError(403, "Você não está autorizado a acessar este recurso...");
			return false;
		}

		return true;
	}
	
	private String pegaTokenDaRequisicao(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7, authorizationHeader.length());
		}
		
		return null;
	}
}
