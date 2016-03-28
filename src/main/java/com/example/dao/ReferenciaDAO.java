package com.example.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import com.example.controllers.TecnologiaController;
import com.example.models.CatalogoClientes;
import com.example.models.CatalogoGerentes;
import com.example.models.ReferenciaWithAutoID;

public class ReferenciaDAO {

	private static ReferenciaDAO singleton;
	private static MongoCollection dao;
	private static CatalogoClientesDAO daoClientes;
	private static CatalogoCoDeDAO daoCoDe;
	private static CatalogoGerentesDAO daoGerentes;
	private static TecnologiaController tecnologiaController;
	private static final String COLLECTION_NAME_MONGO = "referencias";

	private ReferenciaDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
		daoClientes = CatalogoClientesDAO.getInstance();
		daoCoDe = CatalogoCoDeDAO.getInstance();
		daoGerentes = CatalogoGerentesDAO.getInstance();
		tecnologiaController = TecnologiaController.getInstance();
	}

	public static ReferenciaDAO getInstance() throws Exception {
		if (singleton == null) {
			singleton = new ReferenciaDAO();
		}
		return singleton;
	}

	/**
	 * getReferencias
	 * @return Iterator<ReferenciaWithAutoID>
	 * @throws Exception
	 */
	public Iterator<ReferenciaWithAutoID> getReferencias() throws Exception {
		return dao.find().as(ReferenciaWithAutoID.class).iterator();
	}
	
	/**
	 * getReferenciasPendientes
	 * @return Iterator<ReferenciaWithAutoID>
	 * @throws Exception
	 */
	public Iterator<ReferenciaWithAutoID> getReferenciasEstado(String estado) throws Exception {
		return dao.find("{'estado':'"+estado+"'}").as(ReferenciaWithAutoID.class).iterator();
	}
	
	/**
	 * getReferencia
	 * @param key
	 * @return ReferenciaWithAutoID
	 * @throws Exception
	 */
	public ReferenciaWithAutoID getReferencia(ObjectId key) throws Exception{
		return dao.findOne("{'_id':#}", key).as(ReferenciaWithAutoID.class);
	}

	/**
	 * insertReferencia
	 * @param r
	 * @throws Exception
	 */
	public void insertReferencia(ReferenciaWithAutoID r) throws Exception{
		dao.insert(r);
	}
	
	/**
	 * deleteReferencia
	 * @param key
	 * @throws Exception
	 */
	public void deleteReferencia(ObjectId key) throws Exception{
		String i = key.toString();
		dao.remove("{ _id: # }", new ObjectId(i));
	
	}
	
	/**
	 * updateReferencia
	 * @param key
	 * @param r
	 * @throws Exception
	 */
	public void updateReferencia(ObjectId key, ReferenciaWithAutoID r) throws Exception{
		/*dao.update no se puede realizar por error*/
		//dao.update("{_id:"+key+"}").with(r);
		deleteReferencia(key);
		insertReferencia(r);
	}
	public CatalogoGerentes nombreGerentesExportar(String nick) throws Exception {
		return daoGerentes.getGerente(nick);
	}
	
	public CatalogoClientes clienteExportar(String nombre) throws Exception {
		return daoClientes.getCliente(nombre);
	}	
	
	public Iterator<ReferenciaWithAutoID> listaContenido(String cliente, int ultimosAños,List<String> proyecto, List<String> actividad,List<String> sociedad,List<String> sector,List<String> tecnologias,List<String> tipoTecnologias,String producto, String general) throws Exception {
		
		String aux ;
		String consultaActividad ="[";
		String consultaProyecto ="[";
		String consultaSector ="[";
		String consultaSociedad ="[";
		String consultaTecnologias ="[";
		String consultaTipoTecnologias ="[";
		
		if(actividad.size()!=0){
			Iterator<String> iteradorActividad = actividad.iterator();
			while(iteradorActividad.hasNext()){
				aux = iteradorActividad.next();
				if(iteradorActividad.hasNext()){
				consultaActividad = consultaActividad + "\"" + aux +"\",";
				}else{
				consultaActividad = consultaActividad + "\"" + aux +"\"]";
				}
			}
			consultaActividad =  "{ tipoActividad: {$in:"+consultaActividad+"}},";
			
		}else{
			consultaActividad = "";
		}
		
		if(proyecto.size()!=0){
			Iterator<String> iteradorProyecto = proyecto.iterator();
			while(iteradorProyecto.hasNext()){
				aux = iteradorProyecto.next();
				if(iteradorProyecto.hasNext()){
				consultaProyecto = consultaProyecto + "\"" + aux +"\",";
				}else{
				consultaProyecto = consultaProyecto + "\"" + aux +"\"]";
				}
			}
			consultaProyecto =  "{ tipoProyecto: {$in:"+consultaProyecto+"}},";
		}else{
			consultaProyecto = "";
		}
		
		if(sector.size()!=0){
			Iterator<String> iteradorSector = sector.iterator();
			while(iteradorSector.hasNext()){
				aux = iteradorSector.next();
				if(iteradorSector.hasNext()){
				consultaSector = consultaSector + "\"" + aux +"\",";
				}else{
				consultaSector = consultaSector + "\"" + aux +"\"]";
				}
			}
			consultaSector =  "{ sectorEmpresarial: {$in:"+consultaSector+"}},";
		}else{
			consultaSector = "";
		}
		
		if(sociedad.size()!=0){
			Iterator<String> iteradorSociedad = sociedad.iterator();
			while(iteradorSociedad.hasNext()){
				aux = iteradorSociedad.next();
				if(iteradorSociedad.hasNext()){
				consultaSociedad = consultaSociedad + "\"" + aux +"\",";
				}else{
				consultaSociedad = consultaSociedad + "\"" + aux +"\"]";
				}
			}
			consultaSociedad =  "{ sociedad: {$in:"+consultaSociedad+"}},";
		}else{
			consultaSociedad = "";
		}
		if(tecnologias.size()!=0){
			Iterator<String> iteradorTecnologias = tecnologias.iterator();
			while(iteradorTecnologias.hasNext()){
				aux = iteradorTecnologias.next();
				if(iteradorTecnologias.hasNext()){
				consultaTecnologias = consultaTecnologias + "\"" + aux +"\",";
				}else{
				consultaTecnologias = consultaTecnologias + "\"" + aux +"\"]";
				}
			}
			consultaTecnologias =  "{ tecnologias: {$in:"+consultaTecnologias+"}},";
		}else{
			consultaTecnologias = "";
		}
		if(producto==null){
			producto="";
		}
		List<String> tecnologiasTipoBusqueda = tecnologiaController.busquedaTecnologias(producto, tipoTecnologias);
		if(tecnologiasTipoBusqueda.size()!=0){
			Iterator<String> iteradorTipoTecnologias = tecnologiasTipoBusqueda.iterator();
			while(iteradorTipoTecnologias.hasNext()){
				aux = iteradorTipoTecnologias.next();
				if(iteradorTipoTecnologias.hasNext()){
				consultaTipoTecnologias = consultaTipoTecnologias + "\"" + aux +"\",";
				}else{
				consultaTipoTecnologias = consultaTipoTecnologias + "\"" + aux +"\"]";
				}
			}
			consultaTipoTecnologias =  "{ tecnologias: {$in:"+consultaTipoTecnologias+"}},";
		}else{
			if(tipoTecnologias.size()==0){
				consultaTipoTecnologias = "";
			}
			else{
				consultaTipoTecnologias="{tecnologias:{$in:[]}},"; 
			}
		}
		if(producto==null){
			producto="";
		}
		if(general==null){
			general="";
		}
		if(cliente==null){
			cliente="";
		}
		Calendar fecha = Calendar.getInstance();
		// *2 es para aumentr el rango de la fecha actual por si crean referencia sfuturas que salgan el el buscador
		Date actual = new Date(fecha.getTimeInMillis()*2);
		if(ultimosAños == fecha.get(Calendar.YEAR)){
			ultimosAños = 1980;
		}
		fecha.set(ultimosAños, 1, 1);
		Date desde = new Date(fecha.getTimeInMillis());
		List<String> clientesBusqueda = daoClientes.listaContenido(general);
		List<String> coDeBusqueda = daoCoDe.listaContenido(general);
		List<String> gerentesBusqueda = daoGerentes.listaContenido(general);
		//return dao.find("{'responsableComercial':{$in:#}}",gerentesBusqueda).as(ReferenciaWithAutoID.class).iterator();
        
		return dao.find("{$and:"
							+ " [ { cliente: #},"
							+ " { fechaInicio:{$gte:#,$lt:#}},"
							+ consultaProyecto
							+ consultaActividad
							+ consultaSector
							+ consultaSociedad
							+ consultaTecnologias
							+ consultaTipoTecnologias
							+ "{ estado:'validada'}],"
						+ "$or: [ {cliente:{$in:#}},"
							+ "{tipoProyecto:{$in:#}},"
							+ "{tipoActividad:{$in:#}},"
							+ "{sociedad:{$in:#}},"
							+ "{sectorEmpresarial:{$in:#}},"
							+ "{responsableComercial:{$in:#}},"
							+ "{responsableTecnico:{$in:#}},"
							+ "{resumenProyecto:#},"
							+ "{problematicaCliente:#},"
							+ "{solucionGFI:#},"
							+ "{denominacion: #}]}",	
						Pattern.compile(".*"+Pattern.quote(cliente)+".*"), desde, actual,
						clientesBusqueda,coDeBusqueda, coDeBusqueda, coDeBusqueda,
						coDeBusqueda,gerentesBusqueda,gerentesBusqueda,
						Pattern.compile(general),
						Pattern.compile(general),
						Pattern.compile(general),
						Pattern.compile(general)).as(ReferenciaWithAutoID.class).iterator();
		
	}
	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
	public static void main(String[] args) throws Exception {
		
		singleton = new ReferenciaDAO();
		//poner a "" si viene a null NOTA
		List<String> proyecto = new ArrayList();
		//proyecto.add("PROY");
		List<String> actividad = new ArrayList();
		//actividad.add("DES");
		List<String> sociedad = new ArrayList();
		//sociedad.add("AST");
		List<String> sector = new ArrayList();
		//sector.add("BANK");
		Iterator<ReferenciaWithAutoID> aux = singleton.listaContenido("",2016,proyecto,actividad,sociedad,sector,new ArrayList(),new ArrayList(),"","");
		ReferenciaWithAutoID recorrido = null;
		while(aux.hasNext()){
			
			recorrido =  aux.next();
			
		}
		
	}
	
}