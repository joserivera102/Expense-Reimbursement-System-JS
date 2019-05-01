package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ers.models.User;
import com.ers.services.UserService;
import com.ers.util.JWTConfig;
import com.ers.util.JWTGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AuthServlet. This servlet will help to
 * authenticate a user.
 * 
 * @author Jose Rivera
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

	/**
	 * Generated Serial Version ID. Generated by Java.
	 */
	private static final long serialVersionUID = 1444101574839108227L;

	private static final Logger LOG = Logger.getLogger(AuthServlet.class);

	private UserService userService = new UserService();

	/**
	 * POST method to authenticate a login request. Takes the user name and password
	 * from the client and validates and returns a user if found.
	 * 
	 * Writes a valid user back in JSON format or returns a status code of 400 for
	 * failure
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		try {

			// Get the object mapper
			ObjectMapper objectMapper = new ObjectMapper();

			String[] credentials = objectMapper.readValue(req.getInputStream(), String[].class);

			// Our credentials array should only contain two values
			if (credentials.length != 2) {
				LOG.warn(
						"In AuthServlet.doPost():: Credentials array did not have the necessary values or to many values");
				resp.setStatus(401);
				return;
			}

			// Attempt to get a user by the credentials
			User user = userService.getByCredentials(credentials[0], credentials[1]);

			if (user == null) {
				LOG.warn("In AuthServlet.doPost():: UserService.getByCredentials() return " + user);
				resp.setStatus(400);
				return;
			}

			// Add appropriate headers for the user
			resp.addHeader(JWTConfig.HEADER, JWTConfig.PREFIX + JWTGenerator.createJWT(user));
			resp.addHeader("UserId", String.valueOf(user.getId()));
			resp.addHeader("Username", user.getUsername());
			resp.addHeader("Role", user.getRole().getRole());

			// login was successful, set status to 200
			resp.setStatus(200);

			// Send our user object back to client
			PrintWriter printWriter = resp.getWriter();
			resp.setContentType("application/json");

			// Write the user back as JSON
			String userJson = objectMapper.writeValueAsString(user);
			printWriter.write(userJson);

		} catch (IllegalStateException ise) {
			LOG.error(ise.getMessage());
			resp.setStatus(400);
		} catch (JsonParseException jpe) {
			LOG.error(jpe.getMessage());
			resp.setStatus(400);
		} catch (JsonMappingException jmp) {
			LOG.error(jmp.getMessage());
			resp.setStatus(400);
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage());
			resp.setStatus(400);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			resp.setStatus(500);
		}
	}
}
