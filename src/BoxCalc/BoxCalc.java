package BoxCalc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

import sf.*;
import sf.Box;
import sf.Box.BoxType;
import sf.Hitbox.AttackType;

//TODO whole animation using multiple instances of singleViz
public class BoxCalc extends JFrame
{
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

	singleViz current;
	ArrayList<singleViz> vizs = new ArrayList<singleViz>();
	display d;
	ArrayList<JButton> vizChoose = new ArrayList<JButton>();

	JSlider xSlider;
	JSlider ySlider;
	JCheckBox actionableB;
	JSpinner frames;
	JComboBox<AttackType> type;
	JLabel damageL;
	JSpinner damage;
	JLabel stunL;
	JSpinner stun;
	JLabel stunTimerL;
	JSpinner stunTimer;
	JCheckBox chCancel;
	JCheckBox spCancel;
	JCheckBox suCancel;
	JCheckBox low;
	JCheckBox knockdown;
	JButton save = new JButton("Save");
	JButton newFrame = new JButton("New Frame");

	//TODO global setup method for each viz
	BoxCalc()
	{
		d = new display();
		addFrame();
		switchTo(0);

		add(ySlider);
		add(d);
		add(xSlider);
		add(actionableB);
		add(frames);
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
				saveToFile(chooseFile("Animation.anim", new FileNameExtensionFilter("Animation", "anim")));
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
		JButton newButton = new JButton(new ImageIcon(s.sprite));
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

		xSlider = new JSlider(JSlider.HORIZONTAL, -(current.hitboxViz.getWidth()-current.sprite.getWidth()), current.hitboxViz.getWidth()-current.sprite.getWidth(), 0);
		ySlider = new JSlider(JSlider.VERTICAL, -(current.hitboxViz.getHeight()-current.sprite.getHeight()), current.hitboxViz.getHeight()-current.sprite.getHeight(), 0);
		actionableB = new JCheckBox("Actionable", current.actionable);
		frames = new JSpinner(new SpinnerNumberModel(current.frames, 1, 255, 1));
		type = new JComboBox<AttackType>(AttackType.values());
		damageL = new JLabel("Damage: ");
		stunL = new JLabel("Stun: ");
		stunTimerL = new JLabel("Stun Timer: ");
		damage = new JSpinner(new SpinnerNumberModel(current.damage, 0, 255, 1));
		stun = new JSpinner(new SpinnerNumberModel(current.stun, 0, 255, 1));
		stunTimer = new JSpinner(new SpinnerNumberModel(current.stunTimer, 0, 255, 1));
		chCancel = new JCheckBox("Chain Cancel", current.chCancel);
		spCancel = new JCheckBox("Special Cancel", current.spCancel);
		suCancel = new JCheckBox("Super Cancel", current.suCancel);
		low = new JCheckBox("Low", current.low);
		knockdown = new JCheckBox("Knockdown", current.knockdown);

		add(ySlider);
		add(xSlider);
		add(actionableB);
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
		pack();
		repaint();
	}

	void saveToFile(File f)
	{
		animFrame[] afs = new animFrame[vizs.size()];
		for(int i = 0; i < afs.length; i++)
		{
			vizs.get(i).prepareForSave();
			afs[i] = vizs.get(i).frame;
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

	static public File chooseFile(String name, FileFilter extension)
	{
		JFrame chooser = new JFrame();
		JFileChooser fileChooser = new JFileChooser("Z:\\");
		fileChooser.setSelectedFile(new File(name));
		fileChooser.setApproveButtonText("Select");
		fileChooser.setFileFilter(extension);
		chooser.add(fileChooser);
		int returnVal = fileChooser.showSaveDialog(chooser);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
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
			g.drawImage(current.sprite, 0, 0, this);
			g.drawImage(current.hitboxViz, current.offset.x, current.offset.y, this);
			setPreferredSize(new Dimension(current.hitboxViz.getWidth() * 5, current.hitboxViz.getHeight() * 5));
		}
	}

	class singleViz
	{
		BufferedImage sprite;
		BufferedImage hitboxViz;
		Point offset = new Point(0,0);
		animFrame frame;
		ArrayList<Box> boxes = new ArrayList<Box>();
		ArrayList<Box> hitboxes = new ArrayList<Box>();

		boolean actionable = false;
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
				sprite = ImageIO.read(chooseFile("Sprite", new FileNameExtensionFilter("Sprite", "png")));
				hitboxViz = ImageIO.read(chooseFile("Hitboxes", new FileNameExtensionFilter("Hitboxes", "png")));
			}
			catch(Exception boxE)
			{
				System.out.println("File Inavlid");
				boxE.printStackTrace();
			}
			repaint();
		}

		void calculateBoxes()
		{
			for(int i = 0; i < 8; i++)
			{
				boolean done = false;
				while(!done)
				{
					Rectangle r = findBox(hitboxViz, COLORS[i], boxes.toArray(new Rectangle[0]));
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
						Box bx = new Box(b, r);
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
			for(Rectangle r : boxes)
			{
				r.x += offset.x;
				r.y += offset.y;
			}

			frame = new animFrame(actionable, frames, sprite, boxes.toArray(new Box[0]));
		}
	}

	public static void main(String[] args)
	{
		new BoxCalc();
	}

}