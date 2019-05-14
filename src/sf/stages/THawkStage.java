package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class THawkStage extends Stage
{
	public THawkStage()
	{
		length = 695;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/t_hawk.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "T. Hawk Stage";
	}
}
