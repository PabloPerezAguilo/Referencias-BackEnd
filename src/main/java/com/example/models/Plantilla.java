package com.example.models;

import org.jongo.marshall.jackson.oid.Id;

public class Plantilla {
	
	@Id
	private String _id;
	private String nombre;
	private String creador;
	private boolean publica;
	private String tipoDocumento;
	
	public Plantilla() {
		
	}
	public Plantilla(String _id, String nombre,String creador,boolean publica,String tipoDocumento) {
		
		this._id = _id;
		this.nombre = nombre;
		this.creador = creador;
		this.publica = publica;
		this.tipoDocumento = tipoDocumento;
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
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	@Override
	public String toString() {
		return "Plantilla [_id=" + _id + ", nombre=" + nombre + ", creador="
				+ creador + ", publica=" + publica + ", tipoDocumento="
				+ tipoDocumento + "]";
	}
	
	

}
