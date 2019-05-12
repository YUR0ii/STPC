package sf;

import java.io.*;

public class Animation implements Serializable
{
	public static final long serialVersionUID = 1L;
	animFrame[] frames;
	transient int id;
	static int maxID = 0;

	public Animation(animFrame[] frames)
	{
		this.frames = frames;
	}

	public Animation(animFrame[] frames, boolean custom)
	{
		this.frames = frames;
		id = maxID;
		System.out.println(maxID);
		maxID++;
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

	void customEvents(Player parent, int frame) {};

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		id = maxID;
		System.out.println(maxID);
		maxID++;
	}
}