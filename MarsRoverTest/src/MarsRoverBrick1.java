import java.io.DataInputStream;

import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;
import lejos.robotics.SampleProvider;



public class MarsRoverBrick1 {

	public static EV3LargeRegulatedMotor leftMotor 	= new EV3LargeRegulatedMotor(MotorPort.A);
    public static EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
    public static EV3LargeRegulatedMotor mesMotor 	= new EV3LargeRegulatedMotor(MotorPort.C);
    
    public static NXTLightSensor leftLightSensor 	= new NXTLightSensor(LocalEV3.get().getPort("S1"));
    public static NXTLightSensor rightLightSensor 	= new NXTLightSensor(LocalEV3.get().getPort("S2"));
    public static EV3UltrasonicSensor rearUSSensor 	= new EV3UltrasonicSensor(LocalEV3.get().getPort("S3"));
    public static EV3GyroSensor gyroSensor			= new EV3GyroSensor(LocalEV3.get().getPort("S4"));
    
    //local sensor values
    static SampleProvider llsProvider  				= MarsRoverBrick1.leftLightSensor.getRedMode();
	static float[] leftLightSamples 				= new float[llsProvider.sampleSize()];
	static SampleProvider rlsProvider  				= MarsRoverBrick1.leftLightSensor.getRedMode();
	static float[] rightLightSamples 				= new float[rlsProvider.sampleSize()];
	static SampleProvider rearUSProvider 		 	= MarsRoverBrick1.rearUSSensor.getDistanceMode();
	static float[] rearUSSamples 					= new float[rearUSProvider.sampleSize()];
	static SampleProvider gyroProvider 				= MarsRoverBrick1.gyroSensor.getAngleAndRateMode();
	static float[] gyroSamples 						= new float[gyroProvider.sampleSize()];
	
	//bluetooth
	public static NXTConnection connection;
	private static DataInputStream btIn;
	private static SensorValues vals = new SensorValues();
	private static Object valsLock = new Object();
    
    public static void main(String[] args) {
    	init();
		/*Behavior[] bList = {
							 new DriveForward() 
							new UpdateLCD() 
							,new DetectColor()
							,new Quit()
							,btSetup() //returns a Bluetooth behavior
							,new DetectObject()
							,new DetectLine()
							};
		Arbitrator ar = new Arbitrator(bList);
		ar.start(); */
	}
    
    public static void updateSensors(){
    	llsProvider.fetchSample(leftLightSamples, 0);
    	rlsProvider.fetchSample(rightLightSamples, 0);
    	rearUSProvider.fetchSample(rearUSSamples, 0);
    	gyroProvider.fetchSample(gyroSamples, 0);
    }

    public static void init(){
    	btSetup();
    	gyroSensor.reset();
    	listener.start();
    	updateLCD.start();
    }
    
    public static void btSetup(){
    	LCD.drawString("Connecting to brick2", 0, 8);
    	
    	BTConnector connector = new BTConnector();
    	connection = connector.waitForConnection(10000, NXTConnection.RAW);
    	
    	LCD.clear();
    	if(connection == null) {
    		LCD.drawString("CONNECTION CANCELLED!!!", 0, 8);
    		Sound.buzz();
    		while(true) {}
    	} else {
    		btIn = connection.openDataInputStream();
    		Sound.beep();
    	}
    }
    
    static Thread updateLCD = new Thread(new Runnable() {
		@Override
		public void run() {
			while(true) {
				updateSensors();
				synchronized (valsLock) {
					LCD.drawString("LeftL: " + MarsRoverBrick1.leftLightSamples[0], 0, 0);
					LCD.drawString("RightL: " + MarsRoverBrick1.rightLightSamples[0], 0, 1);
					LCD.drawString("RearUs: " + MarsRoverBrick1.rearUSSamples[0], 0, 2);
					LCD.drawString("Gyro: " + MarsRoverBrick1.gyroSamples[0], 0, 3);
					LCD.drawString("TouchL:" + vals.touchLeft, 0, 4);
					LCD.drawString("TouchR: " + vals.touchRight, 0, 5);
					LCD.drawString("FrontUS: " + vals.frontUS, 0, 6);
					LCD.drawString("color:" + vals.color, 0, 7);	
				}
			}
		}	
	});
    
	static Thread listener = new Thread(new Runnable() {
		@Override
		public void run() {
			while(true) {
				byte[] buffer = new byte[256]; //allows for 128char strings
				int i = 0;
				byte b;
				try{
					while ((b = btIn.readByte()) != '\n') {
						buffer[i] = b;
						i++;
					}
					String rec = new String(buffer);
					synchronized (valsLock) {
						vals = SensorValues.fromString(rec);
						if(vals == null) Sound.buzz();
					}
				} catch(Exception e) {
					e.printStackTrace();
					while(true) {}
				}
			}
		}	
	});
    
}
