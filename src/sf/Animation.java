package sf;

public class Animation
{
	animFrame[] frames;
	int[] keyframes;
	int maxFrame = 0;
	int id;
	static int maxID = 0;
	
	Animation(int[] keyframes, animFrame[] frames)
	{
		this.id = maxID;
		System.out.println(maxID);
		maxID++;
		this.keyframes = keyframes;
		for(int i : keyframes)
		{
			if(i > maxFrame)
				maxFrame = i;
		}
		this.frames = frames;
	}
	
	public animFrame getFrame(int frame, Player p)
	{
		int key = 0;
		
		if(keyframes.length > 1)
		{
			if(frame >= keyframes[0])
			{
				for(int i = 0; i < keyframes.length; i++)
				{
					if(frame%maxFrame >= keyframes[i])
					{
						if(i == keyframes.length-1)
							key = i-1;
						else
							key = i;
					}
				}
			}
			else
				return p.currentFrame;
		}

		return frames[key];
	}
	
	@Override
	public boolean equals(Object o)
	{
		return false;
	}
	public boolean equals(Animation a)
	{
			return this.id == a.id;
	}
}