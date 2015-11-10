package com.example.dao;

import java.util.Iterator;

import org.jongo.MongoCollection;

import com.example.models.Usuario;

public class UsuarioDAO {

	private static UsuarioDAO singleton;
	private static MongoCollection dao;
	private static final String COLLECTION_NAME_MONGO = "usuarios";

	private UsuarioDAO() throws Exception {
		dao = DataBase.getInstance().getCollection(COLLECTION_NAME_MONGO);
	}

	public static UsuarioDAO getInstance() throws Exception {
		if (singleton == null) {
			singleton = new UsuarioDAO();
		}
		return singleton;
	}

	/**
	 * getUsuarios
	 * @return Iterator<Usuario>
	 * @throws Exception
	 */
	public Iterator<Usuario> getUsuarios() throws Exception {
		return dao.find(). as(Usuario.class).iterator();
	}

	/**
	 * getUsuario
	 * @param idUser
	 * @return Usuario
	 * @throws Exception
	 */
	public Usuario getUsuario(String idUser) throws Exception {
		return dao.findOne("{'_id':#}", idUser).as(Usuario.class);
	}
	
	/**
	 * getUsuarioLogin
	 * @param idUser
	 * @return Usuario
	 * @throws Exception
	 */
	public Usuario getUsuarioLogin(String idUser) throws Exception {
		return dao.findOne("{'_id':#}", idUser).as(Usuario.class);
	}

	/**
	 * insertUsuario
	 * @param user
	 * @throws Exception
	 */
	public void insertUsuario(Usuario user) throws Exception {
		dao.insert(user);
	}
	
	/**
	 * deleteUsuario
	 * @param key
	 * @throws Exception
	 */
	public void deleteUsuario(String key) throws Exception {
		dao.remove("{'_id':#}", key);
	}
	
	/**
	 * updateUsuario
	 * @param key
	 * @param r
	 * @throws Exception
	 */
	public void updateUsuario(String key, Usuario r) throws Exception {
		dao.update("{'_id':#}", key).with(r);
	}

	/**
	 * clearStore
	 */
	public void clearStore() {
		dao.drop();
	}
}