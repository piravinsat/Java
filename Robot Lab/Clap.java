import lejos.nxt.*;
import lejos.robotics.subsumption.Behavior;

public class Clap implements Behavior, SensorPortListener
{
	private boolean suppressed = false;
	
	static long clapTime = 0, lastClap = 0;
	static SoundSensor ss = new SoundSensor(SensorPort.S1);
	
	@Override
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue)
	{
		if(ss.readValue() > 30)
		{
			clapTime = System.currentTimeMillis();
		}
	}
	
	public static void main(String[] args)
	{
		ss.setDBA(true);
		Clap myClap = new Clap();
		SensorPort.S1.addSensorPortListener(myClap);	
	}
	
	public boolean clapCheck()
	{
		long currentTime = System.currentTimeMillis();
		
		if(clapTime > lastClap && clapTime < currentTime)
		{
			lastClap = System.currentTimeMillis();
			return true;
		}
		else
		{
			lastClap = System.currentTimeMillis();
			return false;
		}
	}
	
	public boolean takeControl()
	{
		return clapCheck();
	}
	
	public void action()
	{
		suppressed = false;
		while(!suppressed)
		{
			Thread.yield();
		}
	}
	
	public void suppress()
	{
		if(clapCheck())
			suppressed = true;
	}
}