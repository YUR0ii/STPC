package sf;

import java.awt.Point;
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

	private Character character;
	private int[] Controls;
	private InputManager inputs;

	private double x;
	public int getX()
	{
		return (int) x;
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
	public BufferedImage sprite()
	{
		return currentFrame.sprite;
	}
	private int frame = 0;

	private int hitstun = 0;
	private int hitlag = 0;
	public Point hitlagShake = new Point(0,0);
	private int moving = 0;
	//SF2-like
	private int health = 30;
	public int getHealth()
	{
		return health;
	}
	//SFV-like
	//	private int health = 144;
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
	private boolean p1;
	public boolean isP1()
	{
		return p1;
	}

	Player(Character c, Point Location, int[] controls, boolean p1, Game origin)
	{
		x = Location.x;
		y = Location.y;
		Controls = controls;
		character = c;
		inputs = new InputManager(controls, character.Commands);
		origin.addKeyListener(inputs);

		this.p1 = p1;
		setAnim(c.Stand, true);
		Animate();

		if(!p1)
			Flip();
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

	public boolean actionable()
	{
		return currentFrame.actionable;
	}

	private void attack(Attack a)
	{
		setAnim(a.animation);

		if(grounded)
		{
			moving = 0;
		}

		if(a.proj)
		{
			projectiles.add(a.projectile);
		}
	}

	public boolean checkCommands()
	{
		for(int i = 0; i < character.Commands.length; i++)
		{
			Command c = character.Commands[i];
			if(inputs.keyCheck(c.button) && inputs.getCommandProgress(i) == c.directions.length-1)
			{
				attack(c);
				return true;
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
		try
		{
			if(grounded)
			{
				if(!crouching)
				{
					if(inputs.keyCheck(Controls[JAB]))
						if(distance < character.JabC.range)
							attack(character.JabC);
						else
							attack(character.JabF);
					if(inputs.keyCheck(Controls[STRONG]))
						if(distance < character.StrongC.range)
							attack(character.StrongC);
						else
							attack(character.StrongF);
					if(inputs.keyCheck(Controls[FIERCE]))
						if(distance < character.FierceC.range)
							attack(character.FierceC);
						else
							attack(character.FierceF);
					if(inputs.keyCheck(Controls[SHORT]))
						if(distance < character.ShortC.range)
							attack(character.ShortC);
						else
							attack(character.ShortF);
					if(inputs.keyCheck(Controls[FORWARD]))
						if(distance < character.ForwardC.range)
							attack(character.ForwardC);
						else
							attack(character.ForwardF);
					if(inputs.keyCheck(Controls[ROUNDHOUSE]))
						if(distance < character.RoundhouseC.range)
							attack(character.RoundhouseC);
						else
							attack(character.RoundhouseF);
				}
				else
				{
					if(inputs.keyCheck(Controls[JAB]))
						attack(character.P7C);
					if(inputs.keyCheck(Controls[STRONG]))
						attack(character.P8C);
					if(inputs.keyCheck(Controls[FIERCE]))
						attack(character.P9C);
					if(inputs.keyCheck(Controls[SHORT]))
						attack(character.K4C);
					if(inputs.keyCheck(Controls[FORWARD]))
						attack(character.K5C);
					if(inputs.keyCheck(Controls[ROUNDHOUSE]))
						attack(character.K6C);
				}
			}
			else
			{
				if(inputs.keyCheck(Controls[JAB]))
					attack(character.P7A);
				if(inputs.keyCheck(Controls[STRONG]))
					attack(character.P8A);
				if(inputs.keyCheck(Controls[FIERCE]))
					attack(character.P9A);
				if(inputs.keyCheck(Controls[SHORT]))
					attack(character.K4A);
				if(inputs.keyCheck(Controls[FORWARD]))
					attack(character.K5A);
				if(inputs.keyCheck(Controls[ROUNDHOUSE]))
					attack(character.K6A);
			}
		}catch(Exception exc) {System.out.println("Unprogrammed Attack");}
	}

	//TODO jump
	private void Jump()
	{
		setAnim(character.Jump);
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
			setAnim(character.Crouch);
			moving = 0;
			break;
		case 2:
			blocking = false;
			crouching = true;
			setAnim(character.Crouch);
			moving = 0;
			break;
		case 3:
			blocking = false;
			crouching = true;
			setAnim(character.Crouch);
			moving = 0;
			break;
		case 4:
			setAnim(character.WalkB);
			if(right)
				moving = -character.backSpeed;
			else
				moving = character.backSpeed;
			blocking = true;
			crouching = false;
			break;
		case 6:
			setAnim(character.WalkF);
			if(right)
				moving = character.forwardSpeed;
			else
				moving = -character.forwardSpeed;
			blocking = false;
			crouching = false;
			break;
		case 7:
			if(right)
				moving = -1;
			else
				moving = 1;
			crouching = false;
			Jump();
			break;
		case 8:
			moving = 0;
			Jump();
			crouching = false;
			break;
		case 9:
			if(right)
				moving = 1;
			else
				moving = -1;
			crouching = false;
			Jump();
			break;
		default:
			setAnim(character.Stand);
			moving = 0;
			blocking = false;
			crouching = false;
			break;
		}
	}

	public void posUpdate()
	{
		for(int i = 0; i < currentFrame.boxes.length; i++)
		{
			Box b = currentFrame.boxes[i];
			b.y = getY() - b.offset.y - b.height;
			if (right)
				b.x = getX() + b.offset.x;
			else
				b.x = getX() - b.offset.x - b.width;
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

	//TODO movement
	public void doMovement()
	{

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
					if(grounded)
						hitlag = 12;
				}
			}
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
		if(!blocking || (!crouching && h.low) || (crouching && !grounded))
		{
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
	}

	//TODO knockdown
	public void Knockdown()
	{
		setAnim(character.Knockdown);
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
		Random rand = new Random();
		if(hitstun != 0)
		{
			if (crouching)
				setAnim(character.DamageC);
			else
				setAnim(character.Damage);

			hitlagShake = new Point((int) (-.5 + rand.nextDouble() * hitlag), (int) (-.5 + rand.nextDouble() * hitlag));
			hitlag--;
			hitstun--;
		}
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
		if(hitlag == 0)
		{
			frame++;
		}
		currentFrame = anim.getFrame(frame, this);
		anim.customEvents(this, frame);
	}
}
