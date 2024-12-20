import java.io.*;
import java.util.ArrayList;

public class Participant {
	 private String name ; // name
	 private int [] rankings ; // rankings of participants
	 private ArrayList < Integer > matches = new ArrayList < Integer >() ;// match indices
	 private int regret ; // total regret
	 private int maxMatches ; // max # of allowed matches / openings

 // constructors
	 public Participant () {
		 this.name = "";
		 this.maxMatches = 1;
		 this.regret = 0;
		 
	 }
	 public Participant ( String name , int maxMatches , int nParticipants ) {
		 this.name = name;
		 this.maxMatches = maxMatches;
		 this.rankings = new int [nParticipants];
	 }
	
	 // getters
	 public String getName () {
		 return this.name;
	 }
	 //this returns the index with the ranking i 
	 public int getRanking ( int i ) {
		 int index = -1;
			for (int j = 0; j <this.rankings.length;j++) {
				if (this.rankings[j] ==i) {
					index = j;
				}
			 }
		return index;
	 }
	 public int getMatch ( int i ) {
		 return this.matches.get(i);
	 }
	 public int getRegret () {
		 calcRegret();
		 return this.regret;
	 }
	 public int getMaxMatches () {
		 return maxMatches;
	 }
	 public int getNMatches () {
		 return this.matches.size();
	 }
	//return length of rankings array
	 public int getNParticipants () {
		 return this.rankings.length;
	 }
	 public boolean isFull () {
		 if (this.matches.size() == this.maxMatches) {
			 return true;
		 }
		 return false;
	 }
	
	 // setters
	 public void setName ( String name ) {
		 this.name = name;
	 }
	 public void setRanking ( int i , int r ) {
		 this.rankings[i] = r;
	 }
	 public void setMatch ( int m ) {
		 this.matches.add(m);
	 }
	 public void setRegret ( int r ) {
		 this.regret = r;
	 }
	 public void setNParticipants ( int n ) {
		 // set rankings array size
		 this.rankings = new int [n];
	 }
	 public void setMaxMatches ( int n ) {
		 this.maxMatches = n;
	 }
	 
	 // methods to handle matches
	  public void clearMatches () {
		 // clear all matches
		  this.matches = new ArrayList < Integer >() ;
	 }
	  public int findRankingByID ( int k ) {
		 // find rank of participant k
		 return this.rankings[k];
	 }
	  public int getWorstMatch () {
		 // find the worst - matched participant
		 int worst_match = -1;
		  for (int i = 0; i < this.matches.size(); i++) {
			  if (i == 0) {
				  worst_match = getMatch(i);
			  }
			  else {
				  if (findRankingByID (worst_match) < findRankingByID (getMatch(i))) {
					  worst_match = getMatch(i);
				  }
			  }
		  }
		  return worst_match;
	 }
	  public void unmatch ( int k ) {
		 // remove the match with participant k
		 this.matches.remove(Integer.valueOf(k));
	 }
	  public boolean matchExists ( int k ) {
		 // check if match to participant k exists
		 if(this.matches.contains(k)) {
			 return true;
		 }
		  return false;
	 }
	  public int getSingleMatchedRegret ( int k ) {
		 // get regret from match with k
		 return this.rankings[k] -1;
	 }
	  public void calcRegret () {
		 // calculate total regret over all matches
		 int total_sum = 0;
		  for(int i = 0; i < this.matches.size(); i++) {
			  total_sum += getSingleMatchedRegret (this.matches.get(i));
			}
		 this.regret = total_sum;
	 }
	 
	  // methods to edit data from the user
	  public <T extends Participant> void editInfo ( ArrayList <T>  P ) throws IOException {
		  if (this.rankings.length != 0) {
			 System.out.print("Name: ");
			 this.name = BasicFunctions.getString(); 
			 this.maxMatches =  BasicFunctions.getInteger("Maximum number of matches: ", 1, Integer.MAX_VALUE);
			 int num = 0;
			 do {
					System.out.print("Edit rankings (y/n): ");
					String choice = BasicFunctions.getString();
					if (choice.equals("y")| choice.equals("Y")) {
						editRankings(P);
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
			  this.name = BasicFunctions.getString();
			  this.maxMatches =  BasicFunctions.getInteger("Maximum number of matches: ", 1, Integer.MAX_VALUE);
		  }
	 }
	  public <T extends Participant> void editRankings ( ArrayList <T>  P ) throws IOException {
		  
		  System.out.println("\nParticipant "+ getName() +"'s rankings:");
		  setNParticipants(P.size());
		   int[] rank_used = new int[P.size()];
		   for(int j = 0; j < P.size(); j++) {
			   boolean rank = true;
			   do {
				   int Participant_rank = BasicFunctions.getInteger("School "+ P.get(j).getName()+": ", 1, P.size());
				   if(! BasicFunctions.containsInterger(rank_used, Participant_rank)){
					   setRanking(j,Participant_rank);
					   rank_used[j] = Participant_rank; 
					   rank = false;
				   }
				   else {
					   System.out.println("ERROR: Rank "+ Participant_rank + " already used!\n");
				   }
			   }while(rank);
		   }
		   System.out.println();
	 }
	 
	  // print methods
	  public void print ( ArrayList <? extends Participant > P ) {
		  if (this.rankings.length != 0) {
			  String spaces = "";
				 if (this.name.length()>=28) {
					 spaces = "";
				 }
				 else if (this.name.length()==27) {
					 spaces = "";
				 }
				 else {
					 spaces = String.format("%"+(27-this.name.length())+"s", "");
				 }
				 System.out.print(this.name+spaces+"     "+this.maxMatches+" ");
				 if (this.matches.size() == 0) {
					 System.out.print(" -                          ");
					 printRankings(P);
					 System.out.println();
				 }
				 else {
					 String Matches = getMatchNames (P);
					 System.out.print(Matches);
					 
					 printRankings(P);
					 System.out.println();
				 }
			 }
			 
			 else {
				 
				 String spaces = "";
				 if (this.name.length()>=28) {
					 spaces = "";
				 }
				 else if (this.name.length()==27) {
					 spaces = "";
				 }
				 else {
					 spaces = String.format("%"+(27-this.name.length())+"s", "");
				 }
				 System.out.print(this.name+spaces+"     "+this.maxMatches+"  -                          -\n");
			 }
		 }
	  public void printRankings ( ArrayList <? extends Participant > P ) {
		  for (int i = 0; i < P.size();i++ ) {
			  for (int j = 0; j < P.size(); j++) {
				  if ((i+1) == P.size()) {
					  if (i+1 == this.rankings[j]) {
						  System.out.print(P.get(j).getName());
					  }
				  }
				  else {
					  if((i+1) == this.rankings[j]) {
						  System.out.print(P.get(j).getName() +", ");
					  }
				  }
			  }
		  }
	  }
	  public String getMatchNames ( ArrayList <? extends Participant > P ) {
		  String Matches_names = "";
		  for(int i = 0; i <this.matches.size(); i++) {
				 if (i== this.matches.size()-1) {
					 Matches_names += P.get(this.matches.get(i)).getName();
				 }
				 else {
					 Matches_names += P.get(this.matches.get(i)).getName() +", ";
				 }
			 }
		  return  Matches_names;
	 }
	 
	  // check if this participant has valid info
	  public boolean isValid () {
		  if (this.maxMatches< 1) {
			  return false;
		  }
	
		  return true;
	 }
 }