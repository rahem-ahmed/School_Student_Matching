import java.io.*;
import java.util.ArrayList;

public class School_Student_Matching {
		public static void main(String[] args) throws IOException {
			ArrayList < Student > Loaded_students = new ArrayList < Student >();
			ArrayList < School > loaded_schools = new ArrayList < School >();
			ArrayList < School > H2 = new ArrayList < School >();
			ArrayList < Student > S2 = new ArrayList < Student >();
			SMPSolver Matcher = new SMPSolver(); 
			SMPSolver GSH = new SMPSolver();
			int num = 0;
			@SuppressWarnings("unused")
			int numberOfLoadedStudents = 0;
			int numberOfLoadedSchools = 0;
			
			do {																									//do while loop to keep the menu going until user decides to quit
				
				displayMenu();																						//calls function that  displays the main menu
				String Choice = BasicFunctions.getString();															// get choice from user for menu choice
				System.out.println();
				
				if (Choice.equals("q") | Choice.equals("Q")) {														//if user chooses to quit the loop ends
					num++;
					System.out.println("Hasta luego!");
				}
				else if(Choice.equals("l") | Choice.equals("L")) {													//choice for user to load schools and students from file
					int oldschools = numberOfLoadedSchools;
					numberOfLoadedSchools += loadSchools(loaded_schools); //load schools from file provided by user
					if (oldschools != numberOfLoadedSchools) {				//if more schools are added then students are removed
						Loaded_students = new ArrayList < Student >();
						
					}
					numberOfLoadedStudents += loadStudents(Loaded_students, loaded_schools); //load students from file provided by user
					
					  H2 = copySchools ( loaded_schools ) ;
					  S2 = copyStudents ( Loaded_students ) ;
				}
				else if(Choice.equals("e") | Choice.equals("E")) {													//choice for editing data
					editData(Loaded_students, loaded_schools); //opens sub menu where user can choose to edit schools or students
					copyEdits(Loaded_students, loaded_schools, S2, H2);//function that copies all edits for GSH
				}
				else if(Choice.equals("p") | Choice.equals("P")) {													//choice for printing schools and students loaded in 
					printStudents(Loaded_students, loaded_schools);
					printSchools(Loaded_students, loaded_schools);
				}
				else if(Choice.equals("m") | Choice.equals("M")) {													//choice where matching is done using the gale shapley algo							
					Matcher.setParticipants(Loaded_students, loaded_schools); //SMPsolver is updeted with current arraylists
					GSH.setParticipants(H2, S2);
					System.out.println("STUDENT-OPTIMAL MATCHING\n");
					if (Matcher.matchingCanProceed()) { // checks if matching is possible
						 
						
						Matcher.match(); // this method employs the algo to do maatching
						 
						
					}
					System.out.println("SCHOOL-OPTIMAL MATCHING\n");
					if (GSH.matchingCanProceed()) { // checks if matching is possible
						 
						GSH.setSuitorFirst(false);//So it print appropriately
						
						GSH.match(); // this method employs the algo to do maatching for school optimal
						
						
					}
				}
				else if(Choice.equals("d") | Choice.equals("D")) {													//choice that displays matching stats and matches							 
					System.out.println("STUDENT-OPTIMAL SOLUTION\n");
					Matcher.print(); // this method prints stats and matches
					System.out.println("SCHOOL-OPTIMAL SOLUTION\n");
					GSH.print();
				}
				else if(Choice.equals("x") | Choice.equals("X")) {//choice that displays comparison between school optimal and school optimal
					printComparison(Matcher, GSH);// function for printing the comparison table
				}
				else if(Choice.equals("r") | Choice.equals("R")) {													//choice to reset the database
					Loaded_students = new ArrayList < Student >();
					loaded_schools = new ArrayList < School >();
					Matcher = new SMPSolver(); 
					GSH = new SMPSolver();
					num = 0;
					numberOfLoadedStudents = 0;
					numberOfLoadedSchools = 0;
					System.out.println("Database cleared!\n");
				}
				else {																								
					System.out.println("ERROR: Invalid menu choice!\n");
				}
				
			}while(num == 0);																						
		}

