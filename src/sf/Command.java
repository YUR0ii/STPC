package sf;

public class Command
{
	int[][] directions;
	int button;
	int meterCost;
	Attack attack;
	
	
	Command(int[][] directions, int button, int meterCost, Attack attack)
	{
		this.directions = directions;
		this.button = button;
		this.meterCost = meterCost;
		this.attack = attack;
		
	}
}
