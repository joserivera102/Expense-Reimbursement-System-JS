package com.ers.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ers.daos.ReimbursementDAO;
import com.ers.models.Reimbursement;
import com.ers.models.User;

/**
 * Reimbursement Service Class. Will handle accepting a request from the servlet
 * to facilitate reimbursement functions and validation.
 * 
 * @author Jose Rivera
 *
 */
public class ReimbursementService {

	private static final Logger LOG = Logger.getLogger(UserService.class);

	private ReimbursementDAO reimbursementDao = new ReimbursementDAO();

	/**
	 * Method to get all reimbursements from the Reimbursement DAO.
	 * 
	 * @return A list of reimbursements.
	 */
	public List<Reimbursement> getAllReimbursements() {
		return reimbursementDao.getAll();
	}

	/**
	 * Method to get all reimbursements from the Reimbursement DAO for a specific
	 * user.
	 * 
	 * @return A list of reimbursements.
	 */
	public List<Reimbursement> getByUser(User user) {

		if (user == null) {
			LOG.warn("In ReimbursementService.getByUser():: User was " + user);
			return new ArrayList<>();
		}

		return reimbursementDao.getByUser(user);
	}

	/**
	 * Method to get all reimbursements from the Reimbursement DAO by a status id.
	 * Id cannot be less than or equal to zero, or greater than 3.
	 * 
	 * @return A list of reimbursements.
	 */
	public List<Reimbursement> getByStatus(int statusId) {

		if (statusId <= 0 || statusId > 3) {
			LOG.warn("In ReimbursementService.getByStatus():: Status id was invalid");
			return new ArrayList<>();
		}

		return reimbursementDao.getByStatus(statusId);
	}

	/**
	 * Method to get a single reimbursement from the Reimbursement DAO by an id. Id
	 * cannot be less than or equal to zero.
	 * 
	 * @return A single reimbursement matching the given id.
	 */
	public Reimbursement getById(int id) {

		if (id <= 0) {
			LOG.warn("In ReimbursementService.getById():: Id was invalid");
			return null;
		}

		return reimbursementDao.getById(id);
	}

	/**
	 * Method to add a reimbursement to the Database. If the reimbursement was null,
	 * will log a warning and return null.
	 * 
	 * @return The newly added reimbursement.
	 */
	public Reimbursement add(Reimbursement reimbursement) {

		if (reimbursement == null) {
			LOG.warn("In ReimbursementService.add():: Reimbursement was " + reimbursement);
			return null;
		}

		return reimbursementDao.add(reimbursement);
	}

	/**
	 * Method to update a reimbursement to the Database. If the reimbursement was
	 * null, will log a warning and return null.
	 * 
	 * @return The newly updated reimbursement.
	 */
	public Reimbursement update(Reimbursement updatedReimbursement) {

		if (updatedReimbursement == null) {
			LOG.warn("In ReimbursementService.update():: Updated reimbursement was " + updatedReimbursement);
			return null;
		}

		return reimbursementDao.update(updatedReimbursement);
	}

	/**
	 * Method to delete a reimbursement from the database. If the id is less than or
	 * equal to zero, will log a warning and return false.
	 * 
	 * @param id The id of the reimbursement to delete.
	 * @return True if the reimbursement was deleted, false if not.
	 */
	public boolean delete(int id) {

		if (id <= 0) {
			LOG.warn("In ReimbursementService.delete():: Id was invalid");
			return false;
		}

		return reimbursementDao.delete(id);
	}
}
