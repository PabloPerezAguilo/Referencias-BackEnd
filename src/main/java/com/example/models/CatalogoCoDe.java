package com.example.models;

import org.jongo.marshall.jackson.oid.Id;

public class CatalogoCoDe {
	
	@Id
	private String codigo;
	private String descripcion;
	private String entidad;
	
	public CatalogoCoDe() {
		
	}
	public CatalogoCoDe(String codigo, String descripcion,String entidad) {
		
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.entidad = entidad;
		
	}
	public CatalogoCoDe(CatalogoCoDe cat) {
		
		this.codigo = cat.getCodigo();
		this.descripcion = cat.getDescripcion();
		this.entidad = cat.getEntidad();
		
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

}
