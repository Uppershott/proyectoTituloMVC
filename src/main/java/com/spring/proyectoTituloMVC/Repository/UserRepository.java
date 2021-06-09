package com.spring.proyectoTituloMVC.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.proyectoTituloMVC.Entity.User;

@Repository 
public interface UserRepository extends JpaRepository <User,Integer>{
	List  <User> findAll();
	User findById(int id);
	User findByRut(String rut);
	User findByCorreo(String correo);
	List <User> findByRol(int rol);
	
	<S extends User> S save(User user);
}
