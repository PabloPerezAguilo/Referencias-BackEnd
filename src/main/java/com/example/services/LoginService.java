package com.example.services;

import java.util.HashMap;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.example.controllers.UsuarioController;
import com.example.models.Usuario;
import com.example.utils.Message;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Path("/login/")
@Api(value = "/login", description = "Login operations")
@Produces(MediaType.APPLICATION_JSON)
public class LoginService {

	private static final Logger log = Logger.getLogger(LoginService.class.getName());
	
	// pepito.usuario
	 //ñuser.password
	/*
	@POST
	@Path("/RealizarLogin")
	@ApiOperation(value = "Validación del acceso de un usuario", notes = "Devuelve un mensaje de estado")
	public Response loginUsuario(
			@ApiParam(value = "Datos de la reserva", required = true) Usuario usuario) {
		Status estado = Response.Status.BAD_REQUEST;
		Mensaje mensaje;
		Map<String, Object> salidaMap = new HashMap<String, Object>();
		String user = usuario.getNombre();
		String pass = usuario.getPassword();
		String userName;

		try {
			UsuarioControlador usuarioControlador = new UsuarioControlador();
			String rol;
			boolean primeraVez;
			String template;
			boolean existeUsuario = usuarioControlador.existeUsuario(user);

			// Se trata de loguear contra LDAP
			final String ldapActive = Configuracion.getInstance().getProperty(Configuracion.LDAP_ACTIVE);
			if (!"false".equalsIgnoreCase(ldapActive)) {
				usuarioControlador.loginLdapUsuario(user, pass);
				userName = usuarioControlador.obtenerNombreUsuarioLdap(user);
				usuario.setNombreCompleto(userName);
			} else {
				if (existeUsuario) {
					userName = usuarioControlador.obtenerNombreUsuarioMongo(user);
					usuario.setNombreCompleto(userName);
				}
			}

			if (!existeUsuario) {
				usuarioControlador.insertarUsuario(user, ROLE_USER, 0, false, "", usuario.getNombreCompleto());
			}

			primeraVez = usuarioControlador.isPrimeraVez(user);
			rol = usuarioControlador.obtenerRol(user);
			template = usuarioControlador.obtenerTemplate(user);

			mensaje = new Mensaje(0, "La autenticación es correcta.");
			salidaMap.put("primeraVez", primeraVez);
			salidaMap.put("rol", rol);
			salidaMap.put("template", template);
			salidaMap.put("nombreCompleto", StringUtils.hasText(usuario
					.getNombreCompleto()) ? usuario.getNombreCompleto() : user);
			estado = Response.Status.OK;
			LOGGER.info("Login del usuario " + usuario.getNombre() + ": "
					+ mensaje.getMensaje());
		} catch (Exception e) {
			LOGGER.error(
					"Error al autenticar al usuario " + usuario.getNombre()
							+ ": ", e);
			mensaje = new Mensaje(1, "Operacion incorrecta: " + e.getMessage());
		}

		salidaMap.put("mensaje", mensaje);
		return Response.status(estado).entity(salidaMap).build();
	}
	*/
	@POST
	@ApiOperation(value = "Make login user", notes = "Check user/password and return their role")
	public Response login(@ApiParam(value = "Role field is not required", required = true) Usuario usuario) {
		Status status = Response.Status.BAD_REQUEST;
		Object out;
		try {
			UsuarioController userController = UsuarioController.getInstance();
			// Make login
			String role = userController.loginUser(usuario.getName(), usuario.getPassword());
			// Take the role and insert into a map
			HashMap<String, Object> outMap = new HashMap<String, Object>();
			outMap.put("role", role);
			out = outMap;
			status = Response.Status.OK;
			log.info("Login successful from user:" + usuario.getName());
		} catch (Exception e) {
			log.error("Error in login from user " + usuario.getName() + ": ", e);
			out = new Message(e.getMessage());
		}
		return Response.status(status).entity(out).build();
	} 
}