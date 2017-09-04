package Object;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Message.BombStateChangeMsg;
import Message.TankStateChangeMsg;
import Net.NetClient;
import Net.NetServer;
import Utils.Constants;

/**
 * Created by myr on 6/24/17.
 */
public class MyTank extends Tank {
	DatagramSocket ds;

	public MyTank() {
		// TODO Auto-generated constructor stub
		super();
		try {
			ds = new DatagramSocket(NetClient.udpPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	boolean bL = false, bU = false, bR = false, bD = false;

	public void setDirection() {
		if (bL == true && bU == false && bR == false && bD == false) {
			dir = Direction.L;
		} else if (bL == false && bU == true && bR == false && bD == false) {
			dir = Direction.U;
		} else if (bL == false && bU == false && bR == true && bD == false) {
			dir = Direction.R;
		} else if (bL == false && bU == false && bR == false && bD == true) {
			dir = Direction.D;
		} else if (bL == true && bU == true && bR == false && bD == false) {
			dir = Direction.LU;
		} else if (bL == true && bU == false && bR == false && bD == true) {
			dir = Direction.LD;
		} else if (bL == false && bU == true && bR == true && bD == false) {
			dir = Direction.RU;
		} else if (bL == false && bU == false && bR == true && bD == true) {
			dir = Direction.RD;
		} else {
			dir = Direction.STAY;
		}
		if (dir != Direction.STAY) {
			barrelDir = dir;
		}
		if (tc.getTanks().size() > 0) {
			tc.getTanks().forEach((k, v) -> {
				System.out.println("id - " + k + ", value - " + v.getPosX());
			});
			HashMap<Integer, Tank> tankMaps = tc.getTanks();
			for (Tank b : tankMaps.values()) {
				if (!this.equals(b) && this.getRect().intersects(b.getRect())) {
					this.dir = Direction.STAY;
					b.dir = Direction.STAY;
				}
			}
		}

		POS_X = POS_X <= 0 ? 0 : POS_X >= Constants.FRAME_WIDTH - ROUND ? Constants.FRAME_WIDTH - ROUND : POS_X;
		POS_Y = POS_Y <= 23 ? 23 : POS_Y >= Constants.FRAME_HEIGHT - ROUND ? Constants.FRAME_HEIGHT - ROUND : POS_Y;
		TankStateChangeMsg tscm = new TankStateChangeMsg(this);
		try {
			ds.send(tscm.generateMsg());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_SHIFT:
			this.alive = true;
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		setDirection();
	}

	public void fire() {
		int x = POS_X + ROUND / 2;
		int y = POS_Y + ROUND / 2;
		Bomb b = new Bomb(barrelDir, x, y, this.bGood);
		tc.getBombs().put(b.getId(), b);
		BombStateChangeMsg msg = new BombStateChangeMsg(b);
		try {
			ds.send(msg.generateMsg());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_SPACE:
			if (alive) {
				fire();
			}
			break;
		}
		setDirection();
	}
	

}
