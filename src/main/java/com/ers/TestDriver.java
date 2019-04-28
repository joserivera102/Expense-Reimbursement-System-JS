package com.ers;

import java.util.List;

import com.ers.daos.ReimbursementDAO;
import com.ers.models.Reimbursement;

public class TestDriver {

	public static void main(String[] args) {

		ReimbursementDAO dao = new ReimbursementDAO();

		Reimbursement test = dao.getById(7);

		System.out.println(test);
	}

}
