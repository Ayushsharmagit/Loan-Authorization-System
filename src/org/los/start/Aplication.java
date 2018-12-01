package org.los.start;

import org.los.operation.LOSProcess;
import org.los.utill.Utility;

public class Aplication {

	public static void main(String[] args) {
		
		LOSProcess process=new LOSProcess();
		while(true) {
		System.out.println("Do u have Appllication number or"
				+ " Not(if Not Enter 0) "
				+ "(press -1 to EXIT)");
		int applicationNumber=Utility.scanner.nextInt();
		
		if(applicationNumber==-1) {
			System.out.println("Thanks for using");
			System.exit(0);
		}
		if(applicationNumber==0) {
			process.Sourcing();
		}
		else {
			//Existing Customer
			process.checkStage(applicationNumber);
		}
	}
	}

}
