package Net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class NetServer {
	public static int TCP_PORT = 9090;
	List<Client> clients = new ArrayList<>();
	public int ID = 100;
	public static final int UDP_PORT = 8544;
	

	public void start() {
		ServerSocket tankServer;
		Socket socket = null;
		new UDPServer().start();
		try {
			tankServer = new ServerSocket(TCP_PORT);
			while (true) {
				socket = tankServer.accept();
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				String IP = socket.getInetAddress().getHostAddress();
				System.out.println("IP--" + IP);
				int udpPort = dis.readInt();
				System.out.println("UDP--" + udpPort);
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.writeInt(ID++);
				boolean isGood = ID % 2 == 0;
				dos.writeBoolean(isGood);
				clients.add(new Client(IP, udpPort));
				System.out.println("A Clinet connected to Server - " + socket.getInetAddress() + ":" + socket.getPort() + "---UPD Port:" + udpPort);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
					socket = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}

	public static void main(String[] args) throws IOException {
		new NetServer().start();
	}

	private class Client {
		String IP;
		int udpPort;

		public Client(String IP, int udpPort) {
			this.IP = IP;
			this.udpPort = udpPort;
		}
	}
	
	private class UDPServer extends Thread {
		private DatagramSocket socket;
		private byte[] buf = new byte[1024];
		
		public UDPServer() {
			try {
				this.socket = new DatagramSocket(UDP_PORT);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run() {
		
			while (socket != null) {
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					socket.receive(dp);
					System.out.println("Server received");
					for (int i = 0; i < clients.size(); i++) {
						
						dp.setSocketAddress(new InetSocketAddress(clients.get(i).IP, clients.get(i).udpPort));
						System.out.println("Server send");
						socket.send(dp);
						System.out.println("send" + clients.get(i).udpPort);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
