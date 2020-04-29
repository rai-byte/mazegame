// The week 1.07 project includes the classes GameInterface, Maze Room and GameObject discussed 
// in the lecture. It also contains the program Game1 which uses them to make a simple 
// game. Here are some things to try:
//
// Run Game1 so you can see how it operates - explore what happens when you try to go
// through a wall, or give an unknown command, and if you decide to finish early.
//
// Make a copy of Game1 and make some changes to it such as:
//    - change the character for the player to be "P" instead of "X"
//    - change the starting position of the player and the treasure
//    - design your own amze - make it bigger and more interesting
//      (not always as easy as it seems - draw it on paper first, and
//      then work out where all the solid walls are)
//    - slightly more challenging: the method updateTreasure is called
//      every time we go round the game loop, but doesn't do anything
//      because the treasure never changes. Add a new instance variable to
//      the game which is the lifetime of the treasure, and modify updateTreasure
//      so the lifetime reduces by one and when it gets to zero, the treasure 
//      disappears (so you can't win the game). HINT: you make the treasure disappear
//      by calling maze.move(treasure, "off");

public class Lab4
{
  
}
