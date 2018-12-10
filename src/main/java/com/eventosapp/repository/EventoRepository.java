package com.eventosapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eventoapp.models.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, String> {

	public Evento findByCodigo(long codigo); // buscando  e exibindo o codigo do Evento
	// select pra buscar convidadados de um evento
	public Evento findByNome(String nome); 
	 
	public List<Evento> ordenaEvento(); // chamando querie SELECT

	public List<Evento> findAll();

	public List<Evento> findByNomeOrderByNomeAsc(String nome); // buscando um nome  

	public List<Evento> findAllByOrderByNomeAsc(); //bucando varios nomes e ordenando-os  por ordem alfabetica crescente

	public List<Evento> findAllByOrderByNomeDesc(); // buscando varios nomes e ordenando-os por ordem alfabetica contraria 
}
