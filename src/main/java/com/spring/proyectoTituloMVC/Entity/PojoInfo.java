package com.spring.proyectoTituloMVC.Entity;

public class PojoInfo {
	private double totalDishes;
	private String nombrePlatillo;
	private int contVecesUso;
	private int contPrecio;
	
	public PojoInfo(double totalDishes, String nombrePlatillo, int contVecesUso, int contPrecio) {
		super();
		this.totalDishes = totalDishes;
		this.nombrePlatillo = nombrePlatillo;
		this.contVecesUso = contVecesUso;
		this.contPrecio = contPrecio;
	}
	
	public PojoInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public double getTotalDishes() {
		return totalDishes;
	}
	public void setTotalDishes(double totalDishes) {
		this.totalDishes = totalDishes;
	}
	public String getNombrePlatillo() {
		return nombrePlatillo;
	}
	public void setNombrePlatillo(String nombrePlatillo) {
		this.nombrePlatillo = nombrePlatillo;
	}
	public int getContVecesUso() {
		return contVecesUso;
	}
	public void setContVecesUso(int contVecesUso) {
		this.contVecesUso = contVecesUso;
	}
	public int getContPrecio() {
		return contPrecio;
	}
	public void setContPrecio(int contPrecio) {
		this.contPrecio = contPrecio;
	}
	
	
}
