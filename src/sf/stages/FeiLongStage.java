package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class FeiLongStage extends Stage
{
	public FeiLongStage()
	{
		length = 695;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/fei_long.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "Fei Long Stage";
	}
}
