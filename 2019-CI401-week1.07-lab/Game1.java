
// Game1 is a simple game using the classes GameInterface, Maze, Room and GameObject
// It creates a 3x3 maze in which there is a player, marked by X and some treasure 
// marked by *. The object of the game is to find a path through the doorways between 
// the rooms to get to the treasure. You move your player by giving commands u, d, l or r.
// The maze is redisplayed every half second whether you move or not
// Make sure the windoow is big enough to show the whole maze, to avoid flicker in the display

public class Game1 extends Thread
{
    // a 'main' method to run this game as a program.
    // It simply creates an instance of the game and calls the 'run' method on that instance
    public static void main(String[] args) {
        new Game1().run();
    }

    
    // Instance variables
    public Maze maze;               // The maze grid 
    public GameObject player;       // A player object
    public GameObject treasure;     // A treasure object
    public boolean gameOver;        // A boolean to tell us when the game is finished
    public GameInterface gameInterface; // The user interface for the game
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
        maze = new Maze(3,3);
        maze.setRoom(0,0,new Room());
        maze.setRoom(0,1,new Room());
        maze.setRoom(0,2,new Room());
        maze.setRoom(1,0,new Room());
        maze.setRoom(1,1,new Room());
        maze.setRoom(1,2,new Room());
        maze.setRoom(2,0,new Room());
        maze.setRoom(2,1,new Room());
        maze.setRoom(2,2,new Room());

        // create a GameObject for the player, and tell the Maze 
        // where it is (the "X" is what will show on the grid display)
        player = new GameObject("player", "X", 1, 2);    
        maze.setContents(player);

        // similarly for the treasure
        treasure = new GameObject("treasure", "*", 2, 1);
        maze.setContents(treasure);

        // now create an object to manage the user interface
        gameInterface = new GameInterface(maze);
    }

    
    // The main game loop
    // This just displays the board, and then updates the GameObjects once every 500 milliseconds,
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
            checkGameOver();
            gameInterface.displayMaze(message, prompt);
        }
        gameInterface.finish();
    }

    
    // When is the game over? This method checks whether the player is in the 
    // same room as the treasure and if so sets gameOver to true. Other parts of
    // the code can set gameOver true too if they want to make the game stop.
    public void checkGameOver() {
        if (player.col == treasure.col && player.row == treasure.row) {
            message = "Well done, you found the treasure";
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
                // anything else is not recognised - set the Maze message to warn user
                default: message = "Unknown command, try again";
            }
        }
    }

    

    // Try and move the player. We use Maze.possible to check if the requested move is 
    // possible from the current player position. If so, we move, otherwise we set the 
    // game message to let the user know it is not possible
    public void tryMovePlayer(String dir) {
        if (maze.possible(player, dir)) {
            maze.move(player, dir);          
        } else {
            message = "You can't move that way, try again";
        }
    }
       
    // Updating the treasure
    public void updateTreasure() {
        // we do nothing here, so the treasure never changes
    }


    // go to sleep for a short period
    // (NB: includes some system magic to cope with 'Exceptions' - don't worry about this for now)
    public void snooze(int millisecs) {
        try {
            sleep(millisecs);
        } catch (Exception e) {}
    }
    
}