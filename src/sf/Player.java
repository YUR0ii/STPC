package sf;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player
{
	static final int UP = 0;
	static final int LEFT = 1;
	static final int DOWN = 2;
	static final int RIGHT = 3;
	static final int JAB = 4, STRONG = 5, FIERCE = 6;
	static final int SHORT = 7, FORWARD = 8, ROUNDHOUSE = 9;
	static final int BLOCK = 13;

	Character character;
	int[] Controls;

	public Body body;
	Point location;
	public ArrayList<Hitbox> hitboxes = new ArrayList<Hitbox>();
	public ArrayList<Hitbox> projectiles = new ArrayList<Hitbox>();
	Animation currentAnim;
	private int currentFrame = 0;
	public BufferedImage sprite;

	public int lag = 0;
	int moving = 0;
	int health = 175;
	double meter = 0;
	boolean right = true;
	public boolean blocking = false;
	public boolean crouching = false;
	public boolean grounded = true;
	public boolean jumpSquat = false;
	public boolean inHitStun = false;
	public boolean isUpdating = true;
	public boolean crossedup = false;
	public boolean knockdown = false;
	public boolean attacking = false;
	public boolean grabbed = false;

	int top;
	int front;
	int center;
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

		this.p1 = p1;
		setAnim(c.Stand, true);
		body = c.Stand.positions[0];
		sprite = c.Stand.images[0];
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

	public void attackEvents(InputManager e)
	{
		boolean special = false;
		if(lag == 0 && !jumpSquat)
		{
			for(Command c : character.Commands)
			{
				if(e.keyCheck(Controls[c.button], true))
				{
					int pos = 0;
					boolean valid = true;

					for(int i = c.directions.length-1; i >= 0 && valid; i--)
					{
						pos = e.posInHistory(directionConvert(c.directions[i][0]), pos, c.directions[i][1], p1);
						if(pos == -1)
							valid = false;
					}

					if(valid && meter >= c.meterCost)
					{
						attack(c.attack);
						meter -= c.meterCost;
						special = true;
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
							if(e.keyCheck(Controls[JAB]))
								attack(character.JabC);
							if(e.keyCheck(Controls[STRONG]))
								attack(character.StrongC);
							if(e.keyCheck(Controls[FIERCE]))
								attack(character.FierceC);
							if(e.keyCheck(Controls[SHORT]))
								attack(character.ShortC);
							if(e.keyCheck(Controls[FORWARD]))
								attack(character.ForwardC);
							if(e.keyCheck(Controls[ROUNDHOUSE]))
								attack(character.RoundhouseC);
						}
						else
						{
							if(e.keyCheck(Controls[JAB]))
								attack(character.P7C);
							if(e.keyCheck(Controls[STRONG]))
								attack(character.P8C);
							if(e.keyCheck(Controls[FIERCE]))
								attack(character.P9C);
							if(e.keyCheck(Controls[SHORT]))
								attack(character.K4C);
							if(e.keyCheck(Controls[FORWARD]))
								attack(character.K5C);
							if(e.keyCheck(Controls[ROUNDHOUSE]))
								attack(character.K6C);
						}
					}
					else if(!knockdown)
					{
						if(e.keyCheck(Controls[JAB]))
							attack(character.P7A);
						if(e.keyCheck(Controls[STRONG]))
							attack(character.P8A);
						if(e.keyCheck(Controls[FIERCE]))
							attack(character.P9A);
						if(e.keyCheck(Controls[SHORT]))
							attack(character.K4A);
						if(e.keyCheck(Controls[FORWARD]))
							attack(character.K5A);
						if(e.keyCheck(Controls[ROUNDHOUSE]))
							attack(character.K6A);
					}
				}catch(Exception exc) {System.out.println("Unprogrammed Attack");}
			}
	}
}
	public void movementEvents(InputManager e)
	{
		if(lag == 0 && grounded && !attacking)
		{
			if(!jumpSquat)
			{
				switch(directionConvert(e.direction(p1)))
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
				switch(directionConvert(e.direction(p1)))
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
		lag += character.jumpsquat;
		jumpSquat = true;
		airTime = 0;
//		grounded = false;
	}

	private void attack(Attack a)
	{
		attacking = true;
		setAnim(a.animation);
		lag = a.faf;
		if(grounded)
		{
			moving = 0;
		}
		if(a.proj)
		{
			for(Hitbox g : a.Hitboxes)
			{
				Hitbox h = new Hitbox(g);
				
				if(!right)
					h.direction = -h.direction;
				
				projectiles.add(h);
			}
		}
		else
		{
			for(Hitbox h : a.Hitboxes)
				hitboxes.add(new Hitbox(h));
		}
	}

	public boolean checkGrounded(int ground)
	{
		return location.y >= ground;
	}

	public void posCalcs()
	{
		if(right)
		{
			front = location.x + (2 * character.width);
		}
		else
		{
			front = location.x - (2 * character.width);
		}

		this.top = location.y - body.height;
		
		this.center = (location.x + front)/2;
	}
	
	public void bodyPosUpdate()
	{
		for(Hurtbox h : body.body)
		{
			if(right)
				h.x = location.x + h.offset.x;
			else
				h.x = location.x + (-h.offset.x - h.width);
			
			h.y = location.y - h.offset.y;
		}
	}
	
	public void setAnim(Animation a)
	{
		if(!currentAnim.equals(a))
		{
			currentFrame = 0;
			currentAnim = a;
			isUpdating = true;
		}
	}
	
	private void setAnim(Animation a, boolean noCheck)
	{
		currentFrame = 0;
		currentAnim = a;
		isUpdating = true;
	}
	
	public void Animate()
	{
		currentFrame++;
		
		int oldFront = front;
		body = currentAnim.boxAnimate(currentFrame, this);
		sprite = currentAnim.spriteAnimate(currentFrame, this);
		posCalcs();
		int newFront = front;
		
		if((right && newFront > oldFront) || (!right && newFront < oldFront))
			isUpdating = true;
		
		
	}

	public void Hit(Player target, Hitbox h)
	{
		h.activeFrames = 0;
		target.hitboxes.clear();

		if(h.direction == 0 || h.speed != 0)
		{
			if(!target.blocking || (!target.crouching && h.hitsLow) || (target.crouching && !grounded))
			{
				target.health -= h.dmg;
	
				if(h.knockdown || (!target.grounded && !target.attacking))
				{
					target.Knockdown();
				}
				else
				{
					target.lag = h.stunCalc(target);
					target.inHitStun = true;
					if(h.type != AttackType.JL && h.type != AttackType.JM && h.type != AttackType.JH) {
						if (right) {
							if (target.location.x + h.width / 4 < 1600)
								target.location.x += h.width / 4;
							else if (h.speed == 0)
								location.x -= h.width / 4;
						} else {
							if (target.location.x - h.width / 4 > 0)
								target.location.x -= h.width / 4;
							else if (h.speed == 0)
								location.x += h.width / 4;
						}
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
				target.lag = h.stunCalc(target);
				if(right)
				{
					if(target.location.x + h.width/6 < 1600)
						target.location.x += h.width/6;
					else if (h.speed == 0)
						location.x -= h.width/6;
				}
				else
				{
					if(target.location.x - h.width/6 > 0)
						target.location.x -= h.width/6;
					else if (h.speed == 0)
						location.x += h.width/6;
				}		
			}
		}
		else
		{
			target.blocking = false;
			target.grabbed = true;
			target.setAnim(target.character.Damage);
			target.health -= h.dmg;
			if(h.direction == 1)
			{
				setAnim(character.GrabB);
				target.location.x = location.x;
				Flip();
			}
			else
			{
				setAnim(character.GrabF);
				target.location.x = front;
			}
			
			lag = 40;
			target.lag = 40;
		}
	}

	public void Knockdown()
	{
		grabbed = false;
		knockdown = true;
		setAnim(character.Knockdown);
		airTime = 0;
		grounded = false;
		if(right)
			moving = -character.airSpeed;
		else
			moving = character.airSpeed;
	}
	
	public void Wakeup(InputManager e)
	{
		knockdown = false;
		
		setAnim(character.Wakeup);
	}

	public void Flip()
	{
		if(right)
			location.x += body.width;
		else
			location.x -= body.width;

		right = !right;
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
