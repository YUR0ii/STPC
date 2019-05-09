package sf.stages;

import java.awt.image.BufferedImage;
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
			sprite = ImageIO.read(new File("ryuStage.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		name = "Ryu Stage";
	}

}
