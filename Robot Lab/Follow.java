import lejos.nxt.*;
import lejos.robotics.subsumption.Behavior;

public class Follow implements Behavior
{
	private boolean suppressed = false;
	
	public static void main(String[] args)
	{
		
	}
	
	public boolean takeControl()
	{
		return true;
	}
	
	public void action()
	{
		suppressed = false;
		
		int propCon = 1000, offset = 45, targetPower = 100, currentVal, error;
		int turn;
		LightSensor ls = new LightSensor(SensorPort.S2, true);
		int onLine = 0;
		int offLine = 0;
		
		//Loops infinitely displaying current value from the Light Sensor, when ENTER is pressed current value is stored.
		while(true)
		{
			LCD.drawString("Current: " + ls.readValue(), 1, 1);
			LCD.drawString("Press Enter for \nonLine Value", 1, 3);
			
			if(Button.ENTER.isPressed())
			{
				onLine = ls.readValue();
				break;
			}
		}
		//Same again only for the ESCAPE
		while(true)
		{
			LCD.drawString("Current: " + ls.readValue(), 1, 1);
			LCD.drawString("Press Escape for \noffLine Value", 1, 3);
			
			if(Button.ESCAPE.isPressed())
			{
				offLine = ls.readValue();
				break;
			}
		}
		//If the values aren't the same it calculates a new offset. If they are the same then line following is going to be impossible
		if(onLine != offLine)
		{
			offset = (onLine + offLine) / 2;
		}
		
		while(!suppressed)
		{
			currentVal = ls.readValue();
			error = currentVal - offset;
			turn = (propCon * error) / 100;
			Motor.A.setSpeed(2*(targetPower - turn));
			Motor.C.setSpeed(2*(targetPower + turn));
			Motor.A.backward();
			Motor.C.backward();
		}
		
		Motor.A.stop();
		Motor.C.stop();
	}
	
	public void suppress()
	{
		suppressed = true;
	}
}