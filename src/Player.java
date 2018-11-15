import mayflower.*;
import java.util.List;
import java.util.ArrayList;

public class Player extends Actor {
    private boolean check;
    private boolean isFalling;
    private boolean isJumping;
    private boolean facingRight;
    private boolean facingLeft;
    private boolean walking;
    private boolean keys;
    private boolean done = true;
    private int frame = 0;
    private int currentFrame = 0;
    private int letters;
    private int jumpHeight;
    private int deathX;
    private int width;
    private int life;
    private int counter;
    private int stopDistance;//distance from border that player stops at
    private int borderLeft; //distance from left border of world
    private int borderRight; //distance from right border of world
    private MayflowerSound oof;
    private MayflowerImage[] rightWalkFrames;
    private MayflowerImage[] activeFrames;
    private MayflowerImage[] idleRightFrames;
    private MayflowerImage[] idleLeftFrames;
    private MayflowerImage[] leftWalkFrames;
    private MayflowerImage[] jumpFrames;

    public Player(int worldWidth, int distance)
    {
        check = false;
        keys = false;
        letters = 0;
        life = 3;
        isJumping = false;
        isFalling = false;
        borderLeft = 0;
        borderRight = worldWidth;
        width = worldWidth;
        stopDistance = distance;
        oof = new MayflowerSound("sounds/oof.wav");
        oof.setVolume(100);
        counter = 0;
        setImage("img/playeridle1.png");

        idleRightFrames = new MayflowerImage[2];
        idleRightFrames[0] = new MayflowerImage("img/playeridle1.png");
        idleRightFrames[1] = new MayflowerImage("img/playeridle2.png");

        jumpFrames = new MayflowerImage[4];
        jumpFrames[0] = new MayflowerImage("img/playerjump1.png");
        jumpFrames[1] = new MayflowerImage("img/playerjump2.png");
        jumpFrames[2] = new MayflowerImage("img/playerjump3.png");
        jumpFrames[3] = new MayflowerImage("img/playerjump4.png");

        rightWalkFrames = new MayflowerImage[3];
        rightWalkFrames[0] = new MayflowerImage("img/playerwalk1.png");
        rightWalkFrames[1] = new MayflowerImage("img/playerwalk2.png");
        rightWalkFrames[2] = new MayflowerImage("img/playerwalk3.png");

        leftWalkFrames = new MayflowerImage[3];
        for(int i = 0; i < leftWalkFrames.length; i++)
        {
            leftWalkFrames[i] = new MayflowerImage("img/playerwalk" + (i+1) + ".png");
            leftWalkFrames[i].mirrorHorizontally();
        }
        activeFrames = idleRightFrames;
    }

    @Override
    public void act() {
        if(isJumping)
        {
            activeFrames = jumpFrames;
        }
        if(counter >= 10 && done)
        {
            if(currentFrame >= activeFrames.length)
            {
                currentFrame = 0;
            }
            MayflowerImage img = activeFrames[currentFrame];
            setImage(img);
            currentFrame++;
            counter = 0;
        }
        counter++;
        gravity();
        checkKeyboard();
        checkFloor();
        ifJump();
        dying();
        collide();
        collection();


        if (isJumping) {
            jump();
        }

        if (Mayflower.isKeyDown(Keyboard.KEY_T)) {
            Mayflower.setWorld(new DedScreen());
        }

    }


    public void gravity() {
        if (isFalling && !isJumping) {
            int x = getX();
            int y = getY();
            setLocation(x, y + 10);
        }
        if (!isTouching(Floor.class) || !isTouching(Platform.class)) {
            isFalling = true;
        }
    }

    public void checkFloor() {
        int y = getY();
        int x = getX();
        if (isTouching(Floor.class) || isTouching(Platform.class)) {
            isFalling = false;
            setLocation(x, y - 10);
        }
    }

    public void jump() {
        int x = getX();
        int y = getY();

        if (y >= jumpHeight) {
            if (collide()) {
                isFalling = true;

            }
            setLocation(x, y - 10);
            isFalling = false;
        } else {
            isJumping = false;
            isFalling = true;
        }
    }

