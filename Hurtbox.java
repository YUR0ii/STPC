package sf;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Hurtbox extends Rectangle
{
	Point offset;
	public boolean invuln = false;

	public Hurtbox(Point point, Dimension dimension)
	{
		super(new Point(0,0), new Dimension(dimension.width * 4, dimension.height * 4));
		offset = new Point(point.x * 4, point.y * 4);
	}
	
	public Hurtbox(Point point, Dimension dimension, boolean invuln)
	{
		super(new Point(0,0), new Dimension(dimension.width * 4, dimension.height * 4));
		offset = new Point(point.x * 4, point.y * 4);
		this.invuln = invuln;
	}

}
