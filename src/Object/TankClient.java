package Object;

import Utils.Constants;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Message.TankDeadMsg;
import Net.NetClient;
import Net.NetServer;

/**
 * Created by myr on 6/21/16.
 */
public class TankClient extends Frame {

	public static final int FRAME_X = 100;
	public static final int FRAME_Y = 100;
	public static final TankClient tc = new TankClient();

	public MyTank myTank;
	private HashMap<Integer, Bomb> bombs;
	private HashMap<Integer, Tank> tanks;
	Image offScreenImage = null;
	private ArrayList<Explode> explodes;
	private NetClient nc;

	public HashMap<Integer, Bomb> getBombs() {
		return bombs;
	}

	public ArrayList<Explode> getExplodes() {
		return explodes;
	}

	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.green);
		gOffScreen.fillRect(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		paint(gOffScreen);
		gOffScreen.setColor(c);
		g.drawImage(offScreenImage, 0, 0, null);

	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		synchronized (bombs) {
			for (Iterator<Entry<Integer, Bomb>> it = bombs.entrySet().iterator(); it.hasNext();) {
				Entry<Integer, Bomb> entry = it.next();
				if (!entry.getValue().alive()) {
					it.remove();
				} else {
					entry.getValue().draw(g);
				}
			}
		}

		synchronized (tanks) {
			for (Iterator<Entry<Integer, Tank>> it = tanks.entrySet().iterator(); it.hasNext();) {
				Entry<Integer, Tank> entry = it.next();
				if (!entry.getValue().alive) {
					
					it.remove();
					
				} else {
					entry.getValue().draw(g);
				}
			}
		}
		synchronized (explodes) {
			Iterator<Explode> i = explodes.iterator();
			while (i.hasNext()) {
				Explode e = i.next();
				if (!e.isExst()) {
					i.remove();
				} else {
					e.draw(g);
				}

			}
		}

	}
	
	public void closeFrame() {
		this.dispose();
	}

	public void launchFrame() {

		this.myTank = new MyTank();
		this.bombs = new HashMap<Integer, Bomb>();
		this.tanks = new HashMap<Integer, Tank>();
		this.explodes = new ArrayList<>();
		this.nc = new NetClient();
		this.setLocation(FRAME_X, FRAME_Y);
		this.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setVisible(true);
		this.addKeyListener(new KeyMonitor());

		new Thread(new PaintThread()).start();

		nc.connect(NetServer.IP_ADDRESS, NetServer.TCP_PORT);
	}

	public static void main(String[] args) {

		tc.launchFrame();
	}

	public HashMap<Integer, Tank> getTanks() {
		return tanks;
	}

	public void addTanks(Tank t) {
		tanks.put(t.id, t);
	}

	private class PaintThread implements Runnable {
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
	}

	public static TankClient getInstance() {
		return tc;
	}
}
