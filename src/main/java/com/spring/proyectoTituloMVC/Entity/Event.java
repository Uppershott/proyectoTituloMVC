package com.spring.proyectoTituloMVC.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
@Table (name="event")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idEvent;
	
	@Column (name="nombre")
	private String nombre;
	
	@Column (name="descripcion")
	private String descripcion;
	
	@Column (name="fechaCreacion")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	
	@Column (name="fechaInicio")
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	
	@Column (name="fechaTermino")
	//@DateTimeFormat(pattern = "dd/MM/yyyy")
	//@Temporal(TemporalType.DATE)
	private String fechaTermino;
	
	//posible a salir de acá
	@Column (name="detalle")
	private String detalle;
	
	
	@Column (name="precio")
	private int precio;
	
	@Column (name="cantidad")
	private int cantidad;
	
	private int cantidadDisp;
	
	private boolean habilitado;
	
	//relación bi-direccional con User rol empresa
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn (name="idEmpresa", nullable=false)
	private User empresa;
	
	//relación bi-direccional con User rol cliente. A un Evento participan muchos Clientes
	@OneToMany(mappedBy="evento", cascade = {CascadeType.ALL})
	private List<Participation> misParticipantes;
	
	//relación bi-direccional con Dish. Un Evento puede contener muchos platillos.
	@OneToMany(mappedBy="eventoContener", cascade = {CascadeType.ALL})
	private List<Contain> misPlatillos;

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaTermino() {
		return fechaTermino;
	}

	public void setFechaTermino(String fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public User getEmpresa() {
		return empresa;
	}

	public void setEmpresa(User empresa) {
		this.empresa = empresa;
	}

	public List<Participation> getMisParticipantes() {
		return misParticipantes;
	}

	public void setMisParticipantes(List<Participation> misParticipantes) {
		this.misParticipantes = misParticipantes;
	}

	public List<Contain> getMisPlatillos() {
		return misPlatillos;
	}

	public void setMisPlatillos(List<Contain> misPlatillos) {
		this.misPlatillos = misPlatillos;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCantidadDisp() {
		return cantidadDisp;
	}

	public void setCantidadDisp(int cantidadDisp) {
		this.cantidadDisp = cantidadDisp;
	}
	
	
}
