import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;

public class DriveForward implements Behavior{

	protected boolean suppressed = false;
	
	@Override
	public boolean takeControl() {
		//we always want control
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		while(!suppressed){
			MarsRoverBrick1.leftMotor.forward();
			MarsRoverBrick1.rightMotor.forward();
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;		
	}

}
