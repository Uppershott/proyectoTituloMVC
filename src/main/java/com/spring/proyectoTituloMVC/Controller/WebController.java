package com.spring.proyectoTituloMVC.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.spring.proyectoTituloMVC.Entity.Participation;
import com.spring.proyectoTituloMVC.Service.DishService;
import com.spring.proyectoTituloMVC.Service.EventService;
import com.spring.proyectoTituloMVC.Service.ParticipationService;
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
	
	@Autowired
	ParticipationService participationService;
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) throws ParseException{
		User user = new User();
		user.setRol(1);
		
		model.addAttribute("user", user);
		session.setAttribute("user", user);
				
		disableExpiredEvents(model,session);
		loadIndexEvents(model,session);
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
			loadUserOrders(model,session,user);
			
			return "myOrders";
		}
	}
	
	public void loadUserOrders(Model model, HttpSession session, User user) {
		System.out.println("Iniciando carga de pedidos de usuario...");
		
		List<Participation> userOrders = participationService.getParticipationByCliente(user);
		System.out.println("ordersList size: "+userOrders.size());
		if(userOrders.size()>0) {
			List<Participation> userOrdersAux = new ArrayList<Participation>();
			
			int cant = userOrders.size();
			
			for(int i=0; i<cant; i++) {
				if(userOrders.get(i).isVigente()) {
					System.out.println("Pedido al evento: "+userOrders.get(i).getEvento().getNombre()+" agregado la lista ordersList...");
					userOrdersAux.add(userOrders.get(i));
					
				}
				System.out.println("index de la lista: "+i);
			}
			
			session.setAttribute("ordersList", userOrdersAux);
			System.out.println("Seteada lista ordersList a session...");
		}else {
			session.setAttribute("ordersList", new ArrayList<Participation>());
		}
		
	}
	
	@GetMapping("/orderDetail.html")
	public String orderDetail(Model model, HttpSession session) {
		System.out.println("Entrando a orderDetail...");
		
		User user = (User) session.getAttribute("user");
		if(user.getNombre()==null) {
			return "401";
		}else {
			return "orderDetail";
		}
	}
	
	@GetMapping("/register.html")
	public String register(Model model, HttpSession session){
		System.out.println("Entr� al register de web controller");
		
		
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
			loadCompanies(model,session);
			return "company";
		}
	}
	
	@GetMapping("/userDetail.html")
	public String userDetail(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user.getNombre()==null) {
			return "401";
		}else {
			
			return "userDetail";
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
	
	@GetMapping("/eventDetail.html")
	public String eventDetail(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user.getNombre()==null) {
			return "401";
		}else {
			model.addAttribute("newOrder", new Participation());
			System.out.println("Agregado newOrder a model...");
			return "eventDetail.html";
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
		System.out.println("Se agreg� dishesSelected y dishesUnselected a model...");
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
		System.out.println("Se agreg� myActiveEvents con un total de "+ myActiveEvents.size()+" eventos a model...");
		System.out.println("Se agreg� myInactiveEvents con un total de "+ myInactiveEvents.size()+" eventos a model...");
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
	
	public void loadCompanies(Model model, HttpSession session) {
		System.out.println("Iniciando loadCompanies...");
		
		List<User> users = userService.getUsers();
		List<User> companies = new ArrayList<User>();
		
		for(int i=0; i<users.size();i++) {
			if(users.get(i).getRol()==2) {
				companies.add(users.get(i));
				System.out.println("Empresa: "+users.get(i).getNombre()+" agregado a companies...");
			}
		}
		model.addAttribute("companies",companies);
		System.out.println("Agregado companies a model...");
	}
	
	public void disableExpiredEvents(Model model, HttpSession session) throws ParseException{
		System.out.println("Iniciando proceso de deshabilitaci�n de eventos que caducaron por fecha...");
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date hoy = new Date();
		
		List<Event> allEvents = eventService.getAllEvents();
		
		for(int i=0; i<allEvents.size(); i++) {
			if(allEvents.get(i).isHabilitado()) {
				Date fTerm = formato.parse(allEvents.get(i).getFechaTermino());
				if(fTerm.before(hoy) || fTerm.equals(hoy)) {
					System.out.println("Evento: "+allEvents.get(i).getNombre()+" ha sido deshabilitado por caducar en fecha");
					allEvents.get(i).setHabilitado(false);
					eventService.save(allEvents.get(i));
				}
			}
		}
	}
	
	public void loadIndexEvents(Model model, HttpSession session) {
		System.out.println("Iniciando carga de eventos de inicio en loadIndexEvents...");
		
		List<Event> allEvents = eventService.getAllEvents();
		List<Event> activeEvents = new ArrayList<Event>(allEvents.size());
		List<Event> indexEvents = new ArrayList<Event>(8);
		
		for(int i=0; i<allEvents.size();i++) {
			if(allEvents.get(i).isHabilitado()) {
				activeEvents.add(allEvents.get(i));
				System.out.println("Evento: "+ allEvents.get(i).getNombre()+" activo");
			}
		}
		
		//Random r = new Random();
		System.out.println("Cantidad de eventos activos: "+activeEvents.size());
		for(int i=0; i<8; i++) {
			//int nRandom = r.nextInt(((activeEvents.size()-2)-0)+1)-0;
			System.out.println("activeEvents size: "+activeEvents.size());
			indexEvents.add(activeEvents.get(i));
			//System.out.println("Evento: "+activeEvents.get(nRandom).getNombre()+" agregado a index...");
			//activeEvents.remove(nRandom);
		}
		model.addAttribute("indexEvents", indexEvents);
		session.setAttribute("indexEvents", indexEvents);
		System.out.println("Seteado en model y session la lista de eventos a index...");
	}
}
