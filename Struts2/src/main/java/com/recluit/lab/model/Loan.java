package com.recluit.lab.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Loan {
	
	private String rfc;
	
	private BigDecimal loan_amount;
	
	private String qualification;
	
	private Date expiry_date;
	
	private boolean status;
	
	private String ID;
	
	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public Loan(){
		
	}
	
	public Loan(String rfc, BigDecimal loan_amount, String qualification,
			Date expiry_date, boolean status, String iD) {
		super();
		this.rfc = rfc;
		this.loan_amount = loan_amount;
		this.qualification = qualification;
		this.expiry_date = expiry_date;
		this.status = status;
		ID = iD;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public BigDecimal getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(BigDecimal loan_amount) {
		this.loan_amount = loan_amount;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Date getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
