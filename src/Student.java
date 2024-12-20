import java.io.*;
import java.util.ArrayList;

 public class Student extends Participant {
	 private double GPA ; // GPA
	 private int ES; // extracurricular score
	
	 // constructors
	 public Student () {
		 setName("");
		 this.GPA = 0;
		 this.ES = 0;
		 setNParticipants(0);
		 setMaxMatches(1);
		 
	 
	 }
	 public Student ( String name , double GPA , int ES , int nSchools ) {
		 setName(name);
		 this.GPA = GPA;
		 this.ES = ES;
		 setNParticipants(nSchools);
		 setMaxMatches(1);
	 }
	
	 // getters and setters
	 public double getGPA () {
		 return this.GPA;
	 }
	 public int getES () {
		 return this.ES;
	 }
	 public void setGPA ( double GPA ) {
		 this.GPA = GPA;
	 }
	 public void setES ( int ES ) {
		 this.ES = ES;
	 }
	
	//get new info from the user 
	 public void editInfo ( ArrayList < School > H , boolean canEditRankings ) throws IOException {
		 
		 if(canEditRankings) {
				System.out.print("Name: ");
				setName(BasicFunctions.getString());
				this.GPA = BasicFunctions.getDouble("GPA: ", 0.0, 4.0);
				this.ES =  BasicFunctions.getInteger("Extracurricular score: ", 0, 5);
				setMaxMatches(BasicFunctions.getInteger("Maximum number of matches: ", 1, Integer.MAX_VALUE));
				int num = 0;
				do {
					System.out.print("Edit rankings (y/n): ");
					String choice = BasicFunctions.getString();
					if (choice.equals("y")| choice.equals("Y")) {
						editRankings(H);
						num++;
					}
					else if (choice.equals("n")| choice.equals("N")) {
						num++;
					}
					else {
						System.out.println("ERROR: Choice must be 'y' or 'n'!");
					}
				}while (num == 0);
			}
			else {
				System.out.print("Name: ");
				setName(BasicFunctions.getString());
				this.GPA = BasicFunctions.getDouble("GPA: ", 0.0, 4.0);
				this.ES =  BasicFunctions.getInteger("Extracurricular score: ", 0, 5);
				setMaxMatches(BasicFunctions.getInteger("Maximum number of matches: ", 1, Integer.MAX_VALUE));
			}
		 }
	
	 //print student row
	 public void print ( ArrayList <? extends Participant > H )  {
		 
		 if (H.size() != 0) {
			 String spaces = "";
			 if (getName().length()>=40) {
				 spaces = "";
			 }
			 else {
				 spaces = String.format("%"+(40-getName().length())+"s", "");
			 }
			 System.out.format(getName()+spaces+"    %.2f   "+this.ES+"  ", this.GPA);
			 if (getNMatches () == 0) {
				 System.out.print("-                                       ");
				 printRankings(H);
				 System.out.println();
			 }
			 else {
				 String Matches = getMatchNames (H);
				 String spaces2 = "";
				 if (Matches.length()>=40) {
					 spaces2 = "";
				 }
				 else {
					 spaces2 = String.format("%"+(40-Matches.length())+"s", "");
				 }
				 System.out.print(Matches+spaces2);
				 
				 printRankings(H);
				 System.out.println();
			 }
		 }
		 else {
			 String spaces = "";
			 if (getName().length()>=40) {
				 spaces = "";
			 }
			 else {
				 spaces = String.format("%"+(40-getName().length())+"s", "");
			 }
			 System.out.format(getName()+spaces+"    %.2f   "+this.ES+"  -                                       -\n", this.GPA);
		 }
	 }
	 public boolean isValid () {
		 // check if this student has valid info
		 for (int i = 0; i < getNParticipants(); i++) {
			  for(int j =0; j < getNParticipants();j++) {
				  if (i==j) {
					  
				  }
				  
				  else if(findRankingByID(i) == findRankingByID (j)) {
					 return false; 
				  }
				  else if(findRankingByID (i) < 1 | findRankingByID (j) < 1 | findRankingByID (i) >  getNParticipants() | findRankingByID (j) >  getNParticipants()) {
					 return false;
				  }
			  }
		  }
		  if (this.GPA < 0 | this.GPA > 4 | this.ES < 0 | this.ES > 5 ) {
			  return false;
		  }
		  return true;
	 }
 
 }