	//This function prints the comparison table between school and student optimal matching	
	public static void printComparison(SMPSolver GSS, SMPSolver GSH) {
		
		if( GSS.matchesExist()) {
			System.out.print("Solution              Stable    Avg school regret   Avg student regret     Avg total regret       Comp time (ms)\n"
					+ "----------------------------------------------------------------------------------------------------------------\n");
			GSS.printStatsRow("Student optimal");//print the row for student optimal
			GSH.printStatsRow("School optimal");//print the row for school optimal
			System.out.print("----------------------------------------------------------------------------------------------------------------\n");
			System.out.print("WINNER");
			if((GSS.determineStability() & GSH.determineStability()) | (!GSS.determineStability() & !GSH.determineStability())) { 			//various if statements that print the appropriate statement
				System.out.print("                   Tie");
			}
			else if(!GSS.determineStability() & GSH.determineStability()) {
				System.out.print("            School-opt");
			}
			else if(GSS.determineStability() & !GSH.determineStability()) {
				System.out.print("           Student-opt");
			}
			if(GSS.getAvgReceiverRegret() == GSH.getAvgSuitorRegret()) {
				System.out.print("                  Tie");
			}
			else if(GSS.getAvgReceiverRegret() > GSH.getAvgSuitorRegret()) {
				System.out.print("           School-opt");
			}
			else if(GSS.getAvgReceiverRegret() < GSH.getAvgSuitorRegret()) {
				System.out.print("          Student-opt");
			}
			if(GSS.getAvgSuitorRegret() == GSH.getAvgReceiverRegret()) {
				System.out.print("                  Tie");
			}
			else if(GSS.getAvgSuitorRegret() > GSH.getAvgReceiverRegret()) {
				System.out.print("           School-opt");
			}
			else if(GSS.getAvgSuitorRegret() < GSH.getAvgReceiverRegret()) {
				System.out.print("          Student-opt");
			}
			if(GSS.getAvgTotalRegret() == GSH.getAvgTotalRegret()) {
				System.out.print("                  Tie");
			}
			else if(GSS.getAvgTotalRegret() > GSH.getAvgTotalRegret()) {
				System.out.print("           School-opt");
			}
			else if(GSS.getAvgTotalRegret() < GSH.getAvgTotalRegret()) {
				System.out.print("          Student-opt");
			}
			if(GSS.getTime() == GSH.getTime()) {
				System.out.print("                  Tie");
			}
			else if(GSS.getTime() > GSH.getTime()) {
				System.out.print("           School-opt");
			}
			else if(GSS.getTime() < GSH.getTime()) {
				System.out.print("          Student-opt");
			}
			
			System.out.println("\n----------------------------------------------------------------------------------------------------------------\n");
			
		}
		else {
			System.out.println("ERROR: No matches exist!\n");
		}
	}
	//this function copeis all edits made to students and schoools to the other arraylists that are used for GSH
	public static void copyEdits(ArrayList<Student> S, ArrayList<School> H, ArrayList<Student> s2, ArrayList<School> h2) {
			
		for (int i =0; i < S.size(); i++) {//student edits
				s2.get(i).setName(S. get ( i ) . getName () );
				s2.get(i).setGPA(S. get ( i ) . getGPA ()) ;
				s2.get(i).setES(S. get ( i ) . getES () );
				s2.get(i).setMaxMatches(S. get ( i ) . getMaxMatches ()) ;
				int nSchools = S. get ( i ) . getNParticipants () ;
				for (int j = 0; j < nSchools ; j ++) {
					s2.get(i).setRanking (j, S.get(i).findRankingByID(j));
				}
				
			}
			for (int i =0; i < H.size(); i++) {//school edits
				h2.get(i).setName(H. get ( i ) . getName ()) ;
				h2.get(i).setAlpha(H. get ( i ) . getAlpha ()) ;
				h2.get(i).setMaxMatches(H. get ( i ) . getMaxMatches ()) ;
				int nStudents = H. get ( i ) . getNParticipants () ;
				for (int j = 0; j < nStudents ; j ++) {
					h2.get(i). setRanking (j, H.get(i).findRankingByID(j));
				}
			}
			
		}

