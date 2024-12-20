import java.util.ArrayList;

public class SMPSolver {
	 private ArrayList<? extends Participant> S = new ArrayList < Participant >() ; // suitors
	 private ArrayList<? extends Participant> R = new ArrayList < Participant >() ; // receivers
	 private double avgSuitorRegret ; // average suitor regret
	 private double avgReceiverRegret ; // average receiver regret
	 private double avgTotalRegret ; // average total regret
	 private boolean matchesExist ; // whether or not matches exist
	 private boolean stable ; // whether or not matching is stable
	 private long compTime ; // computation time
	 private boolean suitorFirst ;// whether to print suitor stats first
	 private int worstSuitorRegret;
	 
	 public SMPSolver () {
		 // constructor
		 this.avgSuitorRegret = 0; 
		 this.avgReceiverRegret = 0; 
		 this.avgTotalRegret = 0; 
		 this.matchesExist = false;
		 this.stable = false;
		
		 this.suitorFirst = true; 
		 this.worstSuitorRegret = 0;
		 
	 }
	
	 // getters
	 public double getAvgSuitorRegret () { return this . avgSuitorRegret ; }
	 public double getAvgReceiverRegret () { return this . avgReceiverRegret ; }
	 public double getAvgTotalRegret () { return this . avgTotalRegret ; }
	 
	 //get the worst regret among suitors
	 public int getWorstSuitorRegret() { return this.worstSuitorRegret;}
	 
	 //this function determines the worst regret among suitors
	 public void determineWorstSuitorRegret(){
		 int worstRegret = 0;
		 for (int i = 0; i < this.S.size(); i++) {
			 if (i==0) {
				 worstRegret = this.S.get(i).getRegret();
			 }
			 else {
				 if (worstRegret <this.S.get(i).getRegret()) {
					 worstRegret = this.S.get(i).getRegret();
				 }
			 }
		 }
		 this.worstSuitorRegret = worstRegret;
	 }
	 
	 public boolean matchesExist () {
		return this.matchesExist;  
		}
	 public boolean isStable () {
		 
		 return this.stable;
	 }
	 public long getTime () {
		 return this.compTime;
	 }
	 
	 public int getNSuitorOpenings () {
		 int suitors_openings = 0;
		 for (int i = 0; i < this.S.size(); i++ ) {
			 suitors_openings += this.S.get(i).getMaxMatches();
		 }
		 return suitors_openings;
	 }
	 public int getNReceiverOpenings () {
		 int reciver_openings = 0;
		 for (int i = 0; i < this.R.size(); i++ ) {
			 reciver_openings += this.R.get(i).getMaxMatches();
		 }
		 return reciver_openings;
	 }
	
	 // setters
	 public void setMatchesExist ( boolean b ) {
		 this.matchesExist = b;
	 }
	 public void setSuitorFirst ( boolean b ) {
		 this.suitorFirst = b;
	 }
	 //set participant to SMPsolver
	 public void setParticipants ( ArrayList <? extends Participant > S , ArrayList <? extends Participant > R ){ 
		 this.S = S;
		 this.R = R;
	 }
	
