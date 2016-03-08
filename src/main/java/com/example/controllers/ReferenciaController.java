package com.example.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.bson.types.ObjectId;
import org.docx4j.openpackaging.io3.Save;
import org.docx4j.openpackaging.packages.SpreadsheetMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.Parts;
import org.docx4j.openpackaging.parts.SpreadsheetML.SharedStrings;
import org.docx4j.openpackaging.parts.SpreadsheetML.WorksheetPart;
import org.xlsx4j.jaxb.Context;
import org.xlsx4j.sml.CTRst;
import org.xlsx4j.sml.CTSst;
import org.xlsx4j.sml.CTXstringWhitespace;
import org.xlsx4j.sml.Cell;
import org.xlsx4j.sml.Row;
import org.xlsx4j.sml.STCellType;
import org.xlsx4j.sml.SheetData;

import com.example.dao.ReferenciaDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.ReferenciaWithAutoID;
import com.example.models.Tecnologia;
import com.example.models.Usuario;
import com.example.utils.Config;

public class ReferenciaController {

	private static ReferenciaDAO dao;
	private static UsuarioDAO usuarioDao;
	
	private static ReferenciaController singleton;

	private ReferenciaController() throws Exception {
		dao = ReferenciaDAO.getInstance();
		usuarioDao = UsuarioDAO.getInstance();
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
		Iterator<ReferenciaWithAutoID> iterator = dao.getReferencias();
		while (iterator.hasNext()) {
			ReferenciaWithAutoID ref = iterator.next();
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
		Iterator<ReferenciaWithAutoID> iiterator = dao.getReferencias();
		Usuario usuarioAplicacion = usuarioDao.getUsuario(user);
		while (iiterator.hasNext()) {
			
			ReferenciaWithAutoID ref = iiterator.next();
			// este if sirve para coger lar referencias cuyo estado no es validado y el usuario esta como autor o uno de los gerentes, requiere una comprobacion de nullpoiunter porque el campo de responsables puede estar vacio, si eres el administrador ves todas!!!
			if(!ref.getEstado().equals("validada") && ((ref.getAutor().equals(user) || (ref.getResponsableComercial()!=null && ref.getResponsableComercial().equals(user)) || (ref.getResponsableTecnico() != null && ref.getResponsableTecnico().equals(user)))|| usuarioAplicacion.getRole().equals("ROLE_ADMINISTRADOR"))){
				
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
		Iterator<ReferenciaWithAutoID> iterator = dao.getReferenciasEstado(estado);
		while (iterator.hasNext()) {
			ReferenciaWithAutoID ref = iterator.next();
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
		
		Iterator<ReferenciaWithAutoID> iterator = dao.getReferencias();
		while (iterator.hasNext()) {
			ReferenciaWithAutoID ref = iterator.next();
			if(ref.getIdEnlaceOriginal()!=null && ref.getIdEnlaceOriginal().equals(key)){
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
	public ReferenciaWithAutoID getReferencia(ObjectId key) throws Exception {
		//ACTUALIZAR
		ReferenciaWithAutoID resource = null;
		try{
			resource = dao.getReferencia(key);
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
		
		if(r.getEstado().equals("validada")){
		// lanza exception si no se puede realizar el update sobre una referencia validada
		comprobarCampos(Config.getInstance().getProperty(Config.CAMPOS_MODIFICAR),r);
		}
		
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
		System.out.println("si");
		if(recursos.get("estado").equals("borrador")){
			
			referencia.setMotivoRechazo((String)recursos.get("motivoRechazo"));
			
		}else if(recursos.get("estado").equals("validada")&&(referencia.getIdEnlaceOriginal() != null && !referencia.getIdEnlaceOriginal().equals(""))){
				
				System.out.println("no");
				dao.deleteReferencia(referencia.getIdEnlaceOriginal());
				System.out.println("no");
				referencia.setIdEnlaceOriginal(null);
				System.out.println("no");
					
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
	
	public void exportar(ObjectId key) throws Exception {
		
		int MAX_COLUMNS = 3;
		// Prueba Apache POI

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow((short) 0);
		HSSFCell cell;
		HSSFCellStyle style = wb.createCellStyle();
		HSSFCellStyle styleCabecera = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		style.setFillForegroundColor(HSSFColor.ORANGE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		font.setColor(HSSFColor.BLACK.index);
		font.setBold(true);
		style.setFont(font);
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		Iterator<Usuario> it;
		Boolean cabecera = true;

		listaUsuarios.add(new Usuario("prueba1 nick", "prueba1 name ",
				"prueba1 role"));
		listaUsuarios.add(new Usuario("prueba2 nick con redimension",
				"prueba2 name ", "prueba2 role"));

		int i = 0;
		if(cabecera){
		row = sheet.createRow(i++);
		cell = row.createCell(0);
		cell.setCellValue("Name");
		cell = row.createCell(1);
		cell.setCellValue("Nick");
		cell = row.createCell(2);
		cell.setCellValue("Role");
		cabecera= false;
		}

		for (Usuario u : listaUsuarios) {
			int k = 0;
			row = sheet.createRow(i++);
			cell = row.createCell(k++);
			cell.setCellValue(u.getName());
			cell.setCellStyle(style);
			cell = row.createCell(k++);
			cell.setCellValue(u.getNick());
			cell.setCellStyle(style);
			cell = row.createCell(k++);
			cell.setCellValue(u.getRole());
			cell.setCellStyle(style);
		}

		for( int j = 0 ; j < MAX_COLUMNS ; j++ ) {
			sheet.autoSizeColumn(j);
		}
		
		// Apache POI IMAGE

				// add picture data to this workbook.
				InputStream is = new FileInputStream(Config.getInstance().getProperty(Config.PATH_IMAGENES)+"error.png");
				byte[] bytes = IOUtils.toByteArray(is);
				int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
				is.close();

				CreationHelper helper = wb.getCreationHelper();

				// Create the drawing patriarch. This is the top level container for all
				// shapes.
				Drawing drawing = sheet.createDrawingPatriarch();

				// add a picture shape
				ClientAnchor anchor = helper.createClientAnchor();
				// set top-left corner of the picture,
				// subsequent call of Picture#resize() will operate relative to it
				anchor.setCol1(5);
				anchor.setRow1(5);
				Picture pict = drawing.createPicture(anchor, pictureIdx);

				// auto-size picture relative to its top-left corner
				pict.resize();
				sheet.autoSizeColumn(6);

				// save with the default palette
				FileOutputStream out = new FileOutputStream("pruebaPOI.xls");
				wb.write(out);
				out.close();
		
	
	}
	public List<ReferenciaWithAutoID> filtrar(String general, String cliente, List<String> sociedad,
			List<String> sector, List<String> actividad, List<String> proyecto,List<String> tecnologias,List<String> tipoTecnologias,String producto, int anios) throws Exception {
			
		//System.out.println(general+"-"+cliente+"-"+sociedad+"-"+sector+"-"+actividad+"-"+tecnologias+"-"+tipoTecnologias+"-"+producto+"-"+proyecto+"-"+anios);
		Iterator<ReferenciaWithAutoID> iterator = dao.listaContenido(cliente,anios,proyecto,actividad,sociedad,sector,tecnologias,tipoTecnologias,producto,general );
		List<ReferenciaWithAutoID> list = new ArrayList<>();
		while (iterator.hasNext()) {
			ReferenciaWithAutoID ref = iterator.next();
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
				
				//este es el unico campo no obligatoria en una referencia validada por lo que hacemos comprobacion de null pointer antes
				if(rOld.getCodigoQr()!=null&&r.getCodigoQr()!=null&&!rOld.getCodigoQr().equals(r.getCodigoQr())){
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
	
	public static void main(String[] args) throws Exception {
		
		ObjectId key = new ObjectId();
		singleton = new ReferenciaController();
		singleton.exportar(key);
		
	}

}