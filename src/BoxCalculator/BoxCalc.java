package BoxCalculator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.filechooser.*;

public class BoxCalc extends JFrame
{
	BufferedImage current;

	BoxCalc()
	{
		setSize(400,400);
		add(new display());
		setTitle("Box Calculator");
		setVisible(true);
		setDefaultCloseOperation(3);
	}

	class display extends JPanel
	{
		@Override
		protected void paintComponent(Graphics graphics)
		{
			super.paintComponent(graphics);
			Graphics2D g = (Graphics2D) graphics;

			g.drawImage(current, 0, 0, null);
		}
	}

	public static void main(String[] args)
	{
		new BoxCalc().read();
	}

	void read()
	{
		try
		{
			current = ImageIO.read(chooseFile("image"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	File chooseFile(String name)
	{
		JFrame chooser = new JFrame();
		JFileChooser fileChooser = new JFileChooser("Z:\\");
		fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG file", "jpg", "jpeg"));
		fileChooser.setSelectedFile(new File("image.jpg"));
		fileChooser.setApproveButtonText(name);
		chooser.add(fileChooser);
		int returnVal = fileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			if(fileChooser.getSelectedFile().getName().contains(".jpg"))
				return fileChooser.getSelectedFile();
			else
				return new File(fileChooser.getSelectedFile().getAbsolutePath()+".jpg");
		}
		return null;
	}
}
