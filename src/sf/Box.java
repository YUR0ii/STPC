package sf;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

public class Box extends Rectangle
{
	public enum BoxType {HIT, HURT, PUSH, PROJ, THROW, THROWABLEG, THROWABLEA}
	public BoxType type;

	public Box(BoxType t, Rectangle r)
	{
		super(r);
		type = t;
	}
	
	public Box(Box b)
	{
		super(b);
		type = b.type;
	}
	
	public boolean testCollision(Player opponent, BoxType testType)
	{
		for(Box h : opponent.boxes())
		{
			if (h.type == testType && this.intersects(h))
			{
				return true;
			}
		}
		return false;
	}
}
