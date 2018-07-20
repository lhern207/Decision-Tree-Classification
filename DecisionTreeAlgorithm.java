/**
 * Implementation of the decision tree classification 
 * algorithm using the information gain algorithm.
 * This version only works with AllElectronic tuples. 
 * However, it can be easily adapted to work with any tuple structure.
 */

/**
 *
 * @author Lester Hernandez Alfonso
 */

package data_mining_project;

import java.util.ArrayList;
import java.util.Collections;

// Class that represents the algorithm used to classify a given tuple
public final class DecisionTreeAlgorithm {
    
    private DecisionTreeAlgorithm(){}
    
    // The general algorithm to grow a decision tree, given a set of training tuples and
    // their class labels (dataPartition), and a list of attributes that can be
    // used for classification purposes.
    public static DecisionTreeNode Generate_decision_tree(ArrayList <AllElectronicsTuple> dataPartition,
                                            ArrayList <String> attributes_list) {
        DecisionTreeNode node;
        
        String common_class = sameClassTest(dataPartition);
        
        if(!common_class.equals("Different Classes")) {
            node = new DecisionTreeNode(common_class);
            return node;
        }
        
        if(attributes_list.isEmpty()) {
            node = new DecisionTreeNode(findMajorityClass(dataPartition));
            return node;
        }
        
        String splitting_attribute = informationGain(dataPartition, attributes_list); 
        node = new DecisionTreeNode(splitting_attribute);
        
        ArrayList<String> new_attributes_list = new ArrayList<>();
        
        for(String attribute: attributes_list)
        {
            if (!attribute.equals(splitting_attribute)) {
                new_attributes_list.add(attribute);
            }
        }
        
        // Generate the list of the possible childs and a new partition
        ArrayList <String> outcomes = AllElectronicsTuple.listAttributeRange(splitting_attribute);
        ArrayList <AllElectronicsTuple> newPartition;
        
        for(String outcome: outcomes) {
            newPartition = findNewPartition(dataPartition, splitting_attribute, outcome);
            if (newPartition.isEmpty()) {
                node.addChild(findMajorityClass(dataPartition));
            }
            else {
                node.attachChild(Generate_decision_tree(newPartition, new_attributes_list));
            }
        }
        
        return node;        
    }
    
    // Function that, given a tuple and a decision tree, will classify the tuple
    public static String Classify_tuple(AllElectronicsTuple tuple, DecisionTreeNode root) {
        ArrayList <DecisionTreeNode> children = root.getChildren();
        //If a leaf is found, then return its value.
        if(children.isEmpty()) {
            return root.getData().toString();
        }
        
        String classification_attribute = root.getData().toString();
        String attribute_value = tuple.get_Attribute(classification_attribute);
        ArrayList <String> outcomes = AllElectronicsTuple.listAttributeRange(classification_attribute);
       
       /*In Generate_Decision_Tree(), children are generated in the order of the
       attribute range (set of outcomes for an attribute). Therefore, a child
       in the children list of a node will have the same index as the
       corresponding outcome that leads to it in outcomes list. Just a trick so
       we do not have to define a branch data structure*/
       int which_child = 0;
       for(int i = 0; i < outcomes.size(); i++) {
           if(attribute_value.equals(outcomes.get(i))) {
               which_child = i;
               break;
           }
       }
       
       DecisionTreeNode next_node = children.get(which_child);
       String classification = Classify_tuple(tuple, next_node);
       
       return classification;   
    }
    
    //Returns a class label if all tuples in a partition belong to the same class.
    private static String sameClassTest(ArrayList <AllElectronicsTuple> dataPartition) {
        String testClass;
        testClass = dataPartition.get(0).getClass_label();
        for (AllElectronicsTuple tuple: dataPartition) {
            if (!tuple.getClass_label().equals(testClass)) {
                return "Different Classes";
            }
        }
        return testClass;
    }
    
    //Only works with AllElectronicsTuple. Could be modified to work with any tuple.
    private static String findMajorityClass(ArrayList <AllElectronicsTuple> dataPartition) {
        ArrayList <Integer> class_counts = classCount(dataPartition);
        
        if(class_counts.get(0) >= class_counts.get(1)){
            return "yes";
        }
        else {
            return "no";
        }     
    }
    
