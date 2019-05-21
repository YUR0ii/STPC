package sf;

public abstract class Command
{
	//first is direction, second is frames of leniency
	public int[][] directions;
	public int button;
	public Animation anim;
	public boolean Super;
	
	public abstract void customEvents(Player parent, int frame);
}
