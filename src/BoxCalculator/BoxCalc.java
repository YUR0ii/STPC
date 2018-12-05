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
	static final Color HIT = new Color(255,0,0);
	static final Color HURT = new Color(0,0,255);
	static final Color PUSH = new Color(0,255,0);
	static final Color PROJ = new Color(255,102,0);
	static final Color PROJVULN = new Color(0,255,255);
	static final Color THROW = new Color(255,255,0);
	static final Color GTHROW = new Color(254,254,254);
	static final Color ATHROW = new Color(1,1,1);
	static final Color AXIS = new Color(127,127,127);


	BoxCalc()
	{
		setSize(400,400);
		add(new display());
		setTitle("Box Calculator");
		setVisible(true);
		setDefaultCloseOperation(3);
	}

	void calculate(BufferedImage e)
	{
		for(int i = 0; i < e.getWidth(); i++)
		{
			for(int j = 0; j < e.getHeight(); j++)
			{
				Color pixel = new Color(e.getRGB(i,j));
				System.out.println(pixel);
			}
		}

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

		repaint();
		pack();
	}

	File chooseFile(String name)
	{
		JFrame chooser = new JFrame();
		JFileChooser fileChooser = new JFileChooser("Z:\\");
		fileChooser.setFileFilter(new FileNameExtensionFilter("PNG file", "png"));
		fileChooser.setSelectedFile(new File("image.png"));
		fileChooser.setApproveButtonText(name);
		chooser.add(fileChooser);
		int returnVal = fileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			if(fileChooser.getSelectedFile().getName().contains(".png"))
				return fileChooser.getSelectedFile();
			else
				return new File(fileChooser.getSelectedFile().getAbsolutePath()+".png");
		}
		return null;
	}
}
