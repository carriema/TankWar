package Message;

import java.io.DataInputStream;
import java.net.DatagramPacket;

public class TankDeadMsg implements BaseMsg {

	public MessageType type = MessageType.TANKDEAD;
	@Override
	public DatagramPacket generateMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseMsg(DataInputStream dis) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void setProperty(T t) {
		// TODO Auto-generated method stub

	}

}
