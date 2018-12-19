package sf;

public class Command extends Attack
{
	//first is direction, second is frames of leniency
	int[][] directions;

	int button;
	int meterCost;
	Attack attack;
	
	
	Command(String name, Animation animation, int[][] directions, int button, int meterCost, Attack attack)
	{
		super(name, animation);
		this.directions = directions;
		this.button = button;
		this.meterCost = meterCost;
		this.attack = attack;
	}

	void customEvents(){}
}
