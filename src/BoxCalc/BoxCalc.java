package BoxCalc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import sf.*;
import sf.Box;
import sf.Box.BoxType;
import sf.Hitbox.AttackType;

public class BoxCalc extends JFrame
{

	public static final Color HIT = new Color(255,0,0);
	public static final Color HURT = new Color(0,0,255);
	public static final Color PUSH = new Color(0,255,0);
	public static final Color PROJ = new Color(255,102,0);
	public static final Color PROJVULN = new Color(0,255,255);
	public static final Color THROW = new Color(255,255,0);
	public static final Color GTHROW = new Color(254,254,254);
	public static final Color ATHROW = new Color(1,1,1);
	public static final Color AXIS = new Color(127,127,127);

	static final Color[] COLORS = new Color[] {HIT, HURT, PUSH, PROJ, PROJVULN, THROW, GTHROW, ATHROW};
	static final String[] COLORNAMES = new String[] {"Hitbox", "Hurtbox", "Pushbox", "Projectile", "Projectile Vulnerable", "Throwbox", "Grounded Throwable", "Aerial Throwable"};

	singleViz current;
	ArrayList<singleViz> vizs = new ArrayList<singleViz>();
	display d;
	ArrayList<JButton> vizChoose = new ArrayList<JButton>();

	JSlider xSlider;
	JSlider ySlider;
	JCheckBox actionableB;
	JCheckBox airborneB;
	JLabel frameLabel = new JLabel("Frame Count:");
	JSpinner frames;
	JComboBox<AttackType> type;
	JLabel damageL = new JLabel("Damage: ");
	JSpinner damage;
	JLabel stunL = new JLabel("Stun: ");
	JSpinner stun;
	JLabel stunTimerL = new JLabel("Stun Timer: ");
	JSpinner stunTimer;
	JCheckBox chCancel;
	JCheckBox spCancel;
	JCheckBox suCancel;
	JCheckBox low;
	JCheckBox knockdown;
	JButton save = new JButton("Save");
	JButton newFrame = new JButton("New Frame");

