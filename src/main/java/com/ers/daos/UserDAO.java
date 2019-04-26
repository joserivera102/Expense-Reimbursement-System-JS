package com.ers.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ers.models.User;
import com.ers.models.UserRole;
import com.ers.util.ConnectionFactory;

/**
 * User Data Access Object ( DAO ) Class. This class will establish a connection
 * to the database in order to perform CRUD methods on users.
 * 
 * @author Jose Rivera
 *
 */
public class UserDAO implements DAO<User> {

	private static final Logger LOG = Logger.getLogger(UserDAO.class);

	@Override
	public List<User> getAll() {

		// Create an empty list
		List<User> users = new ArrayList<>();

		// Open a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Create our sequel statement, using an inner join for the user role
			String sql = "SELECT * FROM ers_users" + " INNER JOIN ers_user_roles ON"
					+ " ers_users.user_role_id = ers_user_roles.ers_user_role_id";

			// Create a basic statement
			Statement statement = connection.createStatement();

			// Get the results set
			ResultSet resultSet = statement.executeQuery(sql);

			// Create a user based on results from sequel query
			while (resultSet.next()) {

				// Create a new user for each user in the result set
				User user = new User();

				user.setId(resultSet.getInt("ers_users_id"));
				user.setUsername(resultSet.getString("ers_username"));
				user.setPassword(resultSet.getString("ers_password"));
				user.setFirstName(resultSet.getString("user_first_name"));
				user.setLastName(resultSet.getString("user_last_name"));
				user.setEmail(resultSet.getString("user_email"));
				user.setRole(new UserRole(resultSet.getInt("ers_user_role_id"), resultSet.getString("user_role")));

				// Add to the list
				users.add(user);
			}

		} catch (SQLException sqle) {
			LOG.error(sqle.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		return users;
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

		// Create a new user
		User user = new User();

		// Open a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Create our sequel statement, using an inner join for the user role
			String sql = "SELECT * FROM ers_users" + " INNER JOIN ers_user_roles ON"
					+ " ers_users.user_role_id = ers_user_roles.ers_user_role_id" + " WHERE ers_users_id = ?";

			// Create the prepared statement
			PreparedStatement statement = connection.prepareStatement(sql);

			// Set statement variables
			statement.setInt(1, id);

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
			} else
				throw new Exception("In UserDAO.add():: User was not added successfully.");

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
	public User update(User updatedUser) {

		// Open a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Set auto commit to false
			connection.setAutoCommit(false);

			// Create our sequel statement
			String sql = "UPDATE ers_users SET user_first_name = ?, user_last_name = ?, user_password = ? WHERE ers_users_id = ?";

			// Create the prepared statement
			PreparedStatement statement = connection.prepareStatement(sql);

			// Set statement variables
			statement.setString(1, updatedUser.getFirstName());
			statement.setString(2, updatedUser.getLastName());
			statement.setString(3, updatedUser.getPassword());
			statement.setInt(4, updatedUser.getId());

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted == 0)
				throw new Exception("In UserDAO.update():: Update was not executed properly.");

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
		return (updatedUser.getId() == 0) ? null : updatedUser;
	}

	@Override
	public boolean delete(int id) {

		// Open a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Set auto commit to false
			connection.setAutoCommit(false);

			// Create our sequel statement
			String sql = "DELETE FROM ers_users WHERE ers_users_id = ?";

			// Create the prepared statement
			PreparedStatement statement = connection.prepareStatement(sql);

			// Set statement variables
			statement.setInt(1, id);

			int result = statement.executeUpdate();

			if (result == 0)
				throw new Exception("In UserDAO.delete():: Delete was not executed properly.");

			// Commit our changes
			connection.commit();

			// Return true if user was deleted properly
			return true;

		} catch (SQLIntegrityConstraintViolationException sicve) {
			LOG.error(sicve.getMessage());
		} catch (SQLException sqle) {
			LOG.error(sqle.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		// Return false if user was not deleted properly
		return false;
	}
}
