package org.los.operation;

import static org.los.utill.Utility.scanner;
import static org.los.utill.Utility.serialCounter;

import java.util.ArrayList;

import org.los.customer.Customer;
import org.los.customer.LoanDetails;
import org.los.customer.PersonalInformation;
import org.los.utill.CommonConstant;
import org.los.utill.LoanConstant;
import org.los.utill.StageConstant;
import org.los.utill.Utility;

public class LOSProcess implements StageConstant, CommonConstant {
	private ArrayList <Customer> customers=new ArrayList<Customer>();
	
	public void qde(Customer customer) {
		customer.setStage(QDE);
		System.out.println("**************************************************");
		System.out.println("Application Number  :"+customer.getId());
		System.out.println("Name                :"
		+customer.getPersonalInformation().getFirstName()+
		" "+customer.getPersonalInformation().getLastName());
		System.out.println("You Appliead For    :"+customer.getLoanDetails().getType());
		System.out.println("Duration            :"+customer.getLoanDetails().getDuration());
		System.out.println("Amount              :"+customer.getLoanDetails().getAmount());
		System.out.println("**************************************************");
		System.out.println("Enter the Pan Details");
		String panCard=scanner.next();
		System.out.println("Enter Voter Id");
		String voterId=scanner.next();
		System.out.println("Enter the Income");
		double Income=scanner.nextDouble();
		System.out.println("Enter the laibility");
		double laibility=scanner.nextDouble();
		System.out.println("Enter the Phone");
		String phone=scanner.next();
		System.out.println("Enter the Email");
		String email=scanner.next();
		customer.getPersonalInformation().setPancard(panCard);
		customer.getPersonalInformation().setVoterId(voterId);
		customer.setIncome(Income);
		customer.setLiability(laibility);
		customer.getPersonalInformation().setPhone(phone);
		customer.getPersonalInformation().setEmail(email);
		System.out.println("QDE stage is done ");
	}
	
	
	public void Approval(Customer customer) {
		customer.setStage(APPROVAL);
		int score=customer.getLoanDetails().getScore();
		System.out.println("ID:-      "+customer.getId());
		System.out.println("Name:-    "+customer.getPersonalInformation().getFirstName()+
				" "+customer.getPersonalInformation().getLastName());
		System.out.println("loan:-    "+customer.getLoanDetails().getType());
		System.out.println("Amount:-  "+customer.getLoanDetails().getAmount());
		System.out.println("Duration:-"+customer.getLoanDetails().getDuration());
		double approveAmount=customer.getLoanDetails().getAmount()*(score/100);
		System.out.println("Loan Approve Amount is:-"+approveAmount);
		System.out.println("Do you want to Bring this Loan or Not");
		char choice =scanner.next().toUpperCase().charAt(0);
		if (choice==NO) {
			customer.setStage(REJECT);
			customer.setReamark("Customer Deny the Approval AMount"+""+approveAmount);
			return;
		}
		else {
			showEMI(customer);
		}
		System.out.println("Approvale stage is Done");
	}
	
