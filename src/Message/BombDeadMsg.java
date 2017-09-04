package Message;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

import Net.NetServer;
import Object.Bomb;

public class BombDeadMsg implements BaseMsg {
	Bomb b;
	public MessageType type = MessageType.BOMBDEAD;
	
	
	@Override
	public DatagramPacket generateMsg() {
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		try {
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
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void setProperty(T t) {
		// TODO Auto-generated method stub
		b = (Bomb) t;
	}

}
