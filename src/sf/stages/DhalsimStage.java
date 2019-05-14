package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class DhalsimStage extends Stage
{
	public DhalsimStage()
	{
		length = 621;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/dhalsim.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "Dhalsim Stage";
	}
}
