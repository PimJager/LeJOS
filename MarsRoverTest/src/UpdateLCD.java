import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;

public class UpdateLCD implements Behavior {

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		MarsRoverMaster.updateSensors();
		LCD.drawString("LeftL: " + MarsRoverMaster.leftLightSamples[0], 0, 0);
		LCD.drawString("RightL: " + MarsRoverMaster.rightLightSamples[0], 0, 1);
		LCD.drawString("RearUs: " + MarsRoverMaster.rearUSSamples[0], 0, 2);
		LCD.drawString("Gyro: " + MarsRoverMaster.gyroSamples[0], 0, 3);
		
	}

	@Override
	public void suppress() {
		//We don't want a half updated LCD
	}

}
