
// This class manages what is shown on the screen and input from the user
// TIHIS CODE IS A LITTLE MORE ADVANCED - DON@T WORRY IF YOU DON'T UNDERSTAND IT PERFECTLY

import java.util.Scanner;
public class GameInterface
{
    Scanner inputScanner;
    Maze maze;
    public String lastPrompt = null;

    public GameInterface(Maze theMaze) {
        maze = theMaze;
        inputScanner = new Scanner(System.in);
    } 

    // Display the current state of the maze
    // This method gets called repeatedly to show the state of the maze (every half second). 
    // it uses the data in the maze object to draw a picture of the maze.
    // Each maze is drawn 'over the top of' the previous one, so you get the effect of
    // (slow) movement.
    public void displayMaze(String message, String prompt) {

        // a little bit of magic to clear the BlueJ termian window
        char CLEAR = '\u000C';
        System.out.print(CLEAR);

        // draw the maze array
        int height = maze.height;
        int width = maze.width;
        // for each row of cells in the grid
        for (int row=0; row<height; row = row+1) {
            // for each line of the 5-line output for each cell
            for (int line=0; line<5; line = line+1) {
                // for each column of cells in the grid
                for (int col=0; col<width; col=col+1) {
                    // display the string corresponding to that particular row/column/line
                    displayRoom(col,row,line);
                }
                // print newlines except for unprinted rows
                if (row==0 || line>0) System.out.println("");
            }
        }
        if (message != null) {
            System.out.println(message);
        }
        if (prompt != null) {
            System.out.print(prompt);
        }
        lastPrompt = prompt;
    }

    // Display one line of the representation of a particular cell
    // (What you display depends on which line it is)
    // This gets called for all five lines of each cell, but it
    // does not print the first line, except at for the first row
    // (because in other rows the first line is the same as the
    // last line of the previous row).
    public void displayRoom(int col, int row, int line) {
        Room r = maze.getRoom(col,row);
        if (r == null) {
            // no Room object in this cell, just print bricks
            // (but not the first line except on the top row)
            if ( row == 0 || line > 0 ) {
                System.out.print("::::::::::");
            }
        } else {
            switch (line) {
                // first line - only on top row
                case 0:
                if (row == 0) { 
                    System.out.print("::::::::::"); 
                }
                break;
                // second and fourth lines - just side walls
                case 1:
                case 3:
                System.out.print(":        :");
                break;
                // third line - spaces or walls, depending on whether a path is possible
                // and in between, a description of the contents provided by the room object
                case 2: 
                System.out.print((maze.possible(col, row,"left")?" ":":") + r.contents() + (maze.possible(col, row,"right")?" ":":"));
                break;
                // fifth line - bricks and a possible space along the bottom
                case 4:
                System.out.print("::::" + (maze.possible(col, row,"down")?"  ":"::") + "::::");
                break;
            } 
        }
    }

    // update the display when the game finishes
    public void finish() {
        System.out.println();
        System.out.println("Goodbye!");
        inputScanner.close();
    }

    // Check whether the user has typed anything.  If not, return false so that other game 
    // objects have a chance to update.
    // (NB: includes some system magic to cope with 'Exceptions' - don't worry about this for now)
    public boolean inputAvailable() {
        try {
            // has the user typed anything?
            if (System.in.available() > 0) {
                return true;
            } 
        } catch (Exception e) {}

        // nothing typed - the game (other objects etc) will update anyway and redisplay 
        // the maze. But if we printed a prompt we need to print a newline, just to tidy up
        if (lastPrompt != null) {
            System.out.println();
        }
        return false;
    }
    
    // get an input command from the keyboard
    public String nextCommand() {
        String command = inputScanner.next(); // read a token
        inputScanner.nextLine();              // read the rest of the line (and ignore it)
        return command;
    }    
        
}
