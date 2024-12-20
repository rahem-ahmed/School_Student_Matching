import java.io.*;
import java.util.ArrayList;

public class School extends Participant {
	private double alpha ; // GPA weight
	
	// constructors
	public School () {
		
		setName("");
		 this.alpha = 0;
	}
	public School ( String name , double alpha , int maxMatches , int nStudents ) {
		
		setName(name);
		 this.alpha = alpha;
		 setMaxMatches(maxMatches);
		 setNParticipants(nStudents);
	}
	
	// getters and setters
	public double getAlpha () { return this . alpha ; }
	public void setAlpha ( double alpha ) { this . alpha = alpha ; }
	
	// get new info from the user ; cannot be inherited or overridden from parent
	public void editSchoolInfo ( ArrayList < Student > S , boolean canEditRankings ) throws IOException {
		
		if (canEditRankings) {
			 System.out.print("Name: ");
			 setName(BasicFunctions.getString());
			 this.alpha =  BasicFunctions.getDouble("GPA weight: ", 0.0, 1.0);
			 setMaxMatches(BasicFunctions.getInteger("Maximum number of matches: ", 1, Integer.MAX_VALUE));
			 calcRankings(S);
		 }
		 else {
			 System.out.print("Name: ");
			 setName(BasicFunctions.getString());
			 this.alpha =  BasicFunctions.getDouble("GPA weight: ", 0.0, 1.0);
			 setMaxMatches(BasicFunctions.getInteger("Maximum number of matches: ", 1, Integer.MAX_VALUE));
		 }
	}
	
	//calc rankings from alpha
	public void calcRankings ( ArrayList < Student > S ) {
		
		setNParticipants(S.size());
		 double[] score = new double[S.size()];
		 for (int i = 0; i < S.size(); i++ ) {
			 score[i] = this.alpha * S.get(i).getGPA() + (1 - this.alpha)*S.get(i).getES();
		 }
		 for (int i = 0; i < S.size(); i++ ) {
			 int rank = S.size();
			 for(int j = 0; j <S.size(); j++) {
				 if (i ==j) { 
				 }
				 else if( score[i] > score[j]) {
					 rank--;
				 }
				 else if (score[i] == score[j] & i<j) {
					 rank--;
				 }
			 }
			 setRanking (i , rank);
			 
		 }
	}
	
	//print school row
	public void print ( ArrayList <? extends Participant > S ) {
		
		if (S.size() != 0) {
			 String spaces = "";
			 if (getName().length()>=40) {
				 spaces = "";
			 }
			 
			 else {
				 spaces = String.format("%"+(40-getName().length())+"s", "");
			 }
			 System.out.format(getName() +spaces);
			 
			 String spaces2 = "";
			 
			 if (String.valueOf(getMaxMatches()).length()>=8) {
				 spaces2 = "";
			 }
			 
			 else {
				 spaces2 = String.format("%"+(8-String.valueOf(getMaxMatches()).length())+"s", "");
			 }
			 System.out.format(spaces2+getMaxMatches()+"    %.2f  ",this.alpha);
			 
			 if (getNMatches () == 0) {
				 System.out.print("-                                       ");
				 printRankings(S);
				 System.out.println();
			 }
			 else {
				 String MatchesN = getMatchNames (S);
				 String spaces3 = "";
				 if (MatchesN.length()>=40) {
					 spaces3 = "";
				 }
				 else {
					 spaces3 = String.format("%"+(40-MatchesN.length())+"s", "");
				 }
				 System.out.print(MatchesN+spaces3);
				 printRankings(S);
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
			 System.out.format(getName() +spaces);
			 String spaces2 = "";
			 
			 if (String.valueOf(getMaxMatches()).length()>=8) {
				 spaces2 = "";
			 }
			 
			 else {
				 spaces2 = String.format("%"+(8-String.valueOf(getMaxMatches()).length())+"s", "");
			 }
			 System.out.format(spaces2+getMaxMatches()+"    %.2f  -                                       -\n",this.alpha);
		 }
	
	}
	//check if this school has valid info
	public boolean isValid () {
		
		if (this.alpha < 0 | this.alpha > 1 | getMaxMatches () <1) {
			 return false;
		 }
		 return true;
	}


}