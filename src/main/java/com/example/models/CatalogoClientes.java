package com.example.models;

import java.util.Map;

import org.jongo.marshall.jackson.oid.Id;

public class CatalogoClientes {

	@Id
	private String nombre;
	private String siglas;
	private boolean publico;
	private String alias;
	private String imagen;
	
	public CatalogoClientes() {
		
	}
	public CatalogoClientes(String nombre, String siglas, boolean publico, String alias, String imagen) {
		
		this.nombre = nombre;
		this.siglas = siglas;
		this.publico = publico;
		this.alias = alias;
		this.imagen = imagen;
		
	}
	public CatalogoClientes(CatalogoClientes cliente) {
		
		this.nombre = cliente.getNombre();
		this.siglas = cliente.getSiglas();
		this.publico = cliente.isPublico();
		this.alias = cliente.getAlias();
		this.imagen = cliente.getImagen();
		
	}
	public CatalogoClientes(Map<String,Object> cliente) {
		
		this.nombre = (String) cliente.get("nombre");
		this.siglas = (String) cliente.get("siglas");
		this.publico = (boolean) cliente.get("publico");
		this.alias = (String) cliente.get("alias");
		this.imagen = (String) cliente.get("imagen");
		
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSiglas() {
		return siglas;
	}
	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}
	public boolean isPublico() {
		return publico;
	}
	public void setPublico(boolean publico) {
		this.publico = publico;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	@Override
	public String toString() {
		return "CatalogoClientes [nombre=" + nombre + ", siglas=" + siglas	+ ", publico=" + publico + ", alias=" + alias + "]";
	}
}
