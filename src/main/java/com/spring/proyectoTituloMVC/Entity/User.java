package com.spring.proyectoTituloMVC.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table (name="user")
public class User {
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
	
	@Column(name="rol")
	@NotNull
	private int rol;
	//rol = 1, cliente. rol = 2, empresa
	
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
}
