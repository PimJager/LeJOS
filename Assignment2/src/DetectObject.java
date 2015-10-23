import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class DetectObject extends WhateverBehavior {

	static SampleProvider touchL 		= Whatever.touchLeftSensor.getTouchMode();
	static SampleProvider touchR 		= Whatever.touchRightSensor.getTouchMode();
	static SampleProvider distance  	= Whatever.distanceSensor.getDistanceMode();
	static float[] touchLeftSamples 	= new float[touchL.sampleSize()];
	static float[] touchRightSamples	= new float[touchR.sampleSize()];
	static float[] distanceSamples 		= new float[distance.sampleSize()];
	
	static float SAFE_DISTANCE = (float) 0.10;
	
	private boolean _suppressed = true;
	
	@Override
	public boolean takeControl() {
		touchL.fetchSample(touchLeftSamples, 0);
		touchR.fetchSample(touchRightSamples, 0);
		distance.fetchSample(distanceSamples, 0);
		if(touchLeftSamples[0] > 0 || touchRightSamples[0] > 0 
				|| distanceSamples[0] < SAFE_DISTANCE){
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
			Whatever.leftMotor.rotate(-100, true);
			Whatever.rightMotor.rotate(100);
		}
		else{
			//go back a bit
			if(!_suppressed) {
				Whatever.leftMotor.rotate(-45, true);
				Whatever.rightMotor.rotate(-45);
			}
			if(touchLeftSamples[0] > 0 && !_suppressed){
				//rotate right
				Whatever.leftMotor.rotate(100, true);
				Whatever.rightMotor.rotate(-100);
			} else if(!_suppressed ){
				//rotate left
				Whatever.leftMotor.rotate(-100, true);
				Whatever.rightMotor.rotate(100);
			}
		}
		if(!_suppressed) afterRotate();
	}

	@Override
	public void suppress() {
		_suppressed = true;
	}

}
