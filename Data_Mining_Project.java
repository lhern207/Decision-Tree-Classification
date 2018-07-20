/**
 * Main module to test algorithm implementation:
 * 1- Creates a set of training tuples of AllElectronics. 
 * 2- Generates decision tree using training tuples and information gain.
 * 3- Classifies user provided tuples.
 */

/**
 *
 * @author Lester Hernandez Alfonso
 */

package data_mining_project;

import java.util.ArrayList;
import java.util.Scanner;

// Main class of the project
public class Data_Mining_Project {

    // Variable used to read user input
	public static Scanner scanner = new Scanner(System.in);
	
    public static void main(String[] args) {

    	// Array that contains the training tuples as defined in the textbook
        ArrayList<AllElectronicsTuple> trainingTuples  = defineTrainingData();
        
        if(trainingTuples.isEmpty()){
            System.exit(0);
        }
        
        // Array that contains the list of the class attributes 
        ArrayList<String> attributes_list = AllElectronicsTuple.listDecisionAttributes();
        
        //Generating the Tree (Learning Phase)
        DecisionTreeNode root = DecisionTreeAlgorithm.Generate_decision_tree(trainingTuples, attributes_list);
        System.out.println("Decision Tree succesfully generated using training\n"
                + "tuples on page 338 of our textbook.\n");
        
        System.out.println("DFS Tree Traversal Order:");
        root.displayTree();
        System.out.println();
        
        //Classification Phase
        ArrayList<AllElectronicsTuple> testTuples = defineTestData();
        
        if(testTuples.isEmpty()){
            System.exit(0);
        }
        
        // 
        System.out.println("Testing classification of tuple: (age: youth, income: high, "
                + "student: yes, credit_rating: fair, buys_computer: unknown");
        System.out.println("RESULT: buys_computer: " + DecisionTreeAlgorithm.Classify_tuple(testTuples.get(0), root) + "\n");
        
        System.out.println("Testing classification of tuple: (age: youth, income: high, "
                + "student: no, credit_rating: fair, buys_computer: unknown");
        System.out.println("RESULT: buys_computer: " + DecisionTreeAlgorithm.Classify_tuple(testTuples.get(1), root) + "\n");
        
        System.out.println("Testing classification of tuple: (age: middle_aged, income: high, "
                + "student: no, credit_rating: fair, buys_computer: unknown");
        System.out.println("RESULT: buys_computer: " + DecisionTreeAlgorithm.Classify_tuple(testTuples.get(2), root) + "\n");
        
        System.out.println("Testing classification of tuple: (age: senior, income: high, "
                + "student: yes, credit_rating: fair, buys_computer: unknown");
        System.out.println("RESULT: buys_computer: " + DecisionTreeAlgorithm.Classify_tuple(testTuples.get(3), root) + "\n");
        
        System.out.println("Testing classification of tuple: (age: senior, income: high, "
                + "student: yes, credit_rating: excellent, buys_computer: unknown");
        System.out.println("RESULT: buys_computer: " + DecisionTreeAlgorithm.Classify_tuple(testTuples.get(4), root) + "\n\n");
        
        
        // Phase 3 : ask the user a tuple and determine its classification
        System.out.println("User, enter a tuple to determine its classification.");
        
        String age, income, student, credit_rating = "";
        
        // Making sure that the values for the different attributes inputed are acceptable
        do{
        	System.out.println("Age (youth/middle_aged/senior): ");
        	age = scanner.nextLine();
        } while(incorrectUserInput("age",age));
        
        do{
        	System.out.println("Income (low/medium/high): ");
        	income = scanner.nextLine();
        } while(incorrectUserInput("income",income));
        
        do{
        	System.out.println("Student (yes/no): ");
        	student = scanner.nextLine();
        } while(incorrectUserInput("student",student));

        do{
        	System.out.println("credit rating (fair/excellent): ");
        	credit_rating = scanner.nextLine();
        } while(incorrectUserInput("credit_rating",credit_rating));
        
        try {
        	// Creation of the user tuple
			AllElectronicsTuple userTuple = new AllElectronicsTuple(age, income, student, credit_rating, "unknown");
			// Classification and output of the result
	        System.out.println("\nUSER TUPLE RESULT: buys_computer: " + DecisionTreeAlgorithm.Classify_tuple(userTuple, root) + "\n");
		} catch (Exception e) {
			System.out.println("Error: invalid input values.");
			e.printStackTrace();
		}
        
        System.exit(0);
    }
    
