package sf;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Game extends JFrame
{
	Graphics2D g2;
	Player p1;
	HealthBar p1b;
	HealthBar p2b;
	Player p2;
	Player[] Players;
	public int floorLevel;
	Timer updateTimer;
	int roundTimerInt = 3;
	JLabel roundTimer = new JLabel("3");
	JLabel winText = new JLabel("");
	boolean start = false;
	InputManager inputManager;
	
	Game(Character p1, Character p2)
	{
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
	}

	class HealthBar extends Rectangle
	{
		Player p;
		boolean p1;
		
		HealthBar(Player p, boolean p1)
		{
			super(650,50);
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
			
			addComponentListener(new ComponentListener()
			{
				@Override
				public void componentHidden(ComponentEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void componentMoved(ComponentEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void componentResized(ComponentEvent e)
				{
					floorLevel = getHeight();
					for(Player p : Players)
					{
						p.location.y = floorLevel;
					}
				}

				@Override
				public void componentShown(ComponentEvent e) {
					// TODO Auto-generated method stub

				}

			});
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g2 = (Graphics2D) g;
			AffineTransform at = new AffineTransform();
			
			at.setToScale(1.25, 1.25);
			g2.drawImage(background, new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR), 0, 0);
			
			for(Player p : Players)
			{
				if(inputManager.isKeyDown(KeyEvent.VK_F9))
				{
					for (Hurtbox h : p.body.body)
					{
						if(p.lag != 0)
						{
							if(p.inHitStun)
								g2.setColor(Color.RED);
							else
								if(p.blocking)
									g2.setColor(Color.ORANGE);
								else
									g2.setColor(Color.GREEN);
						}
						else if(p.blocking)
						{
							if(p.crouching)
								g2.setColor(Color.CYAN);
							else
								g2.setColor(Color.BLUE);
						}
						else if(p.crouching)
							g2.setColor(Color.MAGENTA);
						else
							g2.setColor(Color.GRAY);
						if(h.invuln)
							g2.setColor(Color.WHITE);

						g2.fill(h);
					}
					for (Hitbox h : p.hitboxes)
					{
						g2.setColor(new Color(255,0,0));
						if(h.active)
							g2.fill(h);
					}
				}
				
				
			
				
				for(Hitbox h : p.projectiles)
				{
					int lr = 0;
					if(p.right)
					{
						at.setToScale(4, 4);
					}
					else
					{
						lr = h.width;
						at.setToScale(-4, 4);
					}
					
					if(h.active)
					{
//						g2.setColor(Color.YELLOW);
//						g2.fill(h);
						g2.drawImage(h.image, new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR), h.x + lr, h.y);
					}
				}
				
				if(p.right)
					at.setToScale(4.36, 5);
				else
					at.setToScale(-4.36, 5);
				
				
				g2.drawImage(p.sprite, new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR), p.location.x, p.location.y - (p.sprite.getHeight() * 5));
			}
			
			g2.setColor(Color.red);
			g2.fill(p1b);
			g2.fill(p2b);
			winText.setFont(new Font("Monospace", 1, 150));
			winText.setLocation(800-(winText.getWidth()/2), 450-(winText.getHeight()/2));
			roundTimer.setLocation(800-(roundTimer.getWidth()/2), 0);

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

		p1 = new Player(p1Char, new Point(100,0), p1Controls, true);
		p2 = new Player(p2Char, new Point(1300,0), p2Controls, false);
		inputManager = new InputManager
				(
				new int[] {p1Controls[0], p1Controls[1], p1Controls[2], p1Controls[3]},
				new int[] {p2Controls[0], p2Controls[1], p2Controls[2], p2Controls[3]}
				);

		Players = new Player[] {p1, p2};
	}

	private void initPanel()
	{
		this.addKeyListener(inputManager);
		
		DrawPanel d = new DrawPanel();
		this.add(d);
		p1b = new HealthBar(p1, true);
		p2b = new HealthBar(p2, false);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1600,900);
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		this.setUndecorated(true);
		this.setVisible(true);
	}
	
	private void Update()
	{
//		long time = System.currentTimeMillis();
		for(Player p : Players)
		{
			p.isUpdating = false;
			p.crossedup = false;
			
			Player other;
			if(p.p1)
				other = p2;
			else
				other = p1;
			
			if(p.center > other.center)
			{
				if(p.right)
				{
					if(p.lag == 0 && !p.blocking)
						p.Flip();
					else
						p.crossedup = true;
				}
			}
			else
			{
				if(!p.right)
				{
					if(p.lag == 0 && !p.blocking)
						p.Flip();
					else
						p.crossedup = true;
				}
			}
			
			if(start)
			{
				inputManager.doDirection(p.p1);
				p.attackEvents(inputManager);
				p.movementEvents(inputManager);
			}
			
			p.Animate();
			p.posCalcs();
			
			if(!p.knockdown)
			{
				if(((p.moving != 0 && p.lag == 0)|| !p.grounded))
				{
					int newLoc;

					if(p.grounded)
						newLoc = p.location.x + (p.moving);
					else
					{
						if(p.inHitStun || p.moving == 0)
							newLoc = p.location.x;
						else
							newLoc = p.location.x + (int) Math.copySign(p.character.airSpeed, p.moving);
					}

					int newFront;
					if(p.right)
						newFront = p.front + (newLoc - p.location.x);
					else
						newFront = p.front - (p.location.x - newLoc);

					if((((p.right && newFront < other.front) || (!p.right && newFront > other.front)) || ((p.location.y < other.top) || (p.top > other.location.y))) && ((newFront > 0 && newFront < this.getWidth()) && (newLoc > 0 && newLoc < this.getWidth())))
					{
						if(p.right == other.right && p.grounded)
						{
							if((p.right && newFront < other.location.x) || (!p.right && newFront > other.location.x))
								p.location.x = newLoc;
						}
						else
						{
							p.location.x = newLoc;
						}
					}
				}

				if(p.right != other.right)
				{
					if(((p.right && p.front > other.front && p.location.x < other.location.x) || (!p.right && p.front < other.front && p.location.x > other.location.x)) && !((p.location.y < other.top) || (p.top > other.location.y)))
					{
						if (!p.grounded && other.grounded)
						{
							if(p.right)
							{
								if(p.location.x - (p.front - other.front) > 0)
									p.location.x -= p.front - other.front;
								else
									other.location.x +=  p.front - other.front;
							}
							else
							{
								if(p.location.x + (other.front - p.front) < this.getWidth())
									p.location.x +=  other.front - p.front;
								else
									other.location.x -=  other.front - p.front;
							}
						}
						else if(p.grounded && (!other.grounded && !other.knockdown))
						{
							if(other.right)
							{
								if(other.location.x - (other.front - p.front) > 0)
									other.location.x -= other.front - p.front;
								else
									p.location.x +=  other.front - p.front;
							}
							else
							{
								if(other.location.x + (p.front - other.front) < this.getWidth())
									other.location.x +=  p.front - other.front;
								else
									p.location.x -=  p.front - other.front;
							}
						}
						else if(!p.grounded && (!other.grounded && !other.knockdown))
						{
							boolean b = Math.min(p.airTime, other.airTime) == p.airTime;

							if(b)
							{
								if(p.right)
								{
									if(p.location.x - (p.front - other.front) > 0)
										p.location.x -= p.front - other.front;
									else
										other.location.x +=  p.front - other.front;
								}
								else
								{
									if(p.location.x + (other.front - p.front) < this.getWidth())
										p.location.x +=  other.front - p.front;
									else
										other.location.x -=  other.front - p.front;
								}
							}
						}
						else
						{
							if((p.isUpdating || p.knockdown) && !p.attacking)
							{
								if(p.right)
								{
									if(p.location.x - (p.front - other.front) > 0)
										p.location.x -= p.front - other.front;
									else
										other.location.x +=  p.front - other.front;
								}
								else
								{
									if(p.location.x + (other.front - p.front) < this.getWidth())
										p.location.x +=  other.front - p.front;
									else
										other.location.x -=  other.front - p.front;
								}
							}
						}
					}
				}
				else
				{
					if(other.crossedup)
					{
						if(((p.right && p.front > other.location.x) || (!p.right && p.front < other.location.x)) && !(p.location.y < other.top))
						{
							if(!p.grounded)
							{
								if(p.right)
								{
									if(p.location.x - (p.front - other.location.x) > 0)
										p.location.x -= p.front - other.location.x;
									else
										other.location.x += p.front - other.location.x;

								}
								else
								{

									if(p.location.x + (other.location.x - p.front) < this.getWidth())
										p.location.x += other.location.x - p.front;
									else
										other.location.x -= other.location.x - p.front;
								}
							}
							else
							{
								if(p.right)
								{
									if(p.location.x - (p.front - other.location.x) > 0)
										p.location.x -= p.front - other.location.x;
									else
										other.location.x +=  p.front - other.location.x;
								}
								else
								{
									if(p.location.x + (other.location.x - p.front) < this.getWidth())
										p.location.x +=  other.location.x - p.front;
									else
										other.location.x -=  other.location.x - p.front;
								}
							}
						}
					}
				}
				
				boolean clank = false;
				for(Iterator<Hitbox> i = p.hitboxes.iterator(); i.hasNext();)
				{
					Hitbox h = i.next();

					if(clank)
						i.remove();
					else
					{
						if(h.startup == 1)
						{
							h.active = true;
							h.y = p.location.y - h.offset.y - h.height;
							if(p.right)
								h.x = p.location.x + h.offset.x;
							else
								h.x = p.location.x - h.offset.x - h.width;

							if(h.testClank(p, other))
							{
								i.remove();
								clank = true;
							}

							if(h.testCollision(other))
								p.Hit(other, h);

							if(h.activeFrames == 0)
								i.remove();
							else
								h.activeFrames--;
						}
						else
							h.startup--;
					}
					
					
				}
				
				clank = false;
				for(Iterator<Hitbox> i = p.projectiles.iterator(); i.hasNext();)
				{
					Hitbox h = i.next();

					if(h.startup == 0)
					{
						h.active = true;
						if(h.direction == 1)
							h.x += h.speed;
						else
							h.x -= h.speed;

						if(h.testCollision(other))
							p.Hit(other, h);
						
						if(h.getMaxX() < 0 || h.getMinX() > this.getWidth() || h.activeFrames == 0 || h.testClank(p, other))
							i.remove();
					}
					else
					{
						h.startup--;
						h.y = p.location.y - h.offset.y;
						if(p.right)
							h.x = p.location.x + h.offset.x;
						else
							h.x = p.location.x - h.offset.x - h.width;
					}
					
				}

				if(p.jumpSquat)
				{
					if(p.lag == 0)
					{
						p.grounded = false;
						p.jumpSquat = false;
					}
				}
			}
			else
			{
				
			}
			
			
			
//			if(p.location.x < 0 || p.location.x > this.getWidth())
//			{
//				p.location.x -= p.location.x/4;
//				other.location.x -= p.location.x/4;
//			}
			if(p.front < 0 || p.front > this.getWidth())
			{
				p.location.x -= p.front/4;
				other.location.x -= p.front/4;
			}
			
			if(!p.grounded)
			{
				p.airTime++;
				p.location.y = floorLevel - (int) ( (75 * p.airTime) - (Math.pow((double) p.airTime * 1.5, 2)) );
				p.grounded = p.checkGrounded(floorLevel);
				if(p.knockdown)
				{
					if(p.right && p.location.x - 5 > 0)
						p.location.x -= 5;
					else if(p.location.x + 5 < this.getWidth())
						p.location.x += 5;
				}
				if(p.grounded)
				{
					if(!p.knockdown)
					{
						p.hitboxes.clear();
						p.lag = 4;
					}
					else
					{
						p.Wakeup(inputManager);
						p.lag = p.character.Wakeup.maxFrame;
					}
					p.location.y = floorLevel;
					p.moving = 0;
				}
			}

			
			if(p.lag != 0)
			{
				if(p.inHitStun)
				{
					if(p.crouching)
						p.setAnim(p.character.DamageC);
					else
						p.setAnim(p.character.Damage);
				}
				p.lag--;
			}
			else if(p.jumpSquat)
				p.grounded = false;
			else if(p.grabbed)
				p.Knockdown();
			else if(p.inHitStun)
				p.inHitStun = false;
			else if(p.attacking)
				p.attacking = false;
//			else if(p.knockdown)
//			{
//				p.Wakeup(e);
//			}
			
			p.bodyPosUpdate();
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
}