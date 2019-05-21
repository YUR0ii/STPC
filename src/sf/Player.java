package sf;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import sf.Box.BoxType;
import sf.InputManager;

public class Player
{
	private static final int UP = 0;
	private static final int LEFT = 1;
	private static final int DOWN = 2;
	private static final int RIGHT = 3;
	

	public Character character;
	private InputManager inputs;

	private double x;
	public int getX()
	{
		return (int) x;
	}
	public void moveX(int x)
	{
		this.x += x;
	}
	private double y;
	public int getY()
	{
		return (int) y;
	}
	public void moveY(int y)
	{
		this.y -= y;
		if(pushbox.getMinY() <= 0)
		{
			dy1 = 0;
			if(hitstun == -1)
				Wakeup();
			else if(!isGrounded() && !anim.equals(character.Wakeup))
				setAnim(character.Stand);
//				System.out.println("stand");
		}
	}

	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private Animation anim;
	private animFrame currentFrame;
	public Box[] boxes()
	{
		return currentFrame.boxes;
	}
	public Rectangle pushbox;
	public BufferedImage sprite()
	{
		return currentFrame.sprite;
	}
	private int frame = 0;

	private int hitstun = 0;
	private int hitlag = 0;
	public int getHitlag()
	{
		return hitlag;
	}
	public Point hitlagShake = new Point(0,0);
	public int moving = 0;

	//SF2-like
//	private int health = 30;
	//SFV-like
		private int health = 144;
	public int getHealth()
	{
		return health;
	}

	private double meter = 0;
	private boolean right = true;
	public boolean facingR()
	{
		return right;
	}
	private boolean blocking = false;
	public boolean isBlocking()
	{
		return blocking;
	}
	private boolean crouching = false;
	public boolean isCrouching()
	{
		return crouching;
	}
	public boolean isGrounded()
	{
		return !currentFrame.airborne;
	}
	private boolean hit = false;
	public boolean hitThisFrame()
	{
		return hit;
	}
	private boolean p1;
	public boolean isP1()
	{
		return p1;
	}

	public Point getSpriteOffset()
	{
		return currentFrame.spriteOffset;
	}

	public int rMult()
	{
		if(right)
			return 1;
		else
			return -1;
	}

	Player(Character c, Point Location, InputManager manager, boolean p1)
	{
		character = c;
		inputs = manager;

		this.p1 = p1;
		anim = character.Stand;
		Animate();
		x = Location.x;
		y = Location.y;
	}

	@Override
	public String toString()
	{
		if(p1)
			return character.name + " (P1)";
		else
			return character.name + " (P2)";
	}

	public void readInput()
	{
		inputs.Update();
	}

	public boolean checkGrounded(int ground)
	{
		return y >= ground;
	}

	public boolean movementActionable()
	{
		return currentFrame.actionable && isGrounded();
	}

	public boolean commandActionable()
	{
		return currentFrame.actionable || currentFrame.spCancel;
	}

	public boolean normalActionable()
	{
		return !command && (currentFrame.actionable || (currentFrame.chCancel && hitThisFrame()));
	}

	boolean command = false;
	
	public boolean checkCommands()
	{
		for(int i = 0; i < character.Commands.length; i++)
		{
			Command c = character.Commands[i];
//			 
			if(inputs.command(c))
			{
				if(c.Super)
				{
					System.out.println("super");
					meter = 0;
					return false;
				}
				else
				{
					System.out.println("attack");
					command = true;
					attack(c);
					return true;
				}

			}
		}
		return false;
	}
	
	public void createProjectile(Projectile p)
	{
		
	}

