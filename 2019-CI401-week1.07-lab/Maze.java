// The Maze class
// This class represents the board (or world) the game is played in.
// It is a rectangular grid of Room objects, interconnected where they
// have doors that line up. A room can also be set to null to indicate
// a cell you can't visit. This class provides methods for creating the 
// grid, accessing rooms and changing their content, and displaying
// the whole grid.
public class Maze
{
    // Instance variables - a 2 dimensional array of Room objects, and its width and height
    public Room[][] board;
    public int width = 0;
    public int height = 0;

    // Constructor methods
    // Provide just the width and height - this makes a grid of nulls into
    // which you can add rooms
    public Maze(int theWidth, int theHeight) {
        width = theWidth;
        height = theHeight;
        board = new Room[width][height];
    }

    // Provide the actual array of rooms itself
    public Maze(Room[][] theBoard) {
        board = theBoard;
        height = board.length;
        width = board[0].length;
    }

    // Add a room to the board at given col and row (top left is 0,0)
    // Do nothing if coordinates are off the grid
    public void setRoom(int col, int row, Room r) {
        if (col >= 0 && col < width && row >= 0 && row < height) {
            board[col][row] = r;
        } 
    }

    // Return the room object (or null) for a given col and row (top left is 0,0)
    // Return null if coordinates are off grid
    public Room getRoom(int col, int row) {
        if (col >= 0 && col < width && row >= 0 && row < height) {
            return board[col][row];
        } else {
            return null;
        }
    }

    // Check whether it is possible to move in the given direction (up, down, left right)
    // from the given cell - is there a valid room in the right direction, and do both
    // rooms have doors that line up?
    public boolean possible(int col, int row, String dir) {
        Room r1;
        Room r2;
        switch (dir) {
            case "up": r1 = getRoom(col, row); r2 = getRoom(col, row-1); return (r1 != null && r2 != null && r1.up && r2.down);
            case "down": r1 = getRoom(col, row); r2 = getRoom(col, row+1); return (r1 != null && r2 != null && r1.down && r2.up);
            case "left": r1 = getRoom(col, row); r2 = getRoom(col-1, row); return (r1 != null && r2 != null && r1.left && r2.right);
            case "right": r1 = getRoom(col, row); r2 = getRoom(col+1, row); return (r1 != null && r2 != null && r1.right && r2.left);
            default: return false;
        }
    }

    // Check if it is possible for a game object to move in a particular direction
    public boolean possible(GameObject go, String dir) {
        return (go != null && possible(go.col, go.row, dir));
    }
    
    
    // Set the particular type of contents (eg "player" or "treasure") of a room at the 
    // given coordinates to a particular GameObject
    public void setContents(int col, int row, String contentsType, GameObject contents) {
        Room room = getRoom(col, row);
        if (room != null) {
            room.setContents(contentsType, contents);
        }
    }
    
    // Set the contents using the information (type and position) within a game object
    public void setContents(GameObject go) {
        setContents(go.col, go.row, go.type, go);
    }
    
    // move contents from one room to another, setting old location to null
    public void moveContents(int col, int row, String contentsType, GameObject contents) {
        setContents(contents.col, contents.row, contentsType, null);
        contents.col = col;
        contents.row = row;
        setContents(col, row, contentsType, contents);
    }
    
    // move game object to new location
    public void moveContents(int col, int row, GameObject go) {
        moveContents(col, row, go.type, go);
    }
    
    // move game object in a particular direction ("off" means off the board, 
    // ie make it disappear)
    public void move(GameObject go, String dir) {
        switch (dir) {
            case "up":      moveContents(go.col, go.row-1, go); break;
            case "down":    moveContents(go.col, go.row+1, go); break;
            case "left":    moveContents(go.col-1, go.row, go); break;
            case "right":   moveContents(go.col+1, go.row, go); break;
            case "off":     moveContents(-1, -1, go); break;
        }
    }
}
