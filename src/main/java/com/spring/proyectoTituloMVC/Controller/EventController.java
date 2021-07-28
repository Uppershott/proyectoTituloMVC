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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.proyectoTituloMVC.Entity.Contain;
import com.spring.proyectoTituloMVC.Entity.Dish;
import com.spring.proyectoTituloMVC.Entity.Event;
import com.spring.proyectoTituloMVC.Entity.Participation;
import com.spring.proyectoTituloMVC.Entity.User;
import com.spring.proyectoTituloMVC.Service.ContainService;
import com.spring.proyectoTituloMVC.Service.DishService;
import com.spring.proyectoTituloMVC.Service.EventService;
import com.spring.proyectoTituloMVC.Service.ParticipationService;

@Controller
@Scope ("session")
public class EventController {
	
	@Autowired
	DishService dishService;
	
	@Autowired
	ContainService containService;
	
	@Autowired
	EventService eventService;
	
	@Autowired
	ParticipationService participationService;
	
	@RequestMapping(value = "/addDishEvent", method=RequestMethod.POST)
	public String addDishEvent(@ModelAttribute("dish") Dish dish, Model model, HttpSession session, BindingResult result) {
		System.out.println("Iniciando proceso de agregación de platillo a evento...");
		if(dish.getId()==0) {
			System.out.println("Error: debe agregar al menos 1 platillo al evento");
			result.addError(new FieldError("dish","id","Debe agregar al menos 1 platillo al evento"));
		}
		
		if(result.hasErrors()) return "eventCreate";
		
		Dish dishAux = dishService.getDishById(dish.getId());
		dishAux.setSelected(true);
		System.out.println("Platillo: "+dishAux.getNombre()+" ha sido seleccionado...");
		dishService.save(dishAux);
		System.out.println("Platillo: "+ dishAux.getNombre()+" ha sido agregado al evento...");
		
		return "redirect:/eventCreate.html";
	}
	
	@RequestMapping(value="/addNewDish", method=RequestMethod.POST)
	public String addNewDish(@ModelAttribute("newDish") Dish newDish, Model model, HttpSession session, BindingResult result) {
		
		newDish.setSelected(true);
		
		dishService.save(newDish);
		System.out.println("Platillo: "+newDish.getNombre()+" ha sido agregado como nuevo platillo y al evento...");
		return "redirect:/eventCreate.html";
	}
	
	@GetMapping("/editDishEvent")
	public String editDishEvent(Model model, HttpSession session) {
		
		model.addAttribute("dish", new Dish());
		
		Event event = (Event) session.getAttribute("eventD");
		System.out.println("id evento: "+event.getIdEvent());
		System.out.println("Platillos size: "+event.getMisPlatillos());
		
		List<Contain> contain = containService.getContainsByEventoContener(event);
		System.out.println("contain size: "+ contain.size());
		
		List<Dish> dishes = new ArrayList<Dish>();
		
		List<Dish> allDishes = dishService.getAllDishes();
		
		for(int i=0; i<contain.size(); i++) {
			dishes.add(contain.get(i).getPlatilloContener());
			System.out.println("Platillo: "+ dishes.get(i).getNombre()+" agregado a dishes");
		}
		
		for(int j=0; j<dishes.size(); j++) {
			for(int i=0; i<allDishes.size(); i++) {
				if(allDishes.get(i).getId()==dishes.get(j).getId()) {
					allDishes.get(i).setSelected(true);
				}
			}
		}
		
		loadDishes(model,session);
		
		return "redirect:/eventDishEdit.html";
	}
	
