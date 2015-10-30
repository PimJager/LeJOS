import lejos.hardware.lcd.LCD;

public class UpdateLCD extends WhateverBehavior {

	@Override
	public boolean takeControl() {
		return Whatever.updateLCD;
	}

	@Override
	public void action() {
		stop(); //stop the robot to be safe
		LCD.clear();
		if(Whatever.hasFoundBlue()) LCD.drawString("BBBBB", 14, 0);
		if(Whatever.hasFoundRed()) LCD.drawString("RRRRR", 14, 1);
		if(Whatever.hasFoundYellow()) LCD.drawString("YYYYY", 14, 2);
		LCD.drawString("Blue: " + Whatever.hasFoundBlue(), 0, 0);
		LCD.drawString("RED: " + Whatever.hasFoundRed(), 0, 1);
		LCD.drawString("YELLOW: " + Whatever.hasFoundYellow(), 0, 2);
		String status = "";
		if(Whatever.isRunning()) status = "Searching...";
		else status = "DONE!";
		LCD.drawString(status, 0, 3);
		//TODO: debug line
		LCD.drawString("CURRENT: " + Whatever.curColor, 0, 4);
		LCD.drawString("LastRGB: " + Whatever.lastRealColor, 0, 5);
		Whatever.updateLCD = false;
	}

	@Override
	public void suppress() {
		//We don't want a half updated LCD
	}

}
