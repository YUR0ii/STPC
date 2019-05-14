package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class CammyStage extends Stage
{
	public CammyStage()
	{
		length = 700;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/cammy.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "Cammy Stage";
	}
}
