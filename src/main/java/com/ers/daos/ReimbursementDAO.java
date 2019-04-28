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

import com.ers.models.Reimbursement;
import com.ers.models.ReimbursementStatus;
import com.ers.models.ReimbursementType;
import com.ers.models.User;
import com.ers.util.ConnectionFactory;

/**
 * Reimbursement Data Access Object ( DAO ) Class. This class will establish a
 * connection to the database in order to perform CRUD methods on
 * reimbursements.
 * 
 * @author Jose Rivera
 *
 */
public class ReimbursementDAO implements DAO<Reimbursement> {

	private static final Logger LOG = Logger.getLogger(ReimbursementDAO.class);

	@Override
	public List<Reimbursement> getAll() {

		// Create an empty list
		List<Reimbursement> reimbursements = new ArrayList<>();

		// Get a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Create our sequel statement
			String sql = "SELECT * FROM ers_reimbursement" + " INNER JOIN ers_reimbursement_status ON"
					+ " ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id"
					+ " INNER JOIN ers_reimbursement_type ON"
					+ " ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id";

			// Create a basic statement
			Statement statement = connection.createStatement();

			// Execute the query, obtain the result set
			ResultSet resultSet = statement.executeQuery(sql);

			// Create reimbursements based on results from sequel query
			while (resultSet.next()) {

				// Create a reimbursement
				Reimbursement reimbursement = new Reimbursement();

				reimbursement.setId(resultSet.getInt("reimb_id"));
				reimbursement.setAmount(resultSet.getDouble("reimb_amount"));
				reimbursement.setDateSubmitted(resultSet.getTimestamp("reimb_submitted"));
				reimbursement.setDateResolved(resultSet.getTimestamp("reimb_resolved"));
				reimbursement.setDescription(resultSet.getString("reimb_description"));
				reimbursement.setAuthorId(resultSet.getInt("reimb_author"));
				reimbursement.setResolverId(resultSet.getInt("reimb_resolver"));
				reimbursement.setReimbursementStatus(new ReimbursementStatus(resultSet.getInt("reimb_status_id"),
						resultSet.getString("reimb_status")));
				reimbursement.setReimbursementType(
						new ReimbursementType(resultSet.getInt("reimb_type_id"), resultSet.getString("reimb_type")));

				// Add to the list
				reimbursements.add(reimbursement);
			}

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		return reimbursements;
	}

	public List<Reimbursement> getByUser(User user) {

		// Create an empty list
		List<Reimbursement> reimbursements = new ArrayList<>();

		// Get a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Create our sequel statement
			String sql = "SELECT * FROM ers_reimbursement" + " INNER JOIN ers_reimbursement_status ON"
					+ " ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id"
					+ " INNER JOIN ers_reimbursement_type ON"
					+ " ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id"
					+ " WHERE reimb_author = ?";

			// Create our prepared statement
			PreparedStatement statement = connection.prepareStatement(sql);

			// Set statement variables
			statement.setInt(1, user.getId());

			// Execute the query, obtain the result set
			ResultSet resultSet = statement.executeQuery();

			// Create reimbursements based on results from sequel query
			while (resultSet.next()) {

				// Create a reimbursement
				Reimbursement reimbursement = new Reimbursement();

				reimbursement.setId(resultSet.getInt("reimb_id"));
				reimbursement.setAmount(resultSet.getDouble("reimb_amount"));
				reimbursement.setDateSubmitted(resultSet.getTimestamp("reimb_submitted"));
				reimbursement.setDateResolved(resultSet.getTimestamp("reimb_resolved"));
				reimbursement.setDescription(resultSet.getString("reimb_description"));
				reimbursement.setAuthorId(resultSet.getInt("reimb_author"));
				reimbursement.setResolverId(resultSet.getInt("reimb_resolver"));
				reimbursement.setReimbursementStatus(new ReimbursementStatus(resultSet.getInt("reimb_status_id"),
						resultSet.getString("reimb_status")));
				reimbursement.setReimbursementType(
						new ReimbursementType(resultSet.getInt("reimb_type_id"), resultSet.getString("reimb_type")));

				// Add to the list
				reimbursements.add(reimbursement);
			}

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		return reimbursements;
	}

