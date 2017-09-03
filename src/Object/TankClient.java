package Object;

import Utils.Constants;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import Net.NetClient;
import Net.NetServer;

/**
 * Created by myr on 6/21/16.
 */
public class TankClient extends Frame {

	public static final int FRAME_X = 100;
	public static final int FRAME_Y = 100;
	public final static TankClient tc = new TankClient();

	public MyTank myTank = new MyTank();
	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private HashMap<Integer, Tank> tanks = new HashMap<Integer, Tank>();
	Image offScreenImage = null;
	private ArrayList<Explode> explodes = new ArrayList<Explode>();
	private NetClient nc = new NetClient(this);

	

	public ArrayList getBombs() {
		return bombs;
	}

	public ArrayList getExplodes() {
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
		g.drawString(String.valueOf(bombs.size()), 100, 100);
		g.drawString(String.valueOf(myTank.id), myTank.POS_X, myTank.POS_Y);
		// myTank.draw(g);
		for (int i = 0; i < bombs.size(); i++) {
			bombs.get(i).draw(g);
		}
		for (Tank k : tanks.values()) {
			k.draw(g);
		}
		for (int i = 0; i < explodes.size(); i++) {
			explodes.get(i).draw(g);
		}
	}

	public void launchFrame() {
		
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

		nc.connect("127.0.0.1", NetServer.TCP_PORT);
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
