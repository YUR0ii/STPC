package sf;

import java.awt.Dimension;
import java.awt.Point;

public class Hitbox extends Box
{
	//TODO projectiles in S
	enum AttackType {L, M, H, JL, JM, JH, S}

	boolean knockdown;
	int dmg;
	int blockDmg;
	boolean low = false;
	AttackType strength;
	int customStunStand = -1;
	int customStunCrouch = -1;

	public Hitbox(Point offset, Dimension size, AttackType strength, int dmg, boolean knockdown, boolean low)
	{
		super(offset, size);
		this.knockdown = knockdown;
		this.dmg = dmg;
		this.blockDmg = 0;
		this.strength = strength;
		this.low = low;
		this.type = BoxType.HIT;
	}

	public Hitbox(Point offset, Dimension size, AttackType strength, int dmg, boolean knockdown, boolean low, int customStunStand, int customStunCrouch)
	{
		super(offset, size);
		this.knockdown = knockdown;
		this.dmg = dmg;
		this.blockDmg = 0;
		this.strength = strength;
		this.low = low;
		this.type = BoxType.HIT;
		this.customStunStand = customStunStand;
		this.customStunCrouch = customStunCrouch;
	}
	
	public Hitbox(Point offset, Dimension size, int dmg, boolean knockdown, boolean low, boolean projectile)
	{
		super(offset, size);
		this.knockdown = knockdown;
		this.dmg = dmg;
		this.blockDmg = 0;
		this.strength = AttackType.S;
		this.low = low;
		if(projectile)
			this.type = BoxType.PROJ;
	}

	public int stunCalc(Player opponent)
	{
		if(customStunStand != -1)
		{
			if(opponent.isCrouching())
				return customStunCrouch;
			else
				return customStunStand;
		}
		else
		{
			switch(strength)
			{
			case L:
				return 11;
			case M:
				return 16;
			case H:
				return 20;
			case JL:
				return 11;
			case JM:
				if(opponent.isCrouching() || opponent.isBlocking())
					return 16;
				else
					return 11;
			case JH:
				if(opponent.isCrouching())
					return 21;
				else if (opponent.isBlocking())
					return 20;
				else
					return 11;
			case S:
				return 20;
			default:
				return 0;
			}
		}

	}

}
