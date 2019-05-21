package sf;

import java.io.*;

public class Animation implements Serializable
{
	public static final long serialVersionUID = 1L;
	animFrame[] frames;
	transient int id;
	static int maxID = 0;
	Command c = null;

	public Animation(animFrame[] frames)
	{
		this.frames = frames;
	}

	public Animation(animFrame[] frames, Command c)
	{
		this.frames = frames;
		id = maxID;
		System.out.println(maxID);
		maxID++;
		this.c = c;
	}

	public animFrame getFrame(int frame)
	{
		int total = 0;
		for(animFrame a : frames)
		{
			total += a.frameCount;
			if(frame <= total)
				return a;
		}

		return null;
	}

	@Override
	public boolean equals(Object o)
	{
		return false;
	}
	public boolean equals(Animation a)
	{
		return this.id == a.id;
//		return false;
	}
	
	public void customEvents(Player parent, int frame)
	{
		if(c != null)
			c.customEvents(parent, frame);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		id = maxID;
		System.out.println(maxID);
		maxID++;
	}
}