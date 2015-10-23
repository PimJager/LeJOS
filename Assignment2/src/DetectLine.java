import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class DetectLine extends WhateverBehavior {

	public static int BLACK = 7;
	public static int ALT_BLACK = -1;
	
	static SampleProvider light  	= Whatever.colorSensor.getColorIDMode();
	static float[] lightSamples 	= new float[light.sampleSize()];

	private boolean _supressed = true;
	
	@Override
	public boolean takeControl() {
		light.fetchSample(lightSamples, 0);
		return ((int)lightSamples[0]) == BLACK || ((int) lightSamples[0]) == ALT_BLACK;
	}

	@Override
	public void action() {
		_supressed = false;
		//turn left
		if(!_supressed)	beforeRotate();
		if(!_supressed) Sound.beep();
		if(!_supressed) {
			Whatever.leftMotor.rotate(-130, true);
			Whatever.rightMotor.rotate(-20);
		}
		if(!_supressed) afterRotate();
	}
	
	@Override
	public void suppress() {
		_supressed= true;
	}

}
