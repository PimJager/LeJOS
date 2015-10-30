import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.remote.nxt.NXTConnection;

public class Bluetooth extends WhateverBehavior {

	private static NXTConnection connection;
	private static PrintWriter btWriter;
	private static DataInputStream btIn;
	
	public Bluetooth(NXTConnection conn){
		connection = conn;
		btWriter = new PrintWriter(connection.openOutputStream());
		btIn = connection.openDataInputStream();
		
		listener.start();
	}
	
	@Override
	public boolean takeControl() {
		return Whatever.updateBT;
	}

	@Override
	public void action() {
		stop();
		byte[] send = {'0', '0', '0', '\n'};
		if(Whatever.hasFoundYellow()) send[0] = '1';
		if(Whatever.hasFoundRed()) send[1] = '1';
		if(Whatever.hasFoundBlue()) send[2] = '1';
		btWriter.print(send);
		btWriter.flush();
		Whatever.updateBT = false;
	}

	@Override
	public void suppress() {
		//we can't supress BT communication
	}
	
	Thread listener = new Thread(new Runnable() {
		@Override
		public void run() {
			while(Whatever.isRunning()) {
				byte[] buffer = new byte[4];
				int i = 0;
				byte b;
				try{
					while((b = btIn.readByte()) != '\n' && i < 4)
						buffer[i++] = b;
					if(buffer[0] == '1'){
						Whatever.findYellow();
					}
					if(buffer[1] == '1') {
						Whatever.findRed();
					} 
					if(buffer[2] == '1') {
						Whatever.findBlue();
					} 
					Whatever.updateLCD = true;
					Sound.beep();
				} catch (IOException a) {
					Sound.buzz();
					LCD.drawString("IO_EXCEPTION", 0, 8);
				}
			}
		}	
	});
	
	/*if(master) {
			LCD.drawString("master", 0, 0);
			NXTConnection connection = connector.connect("Rover4", NXTConnection.RAW);
			LCD.drawString("Connected", 0, 1);
			Sound.beep();
			PrintWriter btWriter = new PrintWriter(connection.openOutputStream());
							btWriter.println("3");
							btWriter.flush();
			LCD.drawString("send to S 3", 0, 2);
			//receive from slave
			LCD.clear();
			DataInputStream btIn = connection.openDataInputStream();
			byte[] buffer = new byte[2];
			int i = 0;
			byte b;
			try{
				while((b = btIn.readByte()) != '\n' && i < 2)
					buffer[i++] = b;
				LCD.drawString("Received (S):", 0, 2);
				LCD.drawString(new String(buffer), 0, 3);
			} catch (IOException a) {
				LCD.drawString("IO EXCEPTION", 0, 2);
			}
			Sound.beep();
		} else {
			LCD.drawString("slav e", 0, 0);
			NXTConnection connection = connector.waitForConnection(10000, NXTConnection.RAW);
			LCD.drawString("Connected!!", 0, 1);
			Sound.beep();
			DataInputStream in = connection.openDataInputStream();
			byte[] buffer = new byte[2];
			int i = 0;
			byte b;
			try{
				while((b = in.readByte()) != '\n' && i < 2)
					buffer[i++] = b;
				LCD.drawString("Received (M):", 0, 2);
				LCD.drawString(new String(buffer), 0, 3);
			} catch (IOException a) {
				LCD.drawString("IO EXCEPTION", 0, 2);
			}
			//send to slave
			PrintWriter writer = new PrintWriter(connection.openOutputStream());
			writer.println("9");
			writer.flush();
			LCD.drawString("send to M 9", 0, 2);
			Sound.beep();
		}
		*/

}
