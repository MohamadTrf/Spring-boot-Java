package com.eventoapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecuriyConfig  extends WebSecurityConfigurerAdapter{
	@Autowired
	private ImplementsUserDetailsService userDetailsService;
	
	
	
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			http.csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.GET, "/").permitAll() //  retorna metodo get no index
			.antMatchers(HttpMethod.GET, "/cadastrarEvento").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/cadastrarEvento").hasRole("ADMIN")
			.anyRequest().authenticated() // para todas as demais requisições precisa autenticar
			.and().formLogin().permitAll() // acesso ao form de login do spring security
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")); // como encerrar a sessão
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception{
			auth.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder()); // gerando a senha criptografada no bd

			//auth.inMemoryAuthentication() // definindo o modo de autenticação
			//.withUser("Mohamad").password("123").roles("ADMIN"); // passando usuario senha e permissão de acesso
			//auth.userDetailsService(userDetailsService)
			
		}
	
		@Override
		public void configure(WebSecurity web) throws Exception{
			// impedindo de bloquear a pasta quando pedir o acesso a paginas estaticas 
			web.ignoring().antMatchers("/materialize/**", "/style/**"); // passando quais pastas ignorar tal autenticação
			}
		
}
