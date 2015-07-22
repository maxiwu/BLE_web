package umedia.stomp;

import java.util.Random;

//send this out to subscriber
public class DataMessage {

	private float value;
	float maxvalue;
	float minvalue;
	
	public DataMessage()
	{
		
		Random rand = new Random();
		//value = rand.nextFloat() * (Float.MAX_VALUE - Float.MIN_VALUE) + Float.MIN_VALUE;
		maxvalue = 20.0f;
		minvalue = 5.0f;
		value = rand.nextFloat() * (maxvalue - minvalue) + minvalue;
	}
	
	public float getValue()
	{
		return value;
	}
	
	public String getContent()
	{
		return "text value";
	}
}
