package com.example.dao;


import java.io.File;

import org.apache.log4j.Logger;
import org.jongo.FindOne;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.ObjectId;

import com.example.models.ReferenciaWithAutoID;
import com.example.services.ReferenciaService;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;


public class ImagenDAO {

	private static ImagenDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "photo.files";
	
	//borrar
	private static final Logger log = Logger.getLogger(ImagenDAO.class.getName());

	private ImagenDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static ImagenDAO getInstance() throws Exception {
		if (singleton == null) {
			singleton = new ImagenDAO();
		}
		return singleton;
	}

	/**
	 * guardarImagen
	 * @param contenido
	 * @return String (identificador de la imagen en mongo)
	 * @throws Exception
	 */
	public String insertImagen(File archivo) throws Exception {
		
		String newFileName = "prueba";
		DB db = DataBase.getbd();
		File imageFile = archivo;
		// create a "photo" namespace
		GridFS gfsPhoto = new GridFS(db, "photo");
		// get image file from local drive
		GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
		// set a new filename for identify purpose
		gfsFile.setFilename(newFileName);
		// save the image file into mongoDB
		gfsFile.save();
		Object aux = gfsFile.getId();
		return aux.toString();
		
//		// print the result
//		DBCursor cursor = gfsPhoto.getFileList();
//		while (cursor.hasNext()) {
//			System.out.println(cursor.next());
//		}
//		// get image file by it's filename
//		GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);
//		// save it into a new image file
//		imageForOutput.writeTo("C:\\Users\\usuarioGFI\\nuevasharon.gif");
//		// remove the image file from mongoDB
//		gfsPhoto.remove(gfsPhoto.findOne(newFileName));
//		System.out.println("Done");
//		return identificador;
	}
	
	/**
	 * getImagen
	 * @param identificadorImagen
	 * @return String (identificador de la imagen en mongo)
	 * @throws Exception
	 */
	public FindOne getImagen(String id) throws Exception {
		
		//String aux = dao.findOne("{_id:"+id+"}").as(String.class);
		//return dao.findOne("{'_id':#}", id);
		
		FindOne aux = dao.findOne("{$or: [{_id : true},{_id: '"+id+"'}]}");
		
		return aux;
	}
	
	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
public static void main(String[] args) throws Exception {
	
	ImagenDAO prueba = new ImagenDAO();
	log.info("aqui-->"+prueba.getImagen("ObjectId(\"564db08a43e1b23ea69def6\")"));
	
	
}
}