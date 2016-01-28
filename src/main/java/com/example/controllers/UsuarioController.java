package com.example.controllers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.example.dao.UsuarioDAO;
import com.example.dao.UsuarioLdapDAO;
import com.example.filters.CustomAuthentication;
import com.example.models.InformacionUsuarioLdap;
import com.example.models.Usuario;
import com.example.utils.Config;

public class UsuarioController {

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
	 * createUsuario
	 * Crea un nuevo usuario en la base de datos
	 * @param nick
	 * @param name
	 * @param role
	 * @throws Exception
	 */
	public void createUsuario(String nick, String name, String role) throws Exception {
		
		switch(role){
			case "administrador": role = "ROLE_ADMINISTRADOR";
		break;
			case "validador": role = "ROLE_VALIDADOR";
		break;
			case "consultor": role = "ROLE_CONSULTOR";
		break;
			case "mantenimiento": role = "ROLE_MANTENIMIENTO";
		break;
			default: throw new Exception("Role incorrecto");
		}
		dao.insertUsuario(new Usuario(nick, name, role));
		
	}

	/**
	 * getUsuarios
	 * Recoge todos los usuarios de la base de datos
	 * @return List<Usuario> 
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
	 * getUsuario
	 * Recoge un usuario de la base de datos indicada por parametro
	 * @param id | Identificador de usuario que será el nickname del LDAP
	 * @return Usuario
	 * @throws Exception
	 */
	public Usuario getUsuario(String id) throws Exception {
		Usuario usu = new Usuario();
		usu = dao.getUsuario(id);
		return usu;
	}
	
	/**
	 * deleteUsuario
	 * Borra un usuario de la base de datos indicado por parametro
	 * @param id | Identificador de usuario que será el nickname del LDAP
	 * @return id
	 * @throws Exception
	 */
	public String deleteUsuario(String id) throws Exception{
		dao.deleteUsuario(id);
		return id;
	}
	
	/**
	 * updateUsuario
	 * Modifica un usuario de la base de datos indicado por parametro
	 * @param id | Identificador de usuario que será el nickname del LDAP
	 * @param r
	 * @return Usuario
	 * @throws Exception
	 */
	public Usuario updateUsuario(String id, Usuario r) throws Exception{
		String role = r.getRole();
		switch(role){
		case "administrador": role = "ROLE_ADMINISTRADOR";
	break;
		case "validador": role = "ROLE_VALIDADOR";
	break;
		case "consultor": role = "ROLE_CONSULTOR";
	break;
		case "mantenimiento": role = "ROLE_MANTENIMIENTO";
	break;
		default: throw new Exception("Role incorrecto");
	}
		r.setRole(role);
		dao.updateUsuario(id,r);
		return r;
	}
	
	/**
	 * loginUserLdap
	 * Servicio para loguearse en la aplicacion. Comprueba que el usuario este en la base de datos
	 * y en el LDAP
	 * @param usuario | Objeto UsuarioLdap
	 * @return rol del usuario
	 * @throws Exception
	 */
	public Usuario loginUserLdap(Map<String,String> usuario) throws Exception {
		
		String nick = (String)usuario.get("nick");
		String pass = (String)usuario.get("password");;
		// Comprobacion usuario en MongoDB
		Usuario usu = dao.getUsuarioLogin(nick);
		if (usu == null) {
			throw new Exception("User not found in DB");
		}else{
			log.info("User en Mongo");
		}
		
		// conectar ldap y comprobar si esta con su pass 
		UsuarioLdapDAO usuarioLdap = new UsuarioLdapDAO(nick,pass);
		Authentication authentication = usuarioLdap.loginLdap();
		
		if (authentication == null) {
			throw new Exception("User not found in LDAP");
		}else{
			log.info("User en LDAP");
		}
		
		// Si todo es correcto devuelve el Rol
		return usu;
	}
	
	/**
	 * getAllUserLdap
	 * Recoge todos los usuarios del Ldap de GFI
	 * @return ArrayList<InformacionUsuarioLdap>
	 * @throws Exception
	 */
	public ArrayList<InformacionUsuarioLdap> getAllUserLdap() throws Exception {
		
			Hashtable<String, String> env = new Hashtable<String, String>();
	        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.PROVIDER_URL,Config.getInstance().getProperty(Config.LDAP_URL));

	        DirContext ctx = new InitialDirContext(env);
	        String base = "ou=People, o=gfi-info.com";

	        SearchControls sc = new SearchControls();
	        String[] attributeFilter = {"uid","cn","mail"};
	        sc.setReturningAttributes(attributeFilter);
	        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
	        String filter = "(&(uid=*))";
	        NamingEnumeration<?> results = ctx.search(base, filter, sc);        
	        ArrayList<InformacionUsuarioLdap> usuarios = new ArrayList<InformacionUsuarioLdap>();
	        
	       
	       while (results.hasMore()) {
	        	SearchResult sr = (SearchResult) results.next();
	        	Attributes attrs = sr.getAttributes();
	        	InformacionUsuarioLdap usuario = new InformacionUsuarioLdap(attrs);    
	            usuarios.add(usuario);
	        }
	        ctx.close();
	        return (usuarios);
	        
	}
}