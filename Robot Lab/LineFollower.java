import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class LineFollower
{
	public static void main(String[] args)
	{
		Behavior b1 = new Follow();
		Behavior b2 = new Clap();
		
		Behavior[] bArr = {b1, b2};
		
		Arbitrator arb = new Arbitrator(bArr);
		arb.start();
	}
}