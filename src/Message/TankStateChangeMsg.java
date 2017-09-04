package Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

import Net.NetServer;
import Object.Direction;
import Object.Tank;
import Object.TankClient;

public class TankStateChangeMsg implements BaseMsg{
	Tank t;
	public MessageType type = MessageType.TANKCHANGE;

	public TankStateChangeMsg(){
		
	}
	public TankStateChangeMsg(Tank t) {
		this.t= t;
	}
	@Override
	public DatagramPacket generateMsg() {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		try {
			dos.writeInt(MessageType.TANKCHANGE.ordinal());
			dos.writeInt(t.id);
			dos.writeInt(t.getPosX());
			dos.writeInt(t.getPosY());
			dos.writeBoolean(t.isGood());
			dos.writeInt(t.getDir().ordinal());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(t.id);
		byte[] buf = baos.toByteArray();
		DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(NetServer.IP_ADDRESS, NetServer.UDP_PORT));
		return dp;
	}

	@Override
	public void parseMsg(DataInputStream dis) {
		// TODO Auto-generated method stub
		
		int id, posX, posY, dir;
		boolean isGood;
		try {
			id = dis.readInt();
			posX = dis.readInt();
			posY = dis.readInt();
			isGood = dis.readBoolean();
			dir = dis.readInt();
			TankClient tc = TankClient.getInstance();
			System.out.println("getBy- " + id);
			if (!tc.getTanks().containsKey(id)) {
				Tank tank = new Tank(id, posX, posY, isGood, Direction.values()[dir]);
				tc.addTanks(tank);
				System.out.println("TankStateChange.parse.addTankId: " + tank.id);;
				tc.getTanks().forEach((k,v)->{System.out.println("id: " + k + ", TankId: " +v.id);});
			} else {
				Tank tank = (Tank) tc.getTanks().get(id);
				tank.setPosX(posX);
				tank.setPosY(posY);
				tank.setDirection(Direction.values()[dir]);
				System.out.println("StateChange - " + Direction.values()[dir]);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public <T> void setProperty(T t) {
		// TODO Auto-generated method stub
		this.t = (Tank) t;
		
	}
}
