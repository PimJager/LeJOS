
import lejos.robotics.subsumption.Behavior;

public abstract class WhateverBehavior implements Behavior{

	public WhateverBehavior() {
		super();
	}
	
	public void stop(){
		Whatever.leftMotor.stop(true);
		Whatever.rightMotor.stop();
	}

	public void beforeRotate() {
		stop();
		Whatever.leftMotor.setSpeed(Whatever.ROTATE_SPEED);
		Whatever.leftMotor.setAcceleration(Whatever.ROTATE_ACC);
		Whatever.rightMotor.setSpeed(Whatever.ROTATE_SPEED);
		Whatever.rightMotor.setAcceleration(Whatever.ROTATE_ACC);
	}

	public void afterRotate() {
		Whatever.leftMotor.setSpeed(Whatever.DEFAULT_SPEED);
		Whatever.leftMotor.setAcceleration(Whatever.DEFAULT_ACC);
		Whatever.rightMotor.setSpeed(Whatever.DEFAULT_SPEED);
		Whatever.rightMotor.setAcceleration(Whatever.DEFAULT_ACC);
	}

}