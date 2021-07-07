package com.spring.proyectoTituloMVC.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.proyectoTituloMVC.Entity.User;
import com.spring.proyectoTituloMVC.Service.UserService;

@Controller
@Scope ("session")
public class WebController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String index(Model model, HttpSession session){
		/*model.addAttribute("user", new User());
		session.setAttribute("user", new User());
		
		return "login";
		return volverAInicio(model,session);*/
		model.addAttribute("user", new User());
		session.setAttribute("user", new User());
		
		
		model.addAttribute("user", new User());
		session.setAttribute("user", new User());
		User user = new User();
		user.setRol(1);
		
		model.addAttribute("user", user);
		session.setAttribute("user", user);
		
		return "index";
	}
	
	@GetMapping("/index.html")
	public String home(Model model, HttpSession session)
	{
		User user = (User)session.getAttribute("user");
		//User user = (User) model.getAttribute("user");
		System.out.println("User en index: "+ user.getNombre());
		if(user.getNombre() == null) {
			model.addAttribute("user", new User());
			session.setAttribute("user", new User());
		}else {
			model.addAttribute("user", user);
			session.setAttribute("user", user);
		}
		
		return "index";
	}
	
	//nuevo
	@GetMapping("/profile.html")
	public String profile(Model model, HttpSession session) {
		
		//model.addAttribute("userEdit", new User());
		//session.setAttribute("userEdit", new User());
		
		return "profile";
	}
	
	@GetMapping("/profileEdit.html")
	public String profileEdit(Model model, HttpSession session) {
		System.out.println("WebController profileEdit");
		model.addAttribute("userEdit", new User());
		session.setAttribute("userEdit", new User());
		System.out.println("profileEdit - Agregado userEdit a model y session");
		return "profileEdit";
	}
	
	//nuevo
	@GetMapping("/myOrders.html")
	public String myOrders(Model model, HttpSession session) {
		return "myOrders";
	}
	
	//nuevo
	@GetMapping("/myEvents.html")
	public String myEvents(Model model, HttpSession session) {
		return "myEvents";
	}
	
	@GetMapping("/register.html")
	public String register(Model model, HttpSession session){
		System.out.println("Entró al register de web controller");
		
		
		model.addAttribute("user", new User());
		session.setAttribute("user", new User());
		System.out.println("Creo model y session");
		
		return "register";
	}
	
	@GetMapping("/events.html")
	public String events(Model model, HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		if(user.getNombre()==null) {
			return "401";
		}else {
			return "events";
		}
	}
	
	@GetMapping("/company.html")
	public String company(Model model, HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		if(user.getNombre()==null) {
			return "401";
		}else {
			return "company";
		}
	}
	
	
	
	String volverAInicio(Model model, HttpSession session){
		model.addAttribute("user", new User());
		session.setAttribute("user", null);
		return "login";
	}
	
	
	// -------------------------------------- Vistas de Administrador --------------------------------------------
	@GetMapping("/indexAdmin.html")
	public String indexAdmin(Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		
		model.addAttribute("user", user);
		session.setAttribute("user", user);
		System.out.println("User admin, agregado a session y model");
		System.out.println("Redirigiendo a indexAdmin");
		
		return "indexAdmin";
	}
	
	@GetMapping("/usersAdmin.html")
	public String usersAdmin(Model model, HttpSession session) {
		
		cargar(model, session);
		return "usersAdmin";
	}
	
	@GetMapping("/eventsAdmin.html")
	public String eventsAdmin(Model model, HttpSession session) {
		return "eventsAdmin";
	}
	
	public void cargar(Model model, HttpSession session) {
		System.out.println("Iniciando carga lista de usuarios");
		List<User> usersEnabled = new ArrayList<User>();
		List<User> usersDisabled = new ArrayList<User>();
		
		List<User> myUsers = userService.getUsers();
		System.out.println("Se guardaron usuarios en la lista myUsers");
		
		myUsers.remove(0);
		System.out.println("Admin removido");
		
		for(int i=0; i<myUsers.size(); i++) {
			if(myUsers.get(i).isHabilitado()) {
				usersEnabled.add(myUsers.get(i));
			}else {
				usersDisabled.add(myUsers.get(i));
			}
		}
		
		model.addAttribute("myUsersListEnabled", usersEnabled);
		model.addAttribute("myUsersListDisabled", usersDisabled);
		System.out.println("Se agregaron atributos a model");
	}
}
