package com.spring.proyectoTituloMVC.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.proyectoTituloMVC.Entity.User;
import com.spring.proyectoTituloMVC.Repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List <User > getUsers(){
		List<User> users = userRepository.findAll();
		return users;
	}
	
	public User getUserById(int id) {
		User user = userRepository.findById(id);
		return user;
	}
	
	public User getUserByRut(String rut) {
		User user = userRepository.findByRut(rut);
		return user;
	}
	
	public User getUserByCorreo(String correo) {
		User user = userRepository.findByCorreo(correo);
		return user;
	}
	
	public List<User> getUsersByRol(int rol){
		List<User> users = userRepository.findByRol(rol);
		return users;
	}
	
	public <S extends User> S save(User user) {
		return userRepository.save(user);
	}
}
