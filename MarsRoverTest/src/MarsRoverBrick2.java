import java.io.PrintWriter;

import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;
import lejos.robotics.SampleProvider;

public class MarsRoverBrick2 {

	public static String connectTo = "Rover7";
	
    public static EV3TouchSensor leftTouchSensor 	= new EV3TouchSensor(LocalEV3.get().getPort("S1"));
    public static EV3TouchSensor rightTouchSensor 	= new EV3TouchSensor(LocalEV3.get().getPort("S2"));
    public static EV3UltrasonicSensor frontUSSensor	= new EV3UltrasonicSensor(LocalEV3.get().getPort("S3"));
    public static EV3ColorSensor colorSensor 		= new EV3ColorSensor(LocalEV3.get().getPort("S4"));
	
    public static SampleProvider touchLProvider 	= MarsRoverBrick2.leftTouchSensor.getTouchMode();
    public static float[] touchLeftSamples 			= new float[touchLProvider.sampleSize()];
	public static SampleProvider touchRProvider 	= MarsRoverBrick2.rightTouchSensor.getTouchMode();
	public static float[] touchRightSamples 		= new float[touchRProvider.sampleSize()];
	public static SampleProvider frontUSProvider 	= MarsRoverBrick2.frontUSSensor.getDistanceMode();
	public static float[] frontUSSamples 			= new float[frontUSProvider.sampleSize()];
	public static SampleProvider colorProvider		= MarsRoverBrick2.colorSensor.getColorIDMode();
	public static float[] colorSamples 				= new float[colorProvider.sampleSize()];
	
	//bluetooth
	public static NXTConnection connection;
	private static PrintWriter btWriter;
	private static SensorValues vals = new SensorValues();
    
    public static void main(String[] args) {
    	btSetup();
    	brick2Thread.start();
	}
    
    public static void updateSensors(){
    	touchLProvider.fetchSample(touchLeftSamples, 0);
    	touchRProvider.fetchSample(touchRightSamples, 0);
    	frontUSProvider.fetchSample(frontUSSamples, 0);
    	colorProvider.fetchSample(colorSamples, 0);
    }
    
    public static void btSetup(){
    	LCD.drawString("Connecting to brick1", 0, 8);
    	
    	BTConnector connector = new BTConnector();
    	connection = connector.connect(connectTo, NXTConnection.RAW);
    	
    	LCD.clear();
    	if(connection == null) {
    		LCD.drawString("CONNECTION CANCELLED!!!", 0, 8);
    		Sound.buzz();
    		while(true) {}
    	} else {
    		btWriter = new PrintWriter(connection.openOutputStream());
    		Sound.beep();
    	}
    }
    
    static Thread brick2Thread = new Thread(new Runnable() {
		@Override
		public void run() {
			while(true) {
				updateSensors();
		    	vals.touchLeft = touchLeftSamples[0];
		    	vals.touchRight = touchRightSamples[0];
		    	vals.frontUS = frontUSSamples[0];
		    	vals.color = colorSamples[0];
		    	String send = SensorValues.toString(vals);
		    	btWriter.println(send);
		    	btWriter.flush();
				LCD.drawString("TouchL:" + touchLeftSamples[0], 0, 0);
				LCD.drawString("TouchR: " + touchRightSamples[0], 0, 1);
				LCD.drawString("FrontUS: " + frontUSSamples[0], 0, 2);
				LCD.drawString("color:" + colorSamples[0], 0, 3);
				LCD.drawString(send, 0, 7);
			}
		}	
	});
    
}
