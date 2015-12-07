import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class MarsRoverMaster {

	public static EV3LargeRegulatedMotor leftMotor 	= new EV3LargeRegulatedMotor(MotorPort.A);
    public static EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
    public static EV3LargeRegulatedMotor mesMotor 	= new EV3LargeRegulatedMotor(MotorPort.C);
    
    public static NXTLightSensor leftLightSensor 	= new NXTLightSensor(LocalEV3.get().getPort("S1"));
    public static NXTLightSensor rightLightSensor 	= new NXTLightSensor(LocalEV3.get().getPort("S2"));
    public static EV3UltrasonicSensor rearUSSensor 	= new EV3UltrasonicSensor(LocalEV3.get().getPort("S3"));
    public static EV3GyroSensor gyroSensor			= new EV3GyroSensor(LocalEV3.get().getPort("S4"));
    
    static SampleProvider llsProvider  				= MarsRoverMaster.leftLightSensor.getRedMode();
	static float[] leftLightSamples 				= new float[llsProvider.sampleSize()];
	static SampleProvider rlsProvider  				= MarsRoverMaster.leftLightSensor.getRedMode();
	static float[] rightLightSamples 				= new float[rlsProvider.sampleSize()];
	static SampleProvider rearUSProvider 		 	= MarsRoverMaster.rearUSSensor.getDistanceMode();
	static float[] rearUSSamples 					= new float[rearUSProvider.sampleSize()];
	static SampleProvider gyroProvider 				= MarsRoverMaster.gyroSensor.getAngleAndRateMode();
	static float[] gyroSamples 						= new float[gyroProvider.sampleSize()];
    
    public static void main(String[] args) {
    	init();
		Behavior[] bList = {
//							 new DriveForward() 
							new UpdateLCD() 
//							,new DetectColor()
//							,new Quit()
//							,btSetup() //returns a Bluetooth behavior
//							,new DetectObject()
//							,new DetectLine()
							};
		Arbitrator ar = new Arbitrator(bList);
		ar.start();
	}
    
    public static void updateSensors(){
    	llsProvider.fetchSample(leftLightSamples, 0);
    	rlsProvider.fetchSample(rightLightSamples, 0);
    	rearUSProvider.fetchSample(rearUSSamples, 0);
    	gyroProvider.fetchSample(gyroSamples, 0);
    }

    public static void init(){
    	gyroSensor.reset();
    }
    
}
