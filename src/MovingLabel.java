import mayflower.*;
import mayflower.Label;

public class MovingLabel extends Actor
{
    MayflowerImage img;
    public MovingLabel(String txt)
    {
        img = new MayflowerImage(txt, 36,Color.WHITE);
        setImage(img);
    }

    @Override
    public void act()
    {
        setLocation(getX(), getY() - 2);
        if(getY() <= 0)
        {
            getWorld().removeObject(this);
        }
    }

    public int getWidth()
    {
        return img.getWidth();
    }

}