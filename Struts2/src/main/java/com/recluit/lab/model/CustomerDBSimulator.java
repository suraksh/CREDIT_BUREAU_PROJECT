package com.recluit.lab.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import freemarker.log.Logger;

public class CustomerDBSimulator {
	
	private CustomerDB customerDBList;
	private List<Loan> loanList;
	private Calendar cal = Calendar.getInstance();
	private DBConnect connect = new DBConnect();
	private Connection conn = connect.connectToOracle();
	private ResultSet rs = null;
	Logger log = Logger.getLogger("CustomerDBSimulator");
	private String cust_exists_in_our_bank = "N";
	
	public String getCust_exists_in_our_bank() {
		return cust_exists_in_our_bank;
	}

	public void setCust_exists_in_our_bank(String cust_exists_in_our_bank) {
		this.cust_exists_in_our_bank = cust_exists_in_our_bank;
	}
	
	private static final String FETCH_CUSTOMER_DETAILS = " select RFC, FNAME, LNAME, QUALIFICATION, QUALIFICATION_DATE from system.customer where rfc = ? ";
	
	private static final String FETCH_LOAN_DETAILS = " select ID, RFC, LOAN_AMOUNT, QUALIFICATION, EXPIRATION_DATE, STATUS from system.LOANS loan " +
					" where loan.rfc = ?";
	
	public CustomerDBSimulator(){
//		customerDBList = new CustomerDB();
		loanList = new ArrayList<Loan>();
	}
	
	public CustomerDB getCustomerDBList() {
		return customerDBList;
	}

	public void setCustomerDBList(CustomerDB customerDBList) {
		this.customerDBList = customerDBList;
	}

	public List<Loan> getLoanListFromDB(String input) {

		
		Statement stmt = null;
		PreparedStatement pStmt = null;
		try
		{
			pStmt = conn.prepareStatement(FETCH_CUSTOMER_DETAILS);
			pStmt.setString(1, input);
			rs = pStmt.executeQuery();
			if(rs.next())
			{
				customerDBList = new CustomerDB(rs.getString(1),
						rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getDate(5));
				log.info("Customer record from db: " + rs.getString(1) +
						rs.getString(2) + rs.getString(3) + rs.getString(4) +
						rs.getDate(5));
				cust_exists_in_our_bank = "Y";
				pStmt = null;
				pStmt = conn.prepareStatement(FETCH_LOAN_DETAILS);
				pStmt.setString(1, input);
				rs = pStmt.executeQuery();
				convertResultToList();

			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				pStmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return loanList;
		
	}
	
	private void convertResultToList(){
		
		try {
			log.info(" Result set from the oracle DB :");
			while(rs.next())
			{
				Loan loan = new Loan(rs.getString(2), rs.getBigDecimal(3), rs.getString(4), rs.getDate(5), rs.getString(6).equalsIgnoreCase("Y") ? true : false, rs.getString(1));
				loanList.add(loan);
				log.info(rs.getString(1) + " " + rs.getString(2) + " " + rs.getBigDecimal(3) + " " + rs.getString(4) + " " + rs.getDate(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info(" Loan list total : " + loanList.size());
		
	}

//	customerDBList.add(new Customer("12345ZXCVB", new Date(cal.getTimeInMillis()), "PATSY", "MEXICO", new BigDecimal(5000), "GOOD", true));
//	customerDBList.add(new Customer("23456ZXCVB", new Date(cal.getTimeInMillis()), "SURAKSH", "MEXICO", new BigDecimal(15000), "GOOD", true));
//	customerDBList.add(new Customer("34567ZXCVB", new Date(cal.getTimeInMillis()), "SUPREETH", "MEXICO", new BigDecimal(35000), "GOOD", true));
//	customerDBList.add(new Customer("45678ZXCVB", new Date(cal.getTimeInMillis()), "ARUN", "MEXICO", new BigDecimal(50000), "GOOD", true));
//	customerDBList.add(new Customer("56789ZXCVB", new Date(cal.getTimeInMillis()), "NANCY", "MEXICO", new BigDecimal(500078), "GOOD", true));
//	customerDBList.add(new Customer("12345QWERT", new Date(cal.getTimeInMillis()), "OMAR", "MEXICO", new BigDecimal(500067), "GOOD", true));
//	customerDBList.add(new Customer("12345WERTY", new Date(cal.getTimeInMillis()), "JAVIER", "MEXICO", new BigDecimal(5000566), "GOOD", true));
//	customerDBList.add(new Customer("12345ERTYU", new Date(cal.getTimeInMillis()), "MARIANA", "MEXICO", new BigDecimal(500023), "GOOD", true));
//	customerDBList.add(new Customer("12345RTYUI", new Date(cal.getTimeInMillis()), "SURAKSH", "MEXICO", new BigDecimal(125000), "GOOD", true));
//	customerDBList.add(new Customer("12345TYUIO", new Date(cal.getTimeInMillis()), "SURAKSH", "MEXICO", new BigDecimal(235000), "GOOD", true));
//	customerDBList.add(new Customer("12345ASDFG", new Date(cal.getTimeInMillis()), "SAULO", "MEXICO", new BigDecimal(345000), "GOOD", true));
//	customerDBList.add(new Customer("12345SDFGH", new Date(cal.getTimeInMillis()), "PAULO", "MEXICO", new BigDecimal(455000), "GOOD", true));
//	customerDBList.add(new Customer("12345DFGHJ", new Date(cal.getTimeInMillis()), "ALEX", "MEXICO", new BigDecimal(565000), "GOOD", true));
//	customerDBList.add(new Customer("12345FGHJK", new Date(cal.getTimeInMillis()), "EDUARDO", "MEXICO", new BigDecimal(675000), "GOOD", true));
//	customerDBList.add(new Customer("12345GHJKL", new Date(cal.getTimeInMillis()), "OSCAR", "MEXICO", new BigDecimal(785000), "GOOD", true));
//	customerDBList.add(new Customer("12345ZXCVB", new Date(cal.getTimeInMillis()), "ALBERTO", "MEXICO", new BigDecimal(895000), "GOOD", true));
//	return customerDBList;
}
