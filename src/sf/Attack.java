package sf;

public class Attack
{
	String name;
	Animation animation;
	boolean proj = false;
	Projectile projectile;
	int range;
	
	Attack(String name, Animation animation)
	{
		this.name = name;
		this.animation = animation;
		range = -1;
	}


	Attack(String name, Animation animation, int range)
	{
		this.name = name;
		this.animation = animation;
		this.range = range;
	}
	
	Attack(String name, Animation animation, Projectile p)
	{
		this.name = name;
		this.animation = animation;
		this.proj = true;
		projectile = p;
	}
}