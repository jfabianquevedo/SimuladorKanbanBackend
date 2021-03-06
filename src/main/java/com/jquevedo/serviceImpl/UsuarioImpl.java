package com.jquevedo.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jquevedo.model.Tareas;
import com.jquevedo.model.Usuarios;
import com.jquevedo.repo.GenericRepo;
import com.jquevedo.repo.UsuarioRepo;
import com.jquevedo.service.UsuarioService;

@Service
public class UsuarioImpl extends CrudImpl<Usuarios, Integer> implements UserDetailsService,UsuarioService {

	@Autowired
	private UsuarioRepo repo;
	

	@Override
	protected GenericRepo<Usuarios, Integer> getRepo() {
	return repo;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuarios usuario = repo.findOneByUsername(username);
		if (usuario == null) {
			throw new UsernameNotFoundException(String.format("Usuario no Existe", username));
		}
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("USER"));
		UserDetails ud = new User(usuario.getUsername(), usuario.getPassword(), roles);
		return ud;
	}




}
