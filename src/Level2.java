import mayflower.*;

import java.util.List;

public class Level2 extends World
{
    Player player;
    Floor floor;
    MayflowerMusic level2;
    Label letterCount;
    Label lives;
    MayflowerImage scroll;
    ScrollWorld sbg;
    int startX;

    public Level2(Player p)
    {
        setPaintOrder(ScrollWorld.class,Hazard.class,Floor.class);

        scroll = new MayflowerImage("img/LevelSomethingScroller.png");
        player = p;
        floor = new Floor();
        sbg = new ScrollWorld(scroll);
        letterCount = new Label("Letters: " + player.getLetters());
        lives = new Label("Lives: " + player.getLife());

        addObject(sbg, 0,0);
        addObject(floor,0,576);
        addObject(player, 0, 200);
        addObject(lives, 0,0);
        addObject(letterCount, 0, 25);
        level2 = new MayflowerMusic("sounds/VideoGame#1.wav");
        //beep = new MayflowerSound("sounds/beep.wav");
        level2.playLoop();
        buildLevel();
        startX = 0;
    }

    @Override
    public void act()
    {
        if(player.getKeys() && player.touchDoor())
        {
            level2.stop();
        }

        player.doorCheck(3);
        lives.setText("Lives: " + player.getLife());
        letterCount.setText("Letters: " + player.getLetters());
        List<Platform> platforms = getObjects(Platform.class);
        List<Key> keys = getObjects(Key.class);
        List<Hazard> hazards = getObjects(Hazard.class);
        List<Letter> letters = getObjects(Letter.class);
        List<MovingLabel> labels = getObjects(MovingLabel.class);
        List<Door> door = getObjects(Door.class);

        if(player.isWalking())
        {
            if(player.isRight() && player.onRightBorder() && sbg.getX() > (scroll.getWidth() - 1024) * -1)
            {
                for(MovingLabel p:labels)
                {
                    p.setLocation(p.getX()-10,p.getY());
                }
                for(Platform p:platforms)
                {
                    p.setLocation(p.getX()-10,p.getY());
                }
                for(Hazard h:hazards)
                {
                    h.setLocation(h.getX()-10,h.getY());
                }
                for(Letter l:letters)
                {
                    l.setLocation(l.getX()-10,l.getY());
                }
                for(Door l:door)
                {
                    l.setLocation(l.getX()-10,l.getY());
                }
                for(Key k:keys)
                {
                    k.setLocation(k.getX()-10,k.getY());
                }
                sbg.setLocation(sbg.getX() - 10, sbg.getY());
                player.setLocation(player.getX() - 10, player.getY());


                startX-=10;
                player.setDying(startX);
            }
            else if(!player.isRight() && player.onLeftBorder() && sbg.getX() < 0)
            {

                for(MovingLabel p:labels)
                {
                    p.setLocation(p.getX()+10,p.getY());
                }
                for(Platform p:platforms)
                {
                    p.setLocation(p.getX()+10,p.getY());
                }
                for(Hazard h:hazards)
                {
                    h.setLocation(h.getX()+10,h.getY());
                }
                for(Letter l:letters)
                {
                    l.setLocation(l.getX()+10,l.getY());
                }
                for(Door l:door)
                {
                    l.setLocation(l.getX()+10,l.getY());
                }
                for(Key k:keys)
                {
                    k.setLocation(k.getX()+10,k.getY());
                }
                sbg.setLocation(sbg.getX() + 10, sbg.getY());

                player.setLocation(player.getX() + 10, player.getY());

                startX+=10;
                player.setDying(startX);
            }
        }
        if(player.didDie())
        {
            removeObjects(getObjects(Platform.class));
            removeObjects(getObjects(Hazard.class));
            removeObjects(getObjects(Letter.class));
            removeObjects(getObjects(Door.class));
            removeObjects(getObjects(Key.class));
            buildLevel();
            sbg.setLocation(0, sbg.getY());
            player.setLocation(0, player.getY());

        }
    }


    public void buildLevel()
    {
        int[][] level = levels(2);

        for(int i = 0;i<level.length;i++ )
        {
            for(int k = 0;k<level[0].length;k++)
            {
                if(level[i][k] == 1 )
                {
                    addObject(new Platform(),(k)*250,i*((int)(Math.random()*23)+128));
                }
                if(level[i][k] == 2 )
                {
                    addObject(new Hazard(),((k)*250),((i)*128));
                }
                if(level[i][k] == 4 )
                {
                    addObject(new Key(),((k)*250),((i)*128));
                }
                if(level[i][k] == 5 )
                {
                    addObject(new Letter(),((k)*250),((i)*128));
                }
                if(level[i][k] == 6 )
                {
                    addObject(new Door(),((k)*250),((i)*128));
                }

            }
        }
    }
    public int[][] levels(int levelNumber)
    {
        /* 0 is empty
           1 is platform
           2 is hazard
           3 is ladder
           4 is key
           5 is letter
           6 is door
         */
        int[][] level1 = {{0,0,0,0,2,2,2,2,0,0,2,0,0,0,2,0,2,0,0,0},
                          {0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0},
                          {0,0,0,1,0,0,0,1,0,0,1,1,0,0,4,0,0,1,0,0},
                          {0,1,1,0,1,0,1,0,0,0,0,0,0,1,0,1,0,0,0,6},
                          {0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0}};
        if(player.getLetters() != 2) {
            int x = (int) (Math.random() * 3) + 1;
            if (x == 1) {
                level1[2][4] = 5;
            }
            if (x == 2) {
                level1[1][12] = 5;
            }
            if (x == 3) {
                level1[2][13] = 5;
            }
        }

        if(levelNumber == 1)
        {
            return level1;
        }

        return level1;
    }

    }

