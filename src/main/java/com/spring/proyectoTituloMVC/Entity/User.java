package com.spring.proyectoTituloMVC.Entity;

import java.util.List;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@Table (name="user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idCliente;
	
	@Column(name="rut")
	@NotEmpty @NotNull
	private String rut;

	@Column(name="nombre")
	@NotEmpty(message="El nombre no puede estar vacío..")
	private String nombre;
	
	@Column(name="telefono")
	private int telefono;
	
	@Column(name="correo")
	@NotEmpty
	private String correo;
	
	@Column(name="direccion")
	@NotEmpty
	private String direccion;
	
	@Column(name="password")
	@NotEmpty @NotNull
	private String password;
	
	private String link1;
	
	private String link2;
	
	private String link3;
	
	//1: Restaurant, 2: Cafetería, 3: Delivery, 4: Negocio Pequeño, 5: Otros.
	private int rubro;
	
	private boolean habilitado;
	
	//rol = 1, cliente. rol = 2, empresa. rol = 0, administrador
	@Column(name="rol")
	@NotNull
	private int rol;
	
	//relación bi-direccional con Event. User.empresa crea eventos
	@OneToMany(mappedBy="empresa", cascade = {CascadeType.MERGE})
	private List<Event> misEventos;
	
	//relación bi-direccional con User rol cliente. Un Cliente participa a muchos Eventos
	@OneToMany(mappedBy="cliente", cascade = {CascadeType.ALL})
	private List<Participation> misParticipaciones;
	
	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	
	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<Event> getMisEventos() {
		return misEventos;
	}

	public void setMisEventos(List<Event> misEventos) {
		this.misEventos = misEventos;
	}

	public List<Participation> getMisParticipaciones() {
		return misParticipaciones;
	}

	public void setMisParticipaciones(List<Participation> misParticipaciones) {
		this.misParticipaciones = misParticipaciones;
	}

	public String getLink1() {
		return link1;
	}

	public void setLink1(String link1) {
		this.link1 = link1;
	}

	public String getLink2() {
		return link2;
	}

	public void setLink2(String link2) {
		this.link2 = link2;
	}

	public String getLink3() {
		return link3;
	}

	public void setLink3(String link3) {
		this.link3 = link3;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public int getRubro() {
		return rubro;
	}

	public void setRubro(int rubro) {
		this.rubro = rubro;
	}
	
}
