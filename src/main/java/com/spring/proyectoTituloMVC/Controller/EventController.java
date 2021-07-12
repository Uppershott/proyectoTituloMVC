package com.spring.proyectoTituloMVC.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.proyectoTituloMVC.Entity.Dish;
import com.spring.proyectoTituloMVC.Service.DishService;

@Controller
@Scope ("session")
public class EventController {
	
	@Autowired
	DishService dishService;
	
	
	
	@RequestMapping(value = "/addDishEvent", method=RequestMethod.POST)
	public String addDishEvent(@ModelAttribute("dish") Dish dish, Model model, HttpSession session, BindingResult result) {
		System.out.println("Iniciando proceso de agregación de platillo a evento...");
		
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
	
	@GetMapping("/removeDish/{id}")
	public String removeDish(@PathVariable(value="id") String id, Model model, HttpSession session) {
		System.out.println("Entrando a removeDish...");
		
		Dish dishAux = dishService.getDishById(Integer.parseInt(id));
		dishAux.setSelected(false);
		dishService.save(dishAux);
		System.out.println("Platillo: "+dishAux.getNombre()+" ha sido quitado del evento...");
		
		return "redirect:/eventCreate.html";
	}
	
}
