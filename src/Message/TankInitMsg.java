package Message;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

import Object.Tank;

public class TankInitMsg {
	Tank t;

	public TankInitMsg(Tank t) {
		this.t = t;
	}

	public DatagramPacket getMsg(String IP, int udpPort) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		try {
			dos.writeInt(t.id);
			dos.writeInt(t.getPosX());
			dos.writeInt(t.getPosY());
			dos.writeInt(t.getDir().ordinal());
			dos.writeBoolean(t.isGood());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
		DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, udpPort));
		return dp;

	}
}
