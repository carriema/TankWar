package Message;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

import Net.NetServer;
import Object.Bomb;
import Object.TankClient;

public class BombDeadMsg implements BaseMsg {
	Bomb b;
	
	public MessageType type = MessageType.BOMBDEAD;

	public BombDeadMsg() {

	}

	public BombDeadMsg(Bomb b) {
		this.b = b;
	}

	@Override
	public DatagramPacket generateMsg() {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		try {
			dos.writeInt(MessageType.BOMBDEAD.ordinal());
			dos.writeInt(b.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
		DatagramPacket dp = new DatagramPacket(buf, buf.length,
				new InetSocketAddress(NetServer.IP_ADDRESS, NetServer.UDP_PORT));
		return dp;

	}

	@Override
	public void parseMsg(DataInputStream dis) {
		// TODO Auto-generated method stub
		TankClient tc = TankClient.getInstance();
		try {
			int removeId = dis.readInt();
			System.out.println("BDeadMsg.parseMsg - " + removeId);
			if (tc.getBombs().containsKey(removeId)) {
				tc.getBombs().get(removeId).setAlive(false);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public <T> void setProperty(T t) {
		// TODO Auto-generated method stub
		b = (Bomb) t;
	}

}
