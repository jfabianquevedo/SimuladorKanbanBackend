package com.jquevedo.service;

import java.util.List;

import com.jquevedo.model.Tareas;

public interface TareasService extends GenericCrud<Tareas, Integer> {

	List<Tareas> listarUsuario(Integer idSolicitud);

}