	//this function prints out the main menu
	public static void displayMenu() {
		
		System.out.print("JAVA STABLE MARRIAGE PROBLEM v3\n"
				+ "\n"
				+ "L - Load students and schools from file\n"
				+ "E - Edit students and schools\n"
				+ "P - Print students and schools\n"
				+ "M - Match students and schools using Gale-Shapley algorithm\n"
				+ "D - Display matches\n"
				+ "X - Compare student-optimal and school-optimal matches\n"
				+ "R - Reset database\n"
				+ "Q - Quit\n"
				+ "\n"
				+ "Enter choice: ");
	}

	//this function asks the user for a filename and then loads valid schools from the file provided
	public static int loadSchools(ArrayList<School> H) throws IOException  {
		
		String line;
		int num = 0;
		int SchoolsAdded = 0;
		int LinesInFile = 0;
		do {
			System.out.print("Enter school file name (0 to cancel): ");
			String Filename = BasicFunctions.getString();
			if (Filename.equals("0")) {
				System.out.println("\nFile loading process canceled.\n");
				num++;
			}
			else{
			
				try {
					BufferedReader fin = new BufferedReader (new FileReader(Filename));
					do {
						line = fin.readLine();
						if (line != null) {
							String[] splitstring = line.split(",");
							double alpha = Double.parseDouble(splitstring[1]);
							int openings = Integer.parseInt(splitstring[2]);
							School skule = new School(splitstring[0], alpha, openings,  0);
							if(skule.isValid()) { //uses isValid method to confirm a school is goodd to addd to the array list
								H.add(skule);
								SchoolsAdded++;
								LinesInFile++;
							}
							else {
								LinesInFile++;
							}
						}
					}while(line != null);
					fin.close();
					System.out.println("\n" + SchoolsAdded + " of "+ LinesInFile +" schools loaded!\n");
					num++;
				} catch (FileNotFoundException e) {
				
					System.out.println("\nERROR: File not found!\n");
				}
			
			}
		}while(num==0);
		
		
		return SchoolsAdded;
	}

	//this function asks the user for a filename and then loads valid students from the file
	public static int loadStudents(ArrayList<Student> S, ArrayList<School> H) throws IOException {
		 
		String line;
		int num = 0;
		int StudentsAdded = 0;
		int LinesInFile = 0;
		do {
			System.out.print("Enter student file name (0 to cancel): ");
			String Filename = BasicFunctions.getString();
			if (Filename.equals("0")) {
				System.out.println("\nFile loading process canceled.\n");
				num++;
			}
			else{
			
				try {
					BufferedReader fin = new BufferedReader (new FileReader(Filename));
					do {
						line = fin.readLine();
						if (line != null) {
							String[] splitstring = line.split(",");
							if (splitstring.length == (H.size()+3)) {
								double gpa = Double.parseDouble(splitstring[1]);
								int extracirc = Integer.parseInt(splitstring[2]);
								Student stud = new Student(splitstring[0], gpa, extracirc, H.size());
								stud.setNParticipants(H.size());
								for(int i = 0; i < H.size(); i++) {
									if (Integer.parseInt(splitstring[i+3]) <= 0) {
										stud.setRanking(i, 0);
									}
									else{
										stud.setRanking(Integer.parseInt(splitstring[i+3])-1, i+1);
									}
								}
								if (stud.isValid()) { //uses isValid method to confirm a student is goodd to addd to the array list
									S.add(stud);
									StudentsAdded++;
									LinesInFile++;
								}
								else {
									LinesInFile++;
								}
							}
							else {
								LinesInFile++;
							}
						}
					}while(line != null);
					fin.close();
					System.out.println("\n" + StudentsAdded + " of "+ LinesInFile +" students loaded!\n");
					num++;;
				} catch (FileNotFoundException e) {
				
					System.out.println("\nERROR: File not found!\n");
				}
			
			}
		}while(num==0);
		
		if (S.size() != 0) {									//if rankings have been assigned then when the user is done editing the rankings of students for each school are recalculated
			for(int i = 0; i < H.size(); i++) {
				H.get(i).calcRankings(S);
			}
		}
		return StudentsAdded;
	}
	//this function displays a sub menu where user can choose to either edit students, edit schools or quit to main menu
	public static void editData(ArrayList<Student> S, ArrayList<School> H) throws IOException {
		
		int num = 0;
		do {
			System.out.print("Edit data\n"
					+ "---------\n"
					+ "S - Edit students\n"
					+ "H - Edit high schools\n"
					+ "Q - Quit\n"
					+ "\n"
					+ "Enter choice: ");
			String Choice = BasicFunctions.getString();
			
			if (Choice.equals("q") | Choice.equals("Q")) {
				num++;
				System.out.println();
			}
			else if (Choice.equals("s") | Choice.equals("S")) {
				if (S.size() == 0) {
					System.out.println("\nERROR: No students are loaded!\n");
				}
				else {
					editStudents(S, H);	//if user chooses to edit students and there are students loaded then the edit student function is called
				}
			}
			else if (Choice.equals("h") | Choice.equals("H")) {
				if(H.size() == 0) {
					System.out.println("\nERROR: No schools are loaded!\n");
				}
				else {
					editSchools(S, H);	//if user chooses to edit schools and there are students loaded then the edit school function is called
				}
			}
			else {
				System.out.println("\nERROR: Invalid menu choice!\n");
			}
		}while (num == 0);
	}

