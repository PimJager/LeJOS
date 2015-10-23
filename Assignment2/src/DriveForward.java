import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;

public class DriveForward implements Behavior{

	protected boolean suppressed = false;
	
	@Override
	public boolean takeControl() {
		//we always want control
		return Whatever.isRunning();
	}

	@Override
	public void action() {
		suppressed = false;
		while(!suppressed){
			Whatever.leftMotor.forward();
			Whatever.rightMotor.forward();
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;		
	}

}
