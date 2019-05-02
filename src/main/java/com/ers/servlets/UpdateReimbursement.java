package com.ers.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ers.exceptions.UnauthenticatedAccessException;
import com.ers.models.Principal;
import com.ers.models.Reimbursement;
import com.ers.models.ReimbursementStatus;
import com.ers.services.ReimbursementService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class UpdateReimbursement. This servlet will update
 * the reimbursement status by an authenticated user.
 * 
 * @author Jose Rivera
 */
@WebServlet("/changestatus")
public class UpdateReimbursement extends HttpServlet {

	/**
	 * Generated Serial Version ID. Generated by Java.
	 */
	private static final long serialVersionUID = -3833141907820839711L;

	private static final Logger LOG = Logger.getLogger(UpdateReimbursement.class);

	private ReimbursementService reimbursementService = new ReimbursementService();

	/**
	 * PUT method to update the reimbursement status.
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		try {

			// Get the object mapper
			ObjectMapper objectMapper = new ObjectMapper();

			// Get the principle object
			Principal principal = (Principal) req.getAttribute("principal");

			// Check the principal
			if (principal == null)
				throw new UnauthenticatedAccessException(
						"In UpdateReimbursement.doPut():: No principal attribute found on request object");

			// Check the role of the principal
			if (principal.getRole().equals("FINANCE_MANAGER")) {

				// Get the update info
				String[] info = objectMapper.readValue(req.getInputStream(), String[].class);

				// Get the reimbursement to update
				Reimbursement reimbursement = reimbursementService.getById(Integer.parseInt(info[0]));

				// check the status to update
				if (info[1].equals("approved"))
					reimbursement.setReimbursementStatus(new ReimbursementStatus(2, "APPROVED"));
				else
					reimbursement.setReimbursementStatus(new ReimbursementStatus(3, "DENIED"));

				// Set the resolver Id
				reimbursement.setResolverId(Integer.parseInt(principal.getId()));

				// Attempt to update
				Reimbursement updatedReimbursement = reimbursementService.update(reimbursement);

				if (updatedReimbursement == null) {
					LOG.error("In UpdateReimbursement.doPut():: Unable to update reimbursement status");
					resp.setStatus(400);
					return;
				}

				// Request was successful, set status to 200
				resp.setStatus(200);
				
				// Send our reimbursement object back to client
				PrintWriter printWriter = resp.getWriter();
				resp.setContentType("application/json");

				// Write our user back as JSON
				String reimbursementJson = objectMapper.writeValueAsString(updatedReimbursement);
				printWriter.write(reimbursementJson);

			} else
				throw new UnauthenticatedAccessException("In UpdateReimbursement.doPut():: Role is unauthenticated");

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
