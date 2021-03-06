package com.jquevedo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jquevedo.model.Tareas;

public interface TareasRepo extends GenericRepo<Tareas, Integer> {

	@Query(value = "SELECT * FROM tareas where id_usuario= :idUsuario", nativeQuery = true)
	List<Tareas> listarUsuario(@Param("idUsuario") Integer idUsuario);
}
