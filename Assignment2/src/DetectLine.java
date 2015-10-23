import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class DetectLine implements Behavior {

	public static float TRESHOLD = (float) 0.45;
	
	static SampleProvider light  	= Whatever.lightSensor.getRedMode();
	static float[] lightSamples 	= new float[light.sampleSize()];

	private boolean _supressed;
	
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
		_supressed = false;
		//turn left
		if(!_supressed)	beforeRotate();
		if(!_supressed) {
			Whatever.leftMotor.rotate(-130, true);
			Whatever.rightMotor.rotate(-20);
		}
		if(!_supressed) afterRotate();
	}
	
	public void beforeRotate(){
		Whatever.leftMotor.stop(true);
		Whatever.rightMotor.stop();
		Whatever.leftMotor.setSpeed(Whatever.ROTATE_SPEED);
		Whatever.rightMotor.setSpeed(Whatever.ROTATE_SPEED);
	}
	public void afterRotate(){
		Whatever.leftMotor.setSpeed(Whatever.DEFAULT_SPEED);
		Whatever.rightMotor.setSpeed(Whatever.DEFAULT_SPEED);
	}

	@Override
	public void suppress() {
		_supressed= true;
	}

}
