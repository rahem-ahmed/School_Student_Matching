# School Student Matching

## Overview

This repository contains an implementation of the Stable Marriage Problem (SMP) with the Gale-Shapley algorithm, tailored for a one-to-many matching scenario. Instead of traditional one-to-one pairings, this project matches students to schools where each school has multiple spots, ensuring stability in the matching.

### Key Features
- **Customizable Matching:** Supports various input sizes, from 5 students to 2000, with corresponding schools.
- **Interactive Menu:** Load input files, run the matching algorithm, view/edit student or school data, display results, reset, and exit.
- **Extensible Design:** Built with object-oriented principles to encourage reuse and modification.

---

## File Structure

### Project Directories
- `src/`: Contains all Java source and compiled class files.
- `.bin/`: Directory for compiled binary files (optional, depending on your setup).

### Input Files
The repository includes:
- `studentsX.txt`: Specifies data for `X` students.
- `schoolsX.txt`: Specifies data for schools corresponding to `X` students. The total number of spots across schools equals `X`.

Example file pairs:
- `students5.txt` and `schools5.txt`: 5 students and schools with a total of 5 spots.
- `students2000.txt` and `schools2000.txt`: 2000 students and schools with 2000 spots.

### Core Java Files
- `School_Student_Matching.java`: Main file to run the program.
- `Participant.java`: A generic superclass for `Student` and `School` classes.
- `Student.java`: Represents individual student entities.
- `School.java`: Represents individual school entities.
- `SMPSolver.java`: Contains the Gale-Shapley algorithm for stable matching.

---

## Usage

### Prerequisites
- Ensure you have Java installed on your system.
- Place all necessary `.txt` files (e.g., `studentsX.txt`, `schoolsX.txt`) in the project directory.

### Running the Program
1. Compile all Java files:
   ```bash
   javac src/*.java
   
2. Run the main program:
   ```bash
   java src/School_Student_Matching

## Implementation Details
The Gale-Shapley algorithm is implemented in `SMPSolver.java` to handle one-to-many relationships.
`Participant.java` serves as a generic base class, extended by `Student.java` and `School.java`.
The design is modular, allowing easy extension for additional features.

## Acknowledgments
Special thanks to the creators of the Gale-Shapley algorithm for their foundational work in stable matching theory.
