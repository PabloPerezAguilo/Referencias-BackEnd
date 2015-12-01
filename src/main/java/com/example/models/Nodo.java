package com.example.models;

import org.jongo.marshall.jackson.oid.Id;
import java.util.List;


public class Nodo {
	@Id
	private String nombre;
	private List<Nodo> nodosPadre ;
	
	public Nodo(String nombre, List<Nodo> nodos){
		
		this.nombre = nombre;
		nodosPadre = nodos;
		
	}
	public Nodo(){
		
	}
	
	public List<Nodo> getNodosPadre() {
		return nodosPadre;
	}
	public void setNodosPadre(List<Nodo> nodosPadre) {
		this.nodosPadre = nodosPadre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString() {
		return "nodo [nombre=" + nombre + "]";
	}
	
}