	//this function displays all the students then asks the user which student they would like to edit. it then calls a method that edits info of the student
	public static void editStudents(ArrayList<Student> S, ArrayList<School> H) throws IOException {
		
		boolean Rankingset = true;
		if (H.size()==0) {
			Rankingset = false;
		}
		int num = 0;
		do {
			System.out.println("\n"
					+" #   Name                                         GPA  ES  Assigned school                         Preferred school order\n"
					+ "---------------------------------------------------------------------------------------------------------------------------");
			for (int i = 0; i < S.size(); i++) {
				if ((i+1)<10) {
					System.out.print("  "+(i+1) +". ");
				    S.get(i).print(H);		//uses the print method of the student class to print student appropriately
				}
				else if((i+1)>=10 &(i+1)<100){
					System.out.print(" "+(i+1) +". ");
				    S.get(i).print(H);
				}
				else {
					System.out.print((i+1) +". ");
				    S.get(i).print(H);
				}
			}
			System.out.println("---------------------------------------------------------------------------------------------------------------------------");
			int studentId = BasicFunctions.getInteger("Enter student (0 to quit): ", 0, S.size());
			System.out.println();
			if (studentId == 0) {	//if user wants to quit they type in 0
				num++;
			}
			else {
				S.get(studentId-1).editInfo(H, Rankingset);		//method of student class that edits the info
			}
		}while(num == 0);
											//if rankings have been assigned then when the user is done editing the rankings of students for each school are recalculated
		for(int i = 0; i < H.size(); i++) {
			H.get(i).calcRankings(S);
			
		}
	}

	//this function displays all the schools then asks the user which school they would like to edit. it then calls a method that edits info of the school
	public static void editSchools(ArrayList<Student> S, ArrayList<School> H) throws IOException {
		
		boolean Rankingset = true;
		if (S.size()==0) {
			Rankingset = false;
		}
		int num = 0;
		do {
			System.out.println("\n"
					+ " #   Name                                     # spots  Weight  Assigned students                       Preferred student order\n"
					+ "------------------------------------------------------------------------------------------------------------------------------");
			for (int i = 0; i < H.size(); i++) {
				if ((i+1)<10) {
					System.out.print("  "+(i+1) +". ");
					H.get(i).print(S);	//uses the print method of the school class to print student appropriately
				}
				else if((i+1)>=10 &(i+1)<100) {
					System.out.print(" "+(i+1) +". ");
					H.get(i).print(S);
				}
				else {
					System.out.print((i+1) +". ");
					H.get(i).print(S);
				}
			}
			System.out.println("------------------------------------------------------------------------------------------------------------------------------");
			int schoolId = BasicFunctions.getInteger("Enter school (0 to quit): ", 0, H.size());
			System.out.println();
			if (schoolId == 0) {	//if user wants to quit they type in 0
				num++;
			}
			else {
				H.get(schoolId-1).editSchoolInfo(S, Rankingset);	//method of school class that edits the info
			}
		}while(num == 0);
	}

