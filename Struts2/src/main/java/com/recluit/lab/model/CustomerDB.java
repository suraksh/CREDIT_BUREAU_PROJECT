package com.recluit.lab.model;

import java.sql.Date;

public class CustomerDB {
	
	private String rfc;
	
	private String fname;
	
	private String lname;
	
	private String qualification;
	
	private Date qualification_Date;

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Date getQualification_Date() {
		return qualification_Date;
	}

	public void setQualification_Date(Date qualification_Date) {
		this.qualification_Date = qualification_Date;
	}

	public CustomerDB(String rfc, String fname, String lname,
			String qualification, Date qualification_Date) {
		super();
		this.rfc = rfc;
		this.fname = fname;
		this.lname = lname;
		this.qualification = qualification;
		this.qualification_Date = qualification_Date;
	}

	public CustomerDB() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
