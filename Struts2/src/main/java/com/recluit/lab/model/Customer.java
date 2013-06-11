package com.recluit.lab.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.logging.Logger;

public class Customer {

	String rfc;
	Date date;
	String name;
	String address;
	BigDecimal loan_amount;
	String qualification;
	boolean loan_status;
	private Logger log = Logger.getLogger("Customer");
	
	public Customer() {
		super();
	}

	public Customer(String rfc, Date date, String name, String address,
			BigDecimal loan_amount, String qualification, boolean loan_status) {
		super();
		this.rfc = rfc;
		this.date = date;
		this.name = name;
		this.address = address;
		this.loan_amount = loan_amount;
		this.qualification = qualification;
		this.loan_status = loan_status;
		
		log.info("Customer constructor : " + rfc + loan_status + qualification);
	}
	
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public boolean isLoan_status() {
		return loan_status;
	}
	public void setLoan_status(boolean loan_status) {
		this.loan_status = loan_status;
	}

}
