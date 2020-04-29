// The GameObject class
// Game objects are the moveable things in the game world, such as players,
// treasure, weapons, non-player characters etc.
// In our game world, they are simple data objects, with a type (such as 'player')
// and a single character Id for displaying them in the grid (such as "X").
// They can also have a name, and they know where they are on the grid.

public class GameObject
{
    // Instance variables - type, id and name
    // Id must be a single character string
    public String type = "";
    public String id = "?";
    public String name = "";
    public int col = -1;
    public int row = -1;
    
    // Constructor methods (type and Id are required, name is optional, starting position 
    // also optional)
    
    public GameObject(String theType, String theId) {
        type = theType;
        id = theId;
    }
    
    public GameObject(String theType, String theId, int theCol, int theRow) {
        type = theType;
        id = theId;
        col = theCol;
        row = theRow;
    }
    
    public GameObject(String theType, String theId, String theName) {
        type = theType;
        id = theId;
        name = theName;
    } 
    
    public GameObject(String theType, String theId, String theName, int theCol, int theRow) {
        type = theType;
        id = theId;
        name = theName;
        col = theCol;
        row = theRow;
    } 
}
