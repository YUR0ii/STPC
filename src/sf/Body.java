package sf;

public class Body
{
	Hurtbox[] body;
	int width;
	int height;
	
	Body(Hurtbox[] body)
	{
		this.body = body;
		
		int maxx = 0;
		int maxy = 0;
		for(Hurtbox h : this.body)
		{
			if(h.offset.x + h.width > maxx)
				maxx = h.offset.x + h.width;
			if(h.offset.y + h.height > maxy)
				maxy = h.offset.y + h.height;
			
			h.offset.y += h.height;
		}
		width = maxx;
		height = maxy;
	}
}
