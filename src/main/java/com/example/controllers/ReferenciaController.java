package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;











import javax.xml.bind.DatatypeConverter;








//import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;

import com.example.dao.ReferenciaDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.ReferenciaWithAutoID;
import com.example.models.Tecnologia;
import com.example.models.Usuario;
import com.example.utils.Config;

public class ReferenciaController {

	private static ReferenciaDAO dao;
	private static ReferenciaController singleton;

	private ReferenciaController() throws Exception {
		dao = ReferenciaDAO.getInstance();
	}

	public static ReferenciaController getInstance() throws Exception {
		if (singleton == null) {
			singleton = new ReferenciaController();
		}
		return singleton;
	}

	/**
	 * getReferencias.
	 * Recoge todas las referencias de la base de datos
	 * @return List<ReferenciaWithAutoID> 
	 * @throws Exception
	 */
	public List<ReferenciaWithAutoID> getReferencias() throws Exception {
		// Transform an iterator object to a list
		List<ReferenciaWithAutoID> list = new ArrayList<>();
		Iterator<ReferenciaWithAutoID> i = dao.getReferencias();
		while (i.hasNext()) {
			ReferenciaWithAutoID ref = i.next();
			byte[] imagenByte = null;
			try{
			imagenByte = Files.readAllBytes(Paths.get(Config.getInstance().getProperty(Config.PATH_IMAGENES)+ref.get_id()+".png"));
			}catch(Exception e){
			imagenByte = Files.readAllBytes(Paths.get(Config.getInstance().getProperty(Config.PATH_IMAGENES)+"error.png"));	
			}
			String imagenBase = DatatypeConverter.printBase64Binary(imagenByte);
			ref.setImagenProyecto(imagenBase);
			list.add(ref);
		}
		return list;
	}
	
	/**
	 * getReferenciasPendientes.
	 * Recoge todas las referencias de la base de datos
	 * @return List<ReferenciaWithAutoID> 
	 * @throws Exception
	 */
	public List<ReferenciaWithAutoID> getReferenciasAsociadas(String user) throws Exception {
		
		List<ReferenciaWithAutoID> list = new ArrayList<>();
		Iterator<ReferenciaWithAutoID> i = dao.getReferencias();
		while (i.hasNext()) {
			ReferenciaWithAutoID ref = i.next();
			// este if sirve para coger lar referencias cuyo estado no es validado y el usuario esta como autor o uno de los gerentes, requiere una comprobacion de nullpoiunter porque el campo de responsables puede estar vacio
			if(!ref.getEstado().equals("validada") && (ref.getAutor().equals(user) || (ref.getResponsableComercial()!=null && ref.getResponsableComercial().equals(user)) || (ref.getResponsableTecnico() != null && ref.getResponsableTecnico().equals(user)))){
				
				byte[] imagenByte = null;
				try{
					imagenByte = Files.readAllBytes(Paths.get(Config.getInstance().getProperty(Config.PATH_IMAGENES)+ref.get_id()+".png"));
				}catch(Exception e){
					imagenByte = Files.readAllBytes(Paths.get(Config.getInstance().getProperty(Config.PATH_IMAGENES)+"error.png"));	
				}
				String imagenBase = DatatypeConverter.printBase64Binary(imagenByte);
				ref.setImagenProyecto(imagenBase);
				list.add(ref);
			}
		}
		return list;
	}
	public List<ReferenciaWithAutoID> getReferenciasEstado(String estado) throws Exception {
		// Transform an iterator object to a list
		List<ReferenciaWithAutoID> list = new ArrayList<>();
		Iterator<ReferenciaWithAutoID> i = dao.getReferenciasEstado(estado);
		while (i.hasNext()) {
			ReferenciaWithAutoID ref = i.next();
			byte[] imagenByte = null;
			try{
			imagenByte = Files.readAllBytes(Paths.get(Config.getInstance().getProperty(Config.PATH_IMAGENES)+ref.get_id()+".png"));
			}catch(Exception e){
			imagenByte = Files.readAllBytes(Paths.get(Config.getInstance().getProperty(Config.PATH_IMAGENES)+"error.png"));	
			}
			String imagenBase = DatatypeConverter.printBase64Binary(imagenByte);
			ref.setImagenProyecto(imagenBase);
			list.add(ref);
		}
		return list;
	}
	public ReferenciaWithAutoID getReferenciaCopia(ObjectId key) throws Exception {
		
		Iterator<ReferenciaWithAutoID> i = dao.getReferencias();
		while (i.hasNext()) {
			ReferenciaWithAutoID ref = i.next();
			if(ref.getIdEnlaceOriginal().equals(key)){
				byte[] imagenByte = null;
				try{
				imagenByte = Files.readAllBytes(Paths.get(Config.getInstance().getProperty(Config.PATH_IMAGENES)+ref.get_id()+".png"));
				}catch(Exception e){
				imagenByte = Files.readAllBytes(Paths.get(Config.getInstance().getProperty(Config.PATH_IMAGENES)+"error.png"));	
				}
				String imagenBase = DatatypeConverter.printBase64Binary(imagenByte);
				ref.setImagenProyecto(imagenBase);
				return ref;	
			}
			
		}
		ReferenciaWithAutoID vacia = new ReferenciaWithAutoID();
		vacia.setAutor("vacia");
		return vacia;	
	}

