// Here's a class with a run method that calls 8 exercise methods.
// At the moment they all do nothing. but there's an instruction
// abaove each one. In each case, write the code to do what the 
// comment says
import java.util.ArrayList;
public class Lab3
{
    public static void main( String args[] ) {
        new Lab3().run();
    }
    
    ArrayList<String> names = new ArrayList<>();
    
    
    public void run() {
        names.add("John");
        names.add("Paul");
        names.add("George");
        names.add("Ringo");
        ex1();
        ex2();
        ex3();
        ex4();
        ex5();
        ex6();
        ex7();
        ex8();
    }
    
    // print the first element of the array names
    public void ex1() {
    }
    
    // print the last element of the array names
    public void ex2() {
    }
    
    // print the third element of vector
    public void ex3() {
    }
    
    // print the last element of vector (in a way that
    // still works if you change the length of vector)
    public void ex4() {
    }
    
    // insert your own name as the third element
    public void ex5() {
    }
    
    // remove "Paul"
    public void ex6() {
    }
    
    // Use a for loop to print each elements
    public void ex7() {
    }
    
    // Use a for loop to find the element "George" and remove it
    // NB: you may need to look at the seminar slides on loops!
    public void ex8() {
    }
}
