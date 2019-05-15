package sf;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import sf.Box.BoxType;

public class Player
{
	private static final int UP = 0;
	private static final int LEFT = 1;
	private static final int DOWN = 2;
	private static final int RIGHT = 3;
	private static final int JAB = 4, STRONG = 5, FIERCE = 6;
	private static final int SHORT = 7, FORWARD = 8, ROUNDHOUSE = 9;

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
	public void moveY(int y) {this.y -= y;}

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
	public Point hitlagShake = new Point(0,0);
	public int moving = 0;

	//SF2-like
	private int health = 30;
	//SFV-like
	//	private int health = 144;
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
		return currentFrame.actionable || (currentFrame.chCancel && hitThisFrame());
	}

	public boolean checkCommands()
	{
		for(int i = 0; i < character.Commands.length; i++)
		{
			Command c = character.Commands[i];
			if(inputs.buttonCheck(c.button, true) && inputs.getCommandProgress(i) == c.directions.length-1)
			{
				if(c.Super && !currentFrame.suCancel)
				{
					meter = 0;
					return false;
				}
				else
				{
					attack(c.anim);
					return true;
				}

			}
			else
			{
				if(inputs.getDir(right) == c.directions[inputs.getCommandProgress(i)][0])
				{
					inputs.checkCommandValid(c,i);
				}
			}
		}
		return false;
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
					if(inputs.buttonCheck(FIERCE))
						if(distance < character.fierceRange)
							attack(character.FierceCl);
						else
							attack(character.FierceFa);
					if(inputs.buttonCheck(ROUNDHOUSE))
						if(distance < character.roundhouseRange)
							attack(character.RoundhouseCl);
						else
							attack(character.RoundhouseFa);
					if(inputs.buttonCheck(STRONG))
						if(distance < character.strongRange)
							attack(character.StrongCl);
						else
							attack(character.StrongFa);
					if(inputs.buttonCheck(FORWARD))
						if(distance < character.forwardRange)
							attack(character.ForwardCl);
						else
							attack(character.ForwardFa);
					if(inputs.buttonCheck(JAB))
						if(distance < character.jabRange)
							attack(character.JabCl);
						else
							attack(character.JabFa);
					if(inputs.buttonCheck(SHORT))
						if(distance < character.shortRange)
							attack(character.ShortCl);
						else
							attack(character.ShortFa);
				}
				else
				{
					if(inputs.buttonCheck(FIERCE))
						attack(character.FierceC);
					if(inputs.buttonCheck(ROUNDHOUSE))
						attack(character.RoundhouseC);
					if(inputs.buttonCheck(STRONG))
						attack(character.StrongC);
					if(inputs.buttonCheck(FORWARD))
						attack(character.ForwardC);
					if(inputs.buttonCheck(JAB))
						attack(character.JabC);
					if(inputs.buttonCheck(SHORT))
						attack(character.ShortC);
				}
			}
			else
			{
				if(inputs.buttonCheck(FIERCE))
					attack(character.FierceA);
				if(inputs.buttonCheck(ROUNDHOUSE))
					attack(character.RoundhouseA);
				if(inputs.buttonCheck(STRONG))
					attack(character.StrongA);
				if(inputs.buttonCheck(FORWARD))
					attack(character.ForwardA);
				if(inputs.buttonCheck(JAB))
					attack(character.JabA);
				if(inputs.buttonCheck(SHORT))
					attack(character.ShortA);
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
				double dy = -((double) character.jumpHeight/484) * ((2*airborneFrames) - 44);
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
			blocking = false;
			crouching = true;
			setAnim(character.Crouching);
			moving = 0;
			break;
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

	public void posUpdate(int otherX)
	{
		if(isGrounded())
		{
			y = 0;
			if (otherX > x)
				right = true;
			else
				right = false;
		}

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
					other.Hit(h);
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
				other.Hit(p);
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

	public void Hit(Hitbox h)
	{
		if(hitstun != 0)
			hitlag = 12;
		else
			hitlag = 13;
		moving = 0;
		if(!blocking || (!crouching && h.low) || (crouching && !isGrounded()))
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

				if(h.strength != Hitbox.AttackType.JL && h.strength != Hitbox.AttackType.JM && h.strength != Hitbox.AttackType.JH)
				{
					//TODO Attack pushback
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

			//TODO Attack pushback on block
		}
		currentFrame = anim.getFrame(0);
	}

	//TODO knockdown
	public void Knockdown()
	{
		setAnim(character.Sweep);
	}

	//TODO wakeup
	public void Wakeup()
	{
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
				hitlagShake = new Point((int) (-.5 + rand.nextDouble() * hitlag), (int) (-.5 + rand.nextDouble() * hitlag));
			hitlag--;
		}
		else if(hitstun <= 0)
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
		else
			hitstun--;
	}

	void win()
	{
		setAnim(character.Win);
	}

	void lose()
	{
		setAnim(character.Lose);
	}
}
