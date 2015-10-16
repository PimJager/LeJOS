import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class DetectObject implements Behavior{

	static SampleProvider touchL 	= Main.touchLeftSensor.getTouchMode();
	static SampleProvider touchR 	= Main.touchRightSensor.getTouchMode();
	static float[] touchLeftSamples = new float[touchL.sampleSize()];
	static float[] touchRightSamples = new float[touchR.sampleSize()];
	
	@Override
	public boolean takeControl() {
		touchL.fetchSample(touchLeftSamples, 0);
		touchR.fetchSample(touchRightSamples, 0);
		return touchLeftSamples[0] > 0 || touchRightSamples[0] > 0;
	}

	@Override
	public void action() {
		Main.leftMotor.stop(true);
		Main.rightMotor.stop();
		Main.leftMotor.rotate(-45, true);
		Main.rightMotor.rotate(-45);
		if(touchLeftSamples[0] > 0){
			//rotate right
			Main.leftMotor.rotate(100, true);
			Main.rightMotor.rotate(-100);
		} else {
			//rotate left
			Main.leftMotor.rotate(-100, true);
			Main.rightMotor.rotate(100);
		}
	}

	@Override
	public void suppress() {
		//we don't need to do anything
	}

}
