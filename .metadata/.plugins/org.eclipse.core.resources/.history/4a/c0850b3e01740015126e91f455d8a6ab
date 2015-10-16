import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Main {
	
	public static EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
    public static EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
    
    public static EV3TouchSensor touchLeftSensor = new EV3TouchSensor(LocalEV3.get().getPort("S1"));
    public static EV3TouchSensor touchRightSensor = new EV3TouchSensor(LocalEV3.get().getPort("S4"));
    public static NXTLightSensor lightSensor = new NXTLightSensor(LocalEV3.get().getPort("S2"));
    public static EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(LocalEV3.get().getPort("S3"));

	public static void main(String[] args) {
		SampleProvider touchL 	= touchLeftSensor.getTouchMode();
		SampleProvider touchR 	= touchRightSensor.getTouchMode();
		SampleProvider light  	= lightSensor.getRedMode();
		SampleProvider distance = distanceSensor.getDistanceMode();
		float[] touchLeftSamples = new float[touchL.sampleSize()];
		float[] touchRightSamples = new float[touchR.sampleSize()];
		float[] lightSamples = new float[light.sampleSize()];
		float[] distanceSamples = new float[distance.sampleSize()];
		
		Behavior drive = new DriveForward();
		Behavior detectObj = new DetectObject();
		Behavior detectLine= new DetectLine();
		Behavior[] bList = {drive, detectObj, detectLine};
		Arbitrator ar = new Arbitrator(bList);
		ar.start();
		/*while(!Button.ESCAPE.isDown()){
			touchL.fetchSample(touchLeftSamples, 0);
			touchR.fetchSample(touchRightSamples, 0);
			light.fetchSample(lightSamples, 0);
			distance.fetchSample(distanceSamples, 0);
			LCD.drawString("TL "+touchLeftSamples[0], 0, 0);
			LCD.drawString("TR "+touchRightSamples[0], 0, 1);
			LCD.drawString("LI "+lightSamples[0], 0, 2);
			LCD.drawString("DI "+distanceSamples[0]+"m", 0, 3);
		}*/
	}

}
