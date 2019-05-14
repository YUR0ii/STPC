package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class SagatStage extends Stage
{
	public SagatStage()
	{
		length = 679;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/sagat.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "Sagat Stage";
	}
}
