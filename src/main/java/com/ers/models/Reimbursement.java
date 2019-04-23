package com.ers.models;

import java.sql.Blob;
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
	private Blob receiptImg;
	private int authorId;
	private int resolverId;
	private int statusId;
	private int typeId;

	/**
	 * No Args Constructor.
	 */
	public Reimbursement() {
		super();
	}

	/**
	 * Constructor to create a Reimbursement.
	 * 
	 * @param id: A unique id for the Reimbursement.
	 * @param amount: The amount for the Reimbursement.
	 * @param dateSubmitted: The date that the Reimbursement was submitted.
	 * @param dateResolved: The date that the Reimbursement was resolved.
	 * @param description: A description of the Reimbursement.
	 * @param receiptImg: An image of the reimbursement receipt.
	 * @param authorId: The author id.
	 * @param resolverId: The resolver id.
	 * @param statusId: A status id.
	 * @param typeId: The type id.
	 */
	public Reimbursement(int id, double amount, Timestamp dateSubmitted, Timestamp dateResolved, String description,
			Blob receiptImg, int authorId, int resolverId, int statusId, int typeId) {
		super();
		this.id = id;
		this.amount = amount;
		this.dateSubmitted = dateSubmitted;
		this.dateResolved = dateResolved;
		this.description = description;
		this.receiptImg = receiptImg;
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.statusId = statusId;
		this.typeId = typeId;
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

	public Blob getReceiptImg() {
		return receiptImg;
	}

	public void setReceiptImg(Blob receiptImg) {
		this.receiptImg = receiptImg;
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

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
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
		result = prime * result + ((receiptImg == null) ? 0 : receiptImg.hashCode());
		result = prime * result + resolverId;
		result = prime * result + statusId;
		result = prime * result + typeId;
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
		if (receiptImg == null) {
			if (other.receiptImg != null)
				return false;
		} else if (!receiptImg.equals(other.receiptImg))
			return false;
		if (resolverId != other.resolverId)
			return false;
		if (statusId != other.statusId)
			return false;
		if (typeId != other.typeId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", dateSubmitted=" + dateSubmitted + ", dateResolved="
				+ dateResolved + ", description=" + description + ", receiptImg=" + receiptImg + ", authorId="
				+ authorId + ", resolverId=" + resolverId + ", statusId=" + statusId + ", typeId=" + typeId + "]";
	}
}
