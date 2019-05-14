package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class DeeJayStage extends Stage
{
	public DeeJayStage()
	{
		length = 680;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/dee_jay.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "Dee Jay Stage";
	}
}
