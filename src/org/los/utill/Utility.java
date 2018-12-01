package org.los.utill;

import java.util.Scanner;

public class Utility  implements StageConstant{
	
	public static int serialCounter=1;
	
	private Utility() {}//constructor why?
		 public static Scanner scanner=new Scanner(System.in);
		 public static String getStageName(int stageId) {
		 switch(stageId) {
		 case SOURCING:
			 return "SOURCING Stage";
		 case QDE:
			 return "QDE Stage";
		 case DEDUPE:
			 return "DEDUPE Stage";
		 case SCORING:
			 return "SCORING Stage";
		 case APPROVAL:
			 return "APPROVAL Stage";
		 case REJECT:
			 return "REJECT Stage";
		 default:
			 return "Invalid Stage ";
		 		 
		 }
		 }
}
