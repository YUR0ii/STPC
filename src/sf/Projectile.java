package sf;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Projectile extends Hitbox
{
	public Projectile(Rectangle r, AttackType strength, int dmg, int stun, int stunTimer, boolean low, boolean knockdown)
	{
		super(r, strength, dmg, stun, stun, low, knockdown);
	}

	BufferedImage sprite;
	int speed;
	boolean right;
	int lifespan;
	private boolean delete = false;
	
	public void delete()
	{
		delete = true;
	}

	public boolean getDelete()
	{
		return delete;
	}

}
