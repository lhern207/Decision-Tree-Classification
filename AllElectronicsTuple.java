/**
 * Defines the structure of a tuple in the AllElectronics relation.
 */
package data_mining_project;
import java.util.ArrayList;

/**
 *
 * @author Lester Hernandez Alfonso
 */

// Class that represents the tuples used for AllElectronics classification
public class AllElectronicsTuple {
    private static int currentID = 0;
    private String RID;
    private String age;
    private String income;
    private String student;
    private String credit_rating;
    private String buys_computer;

    // Constructor
    AllElectronicsTuple(String age, String income, String student,
            String credit_rating, String buys_computer) throws Exception {
        
        if (validTuple(age, income, student, credit_rating, buys_computer)) {
            this.RID = String.valueOf(currentID);
            currentID++;
            this.age = age;
            this.income = income;
            this.student = student;
            this.credit_rating = credit_rating;
            this.buys_computer = buys_computer;
        }
        else {
            throw new Exception("invalid argument value");
        }

    }

    // Function that checks that the tuple has been correctly constructed
    private boolean validTuple(String age, String income, String student,
            String credit_rating, String class_label) {
        return (validAge(age) && validIncome(income)
                && validStudent(student) && validCredit_Rating(credit_rating)
                && validClass_Label(class_label));
    }

    private boolean validAge(String age) {
        return (age.equals("youth") || age.equals("middle_aged")
                || age.equals("senior"));
    }

    private boolean validIncome(String income) {
        return (income.equals("low") || income.equals("medium")
                || income.equals("high"));
    }

    private boolean validStudent(String student) {
        return (student.equals("yes") || student.equals("no"));
    }

    private boolean validCredit_Rating(String credit_rating) {
        return (credit_rating.equals("fair")
                || credit_rating.equals("excellent"));
    }

    private boolean validClass_Label(String buys_computer) {
        return (buys_computer.equals("yes")
                || buys_computer.equals("no")
                || buys_computer.equals("unknown"));
    }
    
    public String getRID() {
        return RID;
    }

    public String getAge() {
        return age;
    }

    public String getIncome() {
        return income;
    }

    public String getStudent() {
        return student;
    }

    public String getCredit_rating() {
        return credit_rating;
    }

    public String getClass_label() {
        return buys_computer;
    }
    
    // A general way of retrieving a variable attribute's value
    // Useful when determining partition sizes and compositions or when we don't know what attribute we will be retrieving the value of
    public String get_Attribute(String attribute) {
        if(attribute.equals("RID")){
            return getRID();
        }
        if(attribute.equals("age")){
            return getAge();
        }
        if(attribute.equals("income")){
            return getIncome();
        }
        if(attribute.equals("student")){
            return getStudent();
        }
        if(attribute.equals("credit_rating")){
            return getCredit_rating();
        }
        if(attribute.equals("buys_computer") || attribute.equals("class_label")){
            return getClass_label(); 
        }
        return "Invalid Attribute";
    }
    
    // List classification attributes' names
    public static ArrayList<String> listDecisionAttributes() {
        ArrayList<String> attributes = new ArrayList<String>();
        attributes.add("age");
        attributes.add("income");
        attributes.add("student");
        attributes.add("credit_rating");
        
        return attributes;
    }
   
    // For a given attribute, list all possible values it can have
    public static ArrayList <String> listAttributeRange (String attribute) {
        ArrayList <String> values = new ArrayList<String>();
        
        if(attribute.equals("age")) {
            values.add("youth");
            values.add("middle_aged");
            values.add("senior");
        }
        if(attribute.equals("income")) {
            values.add("low");
            values.add("medium");
            values.add("high");
        }
        if(attribute.equals("student")) {
            values.add("yes");
            values.add("no");
        }
        if(attribute.equals("credit_rating")) {
            values.add("fair");
            values.add("excellent");
        }
        if(attribute.equals("buys_computer") || attribute.equals("class_label")) {
            values.add("yes");
            values.add("no");
            values.add("unknown");
        }
        
        return values;      
    }
    
}
