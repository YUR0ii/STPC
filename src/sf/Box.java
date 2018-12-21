package sf;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Box extends Rectangle
{
	enum BoxType {HIT, HURT, PUSH, PROJ, THROW, THROWABLEG, THROWABLEA}
	BoxType type;
	Point offset;
	Dimension size;

	public Box(Box h)
	{
		super(h.getLocation(), h.getSize());
		type = h.type;
	}

	public Box(Point point, Dimension dimension)
	{
		super(point, dimension);
		offset = point;
		size = dimension;
	}

	public boolean testCollision(Player opponent, BoxType testType)
	{
		for(Box h : opponent.currentFrame.boxes)
		{
			if (h.type == testType && this.intersects(h))
			{
				return true;
			}
		}
		return false;
	}

}
