package sf;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Projectile extends Hitbox
{
	BufferedImage sprite;
	int speed;
	boolean right;
	int lifespan;
	private boolean delete = false;

	public Projectile(Rectangle r, int dmg, boolean knockdown, boolean low, BufferedImage sprite, int speed, boolean right, int lifespan)
	{
		super(r, dmg, knockdown, low, true);
		this.sprite = sprite;
		this.speed = speed;
		this.right = right;
		this.lifespan = lifespan;
	}
	
	public void delete()
	{
		delete = true;
	}

	public boolean getDelete()
	{
		return delete;
	}

}
