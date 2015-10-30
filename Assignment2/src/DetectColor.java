import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;

public class DetectColor extends WhateverBehavior {

	private boolean _supressed = true;
	
	private static int YELLOW = 1;
	private static int BLUE = 2;
	private static int RED = 0;
			
	static SampleProvider light  	= Whatever.colorSensor.getColorIDMode();
	static float[] lightSamples 	= new float[light.sampleSize()];
	
	private int lastColor = 0;
	
	@Override
	public boolean takeControl() {
		light.fetchSample(lightSamples, 0);
		//TODO: Debug code
		if(lastColor != (int) lightSamples[0]){
			Whatever.curColor = (int) lightSamples[0];
			Whatever.updateLCD = true;
			lastColor = (int) lightSamples[0];
		}
		
		if(!Whatever.isRunning()) return false;
		return 		(((int) lightSamples[0]) == YELLOW && !Whatever.hasFoundYellow())
				||	(((int) lightSamples[0]) == RED && !Whatever.hasFoundRed())
				|| 	(((int) lightSamples[0]) == BLUE && !Whatever.hasFoundBlue());
	}

	@Override
	public void action() {
		_supressed = false;
		stop();
		
		light.fetchSample(lightSamples, 0);
		int color = (int) lightSamples[0];
		
		if(color != YELLOW && color != BLUE && color != RED) return;
		
		Whatever.lastRealColor = color;
		if(color == YELLOW) Whatever.findYellow();
		if(color == BLUE) Whatever.findBlue();
		if(color == RED) Whatever.findRed();
	}

	@Override
	public void suppress() {
		_supressed = true;
	}

}
