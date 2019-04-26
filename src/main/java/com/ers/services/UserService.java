package com.ers.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.ers.daos.UserDAO;
import com.ers.models.User;

/**
 * User Service Class. Will handle accepting a request from the servlet to
 * facilitate user functions and validation.
 * 
 * @author Jose Rivera
 *
 */
public class UserService {

	private static final Logger LOG = Logger.getLogger(UserService.class);

	private UserDAO userDao = new UserDAO();

	/**
	 * Method to return all users from the User DAO.
	 * 
	 * @return A list of users.
	 */
	public List<User> getAllUsers() {
		return userDao.getAll();
	}

	/**
	 * Method to return a user by given credentials. If credentials are invalid,
	 * will log a warning and return null.
	 * 
	 * @param username The username passed in.
	 * @param password The password passed in.
	 * @return A valid user by the passed in credentials, or null if none found.
	 */
	public User getByCredentials(String username, String password) {

		if (username == null || password == null) {
			LOG.warn("In UserService.getByCredentials():: User credentials invalid");
			return null;
		}

		return userDao.getByCredentials(username, password);
	}

	/**
	 * Method to return a user by given Id. If the Id is less than or equal to zero,
	 * will log a warning and return null.
	 * 
	 * @param id The id of the user to find.
	 * @return A valid user or null if none found.
	 */
	public User getById(int id) {

		if (id <= 0) {
			LOG.warn("In UserService.getById():: Id was invalid");
			return null;
		}

		return userDao.getById(id);
	}

	/**
	 * Method to add a user to the database. If the user was null, will log a
	 * warning and return null.
	 * 
	 * @param user A user to add to the database.
	 * @return The newly added user.
	 */
	public User addUser(User user) {

		if (user == null) {
			LOG.warn("In UserService.addUser():: User was " + user);
			return null;
		}

		return userDao.add(user);
	}

	/**
	 * Method to update a user in the database. If the user was null, will log a
	 * warning and return null.
	 * 
	 * @param updatedUser The updated user to update.
	 * @return The newly updated user.
	 */
	public User update(User updatedUser) {

		if (updatedUser == null) {
			LOG.warn("In UserService.update():: Updated user was " + updatedUser);
			return null;
		}

		return userDao.update(updatedUser);
	}

	/**
	 * Method to delete a user from the database. If the id is less than or equal to
	 * zero, will log a warning and return false.
	 * 
	 * @param id The id of the user to delete.
	 * @return True if the user was deleted, false if not.
	 */
	public boolean delete(int id) {

		if (id <= 0) {
			LOG.warn("In UserService.delete():: Id was invalid");
			return false;
		}

		return userDao.delete(id);
	}
}
