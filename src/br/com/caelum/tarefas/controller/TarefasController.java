package br.com.caelum.tarefas.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.caelum.tarefas.dao.JdbcTarefaDao;
import br.com.caelum.tarefas.dto.ErroValidacaoDto;
import br.com.caelum.tarefas.modelo.Tarefa;

@Controller
public class TarefasController {
	
	private JdbcTarefaDao dao;

    @Autowired
    public TarefasController(JdbcTarefaDao dao) {
        this.dao = dao;  
    }


	@ResponseBody
	@RequestMapping(value = "/adicionaTarefa", produces = MediaType.APPLICATION_JSON_VALUE)
	public Tarefa adiciona(@Valid Tarefa tarefa) {
		dao.adiciona(tarefa);
		return tarefa;
	}

	@ResponseBody
	@RequestMapping("/listaTarefas")
	public List<Tarefa> lista() {
		return dao.lista();
	}

	@ResponseBody
	@RequestMapping(value = "/removeTarefa", method = RequestMethod.DELETE)
	public void remove(Tarefa tarefa) {
		dao.remove(tarefa);
	}

	@ResponseBody
	@RequestMapping(value = "/alteraTarefa", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Tarefa altera(@RequestBody Tarefa tarefa) {
		dao.altera(tarefa);
		return tarefa;
	}
	
	@ResponseBody
	@RequestMapping(value = "/finalizaTarefa", method = RequestMethod.PUT)
	public Tarefa finaliza(Long id) {
		dao.finaliza(id);
		return dao.buscaPorId(id);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public List<ErroValidacaoDto> trataErrosDeValidacao(BindException exception) {
		List<ErroValidacaoDto> errosDto = new ArrayList<>();

		List<FieldError> errors = exception.getBindingResult().getFieldErrors();
		for (FieldError fieldError : errors) {
			ErroValidacaoDto dto = new ErroValidacaoDto(fieldError.getField(), fieldError.getDefaultMessage());
			errosDto.add(dto);
		}

		return errosDto;
	}
}
