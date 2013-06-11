package com.recluit.lab.action;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.recluit.lab.model.InsertLoanDetails;
import com.recluit.lab.restclient.HelloClient;

public class LoanEstablishmentAction extends ActionSupport{
	
	private String credit_amount;
	private String customer_name;
	private String customer_address;
	private String loan_due_date;
	private Date loan_date;
	private String loan_amt_string;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private String rfc;
	private static String COLUMN_SEPARATOR = "$";
	private static String QUALIFICATION = "NOT_AVAILABLE";
	private static String STATUS = "Y";
	private Logger log = Logger.getLogger("LoanEstablishmentAction");
	private String cust_exists_in_our_bank ;
	private Calendar cal = Calendar.getInstance();
	
	public String getCust_exists_in_our_bank() {
		return cust_exists_in_our_bank;
	}

	public void setCust_exists_in_our_bank(String cust_exists_in_our_bank) {
		this.cust_exists_in_our_bank = cust_exists_in_our_bank;
	}
	
	public Date getLoan_date() {
		return loan_date;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getCredit_amount() {
		return credit_amount;
	}

	public void setCredit_amount(String credit_amount) {
		this.credit_amount = credit_amount;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public String getLoan_due_date() {
		return loan_due_date;
	}

	public void setLoan_due_date(String loan_due_date) {
		
		try {
			this.loan_due_date = loan_due_date;
			this.loan_date = new Date(df.parse(loan_due_date).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLoan_amt_string() {
		return loan_amt_string;
	}

	public void setLoan_amt_string(String loan_amt_string) {
		this.loan_amt_string = loan_amt_string;
	}
	
	public String execute() throws Exception{
		
		InsertLoanDetails loan = new InsertLoanDetails();
		HelloClient client = new HelloClient();
		StringBuffer record = new StringBuffer();
		record = record.append("INSERT:").append("345").append(COLUMN_SEPARATOR).
				append(rfc).append(COLUMN_SEPARATOR).
				append(loan_due_date).append(COLUMN_SEPARATOR).
				append(customer_name).append(COLUMN_SEPARATOR).
				append(customer_address).append(COLUMN_SEPARATOR).
				append(credit_amount).append(COLUMN_SEPARATOR).
				append(QUALIFICATION).append(COLUMN_SEPARATOR).
				append(STATUS);
		
		
		log.info(record.toString());
		
		String ack = client.getMessage(record.toString());
		
		log.info("customer exists in out bank :" + cust_exists_in_our_bank);
		
		
			String name[] = customer_name.split(" ");
			String fname = "", lname = "";
			if(name.length >= 2){
				fname = name[0];
				lname = name[1];
			}
		loan.insertCustomerRecord(rfc, fname, lname, "NORMAL", new Date(cal.getTimeInMillis()));
		
		
		loan.insertLoanRecord(rfc, new BigDecimal(credit_amount), loan_date);
		
		if(ack.equalsIgnoreCase("SUCCESS"))
			return SUCCESS;
		else
			return ERROR;
	}
	
	
}
