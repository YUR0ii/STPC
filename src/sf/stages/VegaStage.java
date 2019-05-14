package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class VegaStage extends Stage
{
	public VegaStage()
	{
		length = 590;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/vega.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "Vega Stage";
	}
}
