package com.example.models;

import org.jongo.marshall.jackson.oid.Id;
import java.util.List;


public class NodoOld {
	@Id
	private String nombre;
	private List<NodoOld> nodosHijos ;
	
	public NodoOld(String nombre, List<NodoOld> nodos){
		
		this.nombre = nombre;
		nodosHijos = nodos;
		
	}
	public NodoOld(){
		
	}
	
	public List<NodoOld> getNodosPadre() {
		return nodosHijos;
	}
	public void setNodosPadre(List<NodoOld> nodosPadre) {
		this.nodosHijos = nodosPadre;
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