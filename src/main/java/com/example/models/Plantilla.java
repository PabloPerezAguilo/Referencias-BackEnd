package com.example.models;

import org.jongo.marshall.jackson.oid.Id;

public class Plantilla {
	
	@Id
	private String _id;
	private String nombre;
	private String creador;
	private boolean publica;
	
	public Plantilla() {
		
	}
	public Plantilla(String _id, String nombre,String creador,boolean publica) {
		
		this._id = _id;
		this.nombre = nombre;
		this.creador = creador;
		this.publica = publica;
		
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
	}
	public boolean isPublica() {
		return publica;
	}
	public void setPublica(boolean publica) {
		this.publica = publica;
	}
	
	@Override
	public String toString() {
		return "Plantillas [_id=" + _id + ", nombre=" + nombre + ", creador="
				+ creador + ", publica=" + publica + "]";
	}
	

}
