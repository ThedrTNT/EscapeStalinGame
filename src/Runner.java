import mayflower.*;
import java.util.List;

//This class is the starting point of your program.
//It contains the main method which will execute when you run the project.
public class Runner extends Mayflower
{
    //Construct a Mayflower Window.
    public Runner()
    {

      //Open a GUI Window with the specified title, width, and height
        //DO NOT WRITE ANY ADDITIONAL CODE IN THIS CONSTRUCTOR!
        //You should only change the arguments to the super method.
        super("Escape to Stalin Final v1.0", 1024, 768);

    }

    //The init method is called as soon as the Mayflower window is Opened.
    //This is where you will setup the initial state of your game
    //ie, create the first world of your game
    @Override
    public void init()
    {

        //Set the argument to true if you want your game to run in Full Screen Mode
        Mayflower.setFullScreen(false);


        World startingWorld = new TitleScreen();


        Mayflower.setWorld(startingWorld);

    }

    public static void main(String[] args)
    {
        //Instantiate an instance of this Runner class.
        //	The constructor will be called
        //	Then the init() method will be called
        //DO NOT WRITE ANY ADDITIONAL CODE IN THIS METHOD!
        new Runner();
    }
}