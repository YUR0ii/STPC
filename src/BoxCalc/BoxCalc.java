package BoxCalc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.*;

public class BoxCalc extends JFrame
{
	BufferedImage sprite;
	BufferedImage visualization;
	Point offset = new Point(0,0);
	display d;
	static final Color HIT = new Color(255,0,0);
	static final Color HURT = new Color(0,0,255);
	static final Color PUSH = new Color(0,255,0);
	static final Color PROJ = new Color(255,102,0);
	static final Color PROJVULN = new Color(0,255,255);
	static final Color THROW = new Color(255,255,0);
	static final Color GTHROW = new Color(254,254,254);
	static final Color ATHROW = new Color(1,1,1);

	static final Color[] COLORS = new Color[] {HIT, HURT, PUSH, PROJ, PROJVULN, THROW, GTHROW, ATHROW};
	static final String[] COLORNAMES = new String[] {"Hitbox", "Hurtbox", "Pushbox", "Projectile", "Projectile Vulnerable", "Throwbox", "Grounded Throwable", "Aerial Throwable"};

	BoxCalc()
	{
		try
		{
			sprite = ImageIO.read(chooseFile("Choose Sprite"));
			visualization = ImageIO.read(chooseFile("Choose Hitboxes"));
		}
		catch(Exception boxE)
		{
			System.out.println("File Inavlid");
		}


		d = new display();
		JButton spriteBtn = new JButton("Add Sprite");
		JButton hitboxBtn = new JButton("Add Hitboxes");
		JButton calcBtn = new JButton("calculate");
		JSlider xSlider =  new JSlider(JSlider.HORIZONTAL, -(visualization.getWidth()-sprite.getWidth()), visualization.getWidth()-sprite.getWidth(), 0);
		JSlider ySlider = new JSlider(JSlider.VERTICAL, -(visualization.getHeight()-sprite.getHeight()), visualization.getHeight()-sprite.getHeight(), 0);
		JTextArea results = new JTextArea("Results");
		setSize(400,400);
		setLayout(new FlowLayout());
		add(spriteBtn);
		add(hitboxBtn);
		add(calcBtn);
		add(results);
		add(ySlider);
		add(d);
		add(xSlider);
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
				results.setText("");

				ArrayList<ArrayList<Rectangle>> boxes = new ArrayList<ArrayList<Rectangle>>(8);
				ArrayList<Point> ignore;
				
				for(int i = 0; i < 8; i++)
				{
					results.append(COLORNAMES[i] + ":\n");
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
						}
					}

					for(Rectangle r : boxes.get(i))
					{
						results.append("Position: (" + (r.x + offset.x) + "," + (r.y + offset.y) + "); Size: (" + r.width + "," + r.height + ")\n");
					}

					if(boxes.get(i).isEmpty())
						results.append("N/A");
				}

			}
		});

		xSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent changeEvent)
			{
				offset.x = xSlider.getValue();
				repaint();
			}
		});

		ySlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent changeEvent)
			{
				offset.y = ySlider.getValue();
				repaint();
			}
		});
	}

	Rectangle findBox(BufferedImage viz, Color c, ArrayList<Rectangle> ignore)
	{
		Point topLeft = null;
		Point bottomRight = null;

		int x = 0;
		int y = 0;

		boolean done;
		do
		{
			done = true;
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
			
			if(y >= viz.getHeight())
			{
//				y = viz.getHeight()-1;
				return null;
			}
		}

		bottomRight = new Point(right, y);
		return new Rectangle(topLeft.x, topLeft.y, bottomRight.x-topLeft.x, bottomRight.y-topLeft.y);
	}

	class display extends JPanel
	{
		@Override
		protected void paintComponent(Graphics graphics)
		{
			super.paintComponent(graphics);
			Graphics2D g = (Graphics2D) graphics;

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, .5f));
			g.scale(5,5);
			g.drawImage(sprite, 0, 0, this);
			g.drawImage(visualization, offset.x, offset.y, this);
			setPreferredSize(new Dimension(visualization.getWidth() * 5, visualization.getHeight() * 5));
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