package com.spring.proyectoTituloMVC.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	
	@Column (name="fechaInicio")
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	
	@Column (name="fechaTermino")
	@Temporal(TemporalType.DATE)
	private Date fechaTermino;
	
	//posible a salir de acá
	@Column (name="detalle")
	private String detalle;
	
	
	@Column (name="precio")
	private Double precio;
	
	@Column (name="cantidad")
	private int cantidad;
	
	private boolean habilitado;
	
	//relación bi-direccional con User rol empresa
	@ManyToOne
	@JoinColumn (name="idEmpresa", nullable=false)
	private User empresa;
	
	//relación bi-direccional con User rol cliente. A un Evento participan muchos Clientes
	@OneToMany(mappedBy="evento")
	private List<Participation> misParticipantes;
	
	//relación bi-direccional con Dish. Un Evento puede contener muchos platillos.
	@OneToMany(mappedBy="eventoContener")
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

	public Date getFechaTermino() {
		return fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
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
	
}
