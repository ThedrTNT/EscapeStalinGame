import mayflower.*;

public class TitleScreen extends World
{
    MayflowerMusic title;
    public TitleScreen()
    {
        title = new MayflowerMusic("sounds/TitleScreen.wav");
        setBackground("img/realtitle.png");
        title.playLoop();
    }

    @Override
    public void act()
    {
        if(Mayflower.isKeyDown(Keyboard.KEY_SPACE))
        {
            Mayflower.setWorld(new StartScreen());
            title.stop();
        }
    }
}
