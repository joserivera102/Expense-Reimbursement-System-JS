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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class RegisterUserServlet. This servlet will help to
 * register a user.
 * 
 * @author Jose Rivera
 */
@WebServlet("/add")
public class RegisterUserServlet extends HttpServlet {

	/**
	 * Generated Serial Version ID. Generated by Java.
	 */
	private static final long serialVersionUID = -1250627709547720918L;

	private static final Logger LOG = Logger.getLogger(RegisterUserServlet.class);

	private UserService userService = new UserService();

	/**
	 * POST method to accept user information in order to create a user. Will accept
	 * the user information in JSON and send the details to the user service to
	 * create a user.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		try {

			// Get the object mapper
			ObjectMapper objectMapper = new ObjectMapper();

			// Create our user based off the information from the request
			User newUser = objectMapper.readValue(req.getInputStream(), User.class);

			// If our user was null, return a status of 400
			if (newUser == null) {
				LOG.warn("In RegisterUserServlet.doPost()::User retrieved from request was " + newUser);
				resp.setStatus(400);
				return;
			}

			// Pass our user to the user service to persist it to the database.
			User user = userService.addUser(newUser);

			if (user == null) {
				LOG.warn("In RegisterUserServlet.doPost()::User returned from User Service was " + user);
				resp.setStatus(400);
				return;
			}

			// Our user was successfully added
			resp.setStatus(200);

			// Send our user object back to client
			PrintWriter printWriter = resp.getWriter();
			resp.setContentType("application/json");
			
			// Write our user back as JSON
			String userJson = objectMapper.writeValueAsString(user);
			printWriter.write(userJson);
			
		} catch (IllegalStateException ise) {
			LOG.error(ise.getMessage());
		} catch (JsonParseException jpe) {
			LOG.error(jpe.getMessage());
		} catch (JsonMappingException jmp) {
			LOG.error(jmp.getMessage());
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
}
