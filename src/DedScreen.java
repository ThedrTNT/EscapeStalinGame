import mayflower.*;

public class DedScreen extends World
{

    public DedScreen()
    {
        setBackground("img/realdeath.png");
    }

    @Override
    public void act()
    {
        if(Mayflower.isKeyDown(Keyboard.KEY_SPACE))
        {
            Mayflower.setWorld(new StartScreen());
        }
    }
}
