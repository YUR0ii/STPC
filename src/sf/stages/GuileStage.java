package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class GuileStage extends Stage
{
	public GuileStage()
	{
		length = 739;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/guile.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "Guile Stage";
	}
}
