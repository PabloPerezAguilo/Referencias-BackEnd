package com.example.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import com.example.models.CatalogoClientes;
import com.example.models.CatalogoGerentes;
import com.example.models.Plantilla;
import com.example.models.ReferenciaWithAutoID;
import com.example.models.Tecnologia;

public class PlantillaDAO {

	private static PlantillaDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "Plantillas";

	private PlantillaDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static PlantillaDAO getInstance() throws Exception {
		if (singleton == null) {
			singleton = new PlantillaDAO();
		}
		return singleton;
	}

	public Iterator<Plantilla> getPlantillas() throws Exception {
		return dao.find().as(Plantilla.class).iterator();
	}

	public Plantilla getPlantilla(String key) throws Exception{
		return dao.findOne("{'_id':#}", key).as(Plantilla.class);
	}

	public void insertPlantilla(Plantilla p) throws Exception{
		dao.insert(p);
	}
	
	public void deletePlantilla(String key) throws Exception{
		dao.remove("{ _id: # }", key);
	
	}
	public void updateReferencia(String key, Plantilla p) throws Exception{
		deletePlantilla(key);
		insertPlantilla(p);
	}

	public void clearStore() {
		dao.drop();
	}
}
