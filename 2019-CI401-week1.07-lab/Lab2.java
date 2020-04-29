// The code below prints out a 2 dimensional array - a matrix
// (Notice how we can initialise the aray using a list 
// of lists of values)
//
// Modify the code to print out the array in 'transposed' form,
// with the columns and rows flipped like this:
//      1 4
//      2 5
//      3 6 
// HINT: you need to swap over the loops so the i loop is inside the j loop
//
// ADVANCED: if you know about matrix multiplication, write a method that takes
// two square arrays (the same size eg 2x2) and multiplies them together, returning 
// a new array (which is the same size)
// ADVANCED2: The slides included an example of an array based on Pascal's triangle
// Write a method which takes a number of rows and creates a Pascal's triangle array
// with that many rows in it. (NB: go and look up Pascal's triangle to find out
// how each row is calcuated)
public class Lab2
{
    public static void main( String args[] ) {
        new Lab2().run();
    }
    
    public void run() {
        
        int[][] matrix = { {1, 2, 3},{4, 5,6} };
        
        for (int i=0; i<2; i++) {
            for (int j=0; j<3; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }    
}