    public void ifJump() {
        if (Mayflower.isKeyDown(Keyboard.KEY_UP) && !isJumping && !isFalling) {

            isJumping = true;
            jumpHeight = getY() - 160;
        }
    }

    public void checkKeyboard() {
        int x = getX();
        int y = getY();


        if (Mayflower.isKeyDown(Keyboard.KEY_LEFT) && !onLeftBorder()) {
            //startX+=10;
            borderLeft -= 10;
            borderRight += 10;
            setLocation(x - 10, y);
            facingLeft = true;
            facingRight = false;
            walking = true;
            activeFrames = leftWalkFrames;

        } else if (Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && !onRightBorder()) {
            //startX-=10;
            borderLeft += 10;
            borderRight -= 10;
            setLocation(x + 10, y);
            facingLeft = false;
            facingRight = true;
            walking = true;
            activeFrames = rightWalkFrames;
        } else {
            activeFrames = idleRightFrames;
            walking = false;
        }
    }

    public boolean onRightBorder() {
        if (getX() >= 740 && borderRight > stopDistance || getX() >= 940) {
            return true;
        }
        //System.out.println("Right: " + borderRight);
        //System.out.println("Right X value: " + getX());
        return false;
    }

    public boolean onLeftBorder() {
        if (getX() <= 200 && borderLeft > 200 || getX() <= 0) {
            return true;
        }
        System.out.println("Left: " + borderLeft);
        return false;
    }

    public boolean isRight() {
        return facingRight;
    }

    public boolean isLeft() {
        return facingLeft;
    }

    public boolean isWalking() {
        return walking;
    }

    public void setDying(int x) {
        deathX = x;
    }

    public void dying() {
        if (isTouching(Hazard.class)) {
            life -= 1;
            letters = 0;
            if(life <= 0)
            {
                Mayflower.setWorld(new DedScreen());
            }
            if(!oof.isPlaying())
            {
                oof.play();
            }
            borderLeft = 0;
            borderRight = width;
            setLocation(deathX, 300);
        }
    }


    public boolean collide() {
        if (isJumping && isTouching(Platform.class)) {
            setLocation(getX(), getY() + 10);
            return true;
        }
        return false;

    }

    public void collection()
    {
      if(isTouching(Key.class))
      {
          Key k = getOneIntersectingObject(Key.class);
          getWorld().removeObject(k);
          keys = true;
      }
      if(isTouching(Letter.class))
      {
          Letter k = getOneIntersectingObject(Letter.class);
          getWorld().removeObject(k);
          letters++;
      }
    }


    public boolean didDie()
    {
        if(getX() < 0)
        {
            return true;
        }
        return false;
    }

    //returns amount of letters collected
    public int getLetters()
    {
        return letters;
    }

    //returns the amount of lifes the player has remaining
    public int getLife()
    {
        return life;
    }

    //checks if colliding with door and moves to next level if key is acquired
    public void doorCheck(int nextLevel)
    {
        if(isTouching(Door.class) && !keys)
        {
            if(!check)
            {
                MovingLabel message = new MovingLabel("You must find a key!");
                getWorld().addObject(message, getX() - (message.getWidth() / 2), getY());
                check = !check;
            }
        }
        if(isTouching(Door.class) && keys && nextLevel == 2)
        {
            borderLeft = 0;
            borderRight = width;
            Mayflower.setWorld(new Level2(this));


        }
        if(isTouching(Door.class) && keys && nextLevel == 3)
    {
        borderLeft = 0;
        borderRight = width;
        Mayflower.setWorld(new Level3(this));

    }
        if(isTouching(Door.class) && keys && nextLevel == 4)
        {
            borderLeft = 0;
            borderRight = width;
            Mayflower.setWorld(new EndScreen(this));

        }
        if(!isTouching(Door.class))
        {
            check = false;
        }
    }

    public void animate(MayflowerImage[] frameSet)
    {
        for(MayflowerImage a : frameSet)
        {
            setImage(a);
        }
    }

    public boolean getKeys()
    {
        return keys;
    }
    public boolean touchDoor() {if(isTouching(Door.class)) { return true;} return false;}

}
