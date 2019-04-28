package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ers.models.Reimbursement;
import com.ers.models.User;
import com.ers.services.ReimbursementService;
import com.ers.services.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class GetAllReimbursementsForUser. This servlet will
 * help to retrieve all reimbursements for a user.
 * 
 * @author Jose Rivera
 */
public class GetAllReimbursementsForUser extends HttpServlet {

	/**
	 * Generated Serial Version ID. Generated by Java.
	 */
	private static final long serialVersionUID = 5996503506356282410L;

	private static final Logger LOG = Logger.getLogger(GetAllReimbursementsForUser.class);

	private ReimbursementService reimbursementService = new ReimbursementService();

	private UserService userService = new UserService();

	/**
	 * 
	 * POST method to accept a user ID in order to create a list of reimbursements
	 * for that user.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		try {

			// Get the object mapper
			ObjectMapper objectMapper = new ObjectMapper();

			// Get the id of the current user
			int userId = Integer.parseInt(req.getHeader("UserId"));

			// Use the id to get the user
			User user = userService.getById(userId);

			// Check our user to make sure it is valid
			if (user == null) {
				LOG.warn("In GetAllReimbursementsForUser.doPost():: User was null");
				resp.setStatus(400);
				return;
			}

			// Get the reimbursements for that user. Can be an empty list
			List<Reimbursement> reimbursements = reimbursementService.getByUser(user);

			// Everything was successful, set the status to ok
			resp.setStatus(200);

			// Send our list back to client
			PrintWriter printWriter = resp.getWriter();
			resp.setContentType("application/json");

			String reimbursementList = objectMapper.writeValueAsString(reimbursements);
			printWriter.write(reimbursementList);

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
