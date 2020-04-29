import java.util.Random;

// Game3 is a simple game using the classes Room, Maze and GameObject
// It creates a 4x4 grid in which there is a player, marked by X, some treasure 
// marked by *, and a zombie marked by Z. The object of the game is to find a path 
// through the doorways between the rooms to get to the treasure. But, you must avoid
// the zombie, and if you take too long, the treasure will disappear!

public class Game3 extends Thread
{
    // a 'main' method to run this game as a program.
    // It simply creates an instance of the game and calls the 'run' method
    public static void main(String[] args) {
        new Game3().run();
    }
   
    // Instance variables
    public Maze maze;               // The game board grid 
    public GameObject player;       // A player object
    public GameObject treasure;     // A treasure object
    public GameObject zombie;       // A zombie object
    public boolean gameOver;        // A boolean to tell us when the game is finished
    public GameInterface gameInterface; // The user interface for the game
    public String message = null;
    public String prompt = null;
    public Random generator;        // A random number generator    
    
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

        // create a GameObject for the player, and tell the maze 
        // where it is (the "X" is what will show on the grid display)
        player = new GameObject("player", "X", 1, 3);    
        maze.setContents(player);

        // similarly for the treasure
        treasure = new GameObject("treasure", "*", 2, 1);
        maze.setContents(treasure);

        // similarly for the zombie
        zombie = new GameObject("zombie", "Z", 3, 0);
        maze.setContents(zombie);

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
        prompt = "Next move: (u, d, l, r, bye)";
        gameInterface.displayMaze(message, prompt);

        gameOver = false;
        while (!gameOver) {
            snooze(500);
            updatePlayer();
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
        if (player.col == treasure.col && player.row == treasure.row) {
            message = "Well done, you found the treasure!";
            prompt = "";
            gameOver = true;
        } else if (player.col == zombie.col && player.row == zombie.row) {
            message = "Oh dear, the zombie got you!";
            prompt = "";
            gameOver = true;
        } else if (treasure.col == -1) {
            message = "Too late, the treasure has disappeared!";
            prompt = "";
            gameOver = true;
        }
    }

    
    // Updating the player. The player object is controlled by the user, so 
    // this method asks the user for a command and then has a switch command 
    // to try and do the command given
    public void updatePlayer() {
        // check first to see if the player has typed anything in the last loop
        // (if not, we do nothing here, but other game objects will get updated 
        // and so they may move)
        if (gameInterface.inputAvailable()) {
            // clear any previous Maze message
            message = "";

            // Get a command from the interface
            String command = gameInterface.nextCommand();

            switch (command) {
                // try to go up
                case "u": tryMovePlayer("up"); break;
                // try to go down    
                case "d": tryMovePlayer("down"); break;
                // try to go left
                case "l": tryMovePlayer("left"); break;
                //try to go right
                case "r": tryMovePlayer("right"); break;
                // stop the game early
                case "bye": gameOver = true; break;
                // anything else is not recognised - set the message to warn user
                default: message = "Unknown command, try again";
            }
        }
    }

    

    // Try and move the player. We use maze.possible to check if the requested move is 
    // possible from the current player position. If so, we move, otherwise we set the 
    // maze message to let the user know it is not possible
    public void tryMovePlayer(String dir) {
        if (maze.possible(player, dir)) {
            maze.move(player, dir);          
        } else {
            message = "You can't move that way, try again";
        }
    }

    
    // Updating the treasure
    // The treasure lives for a fixed number of goes. Each time it is updated, it's life
    // reduces by one. When it reaches zero, the treasure disappears (we move it off the 
    // board)
    int treasureLife = 100;
    public void updateTreasure() {
        if (treasureLife > 0) {
            treasureLife = treasureLife - 1;
            if (treasureLife == 0) {
                maze.move(treasure, "off");
            }
        }
    }

    
    // Updating the zombie
    // The zombie moves randomly. It uses the random number generator to
    // generate a number between 0 and 3 which it maps onto a direction. It 
    // tries that direction and if it works we're done, otherwise it tries another
    // random number, until one works.
    // 
    // The zombieRate variable slows the zombie down - it doesn't move every update,
    // but only when the zombieCounter reaches the rate (and is then reset to zero).
    // So a rate of 3 makes it move once every three updates
    int zombieRate = 3;
    int zombieCounter = 0;
    String [] directions = {"up", "left", "down", "right"};
    public void updateZombie() {
        zombieCounter = zombieCounter+1;
        if (zombieCounter == zombieRate) {
            zombieCounter = 0;
            while (true) { // repeat 'forever' - only stops when we manage to move
                int dirIndex = generator.nextInt(4); // generate an int between 0 and 3
                String dir = directions[dirIndex];
                if (maze.possible(zombie,dir)) {
                    maze.move(zombie,dir);
                    return;
                }
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