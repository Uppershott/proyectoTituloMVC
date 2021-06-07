package com.spring.proyectoTituloMVC.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


//Entidad intermedia entre Usuario como Cliente y Evento
@Entity
@NamedQuery(name="Participation.findAll", query="SELECT p FROM Participation p")
@Table(name="Participation")
public class Participation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="idEvento")
	private Event evento;
	
	@ManyToOne
	@JoinColumn(name="idCliente")
	private User cliente;
	
	@Column(name="cantidad")
	private int cantidad;
	
	@Column(name="direccion")
	private String direccion;
	
	@Column(name="retira")
	private boolean retira;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Event getEvento() {
		return evento;
	}

	public void setEvento(Event evento) {
		this.evento = evento;
	}

	public User getCliente() {
		return cliente;
	}

	public void setCliente(User cliente) {
		this.cliente = cliente;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public boolean isRetira() {
		return retira;
	}

	public void setRetira(boolean retira) {
		this.retira = retira;
	}
	
	
}
