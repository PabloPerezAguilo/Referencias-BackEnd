package com.example.controllers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;

import com.example.dao.UsuarioDAO;
import com.example.dao.UsuarioLdapDAO;
import com.example.filters.CustomAuthentication;
import com.example.models.Usuario;
import com.example.models.UsuarioLdap;

public class UsuarioController {

	// Singleton instances
	private UsuarioDAO dao;
	private static UsuarioController singleton;
	private static final Logger log = LoggerFactory.getLogger(CustomAuthentication.class);

	private UsuarioController() throws Exception {
		dao = UsuarioDAO.getInstance();
	}

	public static UsuarioController getInstance() throws Exception {
		if (singleton == null) {
			singleton = new UsuarioController();
		}
		return singleton;
	}

	

	/**
	 * Create nuevo Usuario
	 * @param name
	 * @param role
	 * @param password
	 * @throws Exception
	 */
	public void createUsuario(String name, String role) throws Exception {
		dao.insertUsuario(new Usuario(name, role));
		//dao.insertUsuario(new Usuario(name, role, this.makePasswordHash(password, this.generateSalting())));
		
	}

	/**
	 * Get Usuarios
	 * @return
	 * @throws Exception
	 */
	public List<Usuario> getUsuarios() throws Exception {
		List<Usuario> list = new ArrayList<Usuario>();
		Iterator<Usuario> i = dao.getUsuarios();
		while (i.hasNext()) {
			list.add(i.next());
		}
		return list;
	}
	
	/**
	 * Get Usuario By ID
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Usuario getUsuario(String id) throws Exception {
		Usuario usu = new Usuario();
		usu = dao.getUsuario(id);
		return usu;
	}
	
	/**
	 * Delete Usuario
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String deleteUsuario(String key) throws Exception{
		dao.deleteUsuario(key);
		return key;
	}
	
	/**
	 * Update Usuario
	 * @param key
	 * @param r
	 * @return
	 */
	public Usuario updateUsuario(String key, Usuario r){
		dao.updateUsuario(key,r);
		return r;
	}
	/**
	 * Check user/password and return the role
	 */
	public String loginUserLdap(UsuarioLdap usuario) throws Exception {
		
		// Comprobacion usuario en MongoDB
		Usuario usu = dao.getUsuarioLogin(usuario.getName());
		if (usu == null) {
			throw new Exception("User not found");
		}
		
		// conectar ldap y comprobar si esta con su pass 
		UsuarioLdapDAO usuarioLdap = new UsuarioLdapDAO(usuario);
		Authentication authentication = usuarioLdap.LoginLdap();
		
		if (authentication == null) {
			throw new Exception("User not found in LDAP");
		}else{
			log.info("User en LDAP");
		}
		
		// Si todo es correcto devuelve el Rol
		return usu.getRole();
	}
	
	public ArrayList<String> getAllUserLdap() throws Exception {
		ArrayList<String> res = null;
		Hashtable env = new Hashtable();

	    String sp = "com.sun.jndi.ldap.LdapCtxFactory";
	    env.put(Context.INITIAL_CONTEXT_FACTORY, sp);

	    String ldapUrl = "ldap://ldap.gfi-info.com:389/o=gfi-info.com";
	    env.put(Context.PROVIDER_URL, ldapUrl);

	    DirContext dctx = new InitialDirContext(env);

	    String base = "ou=People";

	    SearchControls sc = new SearchControls();
	    String[] attributeFilter = { "cn", "mail" };
	    sc.setReturningAttributes(attributeFilter);
	    sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

	    String filter = "uid={0}";

	    NamingEnumeration results = dctx.search(base, filter, sc);
	    while (results.hasMore()) {
	      SearchResult sr = (SearchResult) results.next();
	      Attributes attrs = (Attributes) sr.getAttributes();

	      Attribute attr = attrs.get("cn");
	      System.out.print(attr.get() + ": ");
	      res.add(attr.get().toString());
	      attr = attrs.get("mail");
	      System.out.println(attr.get());
	      res.add(attr.get().toString()); 
	    }
	    dctx.close();
		return res;
	}

}