	public void checkNormals(int distance)
	{
		distance -= Math.abs(currentFrame.spriteOffset.x);
		try
		{
			if(isGrounded())
			{
				if(!(inputs.getDir(right) == 1 || inputs.getDir(right) == 2 || inputs.getDir(right) == 3))
				{
					if(inputs.normal(InputManager.FIERCE))
						if(distance < character.fierceRange)
							attack(character.FierceCl);
						else
							attack(character.FierceFa);
					if(inputs.normal(InputManager.ROUNDHOUSE))
						if(distance < character.roundhouseRange)
							attack(character.RoundhouseCl);
						else
							attack(character.RoundhouseFa);
					if(inputs.normal(InputManager.STRONG))
						if(distance < character.strongRange)
							attack(character.StrongCl);
						else
							attack(character.StrongFa);
					if(inputs.normal(InputManager.FORWARD))
						if(distance < character.forwardRange)
							attack(character.ForwardCl);
						else
							attack(character.ForwardFa);
					if(inputs.normal(InputManager.JAB))
						if(distance < character.jabRange)
							attack(character.JabCl);
						else
							attack(character.JabFa);
					if(inputs.normal(InputManager.SHORT))
						if(distance < character.shortRange)
							attack(character.ShortCl);
						else
							attack(character.ShortFa);
				}
				else
				{
					if(inputs.normal(InputManager.FIERCE))
						attack(character.FierceC);
					if(inputs.normal(InputManager.ROUNDHOUSE))
						attack(character.RoundhouseC);
					if(inputs.normal(InputManager.STRONG))
						attack(character.StrongC);
					if(inputs.normal(InputManager.FORWARD))
						attack(character.ForwardC);
					if(inputs.normal(InputManager.JAB))
						attack(character.JabC);
					if(inputs.normal(InputManager.SHORT))
						attack(character.ShortC);
				}
			}
			else
			{
				if(inputs.normal(InputManager.FIERCE))
				    if(moving == 0)
					    attack(character.FierceA);
				    else
				        attack(character.FierceAD);
				if(inputs.normal(InputManager.ROUNDHOUSE))
                    if(moving == 0)
					    attack(character.RoundhouseA);
                    else
                        attack(character.RoundhouseAD);
				if(inputs.normal(InputManager.STRONG))
                    if(moving == 0)
				        attack(character.StrongA);
                    else
                        attack(character.StrongAD);
				if(inputs.normal(InputManager.FORWARD))
                    if(moving == 0)
					    attack(character.ForwardA);
                    else
                        attack(character.ForwardAD);
				if(inputs.normal(InputManager.JAB))
                    if(moving == 0)
					    attack(character.JabA);
                    else
                        attack(character.JabAD);
				if(inputs.normal(InputManager.SHORT))
                    if(moving == 0)
					    attack(character.ShortA);
                    else
                        attack(character.ShortAD);
			}
		}catch(Exception exc) {System.out.println("Unprogrammed Attack");}
	}

	private void Jump(int dir)
	{
		switch(dir)
		{
		case -1:
			setAnim(character.JumpB);
			break;
		case 0:
			setAnim(character.JumpN);
			break;
		case 1:
			setAnim(character.JumpF);
			break;
		}

	}

	int airborneFrames = 0;
	int dy1 = 0;
	public int dy()
	{
		if(isGrounded())
		{
			airborneFrames = 0;
			return 0;
		}
		else
			{
				airborneFrames++;
				double dy = dy1 - ((double) character.jumpHeight/484) * ((2*airborneFrames) - 44);
				if(dy1 > 0)
					dy1--;
//				System.out.println(dy);
				return (int) dy;
			}
	}

	private void attack(Animation a)
	{
		anim = a;
		frame = -1;
		if(isGrounded())
			moving = 0;
	}
	
	private void attack(Command c)
	{
		anim = c.anim;
		frame = 0;
		currentFrame = c.anim.getFrame(0);
		if(isGrounded())
			moving = 0;
	}

	//TODO crouch transition animation
	public void checkMovement()
	{
		switch(inputs.getDir(right))
		{
		case 1:
			blocking = true;
			crouching = true;
			setAnim(character.Crouching);
			moving = 0;
			break;
		case 2:
		case 3:
			blocking = false;
			crouching = true;
			setAnim(character.Crouching);
			moving = 0;
			break;
		case 4:
			setAnim(character.WalkB);
			moving = character.bSpeed;
			if(right)
				moving = -moving;

			blocking = true;
			crouching = false;
			break;
		case 6:
			setAnim(character.WalkF);
			moving = character.fSpeed;
			if(!right)
				moving = -moving;

			blocking = false;
			crouching = false;
			break;
		case 7:
			moving = character.bASpeed;
			if(right)
				moving = -moving;

			crouching = false;
			Jump(-1);
			break;
		case 8:
			moving = 0;
			Jump(0);
			crouching = false;
			break;
		case 9:
			moving = character.fASpeed;
			if(!right)
				moving = -moving;

			crouching = false;
			Jump(1);
			break;
		default:
			setAnim(character.Stand);
			moving = 0;
			blocking = false;
			crouching = false;
			break;
		}
	}

