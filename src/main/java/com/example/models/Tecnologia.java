package com.example.models;

import java.util.List;

import org.jongo.marshall.jackson.oid.Id;

public class Tecnologia {
	
	@Id
	private String nombre;
	private List<Tecnologia> nodosHijos ;
	private List<Tecnologia> nodosPadre ;
	private boolean producto;
	private String tipo;
	private String clase;
	
	//contructor para hojas
	public Tecnologia(String nombre, List<Tecnologia> nodosHijos, List<Tecnologia> nodosPadre, boolean producto , String tipo, String clase ){
		
		this.nombre = nombre;
		this.nodosHijos = nodosHijos;
		this.nodosPadre = nodosPadre;
		this.producto = producto;
		this.tipo = tipo;
		this.clase = clase;
		
	}
	//constructor para nodos
	public Tecnologia(String nombre, List<Tecnologia> nodosHijos, List<Tecnologia> nodosPadre){
		
		this.nombre = nombre;
		this.nodosHijos = nodosHijos;
		this.nodosPadre = nodosPadre;
		this.tipo = null;
		this.clase = "nodo";
		
	}
	public Tecnologia(){
		
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<Tecnologia> getNodosHijos() {
		return nodosHijos;
	}
	public void setNodosHijos(List<Tecnologia> nodosHijos) {
		this.nodosHijos = nodosHijos;
	}
	public List<Tecnologia> getNodosPadre() {
		return nodosPadre;
	}
	public void setNodosPadre(List<Tecnologia> nodosPadre) {
		this.nodosPadre = nodosPadre;
	}
	public boolean isProducto() {
		return producto;
	}
	public void setProducto(boolean producto) {
		this.producto = producto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	@Override
	public String toString() {
		return "Nodo [nombre=" + nombre + ", nodosHijos=" + nodosHijos
				+ ", nodosPadre=" + nodosPadre + ", producto=" + producto
				+ ", tipo=" + tipo + ", clase=" + clase + "]";
	}
}
