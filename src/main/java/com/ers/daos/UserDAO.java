package com.ers.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.log4j.Logger;

import com.ers.util.ConnectionFactory;

/**
 * User Data Access Object ( DAO ) Class. This class will establish a connection
 * to the database in order to persist a user.
 * 
 * @author Jose Rivera
 *
 */
public class UserDAO implements DAO {

	private static Logger LOG = Logger.getLogger(UserDAO.class);

	@Override
	public List getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object add(Object obj) {

		// Open a connection to the DB
		try (Connection connection = ConnectionFactory.getInstance().getConnection()) {

			// Set auto commit to false
			connection.setAutoCommit(false);

			// Create our sequel statement
			String sql = "INSERT INTO ers_user VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			// Get primary key
			String[] keys = new String[1];
			keys[0] = "ERS_USERS_ID";

			PreparedStatement statement = connection.prepareStatement(sql);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		return null;
		/*
		 * 
		 * 
		 * conn.setAutoCommit(false); PreparedStatement pstmt =
		 * conn.prepareStatement("SELECT MAX(ers_users_id) \"Maximum\" From ers_users");
		 * ResultSet rs = pstmt.executeQuery(); rs.next(); int i = rs.getInt("Maximum");
		 * i++;
		 * 
		 * String sql = "INSERT INTO ers_users VALUES (?, ?, ?, ?, ?, ?, ?)";
		 * 
		 * pstmt = conn.prepareStatement(sql); pstmt.setInt(1, i); pstmt.setString(2,
		 * obj.getUsername()); pstmt.setString(3, obj.getPassword()); pstmt.setString(4,
		 * obj.getFirstName()); pstmt.setString(5, obj.getLastName());
		 * pstmt.setString(6, obj.getEmail()); pstmt.setInt(7, obj.getRoleId());
		 * 
		 * int rowsInserted = pstmt.executeUpdate();
		 * 
		 * if (rowsInserted == 0) { log.error("Rows Inserted was 0"); } obj.setId(i);
		 * 
		 * conn.commit(); } catch (SQLIntegrityConstraintViolationException sicve) {
		 * System.out.println(sicve); log.warn("Username already exists."); } catch
		 * (SQLException e) { System.out.println(e.getMessage());
		 * log.error(e.getMessage()); }
		 * 
		 * if (obj.getId() == 0) return null;
		 * 
		 * return obj;
		 */
	}

	@Override
	public Object update(Object updatedObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
}
