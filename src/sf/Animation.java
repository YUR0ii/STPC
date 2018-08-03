package sf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Animation
{
	int[] keyframes;
	int maxFrame = 0;
	Body[] positions;
	BufferedImage[] images;
	int id;
	static int maxID = 0;
	
	Animation(int[] keyframes, Body[] positions, BufferedImage[] images)
	{
		this.id = maxID;
		System.out.println(maxID);
		maxID++;
		this.keyframes = keyframes;
		this.positions = positions;
		this.images = images;
		for(int i : keyframes)
		{
			if(i > maxFrame)
				maxFrame = i;
		}
	}
	
	public Body boxAnimate(int frame, Player p)
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
				return p.body;
		}

		return positions[key];
	}
	
	public BufferedImage spriteAnimate(int frame, Player p)
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
				return p.sprite;
		}
		
		return images[key];
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