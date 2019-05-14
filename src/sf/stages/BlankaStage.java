package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class BlankaStage extends Stage
{
	public BlankaStage()
	{
		length = 680;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/blanka.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "Blanka Stage";
	}
}
