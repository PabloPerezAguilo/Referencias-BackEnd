package com.example.dao;

import java.util.Iterator;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import com.example.models.ReferenciaWithAutoID;

public class ReferenciaDAO {

	private static ReferenciaDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "referencias";

	private ReferenciaDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
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
	public Iterator<ReferenciaWithAutoID> getReferenciasPendientes() throws Exception {
		return dao.find("{'estado':'pendiente'}").as(ReferenciaWithAutoID.class).iterator();
	}
	
	/**
	 * getReferencia
	 * @param key
	 * @return ReferenciaWithAutoID
	 * @throws Exception
	 */
	public ReferenciaWithAutoID getReferencia(int key) throws Exception{
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
		dao.remove("{_id:"+key+"}");
	}
	
	/**
	 * updateReferencia
	 * @param key
	 * @param r
	 * @throws Exception
	 */
	public void updateReferencia(ObjectId key, ReferenciaWithAutoID r) throws Exception{
		dao.update("{_id:"+key+"}").with(r);
	}

	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
}