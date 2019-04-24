package com.ers.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ers.models.User;
import com.ers.util.ConnectionFactory;

/**
 * User Data Access Object ( DAO ) Class. This class will establish a connection
 * to the database in order to persist a user.
 * 
 * @author Jose Rivera
 *
 */
public class UserDAO implements DAO<User> {

	private static final Logger LOG = Logger.getLogger(UserDAO.class);

	@Override
	public List getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User add(User user) {

		// Open a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Set auto commit to false
			connection.setAutoCommit(false);

			// Create our sequel statement
			String sql = "INSERT INTO ers_users VALUES (0, ?, ?, ?, ?, ?, ?";

			// Get primary key
			String[] keys = new String[1];
			keys[0] = "ERS_USERS_ID";

			// Create the prepared statement
			PreparedStatement statement = connection.prepareStatement(sql, keys);

			// Set statement variables
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, user.getEmail());
			statement.setInt(6, user.getRoleId());

			int rowsInserted = statement.executeUpdate();

			ResultSet resultSet = statement.getGeneratedKeys();

			// Set the user id by getting the generated keys from the table
			if (rowsInserted != 0) {
				while (resultSet.next())
					user.setId(resultSet.getInt(1));
			}

			// Commit our changes
			connection.commit();

		} catch (SQLIntegrityConstraintViolationException sicve) {
			LOG.error(sicve.getMessage());
		} catch (SQLException sqle) {
			LOG.error(sqle.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		// Return a valid user, or null if not valid
		return (user.getId() == 0) ? null : user;
	}

	@Override
	public User update(User updatedObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
}
