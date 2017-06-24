package Object;

import Utils.Constants;

import java.awt.*;

/**
 * Created by myr on 6/23/17.
 */
public class Bomb {

    private Direction dir;
    private final int ROUND = Constants.BOMB_SIZE;
    private TankClient tc;
    private int POS_X;
    private int POS_Y;
    private int SPEED = 6;
    private boolean alive = true;

    public Bomb(Direction d, int x, int y) {
        this.dir = d;
        POS_X = x;
        POS_Y = y;
    }

    public Bomb(Direction d, int x, int y, TankClient tc) {
        this(d,x,y);
        this.tc = tc;
    }

    public void move() {
        switch(dir) {
            case R:
                POS_X += SPEED;
                break;
            case L:
                POS_X -= SPEED;
                break;
            case U:
                POS_Y -= SPEED;
                break;
            case D:
                POS_Y += SPEED;
                break;
            case LU:
                POS_X -= SPEED;
                POS_Y -= SPEED;
                break;
            case RU:
                POS_X += SPEED;
                POS_Y -= SPEED;
                break;
            case LD:
                POS_X -= SPEED;
                POS_Y += SPEED;
                break;
            case RD:
                POS_X += SPEED;
                POS_Y += SPEED;
                break;
        }
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.fillOval(POS_X, POS_Y, ROUND, ROUND);
        g.setColor(c);
        move();
        isAlive();
    }


    public void isAlive() {
        if (POS_X > Constants.FRAME_WIDTH || POS_X < 0
                || POS_Y > Constants.FRAME_HEIGHT || POS_Y < 0) {
            alive = false;
            tc.getBombs().remove(this);
        }
    }

}
