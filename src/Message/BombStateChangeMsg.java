package Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.HashMap;

import Net.NetServer;
import Object.Bomb;
import Object.Direction;
import Object.TankClient;

public class BombStateChangeMsg implements BaseMsg{

	Bomb b;
	public MessageType type = MessageType.BOMBINIT;
	
	public BombStateChangeMsg(){
		
	}
	
	public BombStateChangeMsg(Bomb b) {
		this.b = b;
	}

	@Override
	public DatagramPacket generateMsg() {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		try {
			dos.writeInt(MessageType.BOMBINIT.ordinal());
			dos.writeInt(b.getId());
			dos.writeInt(b.getPosX());
			dos.writeInt(b.getPosY());
			dos.writeBoolean(b.isGood());
			dos.writeInt(b.getDir().ordinal());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
		DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(NetServer.IP_ADDRESS, NetServer.UDP_PORT));
		return dp;
	}

	@Override
	public void parseMsg(DataInputStream dis) {
		TankClient tc = TankClient.getInstance();
		try {
			int bombId = dis.readInt();
			int pos_x = dis.readInt();
			int pos_y = dis.readInt();
			boolean good = dis.readBoolean();
			int dir = dis.readInt();
			Bomb newBomb = new Bomb(bombId, Direction.values()[dir], pos_x, pos_y, good);
			HashMap<Integer, Bomb> bomMap = tc.getBombs();
			if (!bomMap.containsKey(bombId)) {
				bomMap.put(bombId, newBomb);
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
