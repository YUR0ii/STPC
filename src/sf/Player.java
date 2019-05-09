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
	private int[] Controls;
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
	private boolean grounded = true;
	public boolean isGrounded()
	{
		return grounded;
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

	Player(Character c, Point Location, int[] controls, boolean p1, Game origin)
	{

		Controls = controls;
		character = c;
		inputs = new InputManager(controls, character.Commands);
		origin.addKeyListener(inputs);

		this.p1 = p1;
		setAnim(c.Stand, true);
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
		return currentFrame.actionable && grounded;
	}

	public boolean commandActionable()
	{
		return currentFrame.actionable || currentFrame.spCancel;
	}

	public boolean normalActionable()
	{
		return currentFrame.actionable || currentFrame.chCancel;
	}

	public boolean checkCommands()
	{
		for(int i = 0; i < character.Commands.length; i++)
		{
			Command c = character.Commands[i];
			if(inputs.keyCheck(c.button) && inputs.getCommandProgress(i) == c.directions.length-1)
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
				if(dirConvert(inputs.getDir()) == c.directions[inputs.getCommandProgress(i)][0])
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
			if(grounded)
			{
				if(!(dirConvert(inputs.getDir()) == 1 || dirConvert(inputs.getDir()) == 2 || dirConvert(inputs.getDir()) == 3))
				{
					if(inputs.keyCheck(Controls[JAB]))
						if(distance < character.jabRange)
							attack(character.JabCl);
						else
							attack(character.JabFa);
					if(inputs.keyCheck(Controls[STRONG]))
						if(distance < character.strongRange)
							attack(character.StrongCl);
						else
							attack(character.StrongFa);
					if(inputs.keyCheck(Controls[FIERCE]))
						if(distance < character.fierceRange)
							attack(character.FierceCl);
						else
							attack(character.FierceFa);
					if(inputs.keyCheck(Controls[SHORT]))
						if(distance < character.shortRange)
							attack(character.ShortCl);
						else
							attack(character.ShortFa);
					if(inputs.keyCheck(Controls[FORWARD]))
						if(distance < character.forwardRange)
							attack(character.ForwardCl);
						else
							attack(character.ForwardFa);
					if(inputs.keyCheck(Controls[ROUNDHOUSE]))
						if(distance < character.roundhouseRange)
							attack(character.RoundhouseCl);
						else
							attack(character.RoundhouseFa);
				}
				else
				{
					if(inputs.keyCheck(Controls[JAB]))
						attack(character.JabC);
					if(inputs.keyCheck(Controls[STRONG]))
						attack(character.StrongC);
					if(inputs.keyCheck(Controls[FIERCE]))
						attack(character.FierceC);
					if(inputs.keyCheck(Controls[SHORT]))
						attack(character.ShortC);
					if(inputs.keyCheck(Controls[FORWARD]))
						attack(character.ForwardC);
					if(inputs.keyCheck(Controls[ROUNDHOUSE]))
						attack(character.RoundhouseC);
				}
			}
			else
			{
				if(inputs.keyCheck(Controls[JAB]))
					attack(character.JabA);
				if(inputs.keyCheck(Controls[STRONG]))
					attack(character.StrongA);
				if(inputs.keyCheck(Controls[FIERCE]))
					attack(character.FierceA);
				if(inputs.keyCheck(Controls[SHORT]))
					attack(character.ShortA);
				if(inputs.keyCheck(Controls[FORWARD]))
					attack(character.ForwardA);
				if(inputs.keyCheck(Controls[ROUNDHOUSE]))
					attack(character.RoundhouseA);
			}
		}catch(Exception exc) {System.out.println("Unprogrammed Attack");}
	}

	//TODO jump
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

	//TODO airborne calculation
	public int dy()
	{
		if(grounded)
			return 0;
		else
			return 1;
	}

	private void attack(Animation a)
	{
		setAnim(a, true);
		if(grounded)
			moving = 0;
	}

	private int dirConvert(int direction)
	{
		if(!right)
		{
			switch(direction)
			{
			case 1:
				return 3;
			case 4:
				return 6;
			case 7:
				return 9;
			case 3:
				return 1;
			case 6:
				return 4;
			case 9:
				return 7;
			default:
				return direction;
			}
		}
		else
			return direction;
	}

	public void checkMovement()
	{
		switch(dirConvert(inputs.getDir()))
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
		if(otherX > x)
			right = true;
		else
			right = false;
		for(int i = 0; i < currentFrame.boxes.length; i++)
		{
			Box b = currentFrame.boxes[i];
			b.y = (int) (getY() - b.offset.getY() - b.height);
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
					if(grounded)
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
		moving = 0;
		if(!blocking || (!crouching && h.low) || (crouching && !grounded))
		{
			if(crouching)
				setAnim(character.DamageC);
			else
				setAnim(character.Damage);
			health -= h.dmg;

			if(h.knockdown || (!grounded))
				Knockdown();
			else
			{
				if(hitstun != 0)
					hitlag = 12;
				else
					hitlag = 13;

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

	public void Flip()
	{
		right = !right;
	}

	public void doHitstun()
	{
		if(hitlag > 0)
			hitlag--;
	}

	private void setAnim(Animation a)
	{
		if(!anim.equals(a))
		{
			frame = 0;
			anim = a;
		}
	}

	private void setAnim(Animation a, boolean noCheck)
	{
		frame = 0;
		anim = a;
	}

	public void Animate()
	{
		Random rand = new Random();
		if(hitstun != 0)
		{
			hitlagShake = new Point((int) (-.5 + rand.nextDouble() * hitlag), (int) (-.5 + rand.nextDouble() * hitlag));
			hitstun--;
		}

		if(hitlag <= 0)
		{
			hitlagShake = new Point(0,0);
			frame++;
			animFrame newFrame = anim.getFrame(frame);
			if(newFrame == null)
			{
				setAnim(character.Stand, true);
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
			hitlag--;

//		if(anim.equals(character.JumpB) || anim.equals(character.JumpN) || anim.equals(character.JumpF))
//		{
//			if(currentFrame.actionable)
//				grounded = false;
//			else
//				grounded = true;
//		}
	}
}
