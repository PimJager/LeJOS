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
    public static boolean updateLCD = true;
    public static boolean updateBT = false;
    private static boolean running = true;
    
    //TODO: Debug var, remove
    public static int curColor = 0;
	public static int lastRealColor = -20;
    
	public static void main(String[] args) {
		init();
		
		Behavior drive = new DriveForward();
		Behavior lcd = new UpdateLCD();
		Behavior detectColor = new DetectColor();
		Behavior bluetooth = btSetup();
		Behavior detectObj = new DetectObject();
		Behavior detectLine= new DetectLine();
		Behavior quit = new Quit();
		Behavior[] bList = {drive, lcd, detectColor, detectObj, detectLine, quit};
		Arbitrator ar = new Arbitrator(bList);
		ar.start();
		
	}
	
	protected static Behavior btSetup(){
		//select whether we are slave or master
		LCD.drawString("Select BT-mode", 0, 0);
		LCD.drawString("Left: master", 0, 1);
		LCD.drawString("Right: slave", 0, 2);
		boolean slave = false;
		boolean master = false;
		while(!master && !slave){
			master = Button.LEFT.isDown();
			slave = Button.RIGHT.isDown();
		}
		master = !slave;
		
		LCD.clear();
		
		BTConnector connector = new BTConnector();
		NXTConnection connection;
		if(master) {
			LCD.drawString("master", 0, 0);
			connection = connector.connect("Rover4", NXTConnection.RAW);
		} else {
			LCD.drawString("slave", 0, 0);
			connection = connector.waitForConnection(10000, NXTConnection.RAW);
		}
		
		Sound.beep(); 
		LCD.clear();
		return new Bluetooth(connection);
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
		updateLCD = true;
		updateBT = true;
		Whatever.foundBlue = true;
	}

	public static boolean hasFoundRed() { return foundRed; }

	public static void findRed() {
		updateLCD = true;
		updateBT = true;
		Whatever.foundRed = true;
	}

	public static boolean hasFoundYellow() { return foundYellow; }

	public static void findYellow() {
		updateLCD = true;
		updateBT = true;
		Whatever.foundYellow = true;
	}

	public static boolean isRunning() {
		return running;
	}

	public static void finish() {
		updateLCD = true;
		Whatever.running = false;
	}
	
}

