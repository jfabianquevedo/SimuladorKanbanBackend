package com.jquevedo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jquevedo.model.Tareas;
import com.jquevedo.repo.GenericRepo;
import com.jquevedo.repo.TareasRepo;
import com.jquevedo.service.TareasService;

@Service
public class TareasImpl extends CrudImpl<Tareas, Integer> implements TareasService {

	@Autowired
	private TareasRepo repo;

	@Override
	protected GenericRepo<Tareas, Integer> getRepo() {
		return repo;
	}

	@Override
	public List<Tareas> listarUsuario(Integer idUsuario) {
		return repo.listarUsuario(idUsuario);
	}
}
