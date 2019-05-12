package sf;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

public class Box extends Rectangle implements Serializable
{
	public static final long serialVersionUID = 1L;
	public enum BoxType {HIT, HURT, PUSH, PROJ, THROW, THROWABLEG, THROWABLEA}
	public BoxType type;
	final Point offset;

	public Box(BoxType t, Rectangle r)
	{
		super(r);
		type = t;
		offset = r.getLocation();
	}
	
	public Box(Box b)
	{
		super(b);
		type = b.type;
		offset = b.offset;
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
