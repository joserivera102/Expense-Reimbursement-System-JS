package com.ers.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleDriver;

/**
 * Connection Factory class responsible for creating a connection using JDBC.
 * This class will be an eagerly loaded singleton.
 * 
 * @author Jose Rivera
 *
 */
public class ConnectionFactory {

	private static final Logger LOG = Logger.getLogger(ConnectionFactory.class);

	private static ConnectionFactory connectionFactory = new ConnectionFactory();

	/**
	 * Private constructor so class cannot be instantiated.
	 */
	private ConnectionFactory() {
		super();
	}

	/**
	 * Eager Singleton instance of this class.
	 * 
	 * @return An instance of this class
	 */
	public static ConnectionFactory getInstance() {
		return connectionFactory;
	}

	/**
	 * Method to get a connection to the database. Will read from
	 * application.properties to set up a connection to the database.
	 * 
	 * @return A valid connection to the database or null if no connection can be
	 *         established
	 */
	public Connection getConnection() {

		Connection conn = null;

		/*
		 * We use a .properties file so we do not hard-code our DB credentials into the
		 * source code
		 */
		Properties prop = new Properties();

		try {

			// Manually register the driver for Tomcat to recognize
			DriverManager.registerDriver(new OracleDriver());

			// Load the properties file ( keys and values) into the properties object
			// Requires full file path, needs to be looked into later...
			prop.load(new FileReader(
					"D:\\Repositories\\Expense-Reimbursement-System-JS\\src\\main\\resources\\application.properties"));

			// Get a connection from the DriverManager class
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("usr"),
					prop.getProperty("pw"));

		} catch (SQLException sqle) {
			LOG.error(sqle.getMessage());
		} catch (FileNotFoundException fnfe) {
			LOG.error(fnfe.getMessage());
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		// Check our connection
		if (conn == null)
			LOG.warn("In ConnectionFactory.getConnection():: Connection was " + conn);

		// Return the connection
		return conn;
	}
}
