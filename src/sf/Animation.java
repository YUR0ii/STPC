package sf;

public class Animation
{
	animFrame[] frames;
	int[] keyframes;
	int id;
	static int maxID = 0;
	
	Animation(int[] keyframes, animFrame[] frames)
	{
		this.id = maxID;
		System.out.println(maxID);
		maxID++;
		this.keyframes = keyframes;
		this.frames = frames;
	}
	
	//TODO clean this up
	public animFrame getFrame(int frame)
	{
		for(int i = 0; i < keyframes.length; i++)
			if(keyframes[i] > frame)
				return frames[i+1];
		
		return frames[0];
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
	
	void customEvents(Player parent, int frame) {};
}