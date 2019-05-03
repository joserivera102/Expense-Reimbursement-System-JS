package com.ers.models;

import java.sql.Timestamp;

/**
 * Reimbursement class to detail all information of a Reimbursement.
 * 
 * @author Jose Rivera
 *
 */
public class Reimbursement {

	private int id;
	private double amount;
	private Timestamp dateSubmitted;
	private Timestamp dateResolved;
	private String description;
	private int authorId;
	private int resolverId;
	private ReimbursementStatus reimbursementStatus;
	private ReimbursementType reimbursementType;

	/**
	 * No Args constructor
	 */
	public Reimbursement() {
		super();
	}

	/**
	 * Constructor to create a new reimbursement.
	 * 
	 * @param id                  The id of the reimbursement.
	 * @param amount              The amount of the reimbursement.
	 * @param dateSubmitted       Timestamp for date submitted.
	 * @param dateResolved        Timestamp for date resolved.
	 * @param description         A description of the reimbursement.
	 * @param authorId            The id of the author ( User ).
	 * @param resolverId          The id of the resolver.
	 * @param reimbursementStatus The status of the reimbursement.
	 * @param reimbursementType   The type of reimbursement.
	 */
	public Reimbursement(int id, double amount, Timestamp dateSubmitted, Timestamp dateResolved, String description,
			int authorId, int resolverId, ReimbursementStatus reimbursementStatus,
			ReimbursementType reimbursementType) {
		super();
		this.id = id;
		this.amount = amount;
		this.dateSubmitted = dateSubmitted;
		this.dateResolved = dateResolved;
		this.description = description;
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.reimbursementStatus = reimbursementStatus;
		this.reimbursementType = reimbursementType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Timestamp dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public Timestamp getDateResolved() {
		return dateResolved;
	}

	public void setDateResolved(Timestamp dateResolved) {
		this.dateResolved = dateResolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getResolverId() {
		return resolverId;
	}

	public void setResolverId(int resolverId) {
		this.resolverId = resolverId;
	}

	public ReimbursementStatus getReimbursementStatus() {
		return reimbursementStatus;
	}

	public void setReimbursementStatus(ReimbursementStatus reimbursementStatus) {
		this.reimbursementStatus = reimbursementStatus;
	}

	public ReimbursementType getReimbursementType() {
		return reimbursementType;
	}

	public void setReimbursementType(ReimbursementType reimbursementType) {
		this.reimbursementType = reimbursementType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + authorId;
		result = prime * result + ((dateResolved == null) ? 0 : dateResolved.hashCode());
		result = prime * result + ((dateSubmitted == null) ? 0 : dateSubmitted.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((reimbursementStatus == null) ? 0 : reimbursementStatus.hashCode());
		result = prime * result + ((reimbursementType == null) ? 0 : reimbursementType.hashCode());
		result = prime * result + resolverId;
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
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (authorId != other.authorId)
			return false;
		if (dateResolved == null) {
			if (other.dateResolved != null)
				return false;
		} else if (!dateResolved.equals(other.dateResolved))
			return false;
		if (dateSubmitted == null) {
			if (other.dateSubmitted != null)
				return false;
		} else if (!dateSubmitted.equals(other.dateSubmitted))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (reimbursementStatus == null) {
			if (other.reimbursementStatus != null)
				return false;
		} else if (!reimbursementStatus.equals(other.reimbursementStatus))
			return false;
		if (reimbursementType == null) {
			if (other.reimbursementType != null)
				return false;
		} else if (!reimbursementType.equals(other.reimbursementType))
			return false;
		if (resolverId != other.resolverId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", dateSubmitted=" + dateSubmitted + ", dateResolved="
				+ dateResolved + ", description=" + description + ", authorId=" + authorId + ", resolverId="
				+ resolverId + ", reimbursementStatus=" + reimbursementStatus + ", reimbursementType="
				+ reimbursementType + "]";
	}
}