	@RequestMapping(value = "/editEventDetail", method=RequestMethod.POST)
	public String editEventDetail(@ModelAttribute("eventUpdate") Event event, Model model, HttpSession session, BindingResult result) throws ParseException{
		Event eventAux = (Event) session.getAttribute("eventD");
		
		System.out.println("Fecha termino de event: "+event.getFechaTermino());
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date hoy = new Date();
		Date fTerm = formato.parse(event.getFechaTermino());
		
		if(event.getCantidad()==0) {
			System.out.println("Error: la cantidad de pedidos disponibles no puede ser 0");
			result.addError(new FieldError("event", "cantidad", "La cantidad de pedidos disponibles no puede ser 0"));
		}else if(event.getPrecio()==0) {
			System.out.println("Error: el precio del evento no puede ser 0");
			result.addError(new FieldError("event", "precio", "El precio del evento no puede ser 0"));
		}else if(fTerm.before(hoy)) {
			System.out.println("Error: la fecha de término no puede ser anterior a la fecha actual");
			result.addError(new FieldError("event", "fechaTermino", "La fecha de término no puede ser anterior a la fecha actual"));
		}
		
		if(result.hasErrors()) {
			return "eventInfoEdit";
		}
		
		eventAux.setNombre(event.getNombre());
		eventAux.setDescripcion(event.getDescripcion());
		eventAux.setFechaTermino(event.getFechaTermino());
		eventAux.setCantidad(event.getCantidad());
		eventAux.setCantidadDisp(event.getCantidad());
		eventAux.setHabilitado(true);
		eventAux.setPrecio(event.getPrecio());
		System.out.println("Seteados atributos de eventAux...");
		
		eventService.save(eventAux);
		System.out.println("Guardado objeto eventAux en bdd...");
		
		session.setAttribute("eventD", eventAux);
		System.out.println("Seteado atributo eventD en session...");
		
		System.out.println("Retornando a myEvents...");
		return "eventInfo";
	}
	
	@GetMapping("/removeDish/{id}")
	public String removeDish(@PathVariable(value="id") String id, Model model, HttpSession session) {
		System.out.println("Entrando a removeDish...");
		
		Dish dishAux = dishService.getDishById(Integer.parseInt(id));
		dishAux.setSelected(false);
		dishService.save(dishAux);
		System.out.println("Platillo: "+dishAux.getNombre()+" ha sido quitado del evento...");
		
		return "redirect:/eventCreate.html";
	}
	
	@GetMapping("/eventUp/{id}")
	public String eventUp(@PathVariable(value="id") String id, Model model, HttpSession session) {
		System.out.println("Entrando a eventUp...");
		
		int idEv = Integer.parseInt(id);
		/*Event event = eventService.getEventById(idEv);
		System.out.println("Encontrado evento id: "+event.getIdEvent());
		
		model.addAttribute("eventInfo", event);
		System.out.println("Evento eventInfo agregado a model");
		*/
		loadEvent(model,session,idEv);
		model.addAttribute("eventUpdate", new Event());
		
		System.out.println("redireccionando eventInfo.html...");
		
		return "redirect:/eventInfo.html";
	}
	
