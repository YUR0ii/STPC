package sf;

public class Attack
{
	String Name;
	Hitbox[] Hitboxes;
	Animation animation;
	int faf;
	boolean proj = false;
	
	Attack(String Name, Hitbox[] Hitboxes, Animation animation, int faf)
	{
		this.Name = Name;
		this.Hitboxes = Hitboxes;
		this.animation = animation;
		this.faf = faf;
	}
	
	Attack(String Name, Hitbox[] Hitboxes, Animation animation, int faf, boolean proj)
	{
		this.Name = Name;
		this.Hitboxes = Hitboxes;
		this.animation = animation;
		this.faf = faf;
		this.proj = true;
	}
}