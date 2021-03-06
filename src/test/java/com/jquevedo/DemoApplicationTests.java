package com.jquevedo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jquevedo.model.Tareas;
import com.jquevedo.model.Usuarios;
import com.jquevedo.repo.TareasRepo;
import com.jquevedo.repo.UsuarioRepo;
import com.jquevedo.service.TareasService;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	UsuarioRepo repo;

	@Autowired
	TareasService service;

	@Test
	void buscarUsuario() {
		Usuarios us = new Usuarios();
		us.setUsername("jhony");
		us.setPassword("123");
		Usuarios user = repo.findOneByUsername(us.getUsername());
		assertTrue(user.getUsername().equals(us.getUsername()));
	}
	@Test
	void RegistarTareas() {
		Tareas tarea = new Tareas();
		Usuarios user = new Usuarios();
		user.setIdUsuario(1);
		tarea.setNombreTarea("Tarea de prieba");
		tarea.setResumenTarea("Descripion de la tarea 1, para pruebas ");
		tarea.setEstadoTarea("1");
		tarea.setUsuarios(user);
		assertTrue(tarea.equals(service.modificar(tarea)));
	}
}