    //Provides an overall count of how many tuples belong to each class.
    private static ArrayList<Integer> classCount(ArrayList <AllElectronicsTuple> dataPartition) {
        String class1 = "yes";
        String current_class;
        int class1_counter = 0;
        int class2_counter = 0; //For class "no"
        
      
        for (AllElectronicsTuple tuple: dataPartition) {       
            current_class = tuple.getClass_label(); 
            if(current_class.equals(class1)) {
                class1_counter++;
            }
            else {
                class2_counter++;
            }
        }
        
        ArrayList <Integer> class_counts = new ArrayList<>();
        class_counts.add(class1_counter);
        class_counts.add(class2_counter);
        
        return class_counts;
    }
    
    //Implementation of information gain algorithm only for AllElectronicsTuple. 
    //A more general implementation can be added later.
    private static String informationGain(ArrayList <AllElectronicsTuple> dataPartition,
                                          ArrayList <String> attributes_list) {
        
        //InfoAs stands for a collection including InfoA of each attribute, which
        //justifies the 's' in 'InfoAs'.
        ArrayList <Double> InfoAs = new ArrayList <>();
        ArrayList <Double> Gains = new ArrayList <>();
        Double InfoD = InfoPartition(dataPartition);
        
        for(String attribute: attributes_list){
            InfoAs.add(InfoAttribute(dataPartition, attribute));
        }
        
        for(Double InfoA: InfoAs){
            Gains.add(InfoD - InfoA);
        }
        
        double maxValue = Collections.max(Gains);
        String splitting_attribute = attributes_list.get(Gains.indexOf(maxValue));
        
        return splitting_attribute;
    }
    
    //Find info Info(D) for a provided partition.
    private static double InfoPartition(ArrayList <AllElectronicsTuple> dataPartition){
        double InfoD;
        ArrayList <Integer> class_counts = classCount(dataPartition);
        int c1_count = class_counts.get(0);
        int c2_count = class_counts.get(1);
        
        double class1_probability = ((double)c1_count)/(c1_count + c2_count);
        double class2_probability = ((double)c2_count)/(c1_count + c2_count);
        if(c1_count == 0 && c2_count == 0) {
            InfoD = 0;
        }
        else if (c1_count == 0){
            InfoD = 0 - (class2_probability * logbase2(class2_probability));
        }
        else if (c2_count == 0) {
            InfoD = 0 - (class1_probability * logbase2(class1_probability));
        }
        else {
            InfoD = 0 - (class1_probability * logbase2(class1_probability)) - 
               (class2_probability * logbase2(class2_probability));
        }
        
        return InfoD;
        
    }
    
    //Find InfoA(D) for a provided partition and attribute.
    private static double InfoAttribute(ArrayList <AllElectronicsTuple> dataPartition,
                                        String attribute) {
        double InfoA = 0;
        ArrayList <String> outcomes = AllElectronicsTuple.listAttributeRange(attribute);
        ArrayList <AllElectronicsTuple> newPartition;
        for(String outcome: outcomes){
            newPartition = findNewPartition(dataPartition, attribute, outcome);
            InfoA += ((newPartition.size()/(double)dataPartition.size()) * InfoPartition(newPartition));
        }
        
        return InfoA;
        
    }
    
    //Finds the new partition to be used in the recursion.
    private static ArrayList<AllElectronicsTuple> findNewPartition(
                                  ArrayList <AllElectronicsTuple> dataPartition,
                                  String attribute, String outcome) {
        
        ArrayList<AllElectronicsTuple> newPartition = new ArrayList<>();
        
        for (AllElectronicsTuple tuple: dataPartition) {
            if(tuple.get_Attribute(attribute).equals(outcome)){
                newPartition.add(tuple);
            }
        }
        
        return newPartition;
    }
    
    //Helper function
    private static double logbase2(double argument) {
        //Change of base formula.
        return (Math.log(argument)/Math.log(2.0));
    }
}