	private void showEMI(Customer customer) {
		//System.out.println("Emi is ");
		if(customer.getLoanDetails().getType().equalsIgnoreCase(LoanConstant.HOME_LOAN)) {
			customer.getLoanDetails().setRoi(LoanConstant.HOME_LOAN_ROI);
		}
			
		if(customer.getLoanDetails().getType().equalsIgnoreCase(LoanConstant.AUTO_LOAN)) {
				customer.getLoanDetails().setRoi(LoanConstant.AUTO_LOAN_ROI);}
				
		if(customer.getLoanDetails().getType().equalsIgnoreCase(LoanConstant.PRESONAL_LOAN)){
					customer.getLoanDetails().setRoi(LoanConstant.PERSONAL_LOAN_ROI);
				}
				double perMonthPrinciple=
						customer.getLoanDetails().getAmount()/customer.getLoanDetails().getDuration();
				double interest=perMonthPrinciple*customer.getLoanDetails().getRoi();
				double totalemi=perMonthPrinciple+interest;
				System.out.println("Your Emi="+totalemi);
				
		}
	
	
	public void Scoring(Customer customer) {
		customer.setStage(SCORING);
		int score=0;
		double totalIncome=customer.getIncome()-customer.getLiability();
		if (customer.getPersonalInformation().getAge()>=21&& 
				customer.getPersonalInformation().getAge()<=35) {
			score+=50;
			System.out.println("You Get 50 Points Because of Age Score");
		}
		if(totalIncome>=200000) {
			score+=50;
			System.out.println("You Get 50 Points Because of Income Score");
		}
		System.out.println("And Final Score is "+score);
		customer.getLoanDetails().setScore(score);
		System.out.println("Scoring Stage is done ");
	}
	
	
	public void moveToNextStage(Customer customer) {
		while(true) {
		if(customer.getStage()==SOURCING) {
			System.out.println("Want to move to the Next Stage Y/N");
			char choice=scanner.next().toUpperCase().charAt(0);
			if(choice==YES) {
				qde(customer);
				}
			else{return;
			}
		}
		else
		if(customer.getStage()==QDE) {
			System.out.println("Want to move to the Next Stage Y/N");
			char choice=scanner.next().toUpperCase().toUpperCase().charAt(0);
			if(choice==YES) {
				dedupe(customer);
				}
			else{return;
			}
		}
		else
		if(customer.getStage()==DEDUPE) {
			System.out.println("Want to move to the Next Stage Y/N");
			char choice=scanner.next().toUpperCase().charAt(0);
			if(choice==YES) {
				Scoring(customer);
				}
			else{return;
			}
		}
		else
		if(customer.getStage()==SCORING) {
			System.out.println("Want to move to the Next Stage Y/N");
			char choice=scanner.next().toUpperCase().charAt(0);
			if(choice==YES) {
				Approval(customer);
				}
			else{
				return;
			}
		}
		}
	}
	
	
	public void dedupe(Customer customer) {
		//System.out.println("Inside Dedupe");
		customer.setStage(DEDUPE);
		boolean isNagativeFound=false;
		for(Customer negativeCustomers : DB.getNagativeCustomers()) {
			int negativeScore=isNagative(customer ,negativeCustomers);
			if(negativeScore>0) {
				System.out.println("Customer Record Found In Dedupe and Score is "+negativeScore);
				isNagativeFound=true;
				break;
				
			}
			if(isNagativeFound) {
				System.out.println("Do want to Proced This Loan "+customer.getId());
				char choice=scanner.next().toUpperCase().charAt(0);
				if(choice==NO) {
					customer.setReamark("Loan Is Rejacted,Due To High Scores is Dedupe Check  ");
					customer.setStage(REJECT);
					return;
				}
			}
		}
		System.out.println("Dedupe Stage is Done ");
	}
	
	
	private int isNagative(Customer customer,Customer nagetive) {
		int percentageMatch=0;
		if(customer.getPersonalInformation().getPhone()
				.equals(nagetive.getPersonalInformation().getPhone())) {
			percentageMatch+=20;	
		}
		
		if(customer.getPersonalInformation()
				.getEmail().equals(nagetive.getPersonalInformation().getEmail())) {
			percentageMatch+=20;
			
		}
			
		if(customer.getPersonalInformation().getVoterId()
				.equals(nagetive.getPersonalInformation().getVoterId())) {
			percentageMatch+=20;	
		}
		
		if(customer.getPersonalInformation().getPancard()
				.equals(nagetive.getPersonalInformation().getPancard())) {
			percentageMatch+=20;	
		}
		
		if(customer.getPersonalInformation().getAge()== nagetive.getPersonalInformation().getAge()
				&& customer.getPersonalInformation().getFirstName()
				.equalsIgnoreCase(nagetive.getPersonalInformation().getFirstName())) {
			percentageMatch+=20;	
		}
		return percentageMatch;
	
	}
	
	
	public void Sourcing() {
		Customer customer=new Customer();
		customer.setId(serialCounter);
		customer.setStage(SOURCING);
		System.out.println("Enter the FirstName");
		String firstName=scanner.next();
		System.out.println("Enter the LastName");
		String lastName=scanner.next();
		System.out.println("Enter the Age");
		int age=scanner.nextInt();
		System.out.println("Enter the loan Type HL,AL,PL");
		String type=scanner.next();
		System.out.println("Enter the Amount");
		double amount=scanner.nextDouble();
		System.out.println("Duration Type ");
		int duration=scanner.nextInt();
		PersonalInformation pd=new PersonalInformation();
		pd.setFirstName(firstName);
		pd.setLastName(lastName);
		pd.setAge(age);
		customer.setPersonalInformation(pd);//obj k ander obj
		LoanDetails loandetails=new LoanDetails();
		loandetails.setType(type);
		loandetails.setAmount(amount);
		loandetails.setDuration(duration);
		customer.setLoanDetails(loandetails);//obj k ander obj
		customers.add(customer);//Add customer in customers array
		serialCounter++;
		System.out.println("SOURCING Stage Done");
		
		
	}
	
	
	public void checkStage(int applicationNumber) {
		boolean isStageFound=false;
		if(customers!=null&&customers.size()>0) { //check for customers is!=null
		for(Customer customer:customers) {
			if(customer.getId()==applicationNumber) {
				System.out.println("You are On:- "+
			Utility.getStageName(customer.getStage()));
				isStageFound=true;
				moveToNextStage(customer);
				break;
			}
		}
		}
	if(!isStageFound) {
		System.out.println("*Invalid Application Number*");
	}
	
	}
}
