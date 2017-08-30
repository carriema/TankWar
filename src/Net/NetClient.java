package Net;

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

	public static int UDP_START_PORT = 2223;
	public int udpPort;
	public TankClient tc;
	public DatagramSocket socket;
	private byte[] buf;
	
	public NetClient(TankClient tc) {
		udpPort = UDP_START_PORT++;
		this.tc = tc;
		try {
			socket = new DatagramSocket();
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
			tc.myTank.id = id;
			System.out.println("Get tank Id " + id);
		}catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(s != null) {
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
		
	}
	
	public void sendMsg(DatagramPacket dp) {
		try {
			socket.send(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	}

}
