import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;

public class UpdateLCD implements Behavior {

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		MarsRoverBrick1.updateSensors();
		LCD.drawString("LeftL: " + MarsRoverBrick1.leftLightSamples[0], 0, 0);
		LCD.drawString("RightL: " + MarsRoverBrick1.rightLightSamples[0], 0, 1);
		LCD.drawString("RearUs: " + MarsRoverBrick1.rearUSSamples[0], 0, 2);
		LCD.drawString("Gyro: " + MarsRoverBrick1.gyroSamples[0], 0, 3);
		
	}

	@Override
	public void suppress() {
		//We don't want a half updated LCD
	}

}