	/**
	 * getReferencia
	 * Recoge la referencia de la base de datos indicada por parametro
	 * @param key | Clave para identificar la referencia en la base de datos
	 * @return ReferenciaWithAutoID
	 * @throws Exception
	 */
	public ReferenciaWithAutoID getReferencia(String key) throws Exception {
		
		ReferenciaWithAutoID resource = null;
		ObjectId id = new ObjectId(key);
		try{
			resource = dao.getReferencia(id);
			if (resource == null) {
			throw new IOException("Imagen no disponible");
			}
			byte[] imagenByte = Files.readAllBytes(Paths.get(Config.getInstance().getProperty(Config.PATH_IMAGENES)+resource.get_id()+".png"));
			String imagenBase = DatatypeConverter.printBase64Binary(imagenByte);
			resource.setImagenProyecto(imagenBase);
		}
		catch (IOException eImagen) {
			throw new IOException("Fallo al recoger la imagen del disco:"+eImagen.toString());
		}
		catch (Exception bdd) {
			throw new Exception("Fallo al recoger la referencia de la BDD:"+bdd.toString());
		}
		return resource;
	}

	/**
	 * createReferencia
	 * Crea una nueva referencia en la base de datos
	 * @param r | Objeto Referencia que se creara en la base de datos
	 * @return ReferenciaWithAutoID
	 * @throws Exception
	 */
	public ReferenciaWithAutoID createReferencia(ReferenciaWithAutoID r) throws Exception{
		
		//al crear la referencia borramos el campo imagen ya que la guardamos en disco
		if(r.getImagenProyecto()==null){
			r.setImagenProyecto("");
		}
		String imagen = r.getImagenProyecto();
		r.setImagenProyecto("");
		
		// antes de crear la imagen es necesario insertar la referencia para conocer su id
		try {
			
		dao.insertReferencia(r);	
		byte[] imagenByte = DatatypeConverter.parseBase64Binary(imagen);
		//guardamos en disco la imagen usando como nombre el id de su referencia
		File archivo = new File(Config.getInstance().getProperty(Config.PATH_IMAGENES)+r.get_id()+".png");
		
		FileUtils.writeByteArrayToFile(archivo,imagenByte);
		
		}
		catch (IOException eImagen) {
			dao.deleteReferencia(r.get_id());
			throw new IOException("Fallo al guardar la imagen en disco:"+eImagen.toString());
		}
		catch (Exception bdd) {
			throw new Exception("Fallo al guardar la referencia en la BDD:"+bdd.toString());
		}

		return r;
	}
	
	/**
	 * deleteReferencia
	 * Borra una referencia de la base de datos
	 * @param key | Clave para identificar la referencia en la base de datos
	 * @return key
	 * @throws Exception
	 */
	public ObjectId deleteReferencia(ObjectId key) throws Exception{
		try{
		File archivo = new File(Config.getInstance().getProperty(Config.PATH_IMAGENES)+key+".png");
		if(!archivo.delete()){
			throw new Exception("delete no ha podido completarse");
		}
		dao.deleteReferencia(key);
		}
		catch (IOException eImagen) {
			throw new Exception("Fallo al borrar la imagen en disco:"+eImagen.toString());
		}
		catch (Exception bdd) {
			throw new Exception("Fallo al borrar la referencia en la BDD:"+bdd.toString());
		}
		return key;
	}
	public ReferenciaWithAutoID updateReferencia(ReferenciaWithAutoID r) throws Exception{
		
		ObjectId key = r.get_id();
		// lanza exception si no se puede realizar el update
		comprobarCampos(Config.getInstance().getProperty(Config.CAMPOS_MODIFICAR),r);
		//al actualizar la referencia borramos el campo imagen ya que la guardamos en disco
		String imagen = r.getImagenProyecto();
		r.setImagenProyecto("");
				
		// hacer la comprobacion de la imagen nos supone mas coste que sobreescribirla, aunque sea la misma, por tanto lo hacemos.
		try {
			//bug!!!!!!		
			dao.updateReferencia(key,r);	
			byte[] imagenByte = DatatypeConverter.parseBase64Binary(imagen);
			//guardamos en disco la imagen usando como nombre el id de su referencia
			File archivo = new File(Config.getInstance().getProperty(Config.PATH_IMAGENES)+key+".png");
			if(  archivo.exists() && !archivo.delete() ){
				throw new Exception("la imagen no ha podido cargarse en el servidor");
			}
			FileUtils.writeByteArrayToFile(archivo,imagenByte);
				
		}
		catch (IOException eImagen) {
			throw new IOException("Fallo de escritura, Por favor contacte con un administrador de su servidor de aplicaciones");
		}
		catch (Exception bdd) {
			throw new Exception("Fallo al actualizar la referencia en la BDD:"+bdd.toString());
		}

		return r;		
	}
	
