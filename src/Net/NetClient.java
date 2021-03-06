package Net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

import Message.BaseMsg;
import Message.MessageType;
import Message.MsgFactory;
import Message.TankStateChangeMsg;
import Object.TankClient;

public class NetClient {

	public static int udpPort;
	public TankClient tc;
	public DatagramSocket socket;
	private Random random = new Random();

	public NetClient() {
		this.tc = TankClient.getInstance();
		this.udpPort = random.nextInt(60000);
		System.out.println("udpPort: " + udpPort);
		try {
			socket = new DatagramSocket(udpPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connect(String IP, int port) {
		Socket s = null;

		try {
			s = new Socket(IP, port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpPort);
			System.out.println("Connect to Server - " + s.getInetAddress());
			DataInputStream dis = new DataInputStream(s.getInputStream());
			int id = dis.readInt();
			boolean isGood = dis.readBoolean();
			tc.myTank.id = id;
			tc.myTank.setGood(isGood);
			System.out.println("NetClient.connect.myTankId: " + tc.myTank.id);
			tc.addTanks(tc.myTank);
			System.out.println("Get tank Id " + id);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (s != null) {
					s.close();
					s = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		new Thread(new UDPSender()).start();
		new Thread(new UDPReceiver()).start();

	}

	public void sendMsg(DatagramPacket dp) {
		try {
			socket.send(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class UDPReceiver implements Runnable {
		private byte[] buff = new byte[1024];

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (tc.myTank.alive) {
				DatagramPacket dp = new DatagramPacket(buff, buff.length);
				try {
					socket.receive(dp);
					System.out.println("client side receive");
					MsgFactory.parse(dp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			socket.close();
			tc.closeFrame();

		}

	}

	private class UDPSender implements Runnable {

		TankStateChangeMsg tankStateChange = new TankStateChangeMsg(tc.myTank);

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (tc.myTank.alive) {
				sendMsg(tankStateChange.generateMsg());
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
