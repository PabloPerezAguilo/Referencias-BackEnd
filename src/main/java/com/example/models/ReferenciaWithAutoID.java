package com.example.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jongo.marshall.jackson.oid.Id;

import com.example.utils.ObjectIdJsonSerializer;

public class ReferenciaWithAutoID implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	@Id
	@JsonSerialize(using = ObjectIdJsonSerializer.class)
	private ObjectId _id;
	private String cliente;
	private String sociedad;
	private String sectorEmpresarial;
	private String tipoProyecto;
	private String tipoActividad;
	private Date fechaInicio;
	private int duracionMeses;
	private String denominacion;
	private String resumenProyecto;
	private String problematicaCliente;
	private String solucionGfi;
	private String[] tecnologias;
	private int fteTotales;
	private String imagenProyecto;
	private String certificado;
	private String[] regPedidoAsociadoReferencia;
	private String responsableComercial;
	private String responsableTecnico;
	private String creadorReferencia;
	private String codigoQr;
	private String estado;
	private String motivoRechazo;

	public ReferenciaWithAutoID() {
	}
	
	public ReferenciaWithAutoID(String cliente, String sociedad,
			String sectorEmpresarial, String tipoProyecto,String tipoActividad, Date fechaInicio,
			int duracionMeses, String denominacion, String resumenProyecto,
			String problematicaCliente, String solucionGfi,
			String[] tecnologias, int fteTotales, String imagenProyecto,
			String certificado, String[] regPedidoAsociadoReferencia,
			String responsableComercial, String responsableTecnico,
			String creadorReferencia, String codigoQr, String estado,String motivoRechazo) {
		super();
		this.cliente = cliente;
		this.sociedad = sociedad;
		this.sectorEmpresarial = sectorEmpresarial;
		this.tipoProyecto = tipoProyecto;
		this.tipoActividad = tipoActividad;
		if(fechaInicio==null){
			this.fechaInicio = new Date();
		}else{
			this.fechaInicio = fechaInicio;
		}
		this.duracionMeses = duracionMeses;
		this.denominacion = denominacion;
		this.resumenProyecto = resumenProyecto;
		this.problematicaCliente = problematicaCliente;
		this.solucionGfi = solucionGfi;
		this.tecnologias = tecnologias;
		this.fteTotales = fteTotales;
		this.imagenProyecto = imagenProyecto;
		this.certificado = certificado;
		this.regPedidoAsociadoReferencia = regPedidoAsociadoReferencia;
		this.responsableComercial = responsableComercial;
		this.responsableTecnico = responsableTecnico;
		this.creadorReferencia = creadorReferencia;
		this.codigoQr = codigoQr;
		this.estado = estado;
		this.motivoRechazo = motivoRechazo;
	}



	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getSociedad() {
		return sociedad;
	}

	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}

	public String getSectorEmpresarial() {
		return sectorEmpresarial;
	}

	public void setSectorEmpresarial(String sectorEmpresarial) {
		this.sectorEmpresarial = sectorEmpresarial;
	}

	public String getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(String tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

	public String getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public int getDuracionMeses() {
		return duracionMeses;
	}

	public void setDuracionMeses(int duracionMeses) {
		this.duracionMeses = duracionMeses;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getResumenProyecto() {
		return resumenProyecto;
	}

	public void setResumenProyecto(String resumenProyecto) {
		this.resumenProyecto = resumenProyecto;
	}

	public String getProblematicaCliente() {
		return problematicaCliente;
	}

	public void setProblematicaCliente(String problematicaCliente) {
		this.problematicaCliente = problematicaCliente;
	}

	public String getSolucionGfi() {
		return solucionGfi;
	}

	public void setSolucionGfi(String solucionGfi) {
		this.solucionGfi = solucionGfi;
	}  

	public String[] getTecnologias() {
		return tecnologias;
	}

	public void setTecnologias(String[] tecnologias) {
		this.tecnologias = tecnologias;
	}

	public int getFteTotales() {
		return fteTotales;
	}

	public void setFteTotales(int fteTotales) {
		this.fteTotales = fteTotales;
	}

	public String getImagenProyecto() {
		return imagenProyecto;
	}

	public void setImagenProyecto(String imagenProyecto) {
		this.imagenProyecto = imagenProyecto;
	}

	public String getCertificado() {
		return certificado;
	}
	
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public String[] getRegPedidoAsociadoReferencia() {
		return regPedidoAsociadoReferencia;
	}

	public void setRegPedidoAsociadoReferencia(String[] regPedidoAsociadoReferencia) {
		this.regPedidoAsociadoReferencia = regPedidoAsociadoReferencia;
	}

	public String getResponsableComercial() {
		return responsableComercial;
	}

	public void setResponsableComercial(String responsableComercial) {
		this.responsableComercial = responsableComercial;
	}

	public String getResponsableTecnico() {
		return responsableTecnico;
	}

	public void setResponsableTecnico(String responsableTecnico) {
		this.responsableTecnico = responsableTecnico;
	}

	public String getCreadorReferencia() {
		return creadorReferencia;
	}

	public void setCreadorReferencia(String creadorReferencia) {
		this.creadorReferencia = creadorReferencia;
	}

	public String getCodigoQr() {
		return codigoQr;
	}

	public void setCodigoQr(String codigoQr) {
		this.codigoQr = codigoQr;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	@Override
	public String toString() {
		return "ReferenciaWithAutoID [_id=" + _id + ", cliente=" + cliente
				+ ", sociedad=" + sociedad + ", sectorEmpresarial="
				+ sectorEmpresarial + ", tipoProyecto=" + tipoProyecto
				+ ", fechaInicio=" + fechaInicio + ", duracionMeses="
				+ duracionMeses + ", denominacion=" + denominacion
				+ ", resumenProyecto=" + resumenProyecto
				+ ", problematicaCliente=" + problematicaCliente
				+ ", solucionGfi=" + solucionGfi + ", tecnologias="
				+ tecnologias + ", fteTotales=" + fteTotales
				+ ", imagenProyecto=" + imagenProyecto + ", certificado="
				+ certificado + ", regPedidoAsociadoReferencia="
				+ Arrays.toString(regPedidoAsociadoReferencia)
				+ ", responsableComercial=" + responsableComercial
				+ ", responsableTecnico=" + responsableTecnico
				+ ", creadorReferencia=" + creadorReferencia + ", codigoQr="
				+ codigoQr + ", estado=" + estado +", motivoRechazo=" + motivoRechazo +"]";
	}

	
}