	public List<Reimbursement> getByStatus(int statusId) {

		// Create an empty list
		List<Reimbursement> reimbursements = new ArrayList<>();

		// Get a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Create our sequel statement
			String sql = "SELECT * FROM ers_reimbursement" + " INNER JOIN ers_reimbursement_status ON"
					+ " ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id"
					+ " INNER JOIN ers_reimbursement_type ON"
					+ " ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id"
					+ " WHERE ers_reimbursement.reimb_status_id = ?";

			// Create our prepared statement
			PreparedStatement statement = connection.prepareStatement(sql);

			// Set statement variables
			statement.setInt(1, statusId);

			// Execute the query, obtain the result set
			ResultSet resultSet = statement.executeQuery();

			// Create reimbursements based on results from sequel query
			while (resultSet.next()) {

				// Create a reimbursement
				Reimbursement reimbursement = new Reimbursement();

				reimbursement.setId(resultSet.getInt("reimb_id"));
				reimbursement.setAmount(resultSet.getDouble("reimb_amount"));
				reimbursement.setDateSubmitted(resultSet.getTimestamp("reimb_submitted"));
				reimbursement.setDateResolved(resultSet.getTimestamp("reimb_resolved"));
				reimbursement.setDescription(resultSet.getString("reimb_description"));
				reimbursement.setAuthorId(resultSet.getInt("reimb_author"));
				reimbursement.setResolverId(resultSet.getInt("reimb_resolver"));
				reimbursement.setReimbursementStatus(new ReimbursementStatus(resultSet.getInt("reimb_status_id"),
						resultSet.getString("reimb_status")));
				reimbursement.setReimbursementType(
						new ReimbursementType(resultSet.getInt("reimb_type_id"), resultSet.getString("reimb_type")));

				// Add to the list
				reimbursements.add(reimbursement);
			}

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		return reimbursements;
	}

	@Override
	public Reimbursement getById(int id) {
		// Create a reimbursement
		Reimbursement reimbursement = new Reimbursement();

		// Get a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Create our sequel statement
			String sql = "SELECT * FROM ers_reimbursement" + " INNER JOIN ers_reimbursement_status ON"
					+ " ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id"
					+ " INNER JOIN ers_reimbursement_type ON"
					+ " ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id" + " WHERE reimb_id = ?";

			// Create our prepared statement
			PreparedStatement statement = connection.prepareStatement(sql);

			// Set statement variables
			statement.setInt(1, id);

			// Execute the query, obtain the result set
			ResultSet resultSet = statement.executeQuery();

			// Create reimbursements based on results from sequel query
			while (resultSet.next()) {
				reimbursement.setId(resultSet.getInt("reimb_id"));
				reimbursement.setAmount(resultSet.getDouble("reimb_amount"));
				reimbursement.setDateSubmitted(resultSet.getTimestamp("reimb_submitted"));
				reimbursement.setDateResolved(resultSet.getTimestamp("reimb_resolved"));
				reimbursement.setDescription(resultSet.getString("reimb_description"));
				reimbursement.setAuthorId(resultSet.getInt("reimb_author"));
				reimbursement.setResolverId(resultSet.getInt("reimb_resolver"));
				reimbursement.setReimbursementStatus(new ReimbursementStatus(resultSet.getInt("reimb_status_id"),
						resultSet.getString("reimb_status")));
				reimbursement.setReimbursementType(
						new ReimbursementType(resultSet.getInt("reimb_type_id"), resultSet.getString("reimb_type")));
			}

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		// Return a valid reimbursement or null if not valid
		return (reimbursement.getId() == 0) ? null : reimbursement;
	}

