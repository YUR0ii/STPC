package sf;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Hitbox extends Box
{
	//TODO projectiles in S
	public enum AttackType {L, M, H, JL, JM, JH, S}

	boolean knockdown;
	int dmg;
	int blockDmg;
	boolean low = false;
	AttackType strength;
	int stun;
	int stunTimer;

	public Hitbox(Rectangle r, AttackType strength, int dmg, int stun, int stunTimer, boolean low, boolean knockdown)
	{
		super(BoxType.HIT, r);
		this.knockdown = knockdown;
		this.dmg = dmg;
		this.blockDmg = 0;
		this.strength = strength;
		this.low = low;
		this.type = BoxType.HIT;
	}

	public int stunCalc(Player opponent)
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
