import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class DetectLine implements Behavior {

	public static float TRESHOLD = (float) 0.45;
	
	static SampleProvider light  	= Main.lightSensor.getRedMode();
	static float[] lightSamples 	= new float[light.sampleSize()];
	
	@Override
	public boolean takeControl() {
		light.fetchSample(lightSamples, 0);
		if(lightSamples[0] < TRESHOLD){
			//Sound.beep();
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		//turn left
		beforeRotate();
		Main.leftMotor.rotate(-130, true);
		Main.rightMotor.rotate(-20);	
		afterRotate();
	}
	
	public void beforeRotate(){
		Main.leftMotor.stop(true);
		Main.rightMotor.stop();
		Main.leftMotor.setSpeed(Main.ROTATE_SPEED);
		Main.rightMotor.setSpeed(Main.ROTATE_SPEED);
	}
	public void afterRotate(){
		Main.leftMotor.setSpeed(Main.DEFAULT_SPEED);
		Main.rightMotor.setSpeed(Main.DEFAULT_SPEED);
	}

	@Override
	public void suppress() {
		//do nothing
		
	}

}
