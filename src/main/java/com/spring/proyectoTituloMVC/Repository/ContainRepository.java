package com.spring.proyectoTituloMVC.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.proyectoTituloMVC.Entity.Contain;
import com.spring.proyectoTituloMVC.Entity.Event;

@Repository
public interface ContainRepository extends JpaRepository<Contain,Integer>{
	Contain findById(int id);
	List<Contain> findAll();
	List<Contain> findByEventoContener(Event evento);
	
	//Obtiene todos los eventos que contengan un platillo en específico
	//List <Contain> findByPlatillo(int id);
	
	//Obtiene todos los platillos que se encuentren en un evento en específico
	//List <Contain> findByEvento(int id);
	
	<S extends Contain> S save(Contain contain);
}
