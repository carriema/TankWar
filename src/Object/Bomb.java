package Object;

import Utils.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

import Message.BombDeadMsg;
import Message.TankDeadMsg;

/**
 * Created by myr on 6/23/17.
 */
public class Bomb {

	private Direction dir;
	private final int ROUND = Constants.BOMB_SIZE;
	private TankClient tc = TankClient.getInstance();
	private int POS_X;
	private int POS_Y;
	private int SPEED = 6;
	private boolean alive = true;
	private boolean good;

	private static int start_id = 1;
	private int id;

	public Bomb(Direction d, int x, int y, boolean good) {
		this.dir = d;
		POS_X = x;
		POS_Y = y;
		this.good = good;
		this.id = Integer.parseInt(String.valueOf(tc.myTank.id) + String.valueOf(start_id++));
		System.out.println("BombId - " + this.id);
	}

	public int getId() {
		return this.id;
	}
	
	public int getPosX() {
		return this.POS_X;
	}
	
	public int getPosY() {
		return this.POS_Y;
	}
	
	public boolean isGood() {
		return this.good;
	}
	
	public Direction getDir() {
		return this.dir;
	}

	public Bomb(int id, Direction d, int x, int y, boolean good) {
		this(d, x, y, good);
		this.good = good;
		this.id = id;
	}

	public void move() {
		switch (dir) {
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
		isAlive();
		if (!alive) {
			
			new BombDeadMsg(this).generateMsg();
		}
		
	}
	
	public boolean alive() {
		return alive;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(good ? Color.pink : Color.BLACK);
		g.fillOval(POS_X, POS_Y, ROUND, ROUND);
		g.setColor(c);
		move();
	}
	
	public void setAlive(boolean isAlive) {
		this.alive = isAlive;
	}

	public void isAlive() {
		hitTank();
		if (POS_X > Constants.FRAME_WIDTH || POS_X < 0 || POS_Y > Constants.FRAME_HEIGHT || POS_Y < 0) {
			alive = false;
		}
	}

	public Rectangle getRect() {
		return new Rectangle(POS_X, POS_Y, ROUND, ROUND);
	}

	public void hitTank() {
		for (Tank t : tc.getTanks().values()) {
			if (this.good != t.isGood() && this.getRect().intersects(t.getRect())) {
				Tank hitTank = t;
				hitTank.setAlive(false);
				this.alive = false;
				if (t instanceof MyTank) {
					new TankDeadMsg(t).generateMsg();
				}
				new BombDeadMsg(this).generateMsg();
				tc.getExplodes().add(new Explode(hitTank.getPosX(), hitTank.getPosY()));
				return;
			}
		}
	}

}
