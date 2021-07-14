package com.spring.proyectoTituloMVC.Entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


//Entidad intermedia entre Evento y Platillo
@Entity
@NamedQuery(name="Contain.findAll", query="SELECT c FROM Contain c")
@Table (name="contain")
public class Contain implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="idPlatillo")
	private Dish platilloContener;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="idEvento")
	private Event eventoContener;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Dish getPlatilloContener() {
		return platilloContener;
	}

	public void setPlatilloContener(Dish platilloContener) {
		this.platilloContener = platilloContener;
	}

	public Event getEventoContener() {
		return eventoContener;
	}

	public void setEventoContener(Event eventoContener) {
		this.eventoContener = eventoContener;
	}
	
	
}
