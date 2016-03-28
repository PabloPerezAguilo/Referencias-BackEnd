package com.example.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
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
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.bson.types.ObjectId;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.dao.ReferenciaDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.CatalogoClientes;
import com.example.models.CatalogoGerentes;
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
		if(recursos.get("estado").equals("borrador")){
			
			referencia.setMotivoRechazo((String)recursos.get("motivoRechazo"));
			
		}else if(recursos.get("estado").equals("validada")&&(referencia.getIdEnlaceOriginal() != null && !referencia.getIdEnlaceOriginal().equals(""))){
				
				
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
	
	public String exportarExcel(List<ObjectId> key) throws Exception {
		
		Iterator<ObjectId> iteradorReferencias = key.iterator();
		List<ReferenciaWithAutoID> resultado = new ArrayList<ReferenciaWithAutoID>();
		ObjectId actual = null;
		while(iteradorReferencias.hasNext()){
			
			actual= iteradorReferencias.next();
			resultado.add(dao.getReferencia(actual));
		}
		
		int MAX_COLUMNS = 17;
		int OFFSET_FILA_INICIO = 5;
		// Prueba Apache POI

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow((short) 0);
		HSSFCell cell;
		HSSFPalette paletteOrange = wb.getCustomPalette();
		HSSFCellStyle stylePar = wb.createCellStyle();
		HSSFCellStyle styleImpar = wb.createCellStyle();
		HSSFCellStyle styleCabecera = wb.createCellStyle();
		HSSFCellStyle error = wb.createCellStyle();
		HSSFFont fontError = wb.createFont();
		paletteOrange.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 253, (byte) 126,(byte) 42);
		fontError.setColor(HSSFColor.DARK_RED.index);
		error.setFont(fontError);
		HSSFFont font = wb.createFont();
		styleCabecera.setFillForegroundColor(HSSFColor.ORANGE.index);
		styleCabecera.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		font.setColor(HSSFColor.WHITE.index);
		font.setBold(true);
		styleCabecera.setFont(font);
		//ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

		int fila = 0;

		row = sheet.createRow(OFFSET_FILA_INICIO + fila);
		cell = row.createCell(1);
		cell.setCellValue("Cliente");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(2);
		cell.setCellValue("Sociedad");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(3);
		cell.setCellValue("Sector Empresarial");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(4);
		cell.setCellValue("Tipo de actividad");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(5);
		cell.setCellValue("Tipo de proyecto");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(6);
		cell.setCellValue("Fecha");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(7);
		cell.setCellValue("Duracion en meses");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(8);
		cell.setCellValue("Denominacion");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(9);
		cell.setCellValue("Resumen del proyecto");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(10);
		cell.setCellValue("Problematica del Cliente");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(11);
		cell.setCellValue("Solucion de Gfi");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(12);
		cell.setCellValue("Fte totales");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(13);
		cell.setCellValue("¿Tiene certificado?");
		cell.setCellStyle(styleCabecera);
//		cell.setCellValue("Registros de pedidos asociados a la referencia");
//		cell.setCellStyle(styleCabecera);
		cell = row.createCell(14);
		cell.setCellValue("Responsable comercial");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(15);
		cell.setCellValue("Responsable tecnico");
		cell.setCellStyle(styleCabecera);
		cell = row.createCell(16);
		cell.setCellValue("Tecnologias");
		cell.setCellStyle(styleCabecera);
		fila++;

		for (ReferenciaWithAutoID recurso : resultado) {
			int k = 1;
			row = sheet.createRow(OFFSET_FILA_INICIO + fila);
			fila++;
			if ((row.getRowNum() % 2) == 0) {
				stylePar.setFillForegroundColor(HSSFColor.WHITE.index);
				stylePar.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cell = row.createCell(k++);
				String busquedaCliente = recurso.getCliente().split(" \\(")[0];
			    CatalogoClientes clienteExportar = dao.clienteExportar(busquedaCliente);
			    if(clienteExportar==null||clienteExportar.getNombre().equals("")){
			    	cell.setCellValue("Cliente("+recurso.getCliente()+") dado de baja de la bdd");
					cell.setCellStyle(error);
			    }else if(clienteExportar.isPublico()){
			    	cell.setCellValue(recurso.getCliente());
					cell.setCellStyle(stylePar);
			    }else{
			    	cell.setCellValue(clienteExportar.getAlias());
					cell.setCellStyle(stylePar);
			    }
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getSociedad());
				cell.setCellStyle(stylePar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getSectorEmpresarial());
				cell.setCellStyle(stylePar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getTipoActividad());
				cell.setCellStyle(stylePar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getTipoProyecto());
				cell.setCellStyle(stylePar);
				cell = row.createCell(k++);
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
				cell.setCellValue(format1.format(recurso.getFechaInicio().getTime()));
				cell.setCellStyle(stylePar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getDuracionMeses());
				cell.setCellStyle(stylePar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getDenominacion());
				cell.setCellStyle(stylePar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getResumenProyecto());
				cell.setCellStyle(stylePar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getProblematicaCliente());
				cell.setCellStyle(stylePar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getSolucionGfi());
				cell.setCellStyle(stylePar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getFteTotales());
				cell.setCellStyle(stylePar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getCertificado());
				cell.setCellStyle(stylePar);
				cell = row.createCell(k++);
				
				CatalogoGerentes gerenteExportar = dao.nombreGerentesExportar(recurso.getResponsableComercial());
			    if(gerenteExportar==null||gerenteExportar.getNombre().equals("")){
			    	cell.setCellValue("Gerente Comercial("+recurso.getResponsableComercial()+") dado de baja");
					cell.setCellStyle(error);
			    }else{
			    	cell.setCellValue(recurso.getResponsableComercial());
					cell.setCellStyle(stylePar);
			    }
				cell = row.createCell(k++);
				gerenteExportar = dao.nombreGerentesExportar(recurso.getResponsableTecnico());
			    if(gerenteExportar==null||gerenteExportar.getNombre().equals("")){
			    	cell.setCellValue("Gerente Tecnico("+recurso.getResponsableTecnico()+") dado de baja");
					cell.setCellStyle(error);
			    }else{
			    	cell.setCellValue(recurso.getResponsableTecnico());
					cell.setCellStyle(stylePar);
			    }
				cell = row.createCell(k++);
				String tec = Arrays.asList(recurso.getTecnologias()).toString();
				tec = tec.replace("[","");
				tec = tec.replace("]","");
				cell.setCellValue(tec);
				cell.setCellStyle(stylePar);
			} else {
				paletteOrange.setColorAtIndex(HSSFColor.RED.index, (byte) 255,(byte) 245, (byte) 224);
				styleImpar.setFillForegroundColor(HSSFColor.RED.index);
				styleImpar.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cell = row.createCell(k++);
				String busquedaCliente = recurso.getCliente().split(" \\(")[0];
			    CatalogoClientes clienteExportar = dao.clienteExportar(busquedaCliente);
			    if(clienteExportar==null||clienteExportar.getNombre().equals("")){
			    	cell.setCellValue("Cliente("+recurso.getCliente()+") dado de baja de la bdd");
					cell.setCellStyle(error);
			    }else if(clienteExportar.isPublico()){
			    	cell.setCellValue(recurso.getCliente());
					cell.setCellStyle(stylePar);
			    }else{
			    	cell.setCellValue(clienteExportar.getAlias());
					cell.setCellStyle(stylePar);
			    }
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getSociedad());
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getSectorEmpresarial());
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getTipoActividad());
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getTipoProyecto());
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
				cell.setCellValue(format1.format(recurso.getFechaInicio().getTime()));
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getDuracionMeses());
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getDenominacion());
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getResumenProyecto());
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getProblematicaCliente());
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getSolucionGfi());
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getFteTotales());
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				cell.setCellValue(recurso.getCertificado());
				cell.setCellStyle(styleImpar);
				cell = row.createCell(k++);
				CatalogoGerentes gerenteExportar = dao.nombreGerentesExportar(recurso.getResponsableComercial());
			    if(gerenteExportar==null||gerenteExportar.getNombre().equals("")){
			    	cell.setCellValue("Gerente Comercial("+recurso.getResponsableComercial()+") dado de baja");
					cell.setCellStyle(error);
			    }else{
			    	cell.setCellValue(recurso.getResponsableComercial());
					cell.setCellStyle(stylePar);
			    }
				cell = row.createCell(k++);
				gerenteExportar = dao.nombreGerentesExportar(recurso.getResponsableTecnico());
			    if(gerenteExportar==null||gerenteExportar.getNombre().equals("")){
			    	cell.setCellValue("Gerente Tecnic("+recurso.getResponsableTecnico()+")o dado de baja");
					cell.setCellStyle(error);
			    }else{
			    	cell.setCellValue(recurso.getResponsableTecnico());
					cell.setCellStyle(stylePar);
			    }
				cell = row.createCell(k++);
				String tec = Arrays.asList(recurso.getTecnologias()).toString();
				tec = tec.replace("[","");
				tec = tec.replace("]","");
				cell.setCellValue(tec);
				cell.setCellStyle(styleImpar);

			}

		}

		for (int j = 1; j < MAX_COLUMNS; j++) {
			sheet.autoSizeColumn(j);
		}

		// Apache POI IMAGE

		// add picture data to this workbook.
		InputStream is = new FileInputStream(
				Config.getInstance().getProperty(Config.PATH_IMAGENES)+"/logoExportar.JPG");
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
		anchor.setCol1(1);
		anchor.setRow1(0);
		Picture pict = drawing.createPicture(anchor, pictureIdx);


		// auto-size picture relative to its top-left corner
		pict.resize();
		sheet.autoSizeColumn(0);


		// save with the default palette
		FileOutputStream out = new FileOutputStream(Config.getInstance().getProperty(Config.PATH_ARCHIVOS)+SecurityContextHolder.getContext().getAuthentication().getName()+".xlsx");
		
		wb.write(out);
		out.close();
		return Config.getInstance().getProperty(Config.PATH_ARCHIVOS)+SecurityContextHolder.getContext().getAuthentication().getName()+".xlsx";
	}
	public String exportarWord(List<ObjectId> key) throws Exception {
		
		Iterator<ObjectId> iteradorReferencias = key.iterator();
		List<ReferenciaWithAutoID> resultado = new ArrayList<ReferenciaWithAutoID>();
		ObjectId actual = null;
		while(iteradorReferencias.hasNext()){
			
			actual= iteradorReferencias.next();
			resultado.add(dao.getReferencia(actual));
		}
		Iterator<ReferenciaWithAutoID> iteradorResultado = resultado.iterator();
		ReferenciaWithAutoID referenciaActual = null;
		
		XWPFDocument doc = new XWPFDocument();
		
		if(resultado.size()>1){
			
			Iterator<ReferenciaWithAutoID> iteradorTablaResumen = resultado.iterator();
			ReferenciaWithAutoID referenciaActualTabla = null;
			
		    XWPFTable table = doc.createTable();
	
		    //create first row
		    int fila = 0;
		    XWPFTableRow tableRowOne = table.getRow(fila);
		    tableRowOne.getCell(0).setText("Cliente");
		    tableRowOne.getCell(0).setColor("FA9200");
		    tableRowOne.addNewTableCell().setText("Proyecto");
		    tableRowOne.getCell(1).setColor("fa9200");
		    tableRowOne.addNewTableCell().setText("Tecnologias");
		    tableRowOne.getCell(2).setColor("fa9200");
		    tableRowOne.addNewTableCell().setText("FTE");
		    tableRowOne.getCell(3).setColor("fa9200");
		    fila++;
		    
			while(iteradorTablaResumen.hasNext()){
				
				referenciaActualTabla = iteradorTablaResumen.next();

				XWPFTableRow tableRowTwo = table.createRow();
				int k = 0;
				String busquedaCliente = referenciaActualTabla.getCliente().split(" \\(")[0];
				CatalogoClientes clienteExportar = dao.clienteExportar(busquedaCliente);
				if(clienteExportar==null||clienteExportar.getNombre().equals("")){
					tableRowTwo.getCell(k).setColor("FF3D3D");
					tableRowTwo.getCell(k).setText("Cliente ("+referenciaActualTabla.getCliente()+") dado de baja de la bdd");
				}else if(clienteExportar.isPublico()){
					tableRowTwo.getCell(k).setText(referenciaActualTabla.getCliente());
				}else{
					tableRowTwo.getCell(k).setText(clienteExportar.getAlias());
				}
				
				tableRowTwo.getCell(k+1).setText("Denominacion: "+referenciaActualTabla.getDenominacion());
				XWPFParagraph proyecto = tableRowTwo.getCell(k+1).addParagraph();
				XWPFRun run = proyecto.createRun();
				run.addBreak();
				run.setText(referenciaActualTabla.getProblematicaCliente());
				tableRowTwo.getCell(k+2).setText(Arrays.toString(referenciaActualTabla.getTecnologias()));
				tableRowTwo.getCell(k+3).setText(referenciaActualTabla.getFteTotales()+"");
	
				if((fila % 2) == 0){
					if(clienteExportar==null||clienteExportar.getNombre().equals("")){
						tableRowTwo.getCell(k++).setColor("FF3D3D");
						
					}else{
						tableRowTwo.getCell(k++).setColor("fff5e0");	
					}
					
					tableRowTwo.getCell(k++).setColor("fff5e0");
					tableRowTwo.getCell(k++).setColor("fff5e0");
					fila++;
				}else{
					if(clienteExportar==null||clienteExportar.getNombre().equals("")){
						tableRowTwo.getCell(k++).setColor("FF3D3D");
						
					}else{
						tableRowTwo.getCell(k++).setColor("ffffff");	
					}
					
					tableRowTwo.getCell(k++).setColor("ffffff");
					tableRowTwo.getCell(k++).setColor("ffffff");
					fila++;
				}
					
			}
		}
		while(iteradorResultado.hasNext()){
			
			referenciaActual = iteradorResultado.next();

			XWPFParagraph title = doc.createParagraph();
			XWPFRun run = title.createRun();
			run.addBreak();
	    	run.addBreak();
			run.setText("Referencia creada por "+referenciaActual.getAutor());
			run.setFontFamily("Bliss 2");
			run.setBold(true);
			run.setColor("303F48");
			title.setAlignment(ParagraphAlignment.CENTER);
			run.addBreak(); 
	    
			XWPFParagraph cliente = doc.createParagraph();
			run = cliente.createRun();
			run.setFontFamily("Bliss 2");
			run.setColor("303F48");
			String busquedaCliente = referenciaActual.getCliente().split(" \\(")[0];
			CatalogoClientes clienteExportar = dao.clienteExportar(busquedaCliente);
			if(clienteExportar==null||clienteExportar.getNombre().equals("")){
				clienteExportar = new CatalogoClientes();
				clienteExportar.setPublico(false);
				run.setColor("FF3D3D");
				clienteExportar.setAlias("Cliente("+referenciaActual.getCliente()+") dado de baja de la bdd");
			}
			if(clienteExportar.isPublico()){
				run.setText("Cliente:  ");
				run.addBreak(); 
				run.setText(referenciaActual.getCliente());
			}else{
				run.setText("Cliente:  ");
				run.addBreak();
				run.setText(clienteExportar.getAlias());
			}
			run.addBreak(); 
	    
	    
			XWPFParagraph denominacion = doc.createParagraph();
			run = denominacion.createRun();
			run.setFontFamily("Bliss 2");
			run.setColor("303F48");
			run.setText("Denominación:  ");
			run.addBreak();
			run.setText(referenciaActual.getDenominacion());
			run.addBreak();
	    
			XWPFParagraph sociedad = doc.createParagraph();
			run = sociedad.createRun();
			run.setFontFamily("Bliss 2");
			run.setColor("303F48");
			run.setText("Sociedad: ");
			run.addBreak();
			run.setText(referenciaActual.getSociedad());
			run.addBreak();
	    
			XWPFParagraph sectorEmpresarial = doc.createParagraph();
			run = sectorEmpresarial.createRun();
			run.setFontFamily("Bliss 2");
			run.setColor("303F48");
			run.setText("Sector Empresarial:  ");
			run.addBreak();
			run.setText(referenciaActual.getSectorEmpresarial());
			run.addBreak();
	    
			XWPFParagraph tipoActividad = doc.createParagraph();
			run = tipoActividad.createRun();
			run.setFontFamily("Bliss 2");
			run.setColor("303F48");
			run.setText("Tipo de Actividad:  ");
	    	run.addBreak();
	    	run.setText(referenciaActual.getTipoActividad());
	    	run.addBreak();
	    
	    	XWPFParagraph tipoProyecto = doc.createParagraph();
	    	run = tipoProyecto.createRun();
	    	run.setFontFamily("Bliss 2");
	    	run.setColor("303F48");
	    	run.setText("Tipo de proyecto: ");
	    	run.addBreak();
	    	run.setText(referenciaActual.getTipoProyecto());
	    	run.addBreak();
	    
	    	XWPFParagraph fecha = doc.createParagraph();
	    	run = fecha.createRun();
	    	run.setFontFamily("Bliss 2");
	    	run.setColor("303F48");
	    	SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
	    	run.setText("Fecha de inicio:  ");
	    	run.addBreak();
	    	run.setText(format1.format(referenciaActual.getFechaInicio().getTime()));
	    	run.addBreak();
	    
	    	XWPFParagraph duracion = doc.createParagraph();
	    	run = duracion.createRun();
	    	run.setFontFamily("Bliss 2");
	    	run.setColor("303F48");
	    	run.setText("Duracion:  ");
	    	run.addBreak();
	    	if(referenciaActual.getDuracionMeses()==1){
	    		run.setText(referenciaActual.getDuracionMeses()+" mes");
	    	}else{
	    		run.setText(referenciaActual.getDuracionMeses()+" meses");
	    	}
	    	run.addBreak();
	    
	    	XWPFParagraph resumen = doc.createParagraph();
	    	run = resumen.createRun();
	    	run.setFontFamily("Bliss 2");
	    	run.setColor("303F48");
	    	run.setText("Resumen:  "+referenciaActual.getResumenProyecto());
	    	run.addBreak();
	    
	    	XWPFParagraph problematica = doc.createParagraph();
	    	run = problematica.createRun();
	    	run.setFontFamily("Bliss 2");
	    	run.setColor("303F48");
	    	run.setText("Problemática del cliente:  "+referenciaActual.getProblematicaCliente());
	    	run.addBreak();
	    
	    	XWPFParagraph solucion = doc.createParagraph();
	    	run = solucion.createRun();
	    	run.setFontFamily("Bliss 2");
	    	run.setColor("303F48");
	    	run.setText("Solución de Gfi:  "+referenciaActual.getSolucionGfi());
	    	run.addBreak();
	    
	    	XWPFParagraph FTETotales = doc.createParagraph();
	    	run = FTETotales.createRun();
	    	run.setFontFamily("Bliss 2");
	    	run.setColor("303F48");
	    	run.setText("FTE totales: ");
	    	run.addBreak();
	    	run.setText(referenciaActual.getFteTotales()+"");
	    	run.addBreak();
	    
	    	XWPFParagraph certificado = doc.createParagraph();
	    	run = certificado.createRun();
	    	run.setFontFamily("Bliss 2");
	    	run.setColor("303F48");
	    	run.setText("Existe certificado del cliente:  ");
	    	run.addBreak();
	    	run.setText(referenciaActual.getCertificado());
	    	run.addBreak();
	    
//	  		XWPFParagraph registro = doc.createParagraph();
//	  		run = registro.createRun();
//	    	run.setFontFamily("Bliss 2");
//	    	run.setColor("303F48");
//	    	run.setText("Registros de pedido asociados a la referencia:  ");
//	    	run.addBreak();
//	    	run.setText(referenciaActual.getRegPedidoAsociadoReferencia()+"");
//	    	run.addBreak();
	    
	    	XWPFParagraph comercial = doc.createParagraph();
	    	run = comercial.createRun();
	    	run.setFontFamily("Bliss 2");
	    	run.setColor("303F48");
	    	CatalogoGerentes gerenteExportar = dao.nombreGerentesExportar(referenciaActual.getResponsableComercial());
	    	if(gerenteExportar==null||gerenteExportar.getNombre().equals("")){
	    		gerenteExportar = new CatalogoGerentes();
	    		run.setColor("FF3D3D");
	    		gerenteExportar.setNombre("Gerente Comercial("+referenciaActual.getResponsableComercial()+") dado de baja");
	    		gerenteExportar.setApellidos("");
	    	}
	    	run.setText("Responsable comercial: ");
	    	run.addBreak();
	    	run.setText(gerenteExportar.getNombre()+" "+gerenteExportar.getApellidos());
	    	run.addBreak();
	    	
	    	XWPFParagraph tecnico = doc.createParagraph();
	    	run = tecnico.createRun();
	    	gerenteExportar = dao.nombreGerentesExportar(referenciaActual.getResponsableTecnico());
	    	if(gerenteExportar==null||gerenteExportar.getNombre().equals("")){
	    		gerenteExportar = new CatalogoGerentes();
	    		run.setColor("FF3D3D");
	    		gerenteExportar.setNombre("Gerente tecnico("+referenciaActual.getResponsableTecnico()+") dado de baja");
	    		gerenteExportar.setApellidos("");
	    	}
	    	run.setText("Responsable tecnico:  ");
	    	run.addBreak();
	    	run.setText(gerenteExportar.getNombre()+" "+gerenteExportar.getApellidos());
	    	run.addBreak();

	    	XWPFParagraph imagen = doc.createParagraph();
	    	run = imagen.createRun();
	    	imagen.setAlignment(ParagraphAlignment.CENTER);
	    	String imgFile = Config.getInstance().getProperty(Config.PATH_IMAGENES)+"/"+referenciaActual.get_id()+".png";
	    	FileInputStream is = new FileInputStream(imgFile);
	    	run.addBreak();
	    	run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(240), Units.toEMU(200)); // 200x200 pixels
	    	is.close();
	    	
	    	run.addBreak();
	    	run.addBreak();
	    	
		}

	    FileOutputStream out12 = new FileOutputStream(Config.getInstance().getProperty(Config.PATH_ARCHIVOS)+SecurityContextHolder.getContext().getAuthentication().getName()+".docx");
		doc.write(out12);
		out12.close();
		return Config.getInstance().getProperty(Config.PATH_ARCHIVOS)+SecurityContextHolder.getContext().getAuthentication().getName()+".docx";
	}
	
	public List<ReferenciaWithAutoID> filtrar(String general, String cliente, List<String> sociedad,
			List<String> sector, List<String> actividad, List<String> proyecto,List<String> tecnologias,List<String> tipoTecnologias,String producto, int anios) throws Exception {
			
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
			// cambiar a switch(arrayCampos[i]){} para mejorar rendimiento y legibilidad
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
		
		String aux = "suppp(pru)";
		String[] son = aux.split("\\(");
		aux = son[0];
//		singleton = new ReferenciaController();
//		List<ObjectId> aux = new ArrayList<ObjectId>();
//		ObjectId pru = new ObjectId("56bdd82d445ac20fc213c30b");
//		aux.add(pru);
//		singleton.exportarWord(aux);
//		;
//		
	}

}