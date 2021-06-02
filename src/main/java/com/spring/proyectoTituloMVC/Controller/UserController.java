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
import org.springframework.validation.FieldError;
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
		System.out.println("Iniciando proceso de registro de cuenta...");
		
		String rut = user.getRut();
		rut = rut.toUpperCase();
		rut = rut.replace(".", "");
		rut = rut.replace("-", "");
		user.setRut(rut);
		
		if(userService.getUserByRut(user.getRut()) != null){
			System.out.println("Error: el RUT ingresado ya se encuentra registrado");
			result.addError(new FieldError("user", "rut", "El RUT ingresado ya se encuentra registrado!"));
		}else if(userService.getUserByCorreo(user.getCorreo()) != null){
			System.out.println("Error: el Correo ingresado ya se encuentra registrado");
			result.addError(new FieldError("user", "correo", "El Correo ingresado ya se encuentra registrado!"));
		}else if(!isValid(user.getRut())) {
			System.out.println("Error: el RUT ingresado no es válido");
			result.addError(new FieldError("user", "rut", "El RUT ingresado no es válido!"));
		}
		
		if(result.hasErrors()) return "register";
		
		
		System.out.println(user.getNombre()+" "+user.getPassword());
		userService.save(user);
		model.addAttribute("user", user);
		session.setAttribute("user", user);
		return "index"; //login
	}
	
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public String authenticate(@ModelAttribute("user") User user, Model model, HttpSession session, BindingResult result) {
		System.out.println("Iniciando proceso de inicio de sesión...");
		
		if(userService.getUserByCorreo(user.getCorreo())==null) {
			System.out.println("Error: esta cuenta no existe");
			result.addError(new FieldError("user", "correo", "No existe una cuenta asociada a este correo"));
		}else if(!user.getPassword().equals(userService.getUserByCorreo(user.getCorreo()).getPassword())){
			System.out.println("Error: Contraseña incorrecta");
			result.addError(new FieldError("user", "password", "Contraseña incorrecta"));
		}else if(user.getCorreo().equals(userService.getUserByCorreo(user.getCorreo()).getCorreo())) {
			User userAux = userService.getUserByCorreo(user.getCorreo());	
			session.setAttribute("user", userAux);
			System.out.println("Agregado user: "+user.getNombre()+" a session...");
			model.addAttribute("user", userAux);
			System.out.println("Agregado user: "+user.getNombre()+" a model...");
		}
		
		if(result.hasErrors()) return "login";
		
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
	
	
	//------------------------------------------------------------------------------------
	//Validación RUT
	public boolean isValid(String run) {
		if(!run.matches("\\d{1,2}(\\.|\\s| |)\\d{3}(\\.|\\s| |)\\d{3}(\\.|-| |)(\\d{1}|[kK])")) return false;
		return lunhAlgorithm(run);
	}
	
	public boolean lunhAlgorithm(String run) {
		boolean validacion = false;
		try {
			//run =  run.toUpperCase();
			//run = run.replace(".", "");
			//run = run.replace("-", "");
			int rutAux = Integer.parseInt(run.substring(0, run.length() - 1));
			 
			char dv = run.charAt(run.length() - 1);
			 
			int m = 0, s = 1;
			for (; rutAux != 0; rutAux /= 10) {
				s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
			}
			if (dv == (char) (s != 0 ? s + 47 : 75)) {
				validacion = true;
			}
		} catch (Exception e) {
			System.out.println("Excepción al validar rut."+e.getMessage());
		}
		return validacion;
	}
}
