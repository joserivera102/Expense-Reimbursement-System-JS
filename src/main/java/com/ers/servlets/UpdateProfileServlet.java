package com.ers.servlets;

import java.io.IOException;

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
 * Servlet implementation class UpdateProfileServlet. This servlet will help to
 * update the user information from the client.
 * 
 * @author Jose Rivera
 */
@WebServlet("/update")
public class UpdateProfileServlet extends HttpServlet {

	/**
	 * Generated Serial Version ID. Generated by Java.
	 */
	private static final long serialVersionUID = 224914874322650577L;

	private static final Logger LOG = Logger.getLogger(UpdateProfileServlet.class);

	private UserService userService = new UserService();

	/**
	 * PUT method to update user information acquired from the client. Should only
	 * update the user password and/or email.
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		try {

			// Get the object mapper
			ObjectMapper objectMapper = new ObjectMapper();

			// Get the update info
			String[] updateInfo = objectMapper.readValue(req.getInputStream(), String[].class);

			// Get the id of the current user
			int userId = Integer.parseInt(req.getHeader("UserId"));

			// Attempt to get a user by the id
			User user = userService.getById(userId);

			if (user == null) {
				LOG.warn("In UpdateProfileServlet.doPut():: UserService.getById() returned null");
				resp.setStatus(400);
				return;
			}

			// If the length is only 1, there is only one value to update
			if (updateInfo.length == 1) {

				/*
				 * Check the value in [0] if it contains an '@' symbol. If it does, it's an
				 * email update. If not, it's a password update.
				 */
				if (updateInfo[0].contains("@"))
					user.setEmail(updateInfo[0]);
				else
					user.setPassword(updateInfo[0]);
			} else {

				/*
				 * If the length is not 1, then we are updating the email and password in that
				 * order.
				 */
				user.setEmail(updateInfo[0]);
				user.setPassword(updateInfo[1]);
			}

			// Attempt to update the user
			User updatedUser = userService.update(user);

			if (updatedUser == null) {
				LOG.warn("In UpdateProfileServlet.doPut():: UserService.update() returned null");
				resp.setStatus(400);
				return;
			}

			// Update was successful, set status to 200
			resp.setStatus(200);

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