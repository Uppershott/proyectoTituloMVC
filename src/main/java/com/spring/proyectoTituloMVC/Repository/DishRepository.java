package com.spring.proyectoTituloMVC.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.proyectoTituloMVC.Entity.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish,Integer>{
	Dish findById(int id);
	List<Dish> findAll();
	List<Dish> findByNombre(String nombre);
	<S extends Dish> S save(Dish dish);
}
