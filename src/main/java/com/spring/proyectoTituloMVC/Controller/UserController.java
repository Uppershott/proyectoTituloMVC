package com.spring.proyectoTituloMVC.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

import com.spring.proyectoTituloMVC.Entity.Event;
import com.spring.proyectoTituloMVC.Entity.User;
import com.spring.proyectoTituloMVC.Service.EventService;
import com.spring.proyectoTituloMVC.Service.UserService;

@Controller 
@Scope ("session")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	EventService eventService;
	
	@GetMapping("/login.html")
	public String login(Model model, HttpSession session){
		
		model.addAttribute("user", new User());
		session.setAttribute("user", new User());
		return "login";
	}
	
	@RequestMapping(value="/clientRegistering", method=RequestMethod.POST)
	public String clientRegistering(@ModelAttribute("user") User user, Model model, BindingResult result, HttpSession session) {
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
				
		user.setRol(1);
		user.setHabilitado(true);
		System.out.println(user.getNombre()+" "+user.getPassword());
		userService.save(user);
		model.addAttribute("user", user);
		session.setAttribute("user", user);
		return "index"; //login
	}
	
	@RequestMapping(value="/companyRegistering", method=RequestMethod.POST)
	public String companyRegistering(@ModelAttribute("user") User user, Model model, BindingResult result, HttpSession session) {
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
		}else if(user.getRubro()==0) {
			System.out.println("Error: Debe seleccionar un rubro!");
			result.addError(new FieldError("user", "rubro", "Debe seleccionar un rubro!"));
		}
		
		if(result.hasErrors()) return "register";
				
		user.setRol(2);
		user.setHabilitado(true);
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
			
			if(!userAux.isHabilitado()) {
				System.out.println("Error: Usuario baneado/deshabilitado");
				result.addError(new FieldError("user", "habilitado", "El Usuario se encuentra vetado"));
			}else {
				session.setAttribute("user", userAux);
				System.out.println("Agregado user: "+userAux.getNombre()+" a session...");
				model.addAttribute("user", userAux);
				System.out.println("Agregado user: "+userAux.getNombre()+" a model...");
				
				//Si el rol es de administrador
				if(userAux.getRol()==0) {
					return "indexAdmin";
				}
			}
		}
		
		if(result.hasErrors()) return "login";
		
		return "index";
	}
	
	@RequestMapping(value="/profileEditing", method=RequestMethod.POST)
	public String profileEditing(@ModelAttribute("userEdit") User userEdit, Model model, HttpSession session, BindingResult result) {
		System.out.println("Iniciando el proceso de verificación en cambio de datos de perfil...");
		
		/*
		if(userEdit.getNombre()==null) {
			System.out.println("Error: el nombre se encuentra vacío");
			result.addError(new FieldError("userEdit", "nombre", "El nombre se encuentra vacío"));
		}else if(userEdit.getCorreo() == null) {
			System.out.println("Error: el correo se encuentra vacío");
			result.addError(new FieldError("userEdit", "correo", "El correo se encuentra vacío"));
		}else if(userEdit.getTelefono()==0) {
			System.out.println("Error: el teléfono se encuentra vacío");
			result.addError(new FieldError("userEdit", "telefono", "El teléfono se encuentra vacío"));
		}else if(userEdit.getDireccion()==null) {
			System.out.println("Error: la dirección se encuentra vacía");
			result.addError(new FieldError("userEdit", "direccion", "La direccion se encuentra vacía"));
		}else 
		*/
		
		User userAux = (User) session.getAttribute("user");
		
		if(userService.getUserByCorreo(userEdit.getCorreo())!=null && !userAux.getCorreo().equalsIgnoreCase(userEdit.getCorreo())) {
			System.out.println("Error: El correo ya se encuentra registrado");
			result.addError(new FieldError("userEdit","correo", "El correo ingresado ya se encuentra registrado"));
		}
		
		if(result.hasErrors()) return "profileEdit";
		
		System.out.println("Iniciando proceso de guardado de nuevos datos de perfil");
		
		System.out.println("Usuario: "+ userAux.getNombre()+" obtenido de session");
		
		userAux.setNombre(userEdit.getNombre());
		userAux.setCorreo(userEdit.getCorreo());
		userAux.setTelefono(userEdit.getTelefono());
		userAux.setDireccion(userEdit.getDireccion());
		if(userAux.getRol()==2) userAux.setLink1(userEdit.getLink1());
		userAux.setLink2(userEdit.getLink2());
		userAux.setLink3(userEdit.getLink3());
		
		System.out.println("Nuevos datos de Usuario: "+userAux.getNombre()+" preparado para guardar cambios");
		userService.save(userAux);
		System.out.println("Nuevos datos de usuario guardados con éxito");
		
		return "profile";
	}
	
	@GetMapping("/disableUser/{id}")
	public String disableUser(@PathVariable(value="id") String id, Model model, HttpSession session) {
		System.out.println("Entrando a disableUser");
		
		User userAux = userService.getUserById(Integer.parseInt(id));
		userAux.setHabilitado(false);
		
		System.out.println("Usuario: "+ userAux.getNombre()+" deshabilitado...");
		
		userService.save(userAux);
		
		System.out.println("Terminando disableUser...");
		return "redirect:/usersAdmin.html";
	}
	
	@GetMapping("/enableUser/{id}")
	public String enableUser(@PathVariable(value="id") String id, Model model, HttpSession session) {
		System.out.println("Entrando a enableUser");
		
		User userAux = userService.getUserById(Integer.parseInt(id));
		userAux.setHabilitado(true);
		
		System.out.println("Usuario: "+ userAux.getNombre()+" habilitado...");
		
		userService.save(userAux);
		
		System.out.println("Terminando enableUser...");
		return "redirect:/usersAdmin.html";
	}
	
	@GetMapping("/companyDetail/{id}")
	public String companyDetail(@PathVariable(value="id") String id, Model model, HttpSession session) {
		System.out.println("Entrando a companyDetail...");
		
		int idComp = Integer.parseInt(id);
		
		User compAux = userService.getUserById(idComp);
		session.setAttribute("compDet", compAux);
		System.out.println("Empresa a ver detalle agregada a session");
		
		if(compAux.getMisEventos().size()>0) System.out.println("Evento 1: "+ compAux.getMisEventos().get(1).getNombre());
		session.setAttribute("events", compAux.getMisEventos());
		System.out.println("Lista de los eventos de la empresa agregada a session");
		
		return "redirect:/userDetail.html";
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
		
		Random r = new Random();
		System.out.println("Cantidad de eventos activos: "+activeEvents.size());
		for(int i=0; i<8; i++) {
			int nRandom = r.nextInt(((activeEvents.size()-2)-0)+1)-0;
			System.out.println("activeEvents size: "+activeEvents.size());
			indexEvents.add(activeEvents.get(nRandom));
			System.out.println("Evento: "+activeEvents.get(nRandom).getNombre()+" agregado a index...");
			activeEvents.remove(nRandom);
		}
		model.addAttribute("indexEvents", indexEvents);
		session.setAttribute("indexEvents", indexEvents);
		System.out.println("Seteado en model y session la lista de eventos a index...");
	}
	
	/*public void loadCompanyEvents(Model model, HttpSession session, int id) {
		System.out.println("Iniciando carga de eventos de la empresa...");
		
		User comp = userService.getUserById(id);
		List<Event> events = comp.getMisEventos();
		if(events.size()>0)System.out.println("Empresa: "+comp.getNombre()+" Evento 1: "+events.get(0).getNombre());
		
		model.addAttribute("events",events);
	}*/
	
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
	/*@RequestMapping(value = "/register", method=RequestMethod.GET)
	public String register(Model model, HttpSession session) {
		model.addAttribute("user", new User());
		session.setAttribute("user", new User());
		return "register";
	}
	 * Función para elegir entre empresa y cliente
	@RequestMapping(value="/rolSelection", method=RequestMethod.POST)
	public String rolSelection(@ModelAttribute("userRol") User user, Model model, BindingResult result, HttpSession session) {
		System.out.println("Registro rol de usuario: "+user.getRol());
		if(user.getRol()==0) {
			System.out.println("Error: No se ha seleccionado una opción de rol válida en registro");
			result.addError(new FieldError("user", "rol", "Debe seleccionar una opción válida"));
		}
		if(result.hasErrors())return "register";
		model.addAttribute("user", user);
		session.setAttribute("user", user);
		return "register";
	}
	*/
	
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
