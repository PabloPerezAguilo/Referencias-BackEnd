package com.example.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.utils.InitDB;


/**
 * The Class SwaggerFilter.
 */
public class SwaggerFilter implements javax.servlet.Filter {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SwaggerFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.info("Init the filter");
		try {
			InitDB.loadUsers();
		} catch (Exception e) {
			LOG.error("Error en la carga de datos");
			e.printStackTrace();
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		// Allows CORS
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		// Headers allowed
		res.addHeader("Access-Control-Allow-Headers", "X-Requested-With,Authorization,Accept,Origin,Content-Type");
		chain.doFilter(request, res);
	}

	@Override
	public void destroy() {
		LOG.info("Destroy the filter");
	}
}