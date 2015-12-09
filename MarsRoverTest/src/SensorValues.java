
public class SensorValues {

	float touchLeft;
	float touchRight;
	float frontUS;
	float color;
	
	static SensorValues fromString(String s){
		String[] parts = s.split(";");
		if(parts.length < 4) return null;
		SensorValues ret = new SensorValues();
		ret.touchLeft = Float.parseFloat(parts[0]);
		ret.touchRight = Float.parseFloat(parts[1]);
		ret.frontUS = Float.parseFloat(parts[2]);
		ret.color = Float.parseFloat(parts[3]);
		return ret;
	}
	
	static String toString(SensorValues s){
		int touchL = s.touchLeft > 0.9 ? 1 : 0;
		int touchR = s.touchRight > 0.9 ? 1 : 0;
		return ""+touchL+";"+touchR+";"+Float.toString(s.frontUS)+";"+Float.toString(s.color);
	}
	
}
