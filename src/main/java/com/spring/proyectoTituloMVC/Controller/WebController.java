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
import com.spring.proyectoTituloMVC.Entity.Dish;
import com.spring.proyectoTituloMVC.Entity.Event;
import com.spring.proyectoTituloMVC.Service.DishService;
import com.spring.proyectoTituloMVC.Service.EventService;
import com.spring.proyectoTituloMVC.Service.UserService;

@Controller
@Scope ("session")
public class WebController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	EventService eventService;
	
	@Autowired
	DishService dishService;
	
	@GetMapping("/")
	public String index(Model model, HttpSession session){
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
			user.setRol(1);
			model.addAttribute("user", user);
			session.setAttribute("user", user);
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
		User user = (User) session.getAttribute("user");
		if(user.getNombre()==null) {
			return "401";
		}else {
			return "profile";
		}
	}
	
	@GetMapping("/profileEdit.html")
	public String profileEdit(Model model, HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		if(user.getNombre()==null) {
			return "401";
		}else {
			System.out.println("WebController profileEdit");
			model.addAttribute("userEdit", new User());
			session.setAttribute("userEdit", new User());
			System.out.println("profileEdit - Agregado userEdit a model y session");
			
			return "profileEdit";
		}
	}
	
	//nuevo
	@GetMapping("/myOrders.html")
	public String myOrders(Model model, HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		if(user.getNombre()==null) {
			return "401";
		}else {
			return "myOrders";
		}
	}
	
	@GetMapping("/register.html")
	public String register(Model model, HttpSession session){
		System.out.println("Entró al register de web controller");
		
		
		model.addAttribute("user", new User());
		session.setAttribute("user", new User());
		System.out.println("Creo model y session");
		
		return "register";
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
	
	//--------------------------------Vistas Eventos--------------------------------
	
	@GetMapping("/events.html")
	public String events(Model model, HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		if(user.getNombre()==null) {
			return "401";
		}else {
			
			loadEvents(model,session);
			return "events";
		}
	}
	
	@GetMapping("/myEvents.html")
	public String myEvents(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user.getNombre()==null) {
			return "401";
		}else {
			/*List<Dish> allDishes = dishService.getAllDishes();
			for(int i=0; i<allDishes.size(); i++) {
				allDishes.get(i).setSelected(false);
				dishService.save(allDishes.get(i));
			}
			
			System.out.println("Agregados platllos por defecto...");*/
			
			loadMyEvents(model,session);
			unselectDishes(model,session);
			return "myEvents";
		}
	}
	
	@GetMapping("/eventCreate.html")
	public String eventCreate(Model model, HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		if(user.getRol()!=2) {
			return "401";
		}else {
			
			model.addAttribute("newDish", new Dish());
			model.addAttribute("dish", new Dish());
			session.setAttribute("newDish", new Dish());
			session.setAttribute("dish", new Dish());
			loadDishes(model, session);
			unselectDishes(model,session);
			
			System.out.println("Agregado dish y newDish a model y cargadas las listas...");
			return "eventCreate";
		}
	}
	
	@GetMapping("/eventCreateForm.html")
	public String eventCreateForm(Model model, HttpSession session) {
		System.out.println("Ingresando a eventCreateForm...");
		
		loadDishes(model, session);
		model.addAttribute("event", new Event());
		System.out.println("Agregados dishesSelected y event a model y redirigiendo a eventCreateForm...");
		
		return "eventCreateForm";
	}
	
	public void loadDishes(Model model, HttpSession session) {
		System.out.println("Iniciando carga de platillos...");
		List<Dish> dishes = dishService.getAllDishes();
		System.out.println("Se han cargado los platillos en dishes...");
		
		List<Dish> dishesSelected = new ArrayList<Dish>();
		List<Dish> dishesUnselected = new ArrayList<Dish>();
		
		for(int i=0; i<dishes.size();i++) {
			if(dishes.get(i).isSelected()) {
				dishesSelected.add(dishes.get(i));
			}else {
				dishesUnselected.add(dishes.get(i));
			}
		}
		
		model.addAttribute("dishesUnselected", dishesUnselected);
		model.addAttribute("dishesSelected", dishesSelected);
		System.out.println("Se agregó dishesSelected y dishesUnselected a model...");
	}

	public void loadMyEvents(Model model, HttpSession session) {
		System.out.println("Iniciando carga de mis eventos...");
		
		List <Event> allEvents = eventService.getAllEvents();
		List <Event> myActiveEvents = new ArrayList<Event>();
		List<Event> myInactiveEvents = new ArrayList<Event>();
		
		User me = (User) session.getAttribute("user");
		System.out.println("Id de la empresa: "+me.getIdCliente());
		
		for(int i=0; i<allEvents.size();i++) {
			if(allEvents.get(i).getEmpresa().getIdCliente()==me.getIdCliente()) {
				if(allEvents.get(i).isHabilitado()) {
					myActiveEvents.add(allEvents.get(i));
					System.out.println("Evento: "+ allEvents.get(i).getNombre()+" agregado a myActiveEvents");
				}else {
					myInactiveEvents.add(allEvents.get(i));
					System.out.println("Evento: "+allEvents.get(i).getNombre()+" agregado a myInactiveEvents");
				}
			}
		}
		
		model.addAttribute("myActiveEvents", myActiveEvents);
		model.addAttribute("myInactiveEvents", myInactiveEvents);
		System.out.println("Se agregó myActiveEvents con un total de "+ myActiveEvents.size()+" eventos a model...");
		System.out.println("Se agregó myInactiveEvents con un total de "+ myInactiveEvents.size()+" eventos a model...");
	}
	
	// -------------------------------------- Vistas de Administrador --------------------------------------------
	@GetMapping("/indexAdmin.html")
	public String indexAdmin(Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		if(user.getRol()!=0) return "401";
		model.addAttribute("user", user);
		session.setAttribute("user", user);
		System.out.println("User admin, agregado a session y model");
		System.out.println("Redirigiendo a indexAdmin");
		
		return "indexAdmin";
	}
	
	@GetMapping("/usersAdmin.html")
	public String usersAdmin(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user.getRol()!=0) return "401";
		
		loadUsers(model, session);
		return "usersAdmin";
	}
	
	@GetMapping("/eventsAdmin.html")
	public String eventsAdmin(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user.getRol()!=0) return "401";
		
		System.out.println("Cargando Eventos a indexAdmin...");
		loadEvents(model, session);
		System.out.println("Eventos cargados a indexAdmin...");
		
		return "eventsAdmin";
	}
	
	public void loadUsers(Model model, HttpSession session) {
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
	
	public void loadEvents(Model model, HttpSession session) {
		System.out.println("Iniciando carga lista de eventos");
		List<Event> eventsEnabled = new ArrayList<Event>();
		List<Event> eventsDisabled = new ArrayList<Event>();
		
		List<Event> myEvents = eventService.getAllEvents();
		System.out.println("Se guardaron eventos en la lista myEvents");
		
		for(int i=0; i<myEvents.size(); i++) {
			if(myEvents.get(i).isHabilitado()) {
				eventsEnabled.add(myEvents.get(i));
			}else {
				eventsDisabled.add(myEvents.get(i));
			}
		}
		
		model.addAttribute("myEventsListEnabled", eventsEnabled);
		model.addAttribute("myEventsListDisabled", eventsDisabled);
		System.out.println("Se agregaron atributos a model");
	}
	
	public void unselectDishes(Model model, HttpSession session) {
		List<Dish> dishes = dishService.getAllDishes();
		for(int i=0; i<dishes.size();i++) {
			dishes.get(i).setSelected(false);
			dishService.save(dishes.get(i));
		}
	}
	
	/* Otra forma de deshabilitar usuarios con RequestMapping en vez de GetMapping
	 @RequestMapping(value="/disableUser/{id}", method = RequestMethod.GET)
	public String disableUser(@PathVariable (value="id") String id, Model model, HttpSession session) {
		
		//System.out.println("Usuario: "+user.getNombre()+" id: "+user.getIdCliente());
		
		System.out.println("Entrando a disableUser");
		
		User userAux = userService.getUserById(Integer.parseInt(id));
		userAux.setHabilitado(false);
		
		System.out.println("Usuario: "+ userAux.getNombre()+" deshabilitado...");
		
		userService.save(userAux);
		
		System.out.println("Terminando disableUser...");
		return "redirect:/usersAdmin.html";
	} 
	*/
	
}
