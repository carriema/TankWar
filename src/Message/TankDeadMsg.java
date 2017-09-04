package Message;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

import Net.NetServer;
import Object.Tank;
import Object.TankClient;

public class TankDeadMsg implements BaseMsg {
	public Tank t;
	public TankDeadMsg(){
		
	}
	
	public TankDeadMsg(Tank t) {
		this.t= t;
	}

	public MessageType type = MessageType.TANKDEAD;
	@Override
	public DatagramPacket generateMsg() {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		try {
			dos.writeInt(MessageType.TANKDEAD.ordinal());
			dos.writeInt(t.getId());
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
			System.out.println("TDeadMsg.parseMsg - " + removeId);
			if (tc.getTanks().containsKey(removeId)) {
				tc.getTanks().get(removeId).setAlive(false);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public <T> void setProperty(T t) {
		// TODO Auto-generated method stub

	}

}
