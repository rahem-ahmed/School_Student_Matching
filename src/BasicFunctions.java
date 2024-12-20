import java.io.*;

public class BasicFunctions {
	
	public static BufferedReader cin = new BufferedReader (new InputStreamReader ( System . in ) ) ;
	
	


	public static String getString() throws IOException {
		//this function gets a string input from the user
		return cin.readLine();
	}
	
	public static boolean containsInterger(int[] N, int number) {
		//this loops through an integer array to see if the specified integer is inside the array
		for (int i = 0; i < N.length;i++) {
			if (N[i] == number) {
				return true;
			}
		}
	return false;
	}
	
	public static int getInteger(String prompt, int LB, int UB) throws IOException {
		// this function asks the user for an integer between the values of LB and UB, this function loops until correct input is given
		int x = 0;
		boolean valid;
		System.out.print(prompt);
		do {
			valid = true;
			try {
				x = Integer.parseInt (cin.readLine());
				if(x>UB | x<LB) {
					x =1/0;
				}
			}
			catch(NumberFormatException e){
				valid = false;
				if(UB == Integer.MAX_VALUE) {
					System.out.println("\nERROR: Input must be an integer in ["+ LB +", infinity]!\n");
					System.out.print(prompt);
				}
				else {
					System.out.println("\nERROR: Input must be an integer in ["+ LB +", "+ UB+"]!\n");
				    System.out.print(prompt);
				}
			}
			catch(ArithmeticException e){
				valid = false;
				if(UB == Integer.MAX_VALUE) {
					System.out.println("\nERROR: Input must be an integer in ["+ LB +", infinity]!\n");
					System.out.print(prompt);
				}
				else {
					System.out.println("\nERROR: Input must be an integer in ["+ LB +", "+ UB+"]!\n");
				    System.out.print(prompt);
				}
		}
			
	}while(!valid);
	return x;	
	}
	
	public static double getDouble(String prompt, double LB, double UB) throws IOException {
		// this function asks the user for a double between the values of LB (lowerbound) and UB (upperbound), this function loops until correct input is given
		double x = 0;
		boolean valid;
		System.out.print(prompt);
		do {
			valid = true;
			try {
				x = Double.parseDouble (cin.readLine());
				if(x<LB | x>UB) {
					x =1/0;
				}
			}
			catch(NumberFormatException e){
				valid = false;
				if(UB == Double.MAX_VALUE ) {
					System.out.format("\nERROR: Input must be a real number in [%.2f, infinity]!\n\n", LB);
					System.out.print(prompt);
				}
				else {
					System.out.format("\nERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
					System.out.print(prompt);
				}
			}
			catch(ArithmeticException e){
				valid = false;
				if(UB == Double.MAX_VALUE ) {
					System.out.format("\nERROR: Input must be a real number in [%.2f, infinity]!\n\n", LB);
					System.out.print(prompt);
				}
				else {
					System.out.format("\nERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
					System.out.print(prompt);
				}
		}
		
	}while(!valid);
	return x;
	}
}
