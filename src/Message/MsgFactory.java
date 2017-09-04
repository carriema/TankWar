package Message;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

public class MsgFactory {

	public static void parse(DatagramPacket dp) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(dp.getData()));
		try {
			int type_num = dis.readInt();
			MessageType type = MessageType.values()[type_num];
			switch (type) {
			case TANKCHANGE:
				new TankStateChangeMsg().parseMsg(dis);
				break;
			case TANKDEAD:
				new TankDeadMsg().parseMsg(dis);
				break;
			case BOMBINIT:
				new BombStateChangeMsg().parseMsg(dis);
				break;
			case BOMBDEAD:
				new BombDeadMsg().parseMsg(dis);
				break;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
