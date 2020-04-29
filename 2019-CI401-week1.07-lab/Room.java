// Room class for the Grid game
// Each cell in the grid is a Room object
// Room objects can have doorways on any of their four sides. If the
// adjacent room also has a doorway, then there's a path from one room
// to the other.
// Room objects can also have contents, such as players, treasure or enemies
// Rooms can describe their contents, by returning an 8 character string
// with single characters representing the presence of a particular thing.
public class Room
{
    // Instance variables indicating doorways on the four sides of the room
    // By default there are doorways on all four sides
    public boolean up = true;
    public boolean down = true;
    public boolean left = true;
    public boolean right = true;
    
    // Instance variables for different gameObjects which may be present in the room
    // If not present, the value is null
    public GameObject playerPresent = null;
    public GameObject treasurePresent = null;
    public GameObject zombiePresent = null;
    
    // Constructor methods, providing quick ways to set up doors
    // This is 'polymorphism' - several methods with the same name
    // but different numbers of arguments.
    
    // default method - all walls have doors
    public Room() {
    }
    
    // method to set all the walls the same (all doors, or no doors)
    public Room(boolean all) {
        up = all;
        down = all;
        left = all;
        right = all;
    }
    
    // method to set up up-down and left-right paths separately
    public Room(boolean ud, boolean lr) {
        up = ud;
        down = ud;
        left = lr;
        right = lr;
    }
    
    // method to set all the doors individually
    public Room(boolean u, boolean d, boolean l, boolean r) {
        up = u;
        down = d;
        left = l;
        right = r;
    }
    
    // method to set room contents for a particular content type
    public void setContents(String contentsType, GameObject contents) {
        switch (contentsType) {
            case "player": playerPresent = contents; break;
            case "treasure": treasurePresent = contents; break;
            case "zombie": zombiePresent = contents; break;
        }
    }
    
    // Method to return a string to display in the grid form this room.
    // The only restriction is that the string must be exactly 8 characters long.
    // Here, we place an "X" if the player is in the room and a "*" if the treasure
    // is in the room.
    // Notice the use of 'conditional expressions' - (playerPresent==null?"":playerPresent.id) should 
    // be read as 'if playerPresent is null, then use " ", otherwise use playerPresent.id'
    public String contents() {
        return "  " + 
            (zombiePresent==null?" ":zombiePresent.id) +
            (playerPresent==null?" ":playerPresent.id) + 
            (treasurePresent==null?" ":treasurePresent.id) + 
            "   ";
    }       
}
