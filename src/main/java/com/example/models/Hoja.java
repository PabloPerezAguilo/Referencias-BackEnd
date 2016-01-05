package com.example.models;

import java.util.List;

public class Hoja extends NodoOld{

	private String producto;
	private String tipo;
	private boolean validado;
	private List<NodoOld> nodosPadre ;
	
	public Hoja(String nombre, List<NodoOld> nodos,String producto, String tipo, boolean estado,List<NodoOld> nodosPadre) {
		super(nombre, nodos);
		this.producto = producto;
		this.tipo = tipo;
		this.validado = estado;
		this.nodosPadre = nodosPadre;
	}

	
	public boolean isValidado() {
		return validado;
	}


	public void setValidado(boolean validado) {
		this.validado = validado;
	}


	public List<NodoOld> getNodosPadre() {
		return nodosPadre;
	}


	public void setNodosPadre(List<NodoOld> nodosPadre) {
		this.nodosPadre = nodosPadre;
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
}
