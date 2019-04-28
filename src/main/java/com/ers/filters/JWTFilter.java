package com.ers.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ers.models.Principal;
import com.ers.util.JWTConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;

@WebFilter("/*")
public class JWTFilter extends HttpFilter {

	/**
	 * Generated Serial Version ID. Generated by Java.
	 */
	private static final long serialVersionUID = -5961732283003809565L;

	private static final Logger LOG = Logger.getLogger(JWTFilter.class);

	/**
	 * FILTER method to intercept all requests and responses coming into and out of
	 * the server. Will check for JWT's in the header of all requests and allow or
	 * deny access.
	 */
	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		// Configure the headers
		setAccessControlHeaders(resp);

		// 1. Get the HTTP header named "Authorization"
		String header = req.getHeader(JWTConfig.HEADER);

		// 2. Validate the header values and check the prefix
		if (header == null || !header.startsWith(JWTConfig.PREFIX)) {
			LOG.info("Request originates from an unauthenticated origin");
			
			// 2.1: If there is no header, or one that we provided, then go to the next step
			// in the filter chain (target servlet)
			chain.doFilter(req, resp);
			return;
		}

		// 3. Get the token
		String token = header.replaceAll(JWTConfig.PREFIX, "");

		try {

			// 4. Validate the token
			Claims claims = Jwts.parser().setSigningKey(JWTConfig.signingKey).parseClaimsJws(token).getBody();

			// 5. Obtain the principal/subject stored in the JWT
			Principal principal = new Principal();
			principal.setId(claims.getId());
			principal.setRole(claims.get("role", String.class));
			principal.setPassword(claims.get("password", String.class));

			/*
			 * 6. Attach an attribute to the request indicating information about the
			 * principal
			 */
			req.setAttribute("principal", principal);

		} catch (UnsupportedJwtException uje) {
			LOG.error(uje.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		// 7. Send the request to the next filter in the chain (target servlet)
		chain.doFilter(req, resp);
	}

	/**
	 * Method to set up the response headers to allow certain properties to be
	 * exposed.
	 * 
	 * @param resp The Http response object that contains the headers.
	 */
	private void setAccessControlHeaders(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE");
		resp.setHeader("Access-Control-Allow-Headers", "Content-type, Authorization");
		resp.setHeader("Access-Control-Expose-Headers", "Authorization");
	}
}
