package com.ers.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ers.models.User;
import com.ers.models.UserRole;
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

		// Create an empty list
		List<User> users = new ArrayList<>();

		return null;
	}

	public User getByCredentials(String username, String password) {

		// Create a user
		User user = new User();

		// Open a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Create our sequel statement, using an inner join for the user role
			String sql = "SELECT * FROM ers_users" + " INNER JOIN ers_user_roles ON"
					+ " ers_users.user_role_id = ers_user_roles.ers_user_role_id"
					+ " WHERE ers_username = ? AND ers_password = ?";

			// Create the prepared statement
			PreparedStatement statement = connection.prepareStatement(sql);

			// Set statement variables
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet resultSet = statement.executeQuery();

			// Create a user based on results from sequel query
			while (resultSet.next()) {
				user.setId(resultSet.getInt("ers_users_id"));
				user.setUsername(resultSet.getString("ers_username"));
				user.setPassword(resultSet.getString("ers_password"));
				user.setFirstName(resultSet.getString("user_first_name"));
				user.setLastName(resultSet.getString("user_last_name"));
				user.setEmail(resultSet.getString("user_email"));
				user.setRole(new UserRole(resultSet.getInt("ers_user_role_id"), resultSet.getString("user_role")));
			}
		} catch (SQLException sqle) {
			LOG.error(sqle.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		// Return a valid user, or null if not valid
		return (user.getId() == 0) ? null : user;
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
			String sql = "INSERT INTO ers_users VALUES (0, ?, ?, ?, ?, ?, ?)";

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
			statement.setInt(6, user.getRole().getId());

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
