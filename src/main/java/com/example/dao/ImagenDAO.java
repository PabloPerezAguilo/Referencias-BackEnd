package com.example.dao;


import java.io.File;

import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.ObjectId;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;


public class ImagenDAO {

	private static ImagenDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "imagenes";

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
	 * @return ObjectId
	 * @throws Exception
	 */
	public ObjectId guardarImagen(File archivo) throws Exception {
		ObjectId identificador = null;
		String newFileName = "prueba";
		DB db = DataBase.getbd();
		File imageFile = new File("imagenes/temporal.png");

		// create a "photo" namespace
		GridFS gfsPhoto = new GridFS(db, "photo");

		
		// get image file from local drive
		GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);

		// set a new filename for identify purpose
		gfsFile.setFilename(newFileName);

		// save the image file into mongoDB
		gfsFile.save();

		// print the result
		DBCursor cursor = gfsPhoto.getFileList();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}

		// get image file by it's filename
		GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);

		// save it into a new image file
		imageForOutput.writeTo("C:\\Users\\usuarioGFI\\nuevasharon.gif");

		// remove the image file from mongoDB
		gfsPhoto.remove(gfsPhoto.findOne(newFileName));

		System.out.println("Done");
		return identificador;
	}
	
	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
}