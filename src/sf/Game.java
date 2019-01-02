package sf;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;

import sf.Box.BoxType;

//http://zachd.com/nki/ST/data.html
/*
Stage Lengths:
Ryu: 462
Ken: 457
Honda: 462
Chun: 449
Blanka: 466
Zangief: 449
Guile: 459
Dhalsim: 444
Hawk: 458
Cammy: 459
Fei Long: 458
DeeJay: 459
Boxer: 463
Claw: 443
Sagat: 445
 */
//Screen is 384x224
public class Game extends JFrame
{
	Graphics2D g2;
	Player p1;
	HealthBar p1b;
	HealthBar p2b;
	Player p2;
	Player[] Players;
	Timer updateTimer;
	int roundTimerInt = 3;
	JLabel roundTimer = new JLabel("3");
	JLabel winText = new JLabel("");
	boolean start = false;
	boolean debug = false;
	double screenScale;
	final Dimension defaultRes = new Dimension(384,224);
	Stage stage;

	Game(Character p1, Character p2, Stage stage)
	{
		this.stage = stage;
		initCharacters(p1, p2);
		initPanel();
		updateTimer = new Timer();
		updateTimer.scheduleAtFixedRate(new TimerTask(){@Override public void run(){Update();}}, 0, 17);
		updateTimer.scheduleAtFixedRate(new TimerTask(){@Override public void run()
		{
			roundTimerInt--;
			roundTimer.setText(Integer.toString(roundTimerInt));

			if(!start)
			{
				if(roundTimerInt == 0)
				{
					roundTimerInt = 99;
					start = true;
				}
			}
			roundTimer.setText(Integer.toString(roundTimerInt));
		}
		}, 1000, 1000);

		addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent keyEvent)
			{

			}

			@Override
			public void keyPressed(KeyEvent keyEvent)
			{

			}

