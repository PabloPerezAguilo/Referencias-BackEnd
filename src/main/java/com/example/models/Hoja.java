package com.example.models;

import java.util.List;

public class Hoja extends Nodo{

	private String producto;
	private String tipo;
	private boolean estado;
	
	public Hoja(String nombre, List<Nodo> nodos,String producto, String tipo, boolean estado) {
		super(nombre, nodos);
		this.producto = producto;
		this.tipo = tipo;
		this.estado = estado;
	}

	
	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
}
