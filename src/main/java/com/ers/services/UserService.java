package com.ers.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.ers.daos.UserDAO;
import com.ers.models.User;

/**
 * User Service Class. Will handle accepting a request from the servlet to
 * facilitate user functions.
 * 
 * @author Jose Rivera
 *
 */
public class UserService {

	private static final Logger LOG = Logger.getLogger(UserService.class);
	private UserDAO userDao = new UserDAO();

	public List getAllUsers() {
		return null;
	}

	public User getByCredentials(String username, String password) {

		if (username == null || password == null) {
			LOG.warn("In UserService.getByCredentials()::User credentials invalid");
			return null;
		}

		return userDao.getByCredentials(username, password);
	}

	public User getById(int id) {
		return null;
	}

	public User addUser(User user) {

		if (user == null) {
			LOG.warn("In UserService.addUser()::User was " + user);
			return null;
		}

		return userDao.add(user);
	}

	public User update(User updatedUser) {
		return null;
	}

	public boolean delete(int id) {
		return false;
	}
}
