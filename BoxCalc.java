package BoxCalculator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.filechooser.*;

public class BoxCalc extends JFrame
{
	BufferedImage sprite;
	BufferedImage visualization;
	display d;
	static final Color HIT = new Color(255,0,0);
	static final Color HURT = new Color(0,0,255);
	static final Color PUSH = new Color(0,255,0);
	static final Color PROJ = new Color(255,102,0);
	static final Color PROJVULN = new Color(0,255,255);
	static final Color THROW = new Color(255,255,0);
	static final Color GTHROW = new Color(254,254,254);
	static final Color ATHROW = new Color(1,1,1);
//	static final Color AXIS = new Color(127,127,127);

	//	, AXIS,
	static final Color[] COLORS = new Color[] {HIT, HURT, PUSH, PROJ, PROJVULN, THROW, GTHROW, ATHROW};
	static final String[] COLORNAMES = new String[] {"Hitbox", "Hurtbox", "Pushbox", "Projectile", "Projectile Vulnerable", "Throwbox", "Grounded Throwable", "Aerial Throwable"};

	BoxCalc()
	{
		d = new display();
		JButton spriteBtn = new JButton("Add Sprite");
		JButton hitboxBtn = new JButton("Add Hitboxes");
		JButton calcBtn = new JButton("calculate");
		setSize(400,400);
		setLayout(new FlowLayout());
		add(spriteBtn);
		add(hitboxBtn);
		add(calcBtn);
		add(d);
		setTitle("Box Calculator");
		setVisible(true);
		setDefaultCloseOperation(3);

		spriteBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					sprite = ImageIO.read(chooseFile("Choose Sprite"));
					d.repaint();
				}
				catch(Exception spriteE)
				{
					System.out.println("File Inavlid");
				}
			}
		});

		hitboxBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					visualization = ImageIO.read(chooseFile("Choose Hitboxes"));
					d.repaint();
				}
				catch(Exception boxE)
				{
					System.out.println("File Inavlid");
				}
			}
		});

		calcBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
//				Point Axis = calculateAxis(visualization);
				int xDiff = visualization.getWidth()-sprite.getWidth();
				int yDiff = visualization.getHeight()-sprite.getHeight();
				
				ArrayList<ArrayList<Rectangle>> boxes = new ArrayList<ArrayList<Rectangle>>(8);
				ArrayList<Point> ignore;
				
				for(int i = 0; i < 8; i++)
				{
					boxes.add(new ArrayList<Rectangle>());
					boolean done = false;
					while(!done)
					{
						Rectangle r = findBox(visualization, COLORS[i], boxes.get(i));
						if(r == null)
							done = true;
						else
						{
							boxes.get(i).add(r);
							System.out.println(COLORNAMES[i] + " " + r);
						}
					}
					d.repaint();
					
					
					
//					if(points != null)
//						System.out.println(COLORNAMES[i] + ":\nTop-left: (" + points[0].x + "," + points[0].y + ")\nBottom-right: (" + points[1].x + "," + points[1].y +")");
				}

			}
		});
	}

	Rectangle findBox(BufferedImage viz, Color c, ArrayList<Rectangle> ignore)
	{
		Point topLeft = null;
		Point bottomRight = null;

		int x = 0;
		int y = 0;

		boolean done = true;
		do
		{
			while(!c.equals(new Color(viz.getRGB(x, y))))
			{
				x++;

				if(x == viz.getWidth())
				{
					x = 0;
					y++;
				}
				
				if(y == viz.getHeight())
					return null;
			}

			topLeft = new Point(x,y);
			for(Rectangle r : ignore)
			{
				if(r.contains(topLeft))
					done = false;
			}
				
			if(!done)
			{
				x = 0;
				y++;
				if(y == viz.getHeight())
					return null;
			}
		} while(!done);
		

		while(c.equals(new Color(viz.getRGB(x, y))))
		{
			x++;

			if(x == viz.getWidth())
			{
				x--;
				break;
			}
		}

		int right = x;
		y++;

		while(!(c.equals(new Color(viz.getRGB(topLeft.x, y))) && (topLeft.x >= viz.getWidth() || c.equals(new Color(viz.getRGB(topLeft.x+1, y)))) && (topLeft.x <= 0 || !c.equals(new Color(viz.getRGB(topLeft.x-1,y))))))
		{
			x = topLeft.x+1;

			while(!c.equals(new Color(viz.getRGB(x, y))))
			{
				x++;
				if(x >= viz.getWidth())
				{
					x = -1;
					break;
				}
			}

			if(x > right)
				right = x;

			y++;
		}

		bottomRight = new Point(right, y);

		return new Rectangle(topLeft.x, topLeft.y, bottomRight.x-topLeft.x, bottomRight.y-topLeft.y);
	}

//	Point calculateAxis(BufferedImage e)
//	{
//		int left = 0;
//		int top = 0;
//		int right = visualization.getWidth()+1;
//		int bottom = visualization.getHeight()+1;
//
//		for(int i = 0; i < e.getWidth(); i++)
//		{
//			for(int j = 0; j < e.getHeight(); j++)
//			{
//				Color pixel = new Color(e.getRGB(i,j));
//				if(pixel.equals(AXIS))
//				{
//					if(i > left)
//						left = i;
//					if(i < right)
//						right = i;
//					if(j > top)
//						top = j;
//					if(j < bottom)
//						bottom = j;
//				}
//			}
//		}
//
//		return new Point((right+left)/2, (top+bottom)/2);
//	}

	class display extends JPanel
	{
		@Override
		protected void paintComponent(Graphics graphics)
		{
			super.paintComponent(graphics);
			Graphics2D g = (Graphics2D) graphics;

			//			if(sprite != null)
			g.drawImage(sprite, 0, 0, null);
			//			if(visualization != null)
			g.drawImage(visualization, sprite.getWidth() + (sprite.getWidth()/10), 0, null);
			setPreferredSize(new Dimension(visualization.getWidth() + sprite.getWidth() + (sprite.getWidth()/10), visualization.getHeight()));
			//			pack();
		}
	}

	public static void main(String[] args)
	{
		new BoxCalc();
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