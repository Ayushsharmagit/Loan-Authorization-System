package org.los.operation;

import java.util.ArrayList;

import org.los.customer.Customer;
import org.los.customer.PersonalInformation;

public interface DB {
	public static ArrayList<Customer> getNagativeCustomers(){
		ArrayList<Customer> negativeCustomers=new ArrayList<>();
		
		Customer customer=new Customer();		
		customer.setId(1010);
		PersonalInformation pd=new PersonalInformation();
		pd.setFirstName("Tim");
		pd.setLastName("Jakson");
		pd.setPancard("BW1000");
		pd.setVoterId("A111");
		pd.setPhone("2222");
		pd.setEmail("tim@gmail.com");
		customer.setPersonalInformation(pd);
		negativeCustomers.add(customer);//
		
		customer=new Customer();
		customer.setId(1011);
		pd=new PersonalInformation();
		pd.setFirstName("Tom");
		pd.setLastName("Dahl");
		pd.setPancard("BW2000");
		pd.setVoterId("A222");
		pd.setPhone("3333");
		pd.setEmail("tom@gmail.com");
		customer.setPersonalInformation(pd);
		negativeCustomers.add(customer);
		return negativeCustomers;
	}
	}

