package Object;

import Utils.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;


/**
 * Created by myr on 6/21/17.
 */
public class Tank{

    private int POS_X;
    private int POS_Y;
    private int SPEED = 3;
    protected final int ROUND = Constants.TANK_SIZE;
    protected Direction dir = Direction.STAY;
    protected Direction barrelDir = Direction.U;
    protected final int barrelLength = 20;
    TankClient tc;
    protected boolean bGood;
    protected boolean alive = true;
    private Random r = new Random();




    public Tank(TankClient tc, boolean bGood) {
        POS_X = r.nextInt(Constants.FRAME_WIDTH - ROUND);
        POS_Y = r.nextInt(Constants.FRAME_HEIGHT - ROUND);

        this.tc = tc;
        this.bGood = bGood;
    }

    public Tank(int x, int y, TankClient tc, boolean bGood) {
        this.POS_X = x;
        this.POS_Y = y;
        this.tc = tc;
        this.bGood = bGood;
    }

    public void drawBarrelPos(Graphics g) {
        g.setColor(Color.BLUE);
        switch(barrelDir) {
            case D:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2, POS_Y + ROUND/2 + barrelLength);
                break;
            case U:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2, POS_Y + ROUND/2 - barrelLength);
                break;
            case L:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2 - barrelLength, POS_Y + ROUND/2);
                break;
            case R:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2 + barrelLength, POS_Y + ROUND/2);
                break;
            case LU:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2 - (int)(0.7*barrelLength), POS_Y + ROUND/2 - (int)(0.7*barrelLength));
                break;
            case RU:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2 + (int)(0.7*barrelLength), POS_Y + ROUND/2 - (int)(0.7*barrelLength));
                break;
            case LD:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2 - (int)(0.7*barrelLength), POS_Y + ROUND/2 + (int)(0.7*barrelLength));
                break;
            case RD:
                g.drawLine(POS_X + ROUND/2, POS_Y + ROUND/2, POS_X + ROUND/2 + (int)(0.7*barrelLength), POS_Y + ROUND/2 + (int)(0.7*barrelLength));
                break;

        }
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(bGood? Color.RED : Color.LIGHT_GRAY);
        move();
        g.fillOval(POS_X, POS_Y,ROUND, ROUND);
        drawBarrelPos(g);
        g.setColor(c);

        if (!alive) {
            tc.getTanks().remove(this);
        }
    }

    public void setDirection() {
        Direction[] directions = {Direction.D, Direction.L, Direction.LD, Direction.LU,Direction.R,
                Direction.RD,Direction.RU,Direction.STAY,Direction.U} ;
        dir = directions[r.nextInt(directions.length)];
        if (dir != Direction.STAY) {
            barrelDir = dir;
        }
    }

    public void move() {
        setDirection();
        switch(dir) {
            case D:
                POS_Y += SPEED;
                break;
            case U:
                POS_Y -= SPEED;
                break;
            case L:
                POS_X -= SPEED;
                break;
            case R:
                POS_X += SPEED;
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
        POS_X = POS_X <= 0 ? 0 : POS_X >= Constants.FRAME_WIDTH - ROUND ? Constants.FRAME_WIDTH - ROUND: POS_X;
        POS_Y = POS_Y <= 23 ? 23 : POS_Y >= Constants.FRAME_HEIGHT - ROUND ? Constants.FRAME_HEIGHT - ROUND: POS_Y;
    }

    public int getRound() {
        return ROUND;
    }

    public int getPosX() {
        return this.POS_X;
    }

    public int getPosY() {
        return this.POS_Y;
    }

    public void setPosX(int x) {
        this.POS_X = x;
    }

    public void setPosY(int y) {
        this.POS_Y = y;
    }


    public void setAlive(boolean isAlive) {
        this.alive = isAlive;
    }



    public void fire() {
        int x = POS_X + ROUND/2;
        int y = POS_Y + ROUND/2;
        tc.getBombs().add(new Bomb(barrelDir,x,y,this.tc));
    }


    public Rectangle getRect() {
        return new Rectangle(POS_X,POS_Y,ROUND,ROUND);
    }

}
