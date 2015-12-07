import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class MarsRoverBrick2 {

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
    
    public static void main(String[] args) {
    	updateLCD.start();
	}
    
    public static void updateSensors(){
    	touchLProvider.fetchSample(touchLeftSamples, 0);
    	touchRProvider.fetchSample(touchRightSamples, 0);
    	frontUSProvider.fetchSample(frontUSSamples, 0);
    	colorProvider.fetchSample(colorSamples, 0);
    }
    
    static Thread updateLCD = new Thread(new Runnable() {
		@Override
		public void run() {
			while(true) {
				updateSensors();
				LCD.drawString("TouchL:" + touchLeftSamples[0], 0, 0);
				LCD.drawString("TouchR: " + touchRightSamples[0], 0, 1);
				LCD.drawString("FrontUS: " + frontUSSamples[0], 0, 2);
				LCD.drawString("color:" + colorSamples[0], 0, 3);
			}
		}	
	});
    
}
