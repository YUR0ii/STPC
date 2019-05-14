package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class KenStage extends Stage
{
	public KenStage()
	{
		length = 621;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/ken.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "Ken Stage";
	}
}
