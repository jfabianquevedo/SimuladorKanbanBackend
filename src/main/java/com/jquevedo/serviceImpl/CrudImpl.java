package com.jquevedo.serviceImpl;

import java.util.List;

import com.jquevedo.repo.GenericRepo;
import com.jquevedo.service.GenericCrud;

public abstract class CrudImpl<T, ID> implements GenericCrud<T, ID> {

	protected abstract GenericRepo<T, ID> getRepo();

	@Override
	public T registrar(T t) {
		return getRepo().save(t);
	}

	@Override
	public T modificar(T t) {
		return getRepo().save(t);
	}

	@Override
	public List<T> listar() {
		return getRepo().findAll();
	}

	@Override
	public T listarPorId(ID id) {
		return getRepo().findById(id).orElse(null);
	}

	@Override
	public void eliminar(ID id) {
		getRepo().deleteById(id);
	}
}
