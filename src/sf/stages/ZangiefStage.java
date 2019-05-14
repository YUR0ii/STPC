package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class ZangiefStage extends Stage
{
	public ZangiefStage()
	{
		length = 643;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/zangief.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "Zangief Stage";
	}
}
