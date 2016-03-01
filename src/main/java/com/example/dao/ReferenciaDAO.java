package com.example.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import com.example.models.ReferenciaWithAutoID;

public class ReferenciaDAO {

	private static ReferenciaDAO singleton;
	private static MongoCollection dao;
	private static CatalogoClientesDAO daoClientes;
	private static CatalogoCoDeDAO daoCoDe;
	private static CatalogoGerentesDAO daoGerentes;
	private static final String COLLECTION_NAME_MONGO = "referencias";

	private ReferenciaDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
		daoClientes = CatalogoClientesDAO.getInstance();
		daoCoDe = CatalogoCoDeDAO.getInstance();
		daoGerentes = CatalogoGerentesDAO.getInstance();
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
		System.out.println(key);
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
	
	public Iterator<ReferenciaWithAutoID> listaContenido(String cliente, int ultimosAños,List<String> proyecto, List<String> actividad,List<String> sociedad,List<String> sector, String general) throws Exception {
		
		String aux ;
		String consultaActividad ="[";
		String consultaProyecto ="[";
		String consultaSector ="[";
		String consultaSociedad ="[";
		
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
			System.out.println(consultaActividad);
			
		}else{
			System.out.println("actividad vacia");
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
			System.out.println(consultaProyecto);
		}else{
			System.out.println("proyecto vacio");
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
			System.out.println(consultaSector);
		}else{
			System.out.println("sector vacio");
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
			System.out.println(consultaSociedad);
		}else{
			System.out.println("sociedad vacia");
			consultaSociedad = "";
		}
		if(general==null){
			general="";
		}
		if(cliente==null){
			cliente="";
		}
		Calendar fecha = Calendar.getInstance();
		Date actual = new Date(fecha.getTimeInMillis());
		if(ultimosAños == fecha.get(Calendar.YEAR)){
			ultimosAños = 1980;
		}
		fecha.set(ultimosAños, 1, 1);
		Date desde = new Date(fecha.getTimeInMillis());
		List<String> clientesBusqueda = daoClientes.listaContenido(general);
		List<String> coDeBusqueda = daoCoDe.listaContenido(general);
		List<String> gerentesBusqueda = daoGerentes.listaContenido(general);
		//return dao.find("{'responsableComercial':{$in:#}}",gerentesBusqueda).as(ReferenciaWithAutoID.class).iterator();
		System.out.println("inicio consulta");
		return dao.find("{$and:"
							+ " [ { cliente: #},"
							+ " { fechaInicio:{$gte:#,$lt:#}},"
							+ consultaProyecto
							+ consultaActividad
							+ consultaSector
							+ consultaSociedad
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
		
		System.out.println("principio");
		singleton = new ReferenciaDAO();
		//poner a "" si viene a null NOTA
		List<String> proyecto = new ArrayList();
		proyecto.add("PROY");
		List<String> actividad = new ArrayList();
		actividad.add("DES");
		actividad.add("sa");
		actividad.add("pru");
		actividad.add("125");
		actividad.add("nanynyny");
		List<String> sociedad = new ArrayList();
		sociedad.add("");
		List<String> sector = new ArrayList();
		sector.add("BANK");
		Iterator<ReferenciaWithAutoID> aux = singleton.listaContenido("AXA Seguros (AXA)",2016,proyecto,actividad,sociedad,sector,"");
		ReferenciaWithAutoID recorrido = null;
		while(aux.hasNext()){
			
			recorrido =  aux.next();
			System.out.println(recorrido);
			
		}
		
	}
	
}