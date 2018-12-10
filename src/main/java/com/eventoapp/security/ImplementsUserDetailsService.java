package com.eventoapp.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.eventoapp.models.Usuario;
import com.eventosapp.repository.UsuarioRepository;

@Repository
@Transactional
public class ImplementsUserDetailsService  implements UserDetailsService  {
	
	@Autowired
	private UsuarioRepository ur; // injeção de dependencia
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Usuario usuario = ur.findByLogin(login);
		usuario.getLogin();
		usuario.getPassword();

		if(usuario ==  null ) {
			throw  new UsernameNotFoundException("Usuario não encontrado!");
		}
		return new User(usuario.getUsername(), usuario.getPassword(), true ,true ,true ,true, usuario.getAuthorities()); // retornando o user details
	}

}
