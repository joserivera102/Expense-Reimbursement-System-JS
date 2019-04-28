package com.ers.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ers.models.Principal;

/**
 * Helper class to help determine which view to switch to based on the GET
 * request from the view servlet. Will check for authentication before returning
 * the view to display.
 * 
 * @author Jose Rivera
 *
 */
public class RequestViewHelper {

	private static final String FILEPATH = "/ExpenseReimbursementSystemJS/";

	static final Logger LOG = Logger.getLogger(RequestViewHelper.class);

	/**
	 * Method that takes in the uri from the request and passes it through a switch
	 * statement to retrieve the correct view
	 * 
	 * @param uri: String for the view name
	 * @return The proper view or null if no view is found
	 */
	public static String process(HttpServletRequest req) {

		switch (req.getRequestURI()) {

		case FILEPATH + "login.view":
			return "partials/login.html";

		case FILEPATH + "register.view":
			return "partials/register.html";

		case FILEPATH + "dashboard.view":

			// Check for authentication
			Principal principal = (Principal) req.getAttribute("principal");

			if (principal == null) {
				LOG.warn("In RequestViewHelper.process():: No principal attribute found on request object");
				return null;
			}

			return "partials/dashboard.html";

		case FILEPATH + "contact-us.view":
			return "partials/contact-us.html";

		default:
			LOG.warn("In RequestViewHelper.process():: uri was " + uri);
			return null;
		}
	}
}