	@Override
	public Reimbursement add(Reimbursement reimbursement) {

		// Open a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Set auto commit to false
			connection.setAutoCommit(false);

			// Create our sequel statement
			String sql = "INSERT INTO ers_reimbursement VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?)";

			// Get primary key
			String[] keys = new String[1];
			keys[0] = "REIMB_ID";

			// Create the prepared statement
			PreparedStatement statement = connection.prepareStatement(sql, keys);

			// Set statement variables
			statement.setDouble(1, reimbursement.getAmount());
			statement.setTimestamp(2, reimbursement.getDateSubmitted());
			statement.setTimestamp(3, reimbursement.getDateResolved());
			statement.setString(4, reimbursement.getDescription());
			statement.setInt(5, reimbursement.getAuthorId());
			statement.setInt(6, reimbursement.getResolverId());
			statement.setInt(7, reimbursement.getReimbursementStatus().getId());
			statement.setInt(8, reimbursement.getReimbursementType().getId());

			int rowsInserted = statement.executeUpdate();

			ResultSet resultSet = statement.getGeneratedKeys();

			// Set the reimbursement id by getting the generated keys from the table
			if (rowsInserted != 0) {
				while (resultSet.next())
					reimbursement.setId(resultSet.getInt(1));
			} else
				throw new Exception("In ReimbursementDAO.add():: Reimbursement was not added successfully.");

			// Commit our changes
			connection.commit();

		} catch (SQLIntegrityConstraintViolationException sicve) {
			LOG.error(sicve.getMessage());
		} catch (SQLException sqle) {
			LOG.error(sqle.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		// Return a valid reimbursement, or null if not valid
		return (reimbursement.getId() == 0) ? null : reimbursement;
	}

	@Override
	public Reimbursement update(Reimbursement updatedReimbursement) {

		// Open a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Set auto commit to false
			connection.setAutoCommit(false);

			// Create our sequel statement
			String sql = "UPDATE ers_reimbursement SET " + "reimb_amount = ?, " + "reimb_submitted = ?, "
					+ "reimb_resolved = ?, " + "reimb_description = ?, " + "reimb_author = ?, " + "reimb_resolver = ?, "
					+ "reimb_status_id = ?, " + "reimb_type_id = ? " + "WHERE reimb_id = ?";

			// Create the prepared statement
			PreparedStatement statement = connection.prepareStatement(sql);

			// Set statement variables
			statement.setDouble(1, updatedReimbursement.getAmount());
			statement.setTimestamp(2, updatedReimbursement.getDateSubmitted());
			statement.setTimestamp(3, updatedReimbursement.getDateResolved());
			statement.setString(4, updatedReimbursement.getDescription());
			statement.setInt(5, updatedReimbursement.getAuthorId());
			statement.setInt(6, updatedReimbursement.getResolverId());
			statement.setInt(7, updatedReimbursement.getReimbursementStatus().getId());
			statement.setInt(8, updatedReimbursement.getReimbursementType().getId());
			statement.setInt(9, updatedReimbursement.getId());

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted == 0)
				throw new Exception("In ReimbursementDAO.update():: Update was not executed properly.");

			// Commit our changes
			connection.commit();

		} catch (SQLIntegrityConstraintViolationException sicve) {
			LOG.error(sicve.getMessage());
		} catch (SQLException sqle) {
			LOG.error(sqle.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		// Return a valid reimbursement, or null if not valid
		return (updatedReimbursement.getId() == 0) ? null : updatedReimbursement;
	}

	@Override
	public boolean delete(int id) {

		// Open a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Set auto commit to false
			connection.setAutoCommit(false);

			// Create our sequel statement
			String sql = "DELETE FROM ers_reimbursement WHERE reimb_id = ?";

			// Create the prepared statement
			PreparedStatement statement = connection.prepareStatement(sql);

			// Set statement variables
			statement.setInt(1, id);

			int result = statement.executeUpdate();

			if (result == 0)
				throw new Exception("In ReimbursementDAO.delete():: Delete was not executed properly.");

			// Commit our changes
			connection.commit();

			// Return true if the reimbursement was deleted properly
			return true;

		} catch (SQLIntegrityConstraintViolationException sicve) {
			LOG.error(sicve.getMessage());
		} catch (SQLException sqle) {
			LOG.error(sqle.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		// Return false if the reimbursement was not deleted properly
		return false;
	}

}