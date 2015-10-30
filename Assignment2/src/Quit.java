import lejos.hardware.Button;
import lejos.hardware.Sound;

public class Quit extends WhateverBehavior {

	@Override
	public boolean takeControl() {
		if(! Whatever.isRunning()) return false;
		return ( 	Whatever.hasFoundBlue()
				&& 	Whatever.hasFoundRed()
				&& Whatever.hasFoundYellow()
				) || Button.ESCAPE.isDown();
	}

	@Override
	public void action() {
		stop();
		Whatever.finish();
		Sound.buzz();
		Sound.buzz();
		Sound.buzz();
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
