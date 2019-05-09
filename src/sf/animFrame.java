package sf;

import javax.imageio.ImageIO;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class animFrame implements Serializable
{
	private static final long serialVersionUID = 2800579864081696969L;
	public transient BufferedImage sprite;
	Point spriteOffset;
	public Box[] boxes;
	public boolean actionable;
	public int frameCount;
	boolean chCancel;
	boolean spCancel;
	boolean suCancel;

	public animFrame(boolean actionable, boolean chCancel, boolean spCancel, boolean suCancel, int frameCount, BufferedImage sprite, Box[] boxes, Point spriteOffset)
	{
		this.actionable = actionable;
		this.frameCount = frameCount;
		this.sprite = sprite;
		this.boxes = boxes;
		this.spriteOffset = spriteOffset;
		this.chCancel = chCancel;
		this.spCancel = spCancel;
		this.suCancel = suCancel;
	}

	private void writeObject(ObjectOutputStream out) throws IOException
	{
		out.defaultWriteObject();
		ImageIO.write(sprite, "png", out);
//		out.close();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		sprite = ImageIO.read(in);
//		in.close();
	}
	
	@Override
	public String toString()
	{
		String returned = "Actionable: " + actionable + "\nFrame Count: " + frameCount + "\n";
		
		for(Box b : boxes)
			returned += b.type + ": (" + b.x + "," + b.y + ") ; (" + b.width + "," + b.height + ")\n";
		
		return returned;
	}


}