	public void checkFlip(int otherX, boolean otherGrounded)
	{
		if(isGrounded() && otherGrounded)
		{
			y = 0;
			right = otherX > x;
		}
	}

	public void posUpdate()
	{
		for(int i = 0; i < currentFrame.boxes.length; i++)
		{
			Box b = currentFrame.boxes[i];
			b.y = (int) (-getY() - b.offset.getY() - b.height);
			if (right)
				b.x = (int) (getX() + b.offset.getX());
			else
				b.x = (int) (getX() - b.offset.getX() - b.width);
		}
		for(Iterator<Projectile> i = projectiles.iterator(); i.hasNext();)
		{
			Projectile h = i.next();
			if(h.getDelete())
				i.remove();
			else
			{
				if(h.right)
					h.x += h.speed;
				else
					h.x -= h.speed;

				if (h.getMaxX() < 0 || h.getMinX() > 328 || h.lifespan == 0)
					h.delete();
			}
		}
	}

	public void hitboxCalc(Player other)
	{
		for(Box b : currentFrame.boxes)
		{
			if(b.type == Box.BoxType.HIT)
			{
				Hitbox h = (Hitbox) b;

				if (h.testCollision(other, BoxType.HURT))
				{
					other.Hit(h, this);
					this.hit = true;
					if(isGrounded())
						hitlag = 12;
				}
			}
			if(b.type == Box.BoxType.PUSH)
				pushbox = b;
		}
		for(Projectile p : projectiles)
		{
			if (p.testCollision(other, BoxType.HURT))
			{
				//TODO projectiles might have wack behavior
				other.Hit(p, this);
				p.delete();
			}
			for(Projectile q : other.projectiles)
			{
				if (p.intersects(q))
				{
					p.delete();
					q.delete();
				}
			}
		}
	}

	int hitpush = 0;
	public void Hit(Hitbox h, Player source)
	{
		if(hitstun != 0)
			hitlag = 12;
		else
			hitlag = 13;
		moving = 0;
		if(!blocking || (!crouching && h.low) || (crouching && !source.isGrounded()))
		{
			if(crouching)
				setAnim(character.DamageC);
			else
				setAnim(character.Damage);
			health -= h.dmg;

			if(h.knockdown || (!isGrounded()))
				Knockdown();
			else
			{


				hitstun = h.stunCalc(this);

				if(source.isGrounded() && isGrounded())
				{
					hitpush = -rMult() * h.width;
				}
			}
		}
		else
		{
			if(crouching)
				setAnim(character.BlockDmgC);
			else
				setAnim(character.BlockDmg);
			if(health - h.blockDmg > 0)
				health -= h.blockDmg;
			else
				health = 1;

			hitstun = h.stunCalc(this);

			hitpush = -rMult() * h.width/2 + 1;
		}
		currentFrame = anim.getFrame(0);
	}

	//TODO knockdown
	public void Knockdown()
	{
		dy1 += 5;
		hitstun = -1;
		airborneFrames = 10;
		setAnim(character.DamageA);
	}

	//TODO wakeup
	public void Wakeup()
	{
		hitstun = -2;
		setAnim(character.Wakeup);
	}

	private void setAnim(Animation a)
	{
		if(!anim.equals(a))
		{
			frame = 0;
			anim = a;
		}
	}

	public void Animate()
	{
		Random rand = new Random();
		if(hitlag != 0)
		{
			if(!hitThisFrame())
			{
				hitlagShake = new Point((int) (-.5 + rand.nextDouble() * hitlag), (int) (-.5 + rand.nextDouble() * hitlag));
			}
			hitlag--;
		}
		else if(hitstun == 0)
		{
			hitlagShake = new Point(0,0);
			frame++;
			animFrame newFrame = anim.getFrame(frame);
			if(newFrame == null)
			{
				frame = 0;
				anim = character.Stand;
			}
			else
			{
				if(!newFrame.equals(currentFrame))
				{
					currentFrame = newFrame;
					hit = false;
				}
				anim.customEvents(this, frame);
			}
		}
		else if (hitstun <= -2)
		{
			hitstun = 0;
		}
		else if(hitstun > 0)
			hitstun--;
	}

	void win()
	{
		moving = 0;
		setAnim(character.Win);
	}

	void lose()
	{
		moving = 0;
		setAnim(character.Lose);
	}
}
