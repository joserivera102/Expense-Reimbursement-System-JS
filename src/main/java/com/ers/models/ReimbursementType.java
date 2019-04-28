package com.ers.models;

/**
 * Class used to detail the reimbursement type.
 * 
 * @author Jose Rivera
 *
 */
public class ReimbursementType {

	private int id;
	private String type;

	/**
	 * No Args Constructor
	 */
	public ReimbursementType() {
		super();
	}

	/**
	 * Constructor to create a reimbursement type.
	 * 
	 * @param id   The id for the lookup table in the database.
	 * @param type String type of reimbursement.
	 */
	public ReimbursementType(int id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ReimbursementType other = (ReimbursementType) obj;
		if (id != other.id)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReimbursementType [id=" + id + ", type=" + type + "]";
	}
}
