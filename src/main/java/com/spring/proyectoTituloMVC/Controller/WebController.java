package com.spring.proyectoTituloMVC.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.proyectoTituloMVC.Entity.User;

@Controller
@Scope ("session")
public class WebController {

	@GetMapping("/")
	public String index(Model model, HttpSession session){
		/*model.addAttribute("user", new User());
		session.setAttribute("user", new User());
		
		return "login";
		return volverAInicio(model,session);*/
		
<<<<<<< Updated upstream
		
		
		model.addAttribute("user", new User());
		session.setAttribute("user", new User());
=======
		User user = new User();
		user.setRol(1);
		
		model.addAttribute("user", user);
		session.setAttribute("user", user);
>>>>>>> Stashed changes
		
		return "index";
	}
	
	@GetMapping("/index.html")
	public String home(Model model, HttpSession session)
	{
		
		User user = (User)session.getAttribute("user");
		//User user = (User) model.getAttribute("user");
		System.out.println("User en index: "+ user.getNombre());
		if(user.getNombre() == null) {
			User user2 = new User();
			user2.setRol(1);
			model.addAttribute("user", user2);
			session.setAttribute("user", user2);
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
}
