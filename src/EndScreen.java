import mayflower.*;

public class EndScreen extends World
{
    MayflowerMusic end;

    public EndScreen(Player p)
    {
        end = new MayflowerMusic("win.wav");
        if(p.getLetters() == 3)
        {
            end.playLoop();
            setBackground("img/GoodEnding.png");
        }
        else
        {
            setBackground("img/BadEnding.png");
        }

    }
    @Override
    public void act()
    {
        if(Mayflower.isKeyDown(Keyboard.KEY_SPACE))
        {
            end.stop();
            Mayflower.setWorld(new StartScreen());
        }
    }
}