	 // methods for matching
	 public void clearMatches () {
		 // clear out existing matches
		 for (int i = 0; i < this.S.size(); i++ ) {
			 this.S.get(i).clearMatches();
		 }
		 for (int i = 0; i < this.R.size(); i++ ) {
			 this.R.get(i).clearMatches();
		 }
	 }
	 public boolean matchingCanProceed () {
		 // check that matching rules are satisfied
		 int suitors_openings = getNSuitorOpenings ();// this adds the openings of the suitors
		 int reciver_openings = getNReceiverOpenings ();// this adds the openings of the reciever
		 if ((suitors_openings == reciver_openings) & this.S.size()!= 0) {
				return true;
			}
		else if(this.S.size() == 0) {
				System.out.println("ERROR: No suitors are loaded!\n");
			}
		else if(this.R.size() == 0) {
				System.out.println("ERROR: No receivers are loaded!\n");
			}
		else if(suitors_openings != reciver_openings) {
				System.out.println("ERROR: The number of suitor and receiver openings must be equal!\n");
			}
		return false;
		 
		 
	 }
	 public boolean match () {
		 // Gale - Shapley algorithm to match ; students are suitors
		 long start = System . currentTimeMillis () ;
		 int counter = 0;
		 clearMatches ();
		 do { 
				counter = 0;
				for (int i = 0; i < this.S.size(); i++ ) {
					if (!this.S.get(i).isFull()) {// if suitr has openings then continue 
						for(int j =0; j < this.R.size(); j++) {
							if (makeProposal(i, this.S.get(i).getRanking(j+1))) {//checks if proposal is posible
								makeEngagement(i, this.S.get(i).getRanking(j+1), this.R.get(this.S.get(i).getRanking(j+1)).getWorstMatch());//makes engagement
								break;
							}
						} 
					}	 
				}
				for (int i = 0; i < this.S.size(); i++ ) {//if matches are not full for suitors loop again
					 if (!this.S.get(i).isFull()) {
						 counter++;
					 }
				}
			 	}while (counter != 0); // condition to end loop
		 setMatchesExist (true)  ; //set macthes exist after algo 
		 this.stable = determineStability ();
		 printStats ();
	     int numberofmatches = 0;
		 for(int i = 0; i <this.S.size();i++) {
			 numberofmatches += this.S.get(i).getMaxMatches();
		 }
		 this.compTime = System . currentTimeMillis () - start ;
		 System.out.println(numberofmatches+" matches made in "+getTime () +"ms!\n");
		 
		 determineWorstSuitorRegret(); //determines worst regret among suitors
			
		 return true;
	 }
	// suitor proposes
	 private boolean makeProposal (int suitor , int receiver ) {
		 
		if(this.R.get(receiver).matchExists(suitor)){//if matched already cant make proposal
			return false;
		}
	    else if (!this.R.get(receiver).isFull()) {// if openings available then make proposal
			return true;
		}
		else if (this.R.get(receiver).findRankingByID(this.R.get(receiver).getWorstMatch ()) > this.R.get(receiver).findRankingByID(suitor)) {// if ranking of suitor is higher than ranking of worst match then make proposal
				
			return true;
		}
		return false;
	 }
	// make suitor - receiver engagement , break receiver - oldSuitor engagement
	 private void makeEngagement (int suitor , int receiver , int oldSuitor ) {
		  
		 if (!this.R.get(receiver).isFull()) {//if reciever is not full then suitor is add without the removal of worst match
			 this.R.get(receiver).setMatch(suitor);
			 this.S.get(suitor).setMatch(receiver);
		 }
		 else{//else then suitor is add with the removal of worst match
			 this.S.get(oldSuitor).unmatch(receiver);
			 this.R.get(receiver).unmatch(oldSuitor);
			 this.R.get(receiver).setMatch(suitor);
			 this.S.get(suitor).setMatch(receiver);
		 }
		 
		 
	 }
	 
	// calculate regrets	
	 public void calcRegrets () {
		 
		    double suitor_regret = 0;
			double  reciever_regret = 0;
			for (int i = 0; i < this.S.size(); i++) {
				 suitor_regret += this.S.get(i).getRegret(); // sum up all the suitors regrets
				}
			for (int i = 0; i < this.R.size(); i++) {
				reciever_regret += this.R.get(i).getRegret(); // sum up all the recievers regrets
			}
			 this.avgSuitorRegret = suitor_regret/this.S.size();
			 this.avgReceiverRegret = reciever_regret/this.R.size();
			 this.avgTotalRegret = (suitor_regret + reciever_regret)/(this.S.size() +this.R.size());
	 }
	 
