package com.jquevedo.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jquevedo.exception.ModelNotFoundException;
import com.jquevedo.model.Tareas;
import com.jquevedo.model.Usuarios;
import com.jquevedo.repo.UsuarioRepo;
import com.jquevedo.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/Usuario")
public class UsuarioController {

	Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	@Autowired
	UsuarioService service;

	@Autowired
	UsuarioRepo repo;

//	@Operation(operationId = "Registrar", summary = "Permite registar un Usuario", parameters = {
//			@Parameter(in = ParameterIn.PATH, name = "username", description = "Nombre de usuario"),
//			@Parameter(in = ParameterIn.PATH, name = "password", description = "Contraseña del usuario") }, responses = {
//					@ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = Tareas.class))), })

	@PostMapping
	public ResponseEntity<Usuarios> registrar(@RequestBody Usuarios u) throws Exception {
		logger.info("Solicitud para crear el usuario" + u);
		Usuarios user = repo.findOneByUsername(u.getUsername().toLowerCase());
		if (user != null) {
			throw new ModelNotFoundException("Ya existe este usuario");
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		u.setPassword(encoder.encode(u.getPassword()));
		Usuarios us = service.registrar(u);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(us.getIdUsuario())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
