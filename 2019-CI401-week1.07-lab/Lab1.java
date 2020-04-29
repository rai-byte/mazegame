// Here's a class with a run method that calls 8 exercise methods.
// At the moment they all do nothing. but there's an instruction
// abaove each one. In each case, write the code to do what the 
// comment says

public class Lab1
{
     public static void main( String args[] ) {
        new Lab1().run();
    }
    
    int[] vector = {23, 42, 3, 17, 6};
    
    public void run() {
        ex1();
        ex2();
        ex3();
        ex4();
        ex5();
        ex6();
        ex7();
        ex8();
    }
    
    // print the first element of the array vector
    public void ex1() {
    }
    
    // print the first element of the array vector
    public void ex2() {
    }
    
    // print the third element of vector
    public void ex3() {
    }
    
    // print the last element of vector (in a way that
    // still works if you change the length of vector)
    public void ex4() {
    }
    
    // change the fourth element to be the same as the second
    public void ex5() {
    }
    
    // swap the first and third elements (remember you may need 
    // a temporary variable)
    public void ex6() {
    }
    
    // print the whole vector (using a loop) on separate lines
    public void ex7() {
    }
    
    // print the whole vector on one line, separated by spaces
    public void ex8() {
    }
}
