package com.example.models;

import java.util.List;
import java.util.Map;


public class Tecnologia {
	
	
	private String nombre;
	private List<Tecnologia> nodosHijos ;
	private boolean producto;
	private String tipo;
	private String clase;
	
	public Tecnologia(){
		
	}
	public Tecnologia(String nombre,List<Tecnologia> nodosHijos, boolean producto , String tipo, String clase ){
		
		this.nombre = nombre;
		this.producto = producto;
		this.nodosHijos = nodosHijos;
		this.tipo = tipo;
		this.clase = clase;
		
	}
	public Tecnologia(Map<String,Object> tecnologia){
		
		this.nombre = (String)tecnologia.get("nombre");
		if(tecnologia.get("producto")!=null){
			this.producto = (boolean)tecnologia.get("producto");
		}else{
			this.producto = false;
		}
		this.nodosHijos = (List<Tecnologia>)tecnologia.get("nodosHijos");
		this.tipo = (String)tecnologia.get("tipo");
		this.clase = (String)tecnologia.get("clase");
		
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
				+ ", producto=" + producto + ", tipo=" + tipo + ", clase=" + clase + "]";
	}
}
