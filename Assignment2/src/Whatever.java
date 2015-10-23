	import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Whatever {
	
	public static EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
    public static EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
    
    public static EV3TouchSensor touchLeftSensor = new EV3TouchSensor(LocalEV3.get().getPort("S1"));
    public static EV3TouchSensor touchRightSensor = new EV3TouchSensor(LocalEV3.get().getPort("S4"));
    public static EV3ColorSensor colorSensor = new EV3ColorSensor(LocalEV3.get().getPort("S2"));
    public static EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(LocalEV3.get().getPort("S3"));

    public static int DEFAULT_SPEED = 300;
    public static int ROTATE_SPEED = 100;
    public static int DEFAULT_ACC = 800;
    public static int ROTATE_ACC = 6000;
    
    private static boolean foundBlue = false;
	private static boolean foundRed = false;
    private static boolean foundYellow = false;	
    public static boolean changed = true;    
    private static boolean running = true;
    
    //TODO: Debug var, remove
    public static int curColor = 0;
    
	public static void main(String[] args) {
		init();
		/*
		Behavior drive = new DriveForward();
		Behavior lcd = new UpdateLCD();
		Behavior detectColor = new DetectColor();
		Behavior detectObj = new DetectObject();
		Behavior detectLine= new DetectLine();
		Behavior quit = new Quit();
		Behavior[] bList = {drive, detectColor, detectObj, detectLine, quit, lcd};
		Arbitrator ar = new Arbitrator(bList);
		ar.start();
		*/
		
		LCD.drawString("_Select slave | master", 0, 0);
		LCD.drawString("Left: master", 0, 1);
		LCD.drawString("Right: slave", 0, 2);
		boolean master = false;
		boolean slave = false;
		while(!master && !slave){
			master = Button.LEFT.isDown();
			slave = Button.RIGHT.isDown();
		}
		
		LCD.clear();
		
		BTConnector connector = new BTConnector();
		if(master) {
			LCD.drawString("master", 0, 0);
			NXTConnection connection = connector.connect("Rover4", NXTConnection.RAW);
			LCD.drawString("Connected", 0, 1);
			Sound.beep();
			PrintWriter writer = new PrintWriter(connection.openOutputStream());
			writer.println("3");
			writer.flush();
			LCD.drawString("send 3", 0, 2);
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
				LCD.drawString("Received:", 0, 2);
				LCD.drawString(new String(buffer), 0, 3);
			} catch (IOException a) {
				LCD.drawString("IO EXCEPTION", 0, 2);
			}
		}
		while(true) {}
	}

	protected static void init(){
		Sound.setVolume(100);
		
		leftMotor.setSpeed(DEFAULT_SPEED);
		leftMotor.setAcceleration(DEFAULT_ACC);
		
		rightMotor.setSpeed(DEFAULT_SPEED);
		rightMotor.setAcceleration(DEFAULT_ACC);
		
		leftMotor.rotateTo(0);
		rightMotor.rotateTo(0);
	}
	
	public static boolean hasFoundBlue() { return foundBlue; }

	public static void findBlue() {
		changed = true;
		Whatever.foundBlue = true;
	}

	public static boolean hasFoundRed() { return foundRed; }

	public static void findRed() {
		changed = true;
		Whatever.foundRed = true;
	}

	public static boolean hasFoundYellow() { return foundYellow; }

	public static void findYellow() {
		changed = true;
		Whatever.foundYellow = true;
	}

	public static boolean isRunning() {
		return running;
	}

	public static void finish() {
		changed = true;
		Whatever.running = false;
	}
	
}
