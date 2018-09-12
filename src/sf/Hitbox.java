package sf;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hitbox extends Rectangle
{
	boolean knockdown;
	int dmg;
	int blockDmg;
	int activeFrames;
	int startup;
	boolean hitsLow = false;
	AttackType type;
	Point offset;
	Dimension size;
	boolean active = false;
	int direction = 0;
	int speed = 0;
	BufferedImage image;

	private boolean delete = false;
	
	public Hitbox(Hitbox h)
	{
		super(new Point(0,0), new Dimension(h.size.width * 4, h.size.height * 4));
		offset = new Point(h.offset.x * 4, h.offset.y * 4);
		size = new Dimension(h.size.width * 4, h.size.height * 4);
		knockdown = h.knockdown;
		dmg = h.dmg;
		blockDmg = h.blockDmg;
		activeFrames = h.activeFrames;
		startup = h.startup;
		type = h.type;
		speed = h.speed;
		direction = h.direction;
		hitsLow = h.hitsLow;
		this.image = h.image;
	}

	public Hitbox(Point offset, Dimension size, AttackType type, int startup, int activeFrames, int dmg, boolean knockdown, boolean hitsLow)
	{
		this.offset = offset;
		this.size = new Dimension((int) (size.width * 1.1), size.height);
		this.knockdown = knockdown;
		this.dmg = dmg;
		this.blockDmg = 0;
		this.activeFrames = activeFrames;
		this.startup = startup;
		this.type = type;
		this.hitsLow = hitsLow;
	}
	
	public Hitbox(Point offset, Dimension size, int dmg, int frames, int startup, boolean forward)
	{
		this.offset = offset;
		this.size = size;
		this.dmg = dmg;
		this.activeFrames = frames;
		this.startup = startup;
		this.type = AttackType.G;
		if(forward)
			direction = 1;
		else
			direction = -1;
	}
	
	public Hitbox(Point offset, Dimension size, int dmg, int blockDmg, int startup, int speed, BufferedImage image)
	{
		this.offset = offset;
		this.size = size;
		this.knockdown = false;
		this.dmg = dmg;
		this.blockDmg = blockDmg;
		this.activeFrames = -1;
		this.startup = startup;
		this.type = AttackType.S;
		this.speed = speed;
		this.direction = 1;
		this.image = image;
	}
	
	public Hitbox(Point point, Dimension dimension)
	{
		super(point, dimension);
		offset = point;
		size = dimension;
	}
	
	public boolean testClank(Hitbox h)
	{
			if (this.intersects(h) && h.speed != 0)
			{
				return true;
			}
		return false;
	}

	public void delete()
	{
		delete = true;
	}

	public boolean getDelete()
	{
		return delete;
	}

	public boolean testCollision(Player opponent)
	{
			for(Hurtbox h : opponent.body.body)
			{
				if (this.intersects(h) && !h.invuln)
				{
					return true;
				}
			}
			return false;
	}
	
	public int stunCalc(Player opponent)
	{
		switch(type)
		{
		case L:
			return 11;
		case M:
			return 16;
		case H:
			return 20;
		case JL:
			return 11;
		case JM:
			if(opponent.crouching || opponent.blocking)
				return 16;
			else
				return 11;
		case JH:
			if(opponent.crouching)
				return 21;
			else if (opponent.blocking)
				return 20;
			else
				return 11;
		case O:
			if(opponent.crouching)
				return 20;
			else
				return 11;
		case S:
			return 20;
		default:
			return 0;
		}
		
	}

}
