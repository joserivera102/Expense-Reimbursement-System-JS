package com.ers.util;

import org.apache.log4j.Logger;

/**
 * Helper class to help determine which view to switch to based on the GET
 * request from the view servlet.
 * 
 * @author Jose Rivera
 *
 */
public class RequestViewHelper {

	static final Logger LOG = Logger.getLogger(RequestViewHelper.class);

	/**
	 * Method that takes in the uri from the request and passes it through a switch
	 * statement to retrieve the correct view
	 * 
	 * @param uri: String for the view name
	 * @return The proper view or null if no view is found
	 */
	public static String process(String uri) {

		switch (uri) {

		case "login.view":
			return "partials/login.html";

		case "register.view":
			return "partials/register.html";

		case "dashboard.view":
			return "partials/dashboard.html";

		case "contact-us.view":
			return "partials/contact-us.html";

		default:
			LOG.error("In RequestViewHelper.process():: uri was " + uri);
			return null;
		}
	}
}
