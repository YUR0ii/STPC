package sf;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class animFrame implements Serializable
{
	private static final long serialVersionUID = 4060222835319512008L;
	public transient BufferedImage sprite;
	public Box[] boxes;
	public boolean actionable;
	public int frameCount;

	public animFrame(boolean actionable, int frameCount, BufferedImage sprite, Box[] boxes)
	{
		this.actionable = actionable;
		this.frameCount = frameCount;
		this.sprite = sprite;
		this.boxes = boxes;
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