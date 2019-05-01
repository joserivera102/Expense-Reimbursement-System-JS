package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ers.exceptions.UnauthenticatedAccessException;
import com.ers.models.Principal;
import com.ers.models.User;
import com.ers.services.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class GetAllUsersServlet. This servlet will return all
 * users if request was made by an authorized user.
 * 
 * @author Jose Rivera
 *
 */
@WebServlet("/getusers")
public class GetAllUsersServlet extends HttpServlet {

	/**
	 * Generated Serial Version ID. Generated by Java.
	 */
	private static final long serialVersionUID = 5136929716344211652L;

	private static final Logger LOG = Logger.getLogger(GetAllUsersServlet.class);

	private UserService userService = new UserService();

	/**
	 * GET method return all users if the request is authorized.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		try {

			// Get the object mapper
			ObjectMapper objectMapper = new ObjectMapper();

			// Get the principle object
			Principal principal = (Principal) req.getAttribute("principal");

			// Check the principal
			if (principal == null)
				throw new UnauthenticatedAccessException(
						"In GetAllUsersServlet.doGet():: No principal attribute found on request object");

			// Check the role of the principal
			if (principal.getRole().equals("FINANCE_MAANGER")) {

				// Get a list of users
				List<User> users = userService.getAllUsers();

				// Request was successful, set status to 200
				resp.setStatus(200);

				// Send our List object back to client
				PrintWriter printWriter = resp.getWriter();
				resp.setContentType("application/json");

				// Write the list back as JSON
				String userListJson = objectMapper.writeValueAsString(users);
				printWriter.write(userListJson);

			} else
				throw new UnauthenticatedAccessException("In GetAllUsersServlet.doGet():: Role is unauthenticated");

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
		} catch (UnauthenticatedAccessException uae) {
			LOG.error(uae.getMessage());
			resp.setStatus(401);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			resp.setStatus(500);
		}
	}
}
