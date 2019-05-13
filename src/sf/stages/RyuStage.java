package sf.stages;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import sf.Stage;

public class RyuStage extends Stage
{
	public RyuStage()
	{
		length = 621;
		floorHeight = 50;
		try {
			sprite = ImageIO.read(new File("stages/ryu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		name = "Ryu Stage";
	}
}