	//this function prints out all the students that are loaded
	public static void printStudents(ArrayList<Student> S, ArrayList<School> H) {
		
		if (S.size() == 0) {	//there must be students loaded 
			System.out.println("ERROR: No students are loaded!\n");
		}
		else {
			System.out.println("STUDENTS:\n"
					+ "\n"
					+ " #   Name                                         GPA  ES  Assigned school                         Preferred school order\n"
					+ "---------------------------------------------------------------------------------------------------------------------------");
			for (int i = 0; i < S.size(); i++) {
				if ((i+1)<10) {
					System.out.print("  "+(i+1) +". ");
				    S.get(i).print(H);		//uses the print method of the student class to print student appropriately
				}
				else if((i+1)>=10 &(i+1)<100){
					System.out.print(" "+(i+1) +". ");
				    S.get(i).print(H);
				}
				else {
					System.out.print((i+1) +". ");
				    S.get(i).print(H);
				}
			}
			System.out.println("---------------------------------------------------------------------------------------------------------------------------\n");
		}
	}
	
	//this function prints out all the schools that are loaded
	public static void printSchools(ArrayList<Student> S, ArrayList<School> H) {
		
		if (H.size() == 0) {	//there must be schools loaded
			System.out.println("ERROR: No schools are loaded!\n");
		}
		else {
			System.out.println("SCHOOLS:\n"
					+ "\n"
					+ " #   Name                                     # spots  Weight  Assigned students                       Preferred student order\n"
					+ "------------------------------------------------------------------------------------------------------------------------------");
			for (int i = 0; i < H.size(); i++) {
				if ((i+1)<10) {
					System.out.print("  "+(i+1) +". ");
					H.get(i).print(S);	//uses the print method of the school class to print student appropriately
				}
				else if((i+1)>=10 &(i+1)<100) {
					System.out.print(" "+(i+1) +". ");
					H.get(i).print(S);
				}
				else {
					System.out.print((i+1) +". ");
					H.get(i).print(S);
				}
			}
			System.out.println("------------------------------------------------------------------------------------------------------------------------------\n");
		}
	}
	
	//this function copies all the schools that are added to a second arraylist that is used for GSH
	public static ArrayList < School > copySchools ( ArrayList < School > P ) {
		  ArrayList < School > newList = new ArrayList < School >() ;
		  for (int i = 0; i < P . size () ; i ++) {
			  String name = P . get ( i ) . getName () ;
			  double alpha = P . get ( i ) . getAlpha () ;
			  int maxMatches = P . get ( i ) . getMaxMatches () ;
			  int nStudents = P . get ( i ) . getNParticipants () ;
			  School temp = new School ( name , alpha , maxMatches , nStudents ) ;
			  for (int j = 0; j < nStudents ; j ++) {
				  temp.setRanking(j, P.get(i).findRankingByID(j));
			  }
			  newList . add ( temp ) ;
		  }
		  return newList ;
		  }
	//this function copies all the students that are added to a second arraylist that is used for GSH
	public static ArrayList < Student > copyStudents ( ArrayList < Student > P ) {
		  ArrayList < Student > newList = new ArrayList < Student >() ;
		  for (int i = 0; i < P . size () ; i ++) {
			  String name = P . get ( i ) . getName () ;
			  double GPA = P . get ( i ) . getGPA () ;
			  int ES  = P . get ( i ) . getES () ;
			  int nSchools = P . get ( i ) . getNParticipants () ;
			  Student temp = new Student ( name , GPA, ES , nSchools ) ;
			  for (int j = 0; j < nSchools ; j ++) {
				  temp.setRanking(j, P.get(i).findRankingByID(j));
			  }
			  newList . add ( temp ) ;
		  }
		  return newList ;
		  }



	}


