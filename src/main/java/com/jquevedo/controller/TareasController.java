package com.jquevedo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jquevedo.exception.ModelNotFoundException;
import com.jquevedo.model.Tareas;
import com.jquevedo.model.Usuarios;
import com.jquevedo.repo.UsuarioRepo;
import com.jquevedo.service.TareasService;
import com.jquevedo.util.Constantes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/Tarea")
public class TareasController {

	Logger logger = LoggerFactory.getLogger(TareasController.class);
	@Autowired
	private TareasService service;

	@Autowired
	UsuarioRepo repo;

	@Operation(operationId = "Registrar", summary = "Permite registar una Tarea", parameters = {
			@Parameter(in = ParameterIn.PATH, name = "nombreTarea", description = "Nombre o titulo de tarea"),
			@Parameter(in = ParameterIn.PATH, name = "resumenTarea", description = "resumen o descripcion de la tarea") }, responses = {
					@ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = Tareas.class))), })

	@PostMapping
	public ResponseEntity<Tareas> registrar(@Valid @RequestBody Tareas t) throws Exception {
		Usuarios usuario = repo.findOneByUsername(t.getUsuarios().getUsername());
		if(usuario == null) {
			throw new ModelNotFoundException("Usuario no existe. " + t.getUsuarios().getUsername());
		}
		t.setUsuarios(usuario);
		logger.info("Solicitud de regitro para la tarea: " + t);
		Tareas tarea = service.registrar(t);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tarea.getIdTarea())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@Operation(operationId = "Listar", summary = "Lista todas las tareas ", responses = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Tareas.class))), })

	@GetMapping
	public ResponseEntity<List<Tareas>> listar() throws Exception {
		logger.info("Solicitud para listar toda las tareas");
		List<Tareas> listaTareas = service.listar();
		return new ResponseEntity<List<Tareas>>(listaTareas, HttpStatus.OK);
	}

	@Operation(operationId = "Modificar", summary = "Permite modificar una Tarea", parameters = {
			@Parameter(in = ParameterIn.PATH, name = "nombreTarea", description = "Nombre o titulo de tarea"),
			@Parameter(in = ParameterIn.PATH, name = "resumenTarea", description = "resumen o descripcion de la tarea") }, responses = {
					@ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = Tareas.class))),
					@ApiResponse(responseCode = "404", description = "No existe la tarea a modificar")})

	@PutMapping
	public ResponseEntity<Tareas> modificar(@Valid @RequestBody Tareas t) throws Exception {
		logger.info("Solicitud para modificar la tarea: " + t);
		Tareas obj = service.listarPorId(t.getIdTarea());
		if (obj == null) {
			throw new ModelNotFoundException("Tarea no existe. ID: " + t.getIdTarea());
		}
		Tareas solicitud = service.modificar(t);
		return new ResponseEntity<Tareas>(solicitud, HttpStatus.OK);
	}
	@Operation(operationId = "Listar", summary = "Lista las tareas por usuario", responses = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Tareas.class))), })

	@GetMapping("listarUser/{nombreUser}")
	public ResponseEntity<List<Tareas>> listarPorUsuario(@PathVariable("nombreUser") String nombreUser)
			throws Exception {
		logger.info("Solicitud para listar tareas por usuario");
		Usuarios usuario = repo.findOneByUsername(nombreUser);
		if (usuario == null) {
			throw new ModelNotFoundException("nombre de Usuario no existe " + nombreUser);
		}
		List<Tareas> listaTareas = service.listarUsuario(usuario.getIdUsuario());
		return new ResponseEntity<List<Tareas>>(listaTareas, HttpStatus.OK);
	}

	@Operation(operationId = "eliminar", summary = "Permite eliminar una Tarea", parameters = {
			@Parameter(in = ParameterIn.PATH, name = "idTarea", description = "Nombre o titulo de tarea") }, responses = {
					@ApiResponse(responseCode = "204", description = "successful operation", content = @Content(schema = @Schema(implementation = Tareas.class))),
					@ApiResponse(responseCode = "404", description = "No existe la tarea a eliminar")})

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) {
		Tareas obj = service.listarPorId(id);
		logger.info("Solicitud para eliminar la tarea " + obj);
		if (obj == null) {
			throw new ModelNotFoundException("Tarea no existe. ID: " + obj.getIdTarea());
		}
		if (obj.getEstadoTarea().equalsIgnoreCase(Constantes.ENPROCESO)) {
			throw new RuntimeException("No se puede eliminar la tarea hasta que se finalice ID: " + obj.getIdTarea());
		}
		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
