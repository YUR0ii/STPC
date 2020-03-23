package sf;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import BoxCalc.BoxCalc;

//http://zachd.com/nki/ST/data.html
//https://classicreload.com/super-street-fighter-2-turbo.html
//TODO all currently existing animations have the wrong xcoords because HHHHHHHHHH
public class Game extends JFrame
{
	Graphics2D g2;
	Player p1;
	Player p2;
	HealthBar p1b;
	HealthBar p2b;
	InputManager p1C;
	InputManager p2C;
	Player[] Players;
	Timer updateTimer;
	Timer roundTimerT;
	int roundTimerInt = 3;
	JLabel roundTimer = new JLabel("3");
	JLabel winText = new JLabel("");
	boolean running = false;
	boolean debug = false;
	double screenScale;
	final Dimension defaultRes = new Dimension(384,224);
	Stage stage;
	boolean versus;

	Game(Character p1, Character p2, Stage stage, boolean versus)
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.versus = versus;
		this.stage = stage;
		initCharacters(p1, p2);
		initPanel();
		updateTimer = new Timer();
		roundTimerT = new Timer();
		updateTimer.scheduleAtFixedRate(new TimerTask(){@Override public void run(){Update();}}, 0, 17);
		roundTimerT.scheduleAtFixedRate(new TimerTask(){@Override public void run()
		{
			roundTimerInt--;
			if(!running)
			{
			    if(roundTimerInt > 0)
                    roundTimer.setText(Integer.toString(roundTimerInt));
				if(roundTimerInt == 0)
				{
					roundTimerInt = 99;
					running = true;
                    roundTimer.setText(Integer.toString(roundTimerInt));
				}
			}
			else
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
				if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE)
					dispose();
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
				this.x = scale(384);
		}

		public void Update()
		{
			this.width = (int) (scale(150 * ((double) p.getHealth() / 144)));

			if(!p1)
				//				this.x = 1600-this.width;
				this.x = scale(384)-this.width;
		}
	}

	class DrawPanel extends JPanel
	{
		private BufferedImage background;
		DrawPanel()
		{
			background = stage.sprite;
			
			this.add(roundTimer);
			roundTimer.setBounds(scale(180), 0, scale(12), scale(12));
			roundTimer.setFont(new Font("Monospace", 1, scale(12)));
			roundTimer.setForeground(Color.WHITE);

			this.add(winText);
			winText.setFont(new Font("Monospace", 1, scale(36)));
			winText.setLocation(scale(144), scale(144));
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
					for (Box h : p.boxes())
					{
						switch(h.type)
						{
						case HIT:
							g2.setColor(BoxCalc.HIT);
							break;
						case HURT:
							g2.setColor(BoxCalc.HURT);
							break;
						case PUSH:
							g2.setColor(BoxCalc.PUSH);
							break;
						case PROJ:
							g2.setColor(BoxCalc.PROJ);
							break;
						case THROW:
							g2.setColor(BoxCalc.THROW);
							break;
						case THROWABLEG:
							g2.setColor(BoxCalc.GTHROW);
							break;
						case THROWABLEA:
							g2.setColor(BoxCalc.ATHROW);
							break;

						}
						//						g2.fill(new Rectangle(new Point(scale(h.x), scale(h.y)), new Dimension(scale(h.width), scale(h.height))));
						g2.draw(new Rectangle(new Point(scale(h.x),scale(-h.y - h.height) + (getHeight() - stage.floorHeight)), new Dimension(scale(h.width),scale(h.height))));
						//						System.out.println(h.x + "," + h.y + " " + h.width + "," + h.height);
					}
					for(Box h : p.projectiles)
					{
						g2.setColor(Color.YELLOW);
						g2.draw(h);
					}
				}
				//</editor-fold>

				if(p.facingR())
					at.setToScale(screenScale, screenScale);
				else
					at.setToScale(-screenScale, screenScale);

