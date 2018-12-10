package com.eventosapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {// buscando lista de convidados do evento	 
	// buscando no banco de dados atraves do evento
	Iterable<Convidado> findByEvento(Evento evento);
	Convidado findByRg(String rg);
	//Convidado findByCodigo(long codigo);
	Convidado findByNum(long num);
//	Convidado findByCodigo(long codigo);
	
	@Query("select e from Convidado e  where evento_codigo=?1")
	List <Convidado> findByCodigoEvento(long codigo);

//	Convidado upConvidado(long num);


}
