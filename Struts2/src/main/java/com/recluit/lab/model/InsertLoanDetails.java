package com.recluit.lab.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertLoanDetails {
	
	private DBConnect connect  = null;
//	private 
	private static String customer_insert = "insert into Customer values(?, ?, ?, ?, ?)";
	private static String loan_insert = " insert into loans values(SEQUENCE_LOAN_ID.NEXTVAL, ? , ?, ?, ? , ? )";
	private static String QUALIFICATION = "NOT_AVAILABLE";
	private static String status = "Y";
	/*
	 * to be deleted
	 * FETCH_CUSTOMER_DETAILS
	 * 
	 */
	private static final String FETCH_CUSTOMER_DETAILS = " select RFC, FNAME, LNAME, QUALIFICATION, QUALIFICATION_DATE from system.customer where rfc = ? ";
	
	public InsertLoanDetails(){
		connect = new DBConnect();
	}
	
	public int insertCustomerRecord(String rfc, String fname, String lname, String qualification, 
			Date qualification_date){
		Connection conn = connect.connectToOracle();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PreparedStatement stmt = null;
		PreparedStatement pStmt = null;
		int ack = 0;
		try
		{
			pStmt = conn.prepareStatement(FETCH_CUSTOMER_DETAILS);//to be deleted
			pStmt.setString(1, rfc);//to be deleted
			ResultSet rs = pStmt.executeQuery();//to be deleted
			if( rs.next())
				return 0;
			stmt = conn.prepareStatement(customer_insert);
			stmt.setString(1, rfc);
			stmt.setString(2, fname);
			stmt.setString(3, lname);
			stmt.setString(4, qualification);
			stmt.setDate(5, qualification_date);
			
			ack = stmt.executeUpdate();
			System.out.println("Customer value inserted!!!");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		finally
		{
			try {
				conn.commit();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ack;
	}

	public int insertLoanRecord(String rfc, BigDecimal amount, 
			Date expiry_date)
	{
		Connection conn = connect.connectToOracle();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PreparedStatement stmt = null;
		int ack = 0;
		try
		{
			stmt = conn.prepareStatement(loan_insert);
			stmt.setString(1, rfc);
			stmt.setBigDecimal(2, amount);
			stmt.setString(3, QUALIFICATION);
			stmt.setDate(4, expiry_date);
			stmt.setString(5, status);
			
			ack = stmt.executeUpdate();
			System.out.println("Loans value inserted!!!");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		finally
		{
			try {
				conn.commit();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ack;
	}
}