	 // calculate if a matching is stable
	 public boolean determineStability () {
		
		 for (int i = 0; i < this.S.size();i++) {
			 for(int f = 0; f < this.R.size(); f++) {
					if (this.S.get(i).matchExists(f)) {
						
					}
					else if(this.S.get(i).findRankingByID(this.S.get(i).getWorstMatch()) > this.S.get(i).findRankingByID(f)){//checks if suitors worst match rank is lower than recievers rank
						if (this.R.get(f).findRankingByID(this.R.get(f).getWorstMatch()) > this.R.get(f).findRankingByID(i)) {// checks if the recievers worst match rank is lower than the suitors rank
							return false;
						}
					}
				 
			 }
		 }
		return true;		
	 }
	
	 // print methods
	 public void print () {
		 // print the matching results and statistics
		 if (matchesExist ()) {
			 printMatches ();//uses method that prints matches
			 System.out.println();
			 printStats ();
		 }
		 else {
			 System.out.println("ERROR: No matches exist!\n");
		 }
	 }
	// print matches
	 public void printMatches () {
		 
		 if(this.suitorFirst) {// school : matches is the printing in both GSH and GSS
			 System.out.println("Matches:\n"
					+ "--------");
			for (int i = 0; i < this.R.size(); i++) {	//matches are displayed
				System.out.println(this.R.get(i).getName()+": "+R.get(i).getMatchNames(this.S));
			}
		 }
		 else {
			 System.out.println("Matches:\n"
						+ "--------");
				for (int i = 0; i < this.S.size(); i++) {	//matches are displayed
					System.out.println(this.S.get(i).getName()+": "+S.get(i).getMatchNames(this.R));
				}
		 }
		 
	 }
	// print matching statistics
	 public void printStats () {
		 
		 calcRegrets ();
		 if (determineStability ()) {	//checks if matching is stable using the isStable function
				System.out.println("Stable matching? Yes");
				System.out.format("Average suitor regret: %.2f\n", this.avgSuitorRegret);
				System.out.format("Average receiver regret: %.2f\n", this.avgReceiverRegret);
				System.out.format("Average total regret: %.2f\n\n", this.avgTotalRegret);
			}
			
			else {
				System.out.println("Stable matching? No");
				System.out.format("Average suitor regret: %.2f\n", this.avgSuitorRegret);
				System.out.format("Average receiver regret: %.2f\n", this.avgReceiverRegret);
				System.out.format("Average total regret: %.2f\n\n", this.avgTotalRegret);
			}
	 }
	// print stats as row
	 public void printStatsRow ( String rowHeading ) {
		 
		 calcRegrets ();// recalculate regrets
		 String spaces = String.format("%"+(25-rowHeading.length())+"s", "");
		 System.out.print(rowHeading+ spaces);
		 if (determineStability ()) {// checks if stable
			 System.out.print("Yes");
		 }
		 else {
			 System.out.print(" No");
		 }
		 if(this.suitorFirst) {// make sure that GSH and GSS rows are printed properly
			 System.out.format("                 %.2f                 %.2f                 %.2f", this.avgReceiverRegret, this.avgSuitorRegret, this.avgTotalRegret);
			 String spacesa = String.format("%"+(21-String.valueOf(this.compTime).length())+"s", "");
			 System.out.print(spacesa + this.compTime+"\n");
		 }
		 else {
			 System.out.format("                 %.2f                 %.2f                 %.2f", this.avgSuitorRegret, this.avgReceiverRegret, this.avgTotalRegret);
			 String spacesa = String.format("%"+(21-String.valueOf(this.compTime).length())+"s", "");
			 System.out.print(spacesa + this.compTime+"\n");
		 }
	 }
	
	 // reset everything
	 public void reset () {
		 this.S = new ArrayList < Participant >() ;
		 this.R = new ArrayList < Participant >() ;
		 this.avgSuitorRegret = 0; 
		 this.avgReceiverRegret = 0; 
		 this.avgTotalRegret = 0; 
		 this.matchesExist = false;
		 this.stable = false;
		
		 this.suitorFirst = true; 
		 this.worstSuitorRegret = 0;
	 }
 }