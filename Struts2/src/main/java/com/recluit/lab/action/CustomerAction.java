package com.recluit.lab.action;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.recluit.lab.model.Customer;
import com.recluit.lab.model.CustomerDB;
import com.recluit.lab.model.CustomerDBSimulator;
import com.recluit.lab.model.DBConnect;
import com.recluit.lab.model.Loan;
import com.recluit.lab.restclient.HelloClient;

public class CustomerAction extends ActionSupport{
	
	private CustomerDBSimulator custDBList;
	private String rfc;
	private String name;
	private String qualification;
	private boolean status;
	private BigDecimal loan_amount;
	private List<Customer> listFromFile;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	int bad_qualification_count = 0;
	int number_of_times_loan_taken = 0;
	int loan_active_counter = 0;
	Logger log = Logger.getLogger("CustomerAction");
	private String customerQualification;
	private String cust_exists_in_our_bank = "N";
	
	public String getCust_exists_in_our_bank() {
		return cust_exists_in_our_bank;
	}

	public void setCust_exists_in_our_bank(String cust_exists_in_our_bank) {
		this.cust_exists_in_our_bank = cust_exists_in_our_bank;
	}

	public String getCustomerQualification() {
		return customerQualification;
	}

	public void setCustomerQualification(String customerQualification) {
		this.customerQualification = customerQualification;
	}

	public int getBad_qualification_count() {
		return bad_qualification_count;
	}

	public void setBad_qualification_count(int bad_qualification_count) {
		this.bad_qualification_count = bad_qualification_count;
	}

	public int getNumber_of_times_loan_taken() {
		return number_of_times_loan_taken;
	}

	public void setNumber_of_times_loan_taken(int number_of_times_loan_taken) {
		this.number_of_times_loan_taken = number_of_times_loan_taken;
	}

	public int getLoan_active_counter() {
		return loan_active_counter;
	}

	public void setLoan_active_counter(int loan_active_counter) {
		this.loan_active_counter = loan_active_counter;
	}
	
	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public BigDecimal getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(BigDecimal loan_amount) {
		this.loan_amount = loan_amount;
	}

	public String execute() throws Exception
	{
		
		custDBList = new CustomerDBSimulator();
		// fetches the customer from the oracle db
		List<Loan> loanList = custDBList.getLoanListFromDB(rfc);
		cust_exists_in_our_bank = custDBList.getCust_exists_in_our_bank();
//		List<Customer> custListFromDB = custDBList.getCustomerDBList(rfc);
		
		for(Loan loan : loanList)
		{
			if(rfc.equalsIgnoreCase(loan.getRfc()))
			{
				qualification = loan.getQualification();
				status = loan.isStatus();
				loan_amount = loan.getLoan_amount();
				loanVariableCounter(loan.getQualification(), loan.isStatus());
			}
		}
		
		if(custDBList.getCustomerDBList() != null)
		{
			CustomerDB custRecordFromDB = custDBList.getCustomerDBList();
			name = custRecordFromDB.getFname() + " " + custRecordFromDB.getLname();
		}
		
		listFromFile = new ArrayList<Customer>();
		HelloClient client = new HelloClient(); // for restful service
		String dataFromFile = client.getMessage("READ:" + rfc);
		log.info("data from the socket : " + dataFromFile);
		
		if(dataFromFile.equalsIgnoreCase("NO_RECORDS") && number_of_times_loan_taken == 0)
		{
			return "NO_RECORDS";
		}
		else if(dataFromFile.equalsIgnoreCase("NO_RECORDS") && number_of_times_loan_taken > 0)
		{
			return logicToDecideGiveLoanOrNot();
		}
		
		listFromFile = convertDatasFromFileToObject(dataFromFile);
		
		return logicToDecideGiveLoanOrNot();
	}
	
	/*
	 * if there are any records for the given rfc in the creditbureau 
	 * then this method will convert the file records to the customer object
	 */
	private List<Customer> convertDatasFromFileToObject(String dataFromFile)
	{
		List<String> stringList = new ArrayList<String>();
		
		if(dataFromFile.indexOf("||") >= 0)
		{
			String[] str = dataFromFile.split("\\|\\|");
			stringList = Arrays.asList(str);
		}
		else
		{
			stringList.add(dataFromFile);
		}
		
		List<Customer> customerList = new ArrayList<Customer>();
		log.info("Number of records in Credit Buruea " + String.valueOf(stringList.size()));
		
		for(String recordSplit : stringList)
		{
			String[] record = recordSplit.split("\\$");
			log.info("Number of fields in a record :" + String.valueOf(record.length));
			Customer cust;
			try {
				cust = new Customer(record[0], new Date(df.parse(record[1]).getTime()), record[2], record[3], new BigDecimal(record[4]), record[5], (record[6].equalsIgnoreCase("Y") ? true : false));
				customerList.add(cust);
				loanVariableCounter(cust.getQualification(), cust.isLoan_status());
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			
		}
		
		return customerList;
	}
	
	private String logicToDecideGiveLoanOrNot(){
		
		log.info("bad_qualification_count :" + bad_qualification_count);
		log.info("loan_active_counter :" + loan_active_counter);
		if(bad_qualification_count > 0 || loan_active_counter > 0)
			return "DONT_GIVE_LOAN";
		else
			return "LOAN_ESTABLISHMENT";
	}
	
	private void loanVariableCounter(String qualification, boolean status){
		
		if(qualification.equalsIgnoreCase("BAD"))
			bad_qualification_count++;
		if(status == true )
			loan_active_counter++;
		
		number_of_times_loan_taken++;
	}

}
