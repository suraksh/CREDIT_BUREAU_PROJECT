package com.recluit.lab.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerDetails {
	
	private List<Customer> customerList = new ArrayList<Customer>();
	
	public CustomerDetails(List<Customer> customerList){
		this.customerList.addAll(customerList);
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	
	

}
