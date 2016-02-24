package com.example.dao;

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
		
		Calendar fecha = Calendar.getInstance();
		Date actual = new Date(fecha.getTimeInMillis());
		fecha.set(ultimosAños, 1, 1);
		Date desde = new Date(fecha.getTimeInMillis());
		List<String> clientesBusqueda = daoClientes.listaContenido(general);
		List<String> coDeBusqueda = daoCoDe.listaContenido(general);
		List<String> gerentesBusqueda = daoGerentes.listaContenido(general);
		System.out.println(gerentesBusqueda);
		//return dao.find("{'responsableComercial':{$in:#}}",gerentesBusqueda).as(ReferenciaWithAutoID.class).iterator();
		return dao.find("{$and:"
							+ " [ { cliente: #},"
							+ " { fechaInicio:{$gte:#,$lt:#}},"
							+ "{ tipoProyecto: {$in:#}},"
							+ "{ tipoActividad: {$in:#}},"
							+ "{ sociedad: {$in:#}},"
							+ "{ sectorEmpresarial: {$in:#}}],"
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
						cliente, desde, actual, proyecto,
						actividad, sociedad, sector, clientesBusqueda,
						coDeBusqueda, coDeBusqueda, coDeBusqueda,
						coDeBusqueda,gerentesBusqueda,gerentesBusqueda,
						Pattern.compile(".*"+general+".*"),
						Pattern.compile(".*"+general+".*"),
						Pattern.compile(".*"+general+".*"),
						Pattern.compile(".*"+general+".*")).as(ReferenciaWithAutoID.class).iterator();
		
	}
	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
//	public static void main(String[] args) throws Exception {
//		
//		System.out.println("principio");
//		singleton = new ReferenciaDAO();
//		//poner a "" si viene a null NOTA
//		Iterator<ReferenciaWithAutoID> aux = singleton.listaContenido("AXA Seguros (AXA)",null,"PROY","DES","AST","BANK","",null,null,null);
//		ReferenciaWithAutoID recorrido = null;
//		while(aux.hasNext()){
//			
//			recorrido =  aux.next();
//			System.out.println(recorrido);
//			
//		}
//		
//	}
	
}