package com.jquevedo.repo;

import com.jquevedo.model.Usuarios;

public interface UsuarioRepo extends GenericRepo<Usuarios, Integer> {

	Usuarios findOneByUsername(String username);
}