	BoxCalc()
	{
		d = new display();
		addFrame();
		switchTo(0);

		add(d);
		//		add(ySlider);
		//		add(xSlider);
		//		add(actionableB);
		//		add(frames);
		add(save);
		add(newFrame);

		setSize(800,700);
		setLayout(new FlowLayout());


		setTitle("Box Calculator");
		setVisible(true);
		setDefaultCloseOperation(3);

		save.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				saveToFile(chooseFile("Animation.anim", new FileNameExtensionFilter("Animation", "anim"), true));
				System.out.println("Saved Succesfully");
			}
		});

		newFrame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addFrame();

			}

		});
	}

	void addFrame()
	{
		singleViz s = new singleViz();
		vizs.add(s);
		final JButton newButton = new JButton(new ImageIcon(s.sprite));
		vizChoose.add(newButton);
		add(newButton);
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchTo(vizChoose.indexOf(newButton));
			}

		});

	}

	void switchTo(int index)
	{
		if(vizs.size() > 1)
			vizChoose.get(vizs.indexOf(current)).setEnabled(true);
		current = vizs.get(index);

		vizChoose.get(index).setEnabled(false);



		setupComponents();

		d.repaint();
		repaint();
	}

	void setupComponents()
	{
		setLayout(new FlowLayout());

		if(vizs.size() > 1)
		{
			remove(xSlider);
			remove(ySlider);
			remove(actionableB);
			remove(airborneB);
			remove(frameLabel);
			remove(frames);
			remove(type);
			remove(damageL);
			remove(damage);
			remove(stunL);
			remove(stun);
			remove(stunTimerL);
			remove(stunTimer);
			remove(chCancel);
			remove(spCancel);
			remove(suCancel);
			remove(low);
			remove(knockdown);
		}

		xSlider = new JSlider(JSlider.HORIZONTAL, -Math.abs(current.hitboxViz.getWidth()-current.sprite.getWidth()) - 10, Math.abs(current.hitboxViz.getWidth()-current.sprite.getWidth()) + 10, 0);
		ySlider = new JSlider(JSlider.VERTICAL, -Math.abs(current.hitboxViz.getHeight()-current.sprite.getHeight()) - 10, Math.abs(current.hitboxViz.getHeight()-current.sprite.getHeight()) + 10, 0);
		actionableB = new JCheckBox("Actionable", current.actionable);
		airborneB = new JCheckBox("Airborne", current.airborne);
		frames = new JSpinner(new SpinnerNumberModel(current.frames, 1, Integer.MAX_VALUE, 1));
		type = new JComboBox<AttackType>(AttackType.values());
		damage = new JSpinner(new SpinnerNumberModel(current.damage, 0, Integer.MAX_VALUE, 1));
		stun = new JSpinner(new SpinnerNumberModel(current.stun, 0, Integer.MAX_VALUE, 1));
		stunTimer = new JSpinner(new SpinnerNumberModel(current.stunTimer, 0, Integer.MAX_VALUE, 1));
		chCancel = new JCheckBox("Chain Cancel", current.chCancel);
		spCancel = new JCheckBox("Special Cancel", current.spCancel);
		suCancel = new JCheckBox("Super Cancel", current.suCancel);
		low = new JCheckBox("Low", current.low);
		knockdown = new JCheckBox("Knockdown", current.knockdown);

		add(ySlider);
		add(xSlider);
		add(actionableB);
		add(airborneB);
		add(frameLabel);
		add(frames);
		add(type);
		add(damageL);
		add(damage);
		add(stunL);
		add(stun);
		add(stunTimerL);
		add(stunTimer);
		add(chCancel);
		add(spCancel);
		add(suCancel);
		add(low);
		add(knockdown);



		xSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent changeEvent)
			{
				current.offset.x = xSlider.getValue();
				repaint();
			}
		});

		ySlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent changeEvent)
			{
				current.offset.y = -ySlider.getValue();
				repaint();
			}
		});

		actionableB.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				current.actionable = actionableB.isSelected();
			}
		});

		airborneB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				current.airborne = airborneB.isSelected();
			}
		});

		frames.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				current.frames = (int) frames.getValue();
			}
		});

		type.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent arg0)
			{
				current.strength = (AttackType) type.getSelectedItem();
			}
		});

		damage.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				current.damage = (int) damage.getValue();
			}
		});

		stun.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				current.stun = (int) stun.getValue();
			}
		});

		stunTimer.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				current.stunTimer = (int) stunTimer.getValue();
			}
		});

		chCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				current.chCancel = chCancel.isSelected();
			}
		});

		spCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				current.spCancel = spCancel.isSelected();
			}
		});

		suCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				current.suCancel = suCancel.isSelected();
			}
		});

		low.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				current.low = low.isSelected();
			}
		});

		knockdown.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				current.knockdown = knockdown.isSelected();
			}
		});

		validate();
		//		pack();
		repaint();
	}

	void saveToFile(File f)
	{
		animFrame[] afs = new animFrame[vizs.size()];
		for(int i = 0; i < afs.length; i++)
		{
			vizs.get(i).prepareForSave();
			afs[i] = vizs.get(i).frame;
			System.out.println(i);
		}
		Animation a = new Animation(afs);
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(a);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static Point findOrigin(BufferedImage viz)
	{
		int x = 0;
		int y = 0;
		boolean foundX = false;
		boolean foundY = false;
		Color c;

		gamer:
			for(int j = 0; j < viz.getHeight(); j++)
			{
				for(int i = 0; i < viz.getWidth(); i++)
				{
					c = new Color(viz.getRGB(i, j));
					if(close(c,AXIS))
					{
						if(!foundX)
						{
							c = new Color(viz.getRGB(i, j+1));
							if(close(c,AXIS))
							{
								x = i;
								foundX = true;
							}
						}
						else if(!foundY)
						{
							c = new Color(viz.getRGB(i+1, j));
							if(close(c,AXIS))
							{
								y = j;
								foundY = true;
							}
						}
						else
							break gamer;
					}
				}
			}

		return new Point(x,y);
	}

	static boolean isOnEdge(Point2D p, Rectangle r)
	{
		boolean top;
		boolean left;
		boolean bottom;
		boolean right;
		
		top = new Line((int) r.getMinX(),(int) r.getMinY(),(int) r.getMaxX(),(int) r.getMinY()).contains(p);
		left = new Line((int) r.getMinX(),(int) r.getMinY(),(int) r.getMinX(),(int) r.getMaxY()).contains(p);
		bottom = new Line((int) r.getMinX(),(int) r.getMaxY(),(int) r.getMaxX(),(int) r.getMaxY()).contains(p);
		right = new Line((int) r.getMaxX(),(int) r.getMinY(),(int) r.getMaxX(),(int) r.getMaxY()).contains(p);
		
		return top || left || bottom || right;
	}
	
	static Rectangle findBox(BufferedImage viz, Color c, Rectangle[] ignore)
	{
		Point topLeft = null;
		Point bottomRight = null;

		int x = 0;
		int y = 0;

		boolean done;
		do
		{
			done = true;
			while(!close(c,new Color(viz.getRGB(x, y))))
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
				if(isOnEdge(new Point2D(x, y), r))
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


		while(close(c, new Color(viz.getRGB(x, y))))
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

		while(!(close(c, new Color(viz.getRGB(topLeft.x, y))) && (topLeft.x >= viz.getWidth() || close(c, new Color(viz.getRGB(topLeft.x+1, y)))) && (topLeft.x <= 0 || !close(c, new Color(viz.getRGB(topLeft.x-1,y))))))
		{
			x = topLeft.x+1;

			while(!close(c, new Color(viz.getRGB(x, y))))
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

	static String lastLocation = "Z:\\stpcchars";

	static public File chooseFile(String name, FileFilter extension, boolean save)
	{
		String path = "Z:\\Git\\STPC\\characters";
		if(!save)
			path = lastLocation;
		JFrame chooser = new JFrame();
		JFileChooser fileChooser = new JFileChooser(path);
		fileChooser.setSelectedFile(new File(name));
		fileChooser.setApproveButtonText("Select");
		fileChooser.setFileFilter(extension);
		chooser.add(fileChooser);
		int returnVal = fileChooser.showSaveDialog(chooser);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			if(!save)
				lastLocation = fileChooser.getSelectedFile().getPath();
			return fileChooser.getSelectedFile();
		}
		return null;
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
			g.drawImage(current.hitboxViz, 0, 0, this);
			g.drawImage(current.sprite, current.offset.x, current.offset.y, this);
			for (Box h : current.boxes)
			{
				switch(h.type)
				{
				case HIT:
					g.setColor(BoxCalc.HIT);
					break;
				case HURT:
					g.setColor(BoxCalc.HURT);
					break;
				case PUSH:
					g.setColor(BoxCalc.PUSH);
					break;
				case PROJ:
					g.setColor(BoxCalc.PROJ);
					break;
				case THROW:
					g.setColor(BoxCalc.THROW);
					break;
				case THROWABLEG:
					g.setColor(BoxCalc.GTHROW);
					break;
				case THROWABLEA:
					g.setColor(BoxCalc.ATHROW);
					break;

				}
				//						g2.fill(new Rectangle(new Point(scale(h.x), scale(h.y)), new Dimension(scale(h.width), scale(h.height))));
				g.draw(new Rectangle(new Point(5*h.x,5*(-h.y - h.height + getHeight())), new Dimension(5*(h.width),5*(h.height))));
			}
			setPreferredSize(new Dimension(current.hitboxViz.getWidth() * 5, current.hitboxViz.getHeight() * 5));
		}
	}

	static boolean close(Color c, Color hc)
	{
		return !hc.equals(Color.WHITE) && close(hc.getRed(), c.getRed()) && close(hc.getBlue(), c.getBlue()) && close(hc.getGreen(), c.getGreen());
	}

	static boolean close(int a, int b)
	{
		return Math.abs(a-b) <= 1;
	}

	class singleViz
	{
		BufferedImage sprite;
		BufferedImage hitboxViz;
		Point offset = new Point(0,0);
		Point origin;
		animFrame frame;
		ArrayList<Box> boxes = new ArrayList<Box>();
		ArrayList<Box> hitboxes = new ArrayList<Box>();

		boolean actionable = false;
		boolean airborne = false;
		int frames = 1;
		AttackType strength = AttackType.S;
		int damage = 0;
		int stun = 0;
		int stunTimer = 0;
		boolean chCancel = false;
		boolean spCancel = false;
		boolean suCancel = false;
		boolean low = false;
		boolean knockdown = false;

		singleViz()
		{
			try
			{
				sprite = ImageIO.read(chooseFile("Sprite", new FileNameExtensionFilter("Sprite", "png"), false));
				hitboxViz = ImageIO.read(chooseFile("Hitboxes", new FileNameExtensionFilter("Hitboxes", "png"), false));
			}
			catch(Exception boxE)
			{
				System.out.println("File Inavlid");
				boxE.printStackTrace();
			}
			//			fixColor();
			origin = findOrigin(hitboxViz);
			System.out.println(origin);
			repaint();
		}

		void calculateBoxes()
		{
			ArrayList<Rectangle>[] ignore = new ArrayList[8];
			for(int i = 0; i < 8; i++)
			{
				ignore[i] = new ArrayList<Rectangle>();
				boolean done = false;
				while(!done)
				{
					Rectangle r;
					try
					{
						r = findBox(hitboxViz, COLORS[i], ignore[i].toArray(new Rectangle[0]));
					}
					catch(Exception e) {r = null;}
					if(r == null)
						done = true;
					else
					{
						//HURT, PUSH, PROJ, PROJVULN, THROW, GTHROW, ATHROW
						sf.Box.BoxType b = null;
						switch(i)
						{
						case 0:
							b = BoxType.HIT;
							break;
						case 1:
							b = BoxType.HURT;
							break;
						case 2:
							b = BoxType.PUSH;
							break;
						case 3:
							b = BoxType.PROJ;
							break;
						case 4:
							b = BoxType.HURT;
							break;
						case 5:
							b = BoxType.THROW;
							break;
						case 6:
							b = BoxType.THROWABLEG;
							break;
						case 7:
							b = BoxType.THROWABLEA;
							break;
						}
						System.out.println(b);
						ignore[i].add(r);
						Box bx = new Box(b, new Rectangle(new Point(r.x - origin.x, r.y - origin.y), new Dimension(r.width, r.height)));
						if(b == BoxType.HIT || b == BoxType.PROJ)
							hitboxes.add(bx);
						else
							boxes.add(bx);
					}
				}
			}
			for(int i = 0; i < hitboxes.size(); i++)
			{
				boxes.add(new Hitbox(hitboxes.get(i), strength, damage, stun, stunTimer, low, knockdown));
			}
		}

		void prepareForSave()
		{
			calculateBoxes();

			frame = new animFrame(actionable, airborne, chCancel, spCancel, suCancel, frames, sprite, boxes.toArray(new Box[0]), new Point(origin.x - offset.x, origin.y - offset.y));
		}
	}

	public static void main(String[] args)
	{
		new BoxCalc();
	}

}