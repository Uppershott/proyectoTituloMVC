package com.spring.proyectoTituloMVC.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.proyectoTituloMVC.Entity.Participation;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation,Integer> {
	Participation findById(int id);
	List<Participation> findAll();
	
	//Obtiene todos los eventos en los que ha participado un cliente en específico
	//List<Participation> findAllEventosByCliente(int id);
	
	//Obtiene todos los clientes que participaron en un evento en específico
	//List<Participation> findAllClientesByEvento(int id);
	
	//Obtiene todas las participaciones dependiendo de si retiran en el local o solicitan despacho a dommicilio
	List<Participation> findAllParticipacionesByRetira(boolean retira);
	
	<S extends Participation> S save(Participation participation);
}
