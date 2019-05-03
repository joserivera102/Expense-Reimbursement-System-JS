package com.ers.models;

/**
 * Class used to detail the status of a reimbursement.
 * 
 * @author Jose Rivera
 *
 */
public class ReimbursementStatus {

	private int id;
	private String status;

	/**
	 * No Args Constructor
	 */
	public ReimbursementStatus() {
		super();
	}

	/**
	 * Constructor used to create a reimbursement status.
	 * 
	 * @param id     The id for the lookup table in the database.
	 * @param status String status name.
	 */
	public ReimbursementStatus(int id, String status) {
		super();
		this.id = id;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementStatus other = (ReimbursementStatus) obj;
		if (id != other.id)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReimbursementStatus [id=" + id + ", status=" + status + "]";
	}
}
