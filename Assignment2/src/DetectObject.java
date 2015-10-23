import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class DetectObject implements Behavior{

	static SampleProvider touchL 		= Main.touchLeftSensor.getTouchMode();
	static SampleProvider touchR 		= Main.touchRightSensor.getTouchMode();
	static SampleProvider distance  	= Main.distanceSensor.getDistanceMode();
	static float[] touchLeftSamples 	= new float[touchL.sampleSize()];
	static float[] touchRightSamples	= new float[touchR.sampleSize()];
	static float[] distanceSamples 		= new float[distance.sampleSize()];
	
	static float SAFE_DISTANCE = (float) 0.37;
	private boolean _suppressed;
	
	@Override
	public boolean takeControl() {
		touchL.fetchSample(touchLeftSamples, 0);
		touchR.fetchSample(touchRightSamples, 0);
		distance.fetchSample(distanceSamples, 0);
		if(touchLeftSamples[0] > 0 || touchRightSamples[0] > 0 
				|| distanceSamples[0] < SAFE_DISTANCE){
			//Sound.beep();
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		_suppressed = false;
		if(!_suppressed) beforeRotate();
		if(distanceSamples[0] < SAFE_DISTANCE && !_suppressed){
			//turn left
			Main.leftMotor.rotate(-100, true);
			Main.rightMotor.rotate(100);
		}
		else{
			//go back a bit
			if(!_suppressed) {
				Main.leftMotor.rotate(-45, true);
				Main.rightMotor.rotate(-45);
			}
			if(touchLeftSamples[0] > 0 && !_suppressed){
				//rotate right
				Main.leftMotor.rotate(100, true);
				Main.rightMotor.rotate(-100);
			} else if(!_suppressed ){
				//rotate left
				Main.leftMotor.rotate(-100, true);
				Main.rightMotor.rotate(100);
			}
		}
		if(!_suppressed) afterRotate();
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
		_suppressed = true;
	}

}
