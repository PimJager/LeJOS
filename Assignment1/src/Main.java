import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Main {
	
	static EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
    static EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
    
    static EV3TouchSensor touchLeft = new EV3TouchSensor(LocalEV3.get().getPort("S1"));
    static EV3TouchSensor touchRight = new EV3TouchSensor(LocalEV3.get().getPort("S4"));
    static NXTLightSensor lightSensor = new NXTLightSensor(LocalEV3.get().getPort("S2"));
    static EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(LocalEV3.get().getPort("S2"));

	public static void main(String[] args) {
		LCD.drawString("Plugin Test", 0, 4);
		Delay.msDelay(5000);
	}

}