    // Function that generates the training tuples
    private static ArrayList<AllElectronicsTuple> defineTrainingData() {
        ArrayList <AllElectronicsTuple> trainingTuples = new ArrayList <>();
               
        try {
        	trainingTuples.add(new AllElectronicsTuple("youth", "high", "no", "fair", "no"));
        	trainingTuples.add(new AllElectronicsTuple("youth", "high", "no", "excellent", "no"));
        	trainingTuples.add(new AllElectronicsTuple("middle_aged", "high", "no", "fair", "yes"));
        	trainingTuples.add(new AllElectronicsTuple("senior", "medium", "no", "fair", "yes"));
        	trainingTuples.add(new AllElectronicsTuple("senior", "low", "yes", "fair", "yes"));    
        	trainingTuples.add(new AllElectronicsTuple("senior", "low", "yes", "excellent", "no"));
        	trainingTuples.add(new AllElectronicsTuple("middle_aged", "low", "yes", "excellent", "yes"));
        	trainingTuples.add(new AllElectronicsTuple("youth", "medium", "no", "fair", "no"));
        	trainingTuples.add(new AllElectronicsTuple("youth", "low", "yes", "fair", "yes"));
        	trainingTuples.add(new AllElectronicsTuple("senior", "medium", "yes", "fair", "yes"));
        	trainingTuples.add(new AllElectronicsTuple("youth", "medium", "yes", "excellent", "yes"));
        	trainingTuples.add(new AllElectronicsTuple("middle_aged", "medium", "no", "excellent", "yes"));
        	trainingTuples.add(new AllElectronicsTuple("middle_aged", "high", "yes", "fair", "yes"));
        	trainingTuples.add(new AllElectronicsTuple("senior", "medium", "no", "excellent", "no"));
        } catch (Exception e){
            System.out.println("Invalid tuple attribute values.\n"
                    + "Check: AllElectronicsTuple.listAttributeRange(String atttribute)...\n "
                    + "...for a list of valid values each attribute can take on\n");
            return trainingTuples;
        }
        
        return trainingTuples;
    }
    
    // Function that generates the test tuples
    private static ArrayList<AllElectronicsTuple> defineTestData() {
        ArrayList <AllElectronicsTuple> testTuples = new ArrayList <>();
              
        try {
        	testTuples.add(new AllElectronicsTuple("youth", "high", "yes", "fair", "unknown"));
        	testTuples.add(new AllElectronicsTuple("youth", "high", "no", "fair", "unknown"));
        	testTuples.add(new AllElectronicsTuple("middle_aged", "high", "no", "fair", "unknown"));
        	testTuples.add(new AllElectronicsTuple("senior", "high", "yes", "fair", "unknown"));
        	testTuples.add(new AllElectronicsTuple("senior", "high", "yes", "excellent", "unknown"));
        } catch(Exception e) {
            System.out.println("Invalid tuple attribute values.\n"
                    + "Check: AllElectronicsTuple.listAttributeRange(String atttribute)...\n "
                    + "...for a list of valid values each attribute can take on\n");
            return testTuples;
        }
        
        return testTuples;
    }
    
    // Function that checks that the user's input are correct
    public static boolean incorrectUserInput(String node, String input){
    	
    	switch(node){
    	case "age":
    		if(!input.equals("youth") && !input.equals("middle_aged") && !input.equals("senior")){
    			System.out.println("Error, value must be either 'youth', 'middle_aged' or 'senior'. Correct your input please.");
    			return true;
    		}
    		return false;
    	case "income":
    		if(!input.equals("low") && !input.equals("medium") && !input.equals("high")){
    			System.out.println("Error, value must be either 'low', 'medium' or 'high'. Correct your input please.");
    			return true;
    		}
    		return false;	
    	case "student":
    		if(!input.equals("yes") && !input.equals("no")){
    			System.out.println("Error, value must be either 'yes' or 'no'. Correct your input please.");
    			return true;
    		}
    		return false;
    	case "credit_rating":
    		if(!input.equals("fair") && !input.equals("excellent")){
    			System.out.println("Error, value must be either 'fair' or 'excellent'. Correct your input please.");
    			return true;
    		}
    		return false;
    	default:
    		return true;
    	}
    }
}
