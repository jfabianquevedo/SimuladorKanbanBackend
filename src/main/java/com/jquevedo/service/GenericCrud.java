package com.jquevedo.service;

import java.util.List;

public interface GenericCrud<T, ID> {

	T registrar(T t);

	T modificar(T t);

	List<T> listar();

	T listarPorId(ID id);

	void eliminar(ID id);

}