				for(Projectile h : p.projectiles)
					g2.drawImage(h.sprite, new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR), scale(h.x), scale(h.y)); scale(p.getY()- (p.sprite().getHeight() * screenScale));
					g2.drawImage(p.sprite(), new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR), scale(p.getX() + p.hitlagShake.x - (p.getSpriteOffset().x*p.rMult())), scale(p.getY() - p.getSpriteOffset().y  + p.hitlagShake.y) + (getHeight() - stage.floorHeight));
					//				g2.drawImage(p.sprite(), p.getX(), 0, this);
					//				System.out.println(scale(p.getX() + p.hitlagShake.x) + "," + scale(p.getY()) + (getHeight() - stage.floorHeight));
			}
			//			System.out.println(p1.getX() + "," + p1.getY() + " " + p2.getX() + "," + p2.getY());

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
		HumanInputManager p1CH = new HumanInputManager(p1Controls);
		p1C = p1CH;
		addKeyListener(p1CH);

		if(versus)
		{
			HumanInputManager p2CH = new HumanInputManager(p2Controls);
			p2C = p2CH;
			addKeyListener(p2CH);
		}
		else
			p2C = new BogoBoxer();

		p1 = new Player(p1Char, new Point(80,0), p1C, true);
		p2 = new Player(p2Char, new Point(300,0), p2C, false);

		Players = new Player[] {p1, p2};
	}

	private void initPanel()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//		this.setSize(1600,900);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setVisible(true);

		screenScale = ((double) getWidth()) / ((double) defaultRes.getWidth());
		//		screenScale = 4;
		//		System.out.println(screenScale);

		DrawPanel d = new DrawPanel();
		this.add(d);
		p1b = new HealthBar(p1, true);
		p2b = new HealthBar(p2, false);
	}

	private void Update()
	{
		//		long time = System.currentTimeMillis();
		for(Player p : Players)
		{

			Player other;
			if(p.isP1())
				other = p2;
			else
				other = p1;
            if(running)
            {
                p.readInput();

                if (p.movementActionable())
                    p.checkMovement();

				if (p.commandActionable())
					if(!p.checkCommands() && p.normalActionable())
						p.checkNormals(Math.abs(p1.getX() - p2.getX()));
            }

			if(!p.hitThisFrame())
				p.hitboxCalc(other);

			p.Animate();
			p.checkFlip(other.getX(), other.isGrounded());
			p.posUpdate();
		}

		doMovement();

		p1b.Update();
		p2b.Update();

//		System.out.println(p1.getX() + " " + p2.getX());

		//TODO game end
		if(running && (roundTimerInt == 0 || p1.getHealth() <= 0 || p2.getHealth() <= 0))
			gameEnd();

		if(roundTimerInt < -5)
		    dispose();

		this.repaint();
		//		System.out.println(System.currentTimeMillis() - time);
	}

	//TODO corner jumps
	void doMovement()
	{
		Player r;
		Player l;
		if(p1.getX() < p2.getX())
		{
			r = p1;
			l = p2;
		}
		else
		{
			r = p2;
			l = p1;
		}

		if(r.pushbox.getMinX() <= 0)
			r.moveX(-((int) r.pushbox.getMinX()));

		if(l.pushbox.getMaxX() >= defaultRes.width)
			l.moveX(defaultRes.width - (int) (l.pushbox.getMaxX()));

		if(r.pushbox.intersects(l.pushbox))
		{
			int diff = (int) (l.pushbox.getMinX() - r.pushbox.getMaxX()) + 1;

			if(r.pushbox.getMinX() - (diff/2) < 0)
				l.moveX(-diff);
			else if(l.pushbox.getMaxX() + (diff/2) > defaultRes.width)
				r.moveX(diff);
			else
			{
				l.moveX(-diff/2);
				r.moveX(diff/2);
			}
		}

		int p1dx = p1.moving/10;
		int p1dy = p1.dy();
		int p2dx = p2.moving/10;
		int p2dy = p2.dy();

		Rectangle newP1 = new Rectangle(p1.pushbox.x+p1dx, p1.pushbox.y + p1dy, (int) p1.pushbox.getWidth(), (int) p1.pushbox.getHeight());
		Rectangle newP2 = new Rectangle(p2.pushbox.x + p2dx, p2.pushbox.y + p2dy, (int) p2.pushbox.getWidth(), (int) p2.pushbox.getHeight());

		if(newP1.getMinX() <= 0 || newP1.getMaxX() >= 384)
			p1dx = 0;
		if(newP2.getMinX() <= 0 || newP2.getMaxX() >= 384)
			p2dx = 0;

		if(newP1.intersects(newP2))
		{
			if(Math.abs(p1dx) < Math.abs(p2dx))
			{
				p2dx = (int) Math.copySign(p1.character.bSpeed/15, p2dx);
				p1dx = p2dx;
			}
			else if(Math.abs(p2dx) < Math.abs(p1dx))
			{
				p1dx = (int) Math.copySign(p2.character.bSpeed/15, p1dx);
				p2dx = p1dx;
			}
			else
				{
					p1dx = p1dx + p2dx;
					p2dx = p1dx;
				}

			newP1 = new Rectangle(p1.pushbox.x+p1dx, p1.pushbox.y + p1dy, (int) p1.pushbox.getWidth(), (int) p1.pushbox.getHeight());
			newP2 = new Rectangle(p2.pushbox.x + p2dx, p2.pushbox.y + p2dy, (int) p2.pushbox.getWidth(), (int) p2.pushbox.getHeight());

			if(newP1.getMinX() <= 0 || newP1.getMaxX() >= 384 || newP2.getMinX() <= 0 || newP2.getMaxX() >= 384)
			{
				p1dx = 0; p2dx = 0;
			}
		}

		if(p1.hitThisFrame() || p2.hitThisFrame())
		{
			p1dx += (int) ((double) p1.hitpush / 100 * p1.getHitlag());
			p2dx += (int) ((double) p2.hitpush / 100 * p2.getHitlag());

			newP1 = new Rectangle(p1.pushbox.x + p1dx, p1.pushbox.y + p1dy, (int) p1.pushbox.getWidth(), (int) p1.pushbox.getHeight());
			newP2 = new Rectangle(p2.pushbox.x + p2dx, p2.pushbox.y + p2dy, (int) p2.pushbox.getWidth(), (int) p2.pushbox.getHeight());

			if (newP1.getMinX() <= 0 || newP1.getMaxX() >= 384)
			{
				p2dx = -p1dx;
				p1dx = 0;
			}

			if(newP2.getMinX() <= 0 || newP2.getMaxX() >= 384)
			{
				p1dx = -p2dx;
				p2dx = 0;
			}
		}

		p1.moveX(p1dx);
		p2.moveX(p2dx);
		p1.moveY(p1dy);
		p2.moveY(p2dy);
	}

	void gameEnd()
	{
//	    System.out.println(roundTimerInt);
	    running = false;
	    if(roundTimerInt <= 0)
	        roundTimer.setText("TIME UP");
	    roundTimerInt = -1;

	    if(p1.getHealth() > p2.getHealth())
        {
            winText.setText("PLAYER 1 WINS");
            p1.win();
            p2.lose();
        }
	    else if(p1.getHealth() < p2.getHealth())
        {
            winText.setText("PLAYER 2 WINS");
            p1.lose();
            p2.win();
        }
	    else
        {
            winText.setText("DRAW");
            p1.lose();
            p2.lose();
        }

		winText.setVisible(true);
	}

	private int scale(double num)
	{
		return (int) (num * screenScale);
	}

	public static void main(String[] args)
	{
		new Game(new sf.chars.Ryu(), new sf.chars.Ryu(), new sf.stages.RyuStage(), true);
	}
}