package sf.stages;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sf.Stage;

public class MBisonStage extends Stage
{
	public MBisonStage()
	{
		length = 679;
		floorHeight = 50;
		
		try {
			sprite = ImageIO.read(new File("stages/m_bison.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		name = "M. Bison Stage";
	}
}
