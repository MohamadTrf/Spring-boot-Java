package com.eventosapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String>{// buscando lista de convidados do evento	 
	// buscando no banco de dados atraves do evento
	Iterable<Convidado> findByEvento(Evento evento);
	Convidado findByRg(String rg);
	
}