	/**
	 * updateReferenciaEstado
	 * @param key | Clave para identificar la referencia en la base de datos
	 * @param estado | estado al que se actualiza la referencia
	 * @return Referencia
	 * @throws Exception
	 */
	public ReferenciaWithAutoID updateReferenciaEstado(Map<String,Object>  recursos) throws Exception{
		
		ObjectId id = new ObjectId((String)recursos.get("id"));
		ReferenciaWithAutoID referencia = dao.getReferencia(id);
		if(recursos.get("estado").equals("borrador")){
			referencia.setMotivoRechazo((String)recursos.get("motivoRechazo"));
			
		}else if(recursos.get("estado").equals("validado")&&(referencia.getIdEnlaceOriginal() != null || !referencia.getIdEnlaceOriginal().equals(""))){
				
				dao.deleteReferencia(referencia.getIdEnlaceOriginal());
				referencia.setIdEnlaceOriginal(null);
					
		}
		referencia.setEstado((String)recursos.get("estado"));
		dao.updateReferencia(referencia.get_id(),referencia);
		
		return referencia;
	}

	public void updateReferenciaTecnologia(Map<String,String> tecnologias) throws Exception {
		
		Iterator<ReferenciaWithAutoID> resource = dao.getReferencias();
		String anterior = tecnologias.get("anterior");
		String nueva = tecnologias.get("nueva");
		TecnologiaController tecnologiaController = TecnologiaController.getInstance();
		Tecnologia existeTecnologia = tecnologiaController.getTecnologia(nueva);
		if(existeTecnologia == null ||!existeTecnologia.getClase().equals("hoja")){
			
			throw new Exception("La tecnologia a la que se intenta asociar las referencias no exite o no es una tecnologia terminal");
		}
		
		ReferenciaWithAutoID actual ;
		while(resource.hasNext()){
			
			actual = resource.next();
			String[] tecnologiasReferencia = actual.getTecnologias();
			List<String> tecnologiasLista = new ArrayList<String>(Arrays.asList(tecnologiasReferencia));
			tecnologiasLista.remove(anterior);
			if(!tecnologiasLista.contains(nueva)){
				tecnologiasLista.add(nueva);
			}
			tecnologiasReferencia = tecnologiasLista.toArray(new String[tecnologiasLista.size()]);
			actual.setTecnologias(tecnologiasReferencia);
			dao.updateReferencia(actual.get_id(), actual);
			
		}
	
	}
	public boolean hayReferenciaAsociada(String tecnologia) throws Exception {
		
		Iterator<ReferenciaWithAutoID> resource = dao.getReferencias();
		ReferenciaWithAutoID actual ;
		
		while(resource.hasNext()){
			
			actual = resource.next();
			String[] tecnologiasReferencia = actual.getTecnologias();
			for(int i=0;i<tecnologiasReferencia.length; i++){
				
				if(tecnologiasReferencia[i].equals(tecnologia)){
					
					return true;
						
				}	
			}
			
		}
		return false;
	}
	
