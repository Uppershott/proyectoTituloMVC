package com.spring.proyectoTituloMVC.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.proyectoTituloMVC.Entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer>{
	Event findById(int id);
	List<Event> findAll();
	
	//@Query("select e from Event e where e.nombre = %:nombre%")
	//List<Event> findByNombre(@Param("nombre") String nombre);
	List<Event> findByNombre(String nombre);
	List<Event> findByFechaCreacion(Date fechaCreacion);
	List<Event> findByFechaInicio(Date fechaInicio);
	List<Event> findByFechaTermino(Date fechaTermino);
	List<Event> findByPrecio(Double precio);
	
	//Obtiene todos los eventos realizados por un user.empresa en específico
	//List<Event> findByEmpresa(int id);
	
	//Obtiene todos los participantes al evento
	//Esto lo debería hacer el repositorio Participation
	//List<Participation> findAllMisParticipantes();
	
	//Obtiene todos los platillos del evento
	//Esto lo debería hacer el repositorio Contain
	//List<Contain> findAllMisPlatillos();
	
	<S extends Event> S save(Event event);
}
