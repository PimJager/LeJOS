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
			Main.leftMotor.forward();
			Main.rightMotor.forward();
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;		
	}

}
