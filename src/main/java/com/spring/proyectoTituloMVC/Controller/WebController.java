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
		
		return "login";*/
		return volverAInicio(model,session);
	}
	
	@GetMapping("/index.html")
	public String home(Model model, HttpSession session)
	{
		model.addAttribute("user", new User());
		session.setAttribute("user", new User());
		
		return "index";
	}
	
	@GetMapping("/register.html")
	public String register(Model model, HttpSession session){
		System.out.println("Entró al register de web controller");
		model.addAttribute("user", new User());
		session.setAttribute("user", null);
		System.out.println("Creo model y session");
		
		return "register";
	}
	
	String volverAInicio(Model model, HttpSession session){
		model.addAttribute("user", new User());
		session.setAttribute("user", null);
		return "login";
	}
}
