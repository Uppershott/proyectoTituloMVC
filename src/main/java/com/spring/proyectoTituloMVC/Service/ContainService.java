package com.spring.proyectoTituloMVC.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.proyectoTituloMVC.Entity.Contain;
import com.spring.proyectoTituloMVC.Repository.ContainRepository;

@Service
public class ContainService {
	
	@Autowired
	private ContainRepository containRepository;
	
	public Contain getContainById(int id) {
		Contain contain = containRepository.findById(id);
		return contain;
	}
	
	public List<Contain> getAllContains(){
		List<Contain> contains = containRepository.findAll();
		return contains;
	}
	
	public <S extends Contain> S save(Contain contain) {
		return containRepository.save(contain);
	}
	
	/*
	public List<Contain> getContainsByPlatillo(int id){
		List<Contain> contains = containRepository.findByPlatillo(id);
		return contains;
	}
	
	public List<Contain> getContainsByEvento(int id){
		List<Contain> contains = containRepository.findByEvento(id);
		return contains;
	}*/
}
