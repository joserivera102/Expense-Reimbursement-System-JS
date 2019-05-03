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

import com.ers.exceptions.InvalidStatusCodeException;
import com.ers.exceptions.UnauthenticatedAccessException;
import com.ers.models.Principal;
import com.ers.models.Reimbursement;
import com.ers.services.ReimbursementService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Reimbursement servlet to accept a request to retrieve all reimbursements by a
 * requested status if the request was authenticated.
 * 
 * @author Jose Rivera
 *
 */
@WebServlet("/reimbursements/*")
public class GetReimbursementsByStatusServlet extends HttpServlet {

	/**
	 * Generated Serial Version ID. Generated by Java.
	 */
	private static final long serialVersionUID = -3268437225216024099L;

	private static final Logger LOG = Logger.getLogger(GetAllUsersServlet.class);

	private ReimbursementService reimbursementService = new ReimbursementService();

	/**
	 * GET method return all reimbursements by status using the status id.
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
						"In GetReimbursementsByStatusServlet.doGet():: No principal attribute found on request object");

			// Check the role of the principal
			if (principal.getRole().equals("FINANCE_MANAGER")) {

				// Get the status id
				int statusId = getStatusId(req);

				// Get a list of reimbursements
				List<Reimbursement> reimbursements = reimbursementService.getByStatus(statusId);

				// Request was successful, set status to 200
				resp.setStatus(200);

				// Get the print writer to write the List back to client
				PrintWriter printWriter = resp.getWriter();
				resp.setContentType("application/json");

				// Write the list back as JSON
				String reimbursementsListJson = objectMapper.writeValueAsString(reimbursements);
				printWriter.write(reimbursementsListJson);

			} else
				throw new UnauthenticatedAccessException(
						"In GetReimbursementsByStatusServlet.doGet():: Role is unauthenticated");

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
		} catch (InvalidStatusCodeException isce) {
			LOG.error(isce.getMessage());
			resp.setStatus(400);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			resp.setStatus(500);
		}
	}

	/**
	 * Helper function to retrieve the status id from the request URI.
	 * 
	 * @param req The Http Request object.
	 * 
	 * @return A valid status id.
	 * 
	 * @throws InvalidStatusCodeException If a status code that is not supported is
	 *                                    requested, will throw an
	 *                                    InvalidStatusCodeException.
	 */
	private int getStatusId(HttpServletRequest req) throws InvalidStatusCodeException {

		switch (req.getRequestURI()) {

		case "/ExpenseReimbursementSystemJS/reimbursements/1":
			return 1;

		case "/ExpenseReimbursementSystemJS/reimbursements/2":
			return 2;

		case "/ExpenseReimbursementSystemJS/reimbursements/3":
			return 3;

		default:
			throw new InvalidStatusCodeException(
					"In GetReimbursementsByStatusServlet.doGet():: Invalid status code requested");
		}
	}
}