	@RequestMapping(value="/addEvent", method=RequestMethod.POST)
	public String addEvent(@ModelAttribute("event") Event event, Model model, HttpSession session, BindingResult result) throws ParseException{
		System.out.println("Iniciando addEvent...");
		
		System.out.println("Fecha termino de event: "+event.getFechaTermino());
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date hoy = new Date();
		Date fTerm = formato.parse(event.getFechaTermino());
		
		
		System.out.println("Fecha hoy: "+hoy);
		System.out.println("Fecha termino: "+fTerm);
		
		if(event.getCantidad()==0) {
			System.out.println("Error: la cantidad de pedidos disponibles no puede ser 0");
			result.addError(new FieldError("event", "cantidad", "La cantidad de pedidos disponibles no puede ser 0"));
		}else if(event.getPrecio()==0) {
			System.out.println("Error: el precio del evento no puede ser 0");
			result.addError(new FieldError("event", "precio", "El precio del evento no puede ser 0"));
		}else if(fTerm.before(hoy)) {
			System.out.println("Error: la fecha de término no puede ser anterior a la fecha actual");
			result.addError(new FieldError("event", "fechaTermino", "La fecha de término no puede ser anterior a la fecha actual"));
		}
		
		if(result.hasErrors()) {
			loadDishes(model,session);
			return "eventCreateForm";
		}
		
		List <Dish> dishes = dishService.getAllDishes();
		List<Contain> allContained = new ArrayList<Contain>();
		Event eventAux = new Event();
		
		
		
		System.out.println("Lista de platillos contenidos en evento cargada en dishesContained...");
		
		java.util.Date dJava = new java.util.Date();
		java.sql.Date dSqlInicio = new java.sql.Date(dJava.getTime());
		System.out.println("Fecha de Creación: "+ dSqlInicio);
		
		//java.sql.Date dSqlTermino = new java.sql.Date(event.getFechaTermino().getTime());
		//System.out.println("Fecha de Término: "+dSqlTermino);
		
		User empresa = (User) session.getAttribute("user");
		
		eventAux.setNombre(event.getNombre());
		eventAux.setDescripcion(event.getDescripcion());
		eventAux.setFechaCreacion(dSqlInicio);
		eventAux.setFechaTermino(event.getFechaTermino());
		eventAux.setPrecio(event.getPrecio());
		eventAux.setCantidad(event.getCantidad());
		eventAux.setCantidadDisp(eventAux.getCantidad());
		eventAux.setHabilitado(true);
		
		
		System.out.println("id empresa: "+empresa.getIdCliente());
		eventAux.setEmpresa(empresa);
		
		System.out.println("dishes size: "+dishes.size());
		eventService.save(eventAux);
		System.out.println("Guardado en base de datos eventAux 1era vez antes del For...");
		
		Event eventAux2 = eventService.getEventsByNombre(eventAux.getNombre());
		
		
		
		for(int i=0; i<dishes.size();i++) {
			if(dishes.get(i).isSelected()) {
				System.out.println("Platillo: "+dishes.get(i).getNombre()+" seleccionado...");
				Contain contained = new Contain();
				
				contained.setEventoContener(eventAux2);
				System.out.println("Seteado en contained evento...");
				
				System.out.println("Platillo: "+ dishes.get(i).getNombre()+" agregado a dishesContained...");
				contained.setPlatilloContener(dishes.get(i));
				
				containService.save(contained);
				System.out.println("Guardado en base de datos contained...");
				
				allContained.add(contained);
				System.out.println("Agregado contained a allContained...");
			}
		}
		
		eventAux2.setMisPlatillos(allContained);
		eventService.save(eventAux2);
		
		loadMyEvents(model,session);
		unselectDishes(model,session);
		return "myEvents";
	}
	
	@GetMapping("/eventDet/{id}")
	public String eventDet(@PathVariable(value="id") String id, Model model, HttpSession session) {
		System.out.println("Entrando a eventDet...");
		
		int idEvent = Integer.parseInt(id);
		loadEvent(model,session,idEvent);
		System.out.println("Cargando evento a session y retornando a eventDetail...");
		
		return "redirect:/eventDetail.html";
	}
	
	@RequestMapping(value="/addNewOrder", method=RequestMethod.POST)
	public String addNewOrder(@ModelAttribute("newOrder") Participation newOrder, Model model, HttpSession session, BindingResult result) {
		System.out.println("Iniciando addNewOrder...");
		
		Event event = (Event) session.getAttribute("eventD");
		User client = (User) session.getAttribute("user");
		
		if(newOrder.getCantidad()==0) {
			System.out.println("Error: la cantidad de pedidos no puede ser 0...");
			result.addError(new FieldError("newOrder", "cantidad", "La cantidad de pedidos no puede ser 0"));
		}else if(event.getCantidadDisp()<newOrder.getCantidad()) {
			System.out.println("Error: la cantidad de pedidos es superior a la cantidad disponible del evento!");
			result.addError(new FieldError("newOrder", "cantidad", "La cantidad de pedidos es superior a la cantidad disponible del evento"));
		}else if(event.getCantidadDisp()==newOrder.getCantidad()) {
			//event.setHabilitado(false);
			System.out.println("Se han pedido todos los platillos disponibles, el evento ya no tiene stock");
		}
		if(result.hasErrors()) return "eventDetail";
		
		event.setCantidadDisp(event.getCantidadDisp()-newOrder.getCantidad());
		eventService.save(event);
		
		System.out.println("Cantidad de pedidos: "+newOrder.getCantidad());
		System.out.println("Nueva cantidad disponible: "+ event.getCantidadDisp());
		
		newOrder.setCliente(client);
		newOrder.setEvento(event);
		newOrder.setVigente(true);
		System.out.println("Seteado cliente: "+client.getNombre());
		System.out.println("Seteado evento: "+event.getNombre());
		
		participationService.save(newOrder);
		System.out.println("Guardado el pedido");
		
		//Cambiar a myOrders después de probar
		return "redirect:/myOrders.html";
	}
	
