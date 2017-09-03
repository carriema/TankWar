package Net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

import Message.TankInitMsg;
import Object.TankClient;

public class NetClient {

	public static int UDP_START_PORT = 2225;
	public int udpPort;
	public TankClient tc;
	public DatagramSocket socket;
	private byte[] buf;

	public NetClient(TankClient tc) {
		this.tc = tc;
//		udpPort = UDP_START_PORT++;
		udpPort = NetServer.getUDPPort();
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
		TankInitMsg initMsg = new TankInitMsg(tc.myTank);
		sendMsg(initMsg.getMsg("127.0.0.1", NetServer.UDP_PORT));
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
			while (socket != null) {
				DatagramPacket dp = new DatagramPacket(buff, buff.length);
				try {
					socket.receive(dp);
					System.out.println("client side receive");
					parse(dp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		public void parse(DatagramPacket dp) {
			ByteArrayInputStream bs = new ByteArrayInputStream(dp.getData());
			DataInputStream dis = new DataInputStream(bs);
			TankInitMsg.parse(dis);
		}

	}

}