	/**
	 * dropReferencia
	 * Borra la coleccion de Referencias.
	 */
	public void dropReferencia() {
		dao.clearStore();
	}
	public boolean comprobarCampos(String campos, ReferenciaWithAutoID r) throws Exception{
		
		ReferenciaWithAutoID rOld = dao.getReferencia(r.get_id());
		String[] arrayCampos = campos.split(",");
		for(int i=0; i<arrayCampos.length;i++){
			
			if(arrayCampos[i].equals("cliente")){
				
				if(!rOld.getCliente().equals(r.getCliente())){
					throw new Exception("Se han modificado campos que no permite la edicion: Cliente");
				}
				
			}
			else if(arrayCampos[i].equals("sociedad")){
				
				if(!rOld.getSociedad().equals(r.getSociedad())){
					throw new Exception("Se han modificado campos que no permite la edicion: Sociedad");
				}
				
			}
			else if(arrayCampos[i].equals("sectorEmpresarial")){
				
				if(!rOld.getSectorEmpresarial().equals(r.getSectorEmpresarial())){
					throw new Exception("Se han modificado campos que no permite la edicion: S.empresarial");
				}
				
			}
			else if(arrayCampos[i].equals("tipoActividad")){
				
				if(!rOld.getTipoActividad().equals(r.getTipoActividad())){
					throw new Exception("Se han modificado campos que no permite la edicion: T.actividad");
				}
			}
			else if(arrayCampos[i].equals("tipoProyecto")){
				
				if(!rOld.getTipoProyecto().equals(r.getTipoProyecto())){
					throw new Exception("Se han modificado campos que no permite la edicion: T.proyecto");
				}
	
			}
			else if(arrayCampos[i].equals("fechaInicio")){
				
				if(!rOld.getFechaInicio().equals(r.getFechaInicio())){
					throw new Exception("Se han modificado campos que no permite la edicion: Fecha");
				}
			}
			else if(arrayCampos[i].equals("duracionMeses")){
				
				if(rOld.getDuracionMeses()!= r.getDuracionMeses()){
					throw new Exception("Se han modificado campos que no permite la edicion: Duracion");
				}
	
			}
			else if(arrayCampos[i].equals("denominacion")){
	
				if(!rOld.getDenominacion().equals(r.getDenominacion())){
					throw new Exception("Se han modificado campos que no permite la edicion: Denominacion");
				}
			}
			else if(arrayCampos[i].equals("resumen")){
				
				if(!rOld.getResumenProyecto().equals(r.getResumenProyecto())){
					throw new Exception("Se han modificado campos que no permite la edicion: Resumen");
				}
			}
			else if(arrayCampos[i].equals("problematicaCliente")){
				
				if(!rOld.getProblematicaCliente().equals(r.getProblematicaCliente())){
					throw new Exception("Se han modificado campos que no permite la edicion: Problematica");
				}
				
			}
			else if(arrayCampos[i].equals("solucionGfi")){
				
				if(!rOld.getSolucionGfi().equals(r.getSolucionGfi())){
					throw new Exception("Se han modificado campos que no permite la edicion: Solucion");
				}
				
			}
			else if(arrayCampos[i].equals("fteTotales")){
				
				if(rOld.getFteTotales() != r.getFteTotales()){
					throw new Exception("Se han modificado campos que no permite la edicion: Fte");
				}
				
			}
			else if(arrayCampos[i].equals("certificado")){
				
				if(!rOld.getCertificado().equals(r.getCertificado())){
					throw new Exception("Se han modificado campos que no permite la edicion: Certificado");
				}
				
			}
			else if(arrayCampos[i].equals("responsableTecnico")){
					
				if(!rOld.getResponsableTecnico().equals(r.getResponsableTecnico())){
					throw new Exception("Se han modificado campos que no permite la edicion: R.tecnico");
				}
				
			}
			else if(arrayCampos[i].equals("responsableComercial")){
				
				if(!rOld.getResponsableComercial().equals(r.getResponsableComercial())){
					throw new Exception("Se han modificado campos que no permite la edicion: R.comercial");
				}
				
			}
			else if(arrayCampos[i].equals("codigoQr")){
				
				if(!rOld.getCodigoQr().equals(r.getCodigoQr())){
					throw new Exception("Se han modificado campos que no permite la edicion: Codigo Qr");
				}
				
				
			}
			else if(arrayCampos[i].equals("tecnologiasSeleccionadas")){
				
				List<String> tecnologiasOld = new ArrayList<String>(Arrays.asList(rOld.getTecnologias()));
				List<String> tecnologias = new ArrayList<String>(Arrays.asList(r.getTecnologias()));
				if(!tecnologias.containsAll(tecnologiasOld) && !tecnologiasOld.containsAll(tecnologias)){
					throw new Exception("Se han modificado campos que no permite la edicion: Tecnologias");
				}
				
			}
			else if(arrayCampos[i].equals("imagenProyecto")){
				
				byte[] imagenByte = Files.readAllBytes(Paths.get(Config.getInstance().getProperty(Config.PATH_IMAGENES)+rOld.get_id()+".png"));
				String imagen = DatatypeConverter.printBase64Binary(imagenByte);
				if(!rOld.getImagenProyecto().equals(imagen)){
					throw new Exception("Se han modificado campos que no permite la edicion : Imagen");
				}
			}
		
		}
		return true;
		
	}
	
}