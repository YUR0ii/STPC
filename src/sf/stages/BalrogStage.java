package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class BalrogStage extends Stage
{
	public BalrogStage()
	{
		length = 557;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/balrog.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "Balrog Stage";
	}
}
