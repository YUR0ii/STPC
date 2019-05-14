package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class ChunLiStage extends Stage
{
	public ChunLiStage()
	{
		length = 621;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/chun_li.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "Chun Li Stage";
	}
}
