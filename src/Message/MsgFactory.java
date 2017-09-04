package Message;

import java.io.DataInputStream;

public class MsgFactory {
	
	public static void parse(MessageType type, DataInputStream dis) {
		switch(type) {
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
		
	}
}
