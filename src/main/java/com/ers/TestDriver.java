package com.ers;

import java.util.List;

import com.ers.daos.ReimbursementDAO;
import com.ers.models.Reimbursement;

public class TestDriver {

	public static void main(String[] args) {

		ReimbursementDAO dao = new ReimbursementDAO();

		List<Reimbursement> testList = dao.getAll();
		
		System.out.println(testList);
	}

}
