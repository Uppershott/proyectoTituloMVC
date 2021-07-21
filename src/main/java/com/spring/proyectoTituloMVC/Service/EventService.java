package com.spring.proyectoTituloMVC.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.proyectoTituloMVC.Entity.Event;
import com.spring.proyectoTituloMVC.Entity.User;
import com.spring.proyectoTituloMVC.Repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	public Event getEventById(int id) {
		Event event = eventRepository.findById(id);
		return event;
	}
	
	public List<Event> getAllEvents(){
		List<Event> events = eventRepository.findAll();
		return events;
	}
	
	public Event getEventsByNombre(String nombre){
		Event events = eventRepository.findByNombre(nombre);
		return events;
	}
	
	public List<Event> getEventsByFechaCreacion(Date fechaCreacion){
		List<Event> events = eventRepository.findByFechaCreacion(fechaCreacion);
		return events;
	}
	
	public List<Event> getEventsByFechaTermino(Date fechaTermino){
		List<Event> events = eventRepository.findByFechaTermino(fechaTermino);
		return events;
	}
	
	public List<Event> getEventsByPrecio(Double precio){
		List<Event> events = eventRepository.findByPrecio(precio);
		return events;
	}
	
	public List<Event> getEventsByEmpresa(User empresa){
		List<Event> events = eventRepository.findByEmpresa(empresa);
		return events;
	}
	
	public <S extends Event> S save(Event event) {
		return eventRepository.save(event);
	}
}
