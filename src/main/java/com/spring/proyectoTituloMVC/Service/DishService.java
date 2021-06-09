package com.spring.proyectoTituloMVC.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.proyectoTituloMVC.Entity.Dish;
import com.spring.proyectoTituloMVC.Repository.DishRepository;

@Service
public class DishService {
	
	@Autowired
	private DishRepository dishRepository;
	
	public Dish getDishById(int id) {
		Dish dish = dishRepository.findById(id);
		return dish;
	}
	
	public List<Dish> getAllDishes(){
		List<Dish> dishes = dishRepository.findAll();
		return dishes;
	}
	
	public List<Dish> getDishesByNombre(String nombre){
		List<Dish> dishes = dishRepository.findByNombre(nombre);
		return dishes;
	}
	
	public <S extends Dish> S save(Dish dish) {
		return dishRepository.save(dish);
	}
}