			@Override
			public void keyReleased(KeyEvent keyEvent)
			{
				if(keyEvent.getKeyCode() == KeyEvent.VK_F9)
					debug = !debug;
			}
		});


	}

	class HealthBar extends Rectangle
	{
		Player p;
		boolean p1;

		HealthBar(Player p, boolean p1)
		{
			super(scale(150),scale(15));
			this.p = p;
			this.p1 = p1;

			if(!p1)
				this.x = 950;
		}

		public void Update()
		{
			this.width = (int) (650 * ((double) p.health / 175));
			if(!p1)
				this.x = 1600-this.width;
		}
	}

	class DrawPanel extends JPanel
	{
		private BufferedImage background;
		DrawPanel()
		{
			try{background = ImageIO.read(new File("ryuStage.jpg"));}catch(Exception e) {};
			this.add(roundTimer);
			roundTimer.setBounds(750, 0, 50, 50);
			roundTimer.setFont(new Font("Monospace", 1, 50));
			roundTimer.setForeground(Color.WHITE);

			this.add(winText);
			winText.setFont(new Font("Monospace", 1, 150));
			winText.setLocation(600, 600);
			winText.setForeground(Color.WHITE);
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g2 = (Graphics2D) g;
			AffineTransform at = new AffineTransform();
			at.setToScale(screenScale, screenScale);

			g2.drawImage(background, new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR), 0, 0);

			for(Player p : Players)
			{
				//<editor-fold desc="Hit/Hurtbox Debug">
				if(debug)
				{
					for (Box h : p.currentFrame.boxes)
					{
						switch(h.type)
						{
						case HIT:
							g2.setColor(Color.RED);
							break;
						case HURT:
							if(p.hitstun != 0)
								g2.setColor(Color.BLUE);
							else if(p.blocking)
								g2.setColor(Color.CYAN);
							else if(p.crouching)
								g2.setColor(Color.MAGENTA);
							break;
						case PUSH:
							g2.setColor(Color.GREEN);
							break;
						case PROJ:
							break;
						case THROW:
							break;
						case THROWABLEG:
							break;
						case THROWABLEA:
							break;

						}


						g2.draw(h);
					}
					for(Box h : p.projectiles)
					{
						g2.setColor(Color.YELLOW);
						g2.fill(h);
					}
				}
				//</editor-fold>

				if(!p.right)
					at.setToScale(-screenScale, screenScale);

				for(Projectile h : p.projectiles)
					g2.drawImage(h.sprite, new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR), scale(h.x), scale(h.y));
				g2.drawImage(p.currentFrame.sprite, new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR), scale(p.getX() + p.hitlagShake.x), scale(p.getX()  + p.hitlagShake.y - (p.currentFrame.sprite.getHeight() * 5)));
			}

			g2.setColor(Color.red);
			g2.fill(p1b);
			g2.fill(p2b);
			winText.setFont(new Font("Monospace", 1, scale(36)));
			winText.setLocation(scale(192)-(winText.getWidth()/2), scale(108)-(winText.getHeight()/2));
			roundTimer.setLocation(scale(192)-(roundTimer.getWidth()/2), 0);
		}  
	}

	private void initCharacters(Character p1Char, Character p2Char)
	{
		int[] p1Controls = new int[]
				{
						KeyEvent.VK_W,
						KeyEvent.VK_A,
						KeyEvent.VK_S,
						KeyEvent.VK_D,
						KeyEvent.VK_R,
						KeyEvent.VK_T,
						KeyEvent.VK_Y,
						KeyEvent.VK_F,
						KeyEvent.VK_G,
						KeyEvent.VK_H,
						KeyEvent.VK_V,
						KeyEvent.VK_B,
						KeyEvent.VK_N,
						KeyEvent.VK_M
				};

		int[] p2Controls = new int[]
				{
						KeyEvent.VK_UP,
						KeyEvent.VK_LEFT,
						KeyEvent.VK_DOWN,
						KeyEvent.VK_RIGHT,
						KeyEvent.VK_NUMPAD7,
						KeyEvent.VK_NUMPAD8,
						KeyEvent.VK_NUMPAD9,
						KeyEvent.VK_NUMPAD4,
						KeyEvent.VK_NUMPAD5,
						KeyEvent.VK_NUMPAD6,
						KeyEvent.VK_NUMPAD1,
						KeyEvent.VK_NUMPAD2,
						KeyEvent.VK_NUMPAD3,
						KeyEvent.VK_ENTER
				};

		p1 = new Player(p1Char, new Point(scale(50),0), p1Controls, true);
		p2 = new Player(p2Char, new Point(scale(300),0), p2Controls, false);

		Players = new Player[] {p1, p2};
	}

	private void initPanel()
	{
		this.addKeyListener(p1.inputs);
		this.addKeyListener(p2.inputs);

		DrawPanel d = new DrawPanel();
		this.add(d);
		p1b = new HealthBar(p1, true);
		p2b = new HealthBar(p2, false);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//		this.setSize(1600,900);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setVisible(true);
		screenScale = defaultRes.getHeight() / getSize().getHeight();
	}

	private void Update()
	{
		//		long time = System.currentTimeMillis();
		for(Player p : Players)
		{
			p.inputs.Update();
			Player other;
			if(p.p1)
				other = p2;
			else
				other = p1;

			if(p.getX() > other.getX())
				if(p.right)
					if(p.hitstun == 0)
						p.Flip();
					else
						if(!p.right)
							if(p.hitstun == 0)
								p.Flip();

			if(p.actionable())
			{
				if(!p.checkCommands())
					p.checkNormals(Math.abs(p1.getX() - p2.getX()));
				p.checkMovement();
			}
			p.Animate();
			p.posUpdate();

			//TODO Movement Here

			for(int i = 0; i < p.currentFrame.boxes.length; i++)
			{
				if(p.currentFrame.boxes[i].type == BoxType.HIT)
				{
					Hitbox h = (Hitbox) p.currentFrame.boxes[i];
					if (h.testCollision(other, BoxType.HURT))
						p.Hit(other, h);
				}
			}

			for(Iterator<Projectile> i = p.projectiles.iterator(); i.hasNext();)
			{
				Projectile h = i.next();
				if(h.getDelete())
					i.remove();
				else
				{
					if (h.right)
						h.x += h.speed;
					else
						h.x -= h.speed;

					if (h.testCollision(other, BoxType.HURT))
					{
						p.Hit(other, h);
						h.delete();
					}

					if (h.getMaxX() < 0 || h.getMinX() > this.getWidth() || h.lifespan == 0)
						h.delete();

					for (Projectile j : other.projectiles)
					{
						if (h.intersects(j))
						{
							h.delete();
							j.delete();
						}
					}
				}

			}

			if(p.jumpSquat)
			{
				if(p.hitstun == 0)
				{
					p.grounded = false;
					p.jumpSquat = false;
				}
			}


			if(p.hitstun != 0)
			{
				if(p.hitlag != 0)
				{
					p.doHitlagShake();
					p.hitlag--;
				}
				else
					p.hitstun--;

				if(p.hitstun != 0)
				{
					if (p.crouching)
						p.setAnim(p.character.DamageC);
					else
						p.setAnim(p.character.Damage);
				}
			}
		}

		p1b.Update();
		p2b.Update();

		if(p1.health <= 0 || p2.health <= 0 || roundTimerInt == 0)
			gameEnd();

		this.repaint();
		//		System.out.println(System.currentTimeMillis() - time);
	}

	void gameEnd()
	{
		start = false;
		if(p1.health > p2.health)
			winText.setText(p1.toString() + " wins");
		else
			winText.setText(p2.toString() + " wins");

		updateTimer.cancel();
	}

	private int scale(double num)
	{
		return (int) (num * screenScale);
	}
}