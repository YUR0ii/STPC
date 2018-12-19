package sf;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import sf.Box.BoxType;

public class Player
{
	static final int UP = 0;
	static final int LEFT = 1;
	static final int DOWN = 2;
	static final int RIGHT = 3;
	static final int JAB = 4, STRONG = 5, FIERCE = 6;
	static final int SHORT = 7, FORWARD = 8, ROUNDHOUSE = 9;

	Character character;
	int[] Controls;
	InputManager inputs;

	Point location;
	public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	Animation anim;
	animFrame currentFrame;
	private int frame = 0;

	public int hitstun = 0;
	public int hitlag = 0;
	public Point hitlagShake = new Point(0,0);
	int moving = 0;
	int health = 175;
	double meter = 0;
	boolean right = true;
	public boolean blocking = false;
	public boolean crouching = false;
	public boolean grounded = true;
	public boolean jumpSquat = false;
	public boolean knockdown = false;

	int airTime = 0;

	boolean p1;

	Player(Point Location, int[] controls)
	{
		location = Location;
		Controls = controls;
	}

	Player(Character c, Point Location, int[] controls, boolean p1)
	{
		location = Location;
		Controls = controls;
		character = c;
		inputs = new InputManager(controls, character.Commands);

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

	public void attackEvents(int distance)
	{
		boolean special = false;
		if(hitstun == 0 && !jumpSquat)
		{
			for(int i = 0; i < character.Commands.length; i++)
			{
				Command c = character.Commands[i];
				if(inputs.keyCheck(c.button) && inputs.getCommandProgress(i) == c.directions.length-1)
				{
					attack(c, true);
				}
				else
				{
					if(inputs.getDir() == c.directions[inputs.getCommandProgress(i)][0])
					{
						inputs.checkCommandValid(c,i);
					}
				}
			}

			if(!special)
			{
				try{
					if(grounded)
					{
						if(!crouching)
						{
							if(inputs.keyCheck(Controls[JAB]))
								if(distance < character.JabC.range)
									attack(character.JabC, false);
								else
									attack(character.JabF, false);
							if(inputs.keyCheck(Controls[STRONG]))
								if(distance < character.StrongC.range)
									attack(character.StrongC, false);
								else
									attack(character.StrongF, false);
							if(inputs.keyCheck(Controls[FIERCE]))
								if(distance < character.FierceC.range)
									attack(character.FierceC, false);
								else
									attack(character.FierceF, false);
							if(inputs.keyCheck(Controls[SHORT]))
								if(distance < character.ShortC.range)
									attack(character.ShortC, false);
								else
									attack(character.ShortF, false);
							if(inputs.keyCheck(Controls[FORWARD]))
								if(distance < character.ForwardC.range)
									attack(character.ForwardC, false);
								else
									attack(character.ForwardF, false);
							if(inputs.keyCheck(Controls[ROUNDHOUSE]))
								if(distance < character.RoundhouseC.range)
									attack(character.RoundhouseC, false);
								else
									attack(character.RoundhouseF, false);
						}
						else
						{
							if(inputs.keyCheck(Controls[JAB]))
								attack(character.P7C, false);
							if(inputs.keyCheck(Controls[STRONG]))
								attack(character.P8C, false);
							if(inputs.keyCheck(Controls[FIERCE]))
								attack(character.P9C, false);
							if(inputs.keyCheck(Controls[SHORT]))
								attack(character.K4C, false);
							if(inputs.keyCheck(Controls[FORWARD]))
								attack(character.K5C, false);
							if(inputs.keyCheck(Controls[ROUNDHOUSE]))
								attack(character.K6C, false);
						}
					}
					else if(!knockdown)
					{
						if(inputs.keyCheck(Controls[JAB]))
							attack(character.P7A, false);
						if(inputs.keyCheck(Controls[STRONG]))
							attack(character.P8A, false);
						if(inputs.keyCheck(Controls[FIERCE]))
							attack(character.P9A, false);
						if(inputs.keyCheck(Controls[SHORT]))
							attack(character.K4A, false);
						if(inputs.keyCheck(Controls[FORWARD]))
							attack(character.K5A, false);
						if(inputs.keyCheck(Controls[ROUNDHOUSE]))
							attack(character.K6A, false);
					}
				}catch(Exception exc) {System.out.println("Unprogrammed Attack");}
			}
		}
	}
	
	public void movementEvents()
	{
		if(hitstun == 0 && grounded)
		{
			if(!jumpSquat)
			{
				switch(directionConvert(inputs.getDir()))
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
			else
			{
				switch(directionConvert(inputs.getDir()))
				{
				case 1:
				case 4:
				case 7:
					if(right)
						moving = -1;
					else
						moving = 1;
					break;
				case 9:
				case 6:
				case 3:
					if(right)
						moving = 1;
					else
						moving = -1;
					break;
				default:
					break;
				}
			}
		}
	}

	private void Jump()
	{
		setAnim(character.Jump);
		hitstun += character.jumpsquat;
		jumpSquat = true;
		airTime = 0;
		//		grounded = false;
	}

	private void attack(Attack a, boolean command)
	{
		setAnim(a.animation);

		if(grounded)
		{
			moving = 0;
		}

		if(command)
			((Command) a).customEvents();

		if(a.proj)
		{
			projectiles.add(a.projectile);
		}
	}

	public boolean checkGrounded(int ground)
	{
		return location.y >= ground;
	}

	public void posUpdate()
	{
		for(int i = 0; i < currentFrame.boxes.length; i++)
		{
			Box b = currentFrame.boxes[i];
			b.y = location.y - b.offset.y - b.height;
			if (right)
				b.x = location.x + b.offset.x;
			else
				b.x = location.x - b.offset.x - b.width;
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
					Hit(other, h);
			}
		}
	}

	public void setAnim(Animation a)
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
		posUpdate();
	}

	public void Hit(Player target, Hitbox h)
	{
		if(!target.blocking || (!target.crouching && h.low) || (target.crouching && !grounded))
		{
			target.health -= h.dmg;

			if(h.knockdown || (!target.grounded))
				target.Knockdown();
			else
			{
				if(target.hitstun != 0)
					target.hitlag = 12;
				else
					target.hitlag = 13;
				if(h.type != BoxType.PROJ && grounded)
					this.hitlag = 12;

				target.hitstun = h.stunCalc(target);

				if(h.strength != Hitbox.AttackType.JL && h.strength != Hitbox.AttackType.JM && h.strength != Hitbox.AttackType.JH)
				{
					//TODO Attack pushback
				}

			}
		}
		else
		{
			if(target.crouching)
				target.setAnim(target.character.BlockDmgC);
			else
				target.setAnim(target.character.BlockDmg);

			if(target.health - h.blockDmg > 0)
				target.health -= h.blockDmg;
			else
				target.health = 1;

			target.hitstun = h.stunCalc(target);

			//TODO Attack pushback on block
		}
		
		//TODO Grabs
	}

	public void Knockdown()
	{
		knockdown = true;
		setAnim(character.Knockdown);
		airTime = 0;
		grounded = false;
		if(right)
			moving = -character.airSpeed;
		else
			moving = character.airSpeed;
	}

	public void Wakeup()
	{
		knockdown = false;

		setAnim(character.Wakeup);
	}

	public void Flip()
	{
		right = !right;
	}

	public void doHitlagShake()
	{
			Random rand = new Random();
			hitlagShake = new Point((-1 + rand.nextInt(2)) * hitlag, (-1 + rand.nextInt(2)) * hitlag);
	}

	private int directionConvert(int direction)
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
}