	@GetMapping("/orderDet/{id}")
	public String orderDet(@PathVariable(value="id") String id, Model model, HttpSession session) {
		System.out.println("Entrando a orderDet...");
		
		int idOrder = Integer.parseInt(id);
		loadOrderDetail(model,session,idOrder);
		System.out.println("Cargando participation a session y retornando a orderDetail...");
		
		return "redirect:/orderDetail.html";
	}
	
	@GetMapping("/cancelOrder")
	public String cancelOrder(Model model, HttpSession session) {
		System.out.println("Iniciando cancelación de pedido...");
		
		User user = (User) session.getAttribute("user");
		
		Participation order = (Participation) session.getAttribute("order");
		Event event = (Event) session.getAttribute("eventD");
		System.out.println("Cantidad del pedido: "+order.getCantidad());
		System.out.println("Cantidad disponible del evento: "+event.getCantidadDisp());
		
		event.setCantidadDisp(event.getCantidadDisp()+order.getCantidad());
		System.out.println("Cantidad disponible del evento luego de sumar: "+event.getCantidadDisp());
		
		order.setVigente(false);
		
		participationService.save(order);
		eventService.save(event);
		System.out.println("Pedido cancelado y evento restaurado, ambos guardados...");
		
		loadUserOrders(model,session,user);
		
		System.out.println("Redirigiendo a myOrders.html...");
		return "myOrders.html";
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
	
	public void loadOrderDetail(Model model, HttpSession session, int idParticipation) {
		System.out.println("Iniciando la carga de los detalles de participation...");
		
		Participation order = participationService.getParticipationById(idParticipation);
		System.out.println("id evento: "+ order.getEvento().getIdEvent());
		
		loadEvent(model,session,order.getEvento().getIdEvent());
		session.setAttribute("order", order);
		System.out.println("Agregado order a session...");
		
	}
	
	public void loadEvent(Model model, HttpSession session, int id) {
		System.out.println("Iniciando carga de datos de evento seleccionado en eventDetail...");
		
		Event event = eventService.getEventById(id);
		List<Dish> dishesFromEvent = new ArrayList<Dish>(); 
		System.out.println("Obtenido evento: "+event.getNombre());
		
		List<Contain> todosContains = containService.getContainsByEventoContener(event);
		System.out.println("todosContains size: "+ todosContains.size());
		
		for(int i=0; i<todosContains.size();i++) {
			dishesFromEvent.add(todosContains.get(i).getPlatilloContener());
			System.out.println("Platillo: "+dishesFromEvent.get(i).getNombre()+" agregado a dishesFromEvent...");
		}
		
		List<Participation> partEvent = participationService.getParticipationByEvento(event);
		/*List<User> clients = new ArrayList<User>();
		
		for(int i=0; i<partEvent.size(); i++) {
			clients.add(partEvent.get(i).getCliente());
			System.out.println("Cliente: "+ clients.get(i).getNombre()+" participó en evento "+partEvent.get(i).getEvento().getNombre());
		}*/
		
		System.out.println("Participantes size: "+event.getMisParticipantes().size());
		
		session.setAttribute("clients", partEvent);
		session.setAttribute("eventD", event);
		session.setAttribute("dishesFromEvent", dishesFromEvent);
		System.out.println("Agregados dishesFromEvent, eventD y clients a session...");
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
	
	public void unselectDishes(Model model, HttpSession session) {
		List<Dish> dishes = dishService.getAllDishes();
		for(int i=0; i<dishes.size();i++) {
			dishes.get(i).setSelected(false);
			dishService.save(dishes.get(i));
		}
	}
}
