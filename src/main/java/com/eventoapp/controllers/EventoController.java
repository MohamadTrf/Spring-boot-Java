package com.eventoapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import com.eventosapp.repository.ConvidadoRepository;
import com.eventosapp.repository.EventoRepository;

@Controller
public class EventoController {

// value = url
// method = metodo http(post get delete) etc
	@Autowired
	private EventoRepository er;
	
	@Autowired
	private ConvidadoRepository cr;
	
	
	@RequestMapping(value="/cadastrarEvento",method=RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	// Pesquisa objeto na view para exibir no atualizarEventoPut
	@RequestMapping(value="/atualizarEvento",method=RequestMethod.GET)
	public ModelAndView atualizarEvento(@RequestParam("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo); // pesquisou o evento pelo codigo
		
		// pegando do modelo de dados e jogando na view editar evento
		ModelAndView mv = new ModelAndView("evento/editarEvento"); //chamando a view
   		mv.addObject("evento",evento); // mandando objeto pra view editar evento
		return mv;
		//return	atualizarEventoPUT(evt);
		//return "evento/editarEvento";
	}
	
	@RequestMapping (value="/atualizarConvidado",method=RequestMethod.GET)
	public ModelAndView atualizarConvidado (@RequestParam ("num") long  num) {
		Convidado convidado = cr.findByNum(num);
		ModelAndView mv = new ModelAndView("evento/editarConvidado");
		mv.addObject("convidado",convidado);
		return mv;
	}
	//SALVANDO UPDATE
   	@RequestMapping(value = "/atualizarConvidado", method=RequestMethod.POST)
	public String salvarAtualizacao(Convidado convidado)
	{
   		cr.save(convidado);
   		return"redirect:/eventos";
	}
   	@RequestMapping(value = "/atualizarEvento", method=RequestMethod.POST)
	public String salvarAtualizacao(Evento evento)
	{
   		er.save(evento);
   		return"redirect:/eventos";
	}
	public String form(@Valid Evento evento,BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarEvento";
		}
	
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso");
		er.save(evento);
		
		return "redirect:/cadastrarEvento";
		
	}
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> eventos = er.findAll();
		mv.addObject("eventos",eventos);
		return mv;
		
	}
	
	@RequestMapping("/deletarEvento") 
	public String deletarEvento(@RequestParam ("codigo") long codigo ){
		System.out.println(codigo);
		Evento evento =  er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}
	
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo ) {// cadastrando evento no banco
		Evento evento = er.findByCodigo(codigo); // guardando a busca do codigo na variavel evento
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", evento);
		Iterable<Convidado> convidados = cr.findByEvento(evento);
		mv.addObject("convidados", convidados);
		//System.out.println("evento "+evento);
		return mv;
	}
	// cadastrando convidado no banco de dados relacionando o codigo do evento ao convidado.
			// de acordo com o evento que ele pertence
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/{codigo}";
		}
		
		Evento evento = er.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);	
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!!");
		return "redirect:/{codigo}";
	}
	
	@RequestMapping("/deletarConvidado") 
	public String deletarConvidado (String rg ) {
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
		Evento evento = convidado.getEvento();
		long codigoLong = evento.getCodigo();
		String codigo =""+codigoLong;
		return "redirect:/" + codigo ;
	}
}
	

