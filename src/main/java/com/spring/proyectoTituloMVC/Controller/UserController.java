package com.spring.proyectoTituloMVC.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.proyectoTituloMVC.Entity.User;
import com.spring.proyectoTituloMVC.Service.UserService;

@Controller 
@Scope ("session")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/login.html")
	public String login(Model model, HttpSession session){
		model.addAttribute("user", new User());
		session.setAttribute("user", new User());
		return "login";
	}
	
	/*@RequestMapping(value = "/register", method=RequestMethod.GET)
	public String register(Model model, HttpSession session) {
		model.addAttribute("user", new User());
		session.setAttribute("user", new User());
		return "register";
	}*/
	
	@RequestMapping(value="/registering", method=RequestMethod.POST)
	public String registering(@ModelAttribute("user") User user, Model model, BindingResult result, HttpSession session) {
		user.setIdCliente(1);
		user.setRol(1);
		System.out.println(user.getNombre()+" "+user.getPassword());
		userService.save(user);
		model.addAttribute("user", user);
		session.setAttribute("user", user);
		return "index"; //login
	}
	
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public String authenticate(@ModelAttribute("user") User user, Model model, HttpSession session, BindingResult result) {
		user.setNombre("Basthian");
		session.setAttribute("user", user);
		model.addAttribute("user", new User());
		return "index";
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> users = userService.getUsers();
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	@GetMapping("/getUserById/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id){
		User user = userService.getUserById(id);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@GetMapping("/getUserByRut/{rut}")
	public ResponseEntity<User> getUserByRut(@PathVariable String rut){
		User user = userService.getUserByRut(rut);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@GetMapping("/getUserByCorreo/{correo}")
	public ResponseEntity<User> getUserByCorreo(@PathVariable String correo){
		User user = userService.getUserByCorreo(correo);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@GetMapping("/getUsersByRol/{rol}")
	public ResponseEntity<List<User>> getUsersByRol(@PathVariable int rol){
		List<User> users = userService.getUsersByRol(rol);
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	@PostMapping("/saveUser/{user}")
	public void saveUser(@PathVariable User user) {
		userService.save(user);
	}
}
