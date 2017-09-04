package Message;

import java.io.DataInputStream;
import java.net.DatagramPacket;

public interface BaseMsg {
	
	public DatagramPacket generateMsg();
	public void parseMsg(DataInputStream dis);
	public <T> void setProperty(T t);
}
