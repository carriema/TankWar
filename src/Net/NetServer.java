package Net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetServer {
	public static int TCP_PORT = 9090;
	List<Client> clients = new ArrayList<>();
	public int ID = 100;

	public void start() {
		ServerSocket tankServer;
		Socket socket = null;
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
}
