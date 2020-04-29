import java.util.Random;

// Game2 is a maze solver using the classes GameInterface, Maze, Room and GameObject
// It creates a 4x4 maze in which there is a zombie, marked by Z, and an exit 
// marked by *. This 'game' is not interactive - the zombie just tries to
// find the exit and the game finishes when it does so.
// The strategy implemented here is random search (see the updateZombie method).

public class Game2 extends Thread
{
    // a 'main' method to run this game as a program.
    // It simply creates an instance of the game and calls the 'run' method
    public static void main(String[] args) {
        new Game2().run();
    }

    
    // Instance variables
    public Maze maze;               // The maze grid 
    public GameObject treasure;     // A treasure object
    public GameObject zombie;       // A zombie object
    public boolean gameOver;        // A boolean to tell us when the game is finished
    public GameInterface gameInterface; // The user interface for the game
    public Random generator;        // A random number generator 
    public String message = null;
    public String prompt = null;
    
    // the main method to play the game
    public void run() {
        makeGame();
        gameLoop();
    }

    
    // Make the game. Create the Maze and the GameObjects, and put the GameObjects in 
    // their starting positions on the board.
    public void makeGame() {     
        // create a Maze and then add rooms to it
        // most of the rooms have doorways on all four sides (the default), 
        // one is a horizontal corridor, and one is just wall (null)
        // (NB: the Maze ignores doorways which don't exist in both
        // rooms and doors that are on the edge of the grid).
        maze = new Maze(4,4);
        maze.setRoom(0,0,new Room());
        maze.setRoom(0,1,new Room());
        maze.setRoom(0,2,new Room());
        maze.setRoom(0,3,new Room());
        maze.setRoom(1,0,new Room(false,true));
        maze.setRoom(1,1,new Room());
        maze.setRoom(1,2,new Room());
        maze.setRoom(1,3,new Room());
        maze.setRoom(2,0,new Room());
        maze.setRoom(2,1,new Room());
        maze.setRoom(2,2,null);
        maze.setRoom(2,3,new Room());
        maze.setRoom(3,0,new Room());
        maze.setRoom(3,1,new Room(true,false));
        maze.setRoom(3,2,new Room());
        maze.setRoom(3,3,new Room());

        // create game objects, and tell the Maze 
        // where they are
        zombie = new GameObject("zombie", "Z", 3, 0);
        maze.setContents(zombie);
        
        treasure = new GameObject("treasure", "*", 2, 3);
        maze.setContents(treasure);
        
        // now create an object to manage the user interface
        gameInterface = new GameInterface(maze);
        // set up the random number generator
        generator = new Random();
    }

    
    // The main game loop
    // This just displays the board, and then updates the GameObjects once a second,
    // and keeps doing that until the game is over. The idea is that
    // the only things that cause anything to change in the game are the GameObjects,
    // so by updating them repeatedly, the game gets played.  
    public void gameLoop() {      
        message = "";
        gameInterface.displayMaze(message, prompt);

        gameOver = false;
        while (!gameOver) {
            snooze(500);
            updateTreasure();
            updateZombie();
            checkGameOver();
            gameInterface.displayMaze(message, prompt);
        }
        gameInterface.finish();
    }

    
    // When is the game over? This method checks the conditions for stopping
    // and sets an appropriate message to be displayed.
    public void checkGameOver() {
        if (zombie.col == treasure.col && zombie.row == treasure.row) {
            message = "Zombie found the treasure";
            gameOver = true;
        } 
    }

    
    // Updating the treasure
    // The 'treasure' marks the exit. It doesn't change on update
    public void updateTreasure() {
    }

    
    // Updating the zombie
    // The zombie moves randomly. It uses the random number generator to
    // generate a number between 0 and 3 which it maps onto a direction. It 
    // tries that direction and if it works we're done, otherwise it tries another
    // random number, until one works.   
    String [] directions = {"up", "left", "down", "right"};
    public void updateZombie() {   
        while (true) { // repeat 'forever' - only stops when we manage to move
            int dirIndex = generator.nextInt(4); // generate an int between 0 and 3
            String dir = directions[dirIndex];
            if (maze.possible(zombie,dir)) {
                maze.move(zombie,dir);
                return;
            }
        }    
    }

    // go to sleep for a short period
    // (NB: includes some system magic to cope with 'Exceptions' - don't worry about this for now)
    public void snooze(int millisecs) {
        try {
            sleep(millisecs);
        } catch (Exception e) {}
    }
}
