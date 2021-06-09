package com.spring.proyectoTituloMVC.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.proyectoTituloMVC.Entity.Participation;
import com.spring.proyectoTituloMVC.Repository.ParticipationRepository;

@Service
public class ParticipationService {
	
	@Autowired
	private ParticipationRepository participationRepository;
	
	public Participation getParticipationById(int id) {
		Participation participation = participationRepository.findById(id);
		return participation;
	}
	
	public List<Participation> getAllParticipations(){
		List<Participation> participations = participationRepository.findAll();
		return participations;
	}
	
	public List<Participation> getParticipationsByRetira(boolean retira){
		List<Participation> participations = participationRepository.findAllParticipacionesByRetira(retira);
		return participations;
	}
	
	public <S extends Participation> S save (Participation participation) {
		return participationRepository.save(participation);
	}
}
