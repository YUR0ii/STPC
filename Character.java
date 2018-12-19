package sf;

import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Character
{
	String name;
	int jumpsquat;
	int forwardSpeed;
	int backSpeed;
	int airSpeed;
	int width;
	
	public BufferedImage selectIcon;
	
	Animation Stand;
	Animation Crouch;
	Animation Damage;
	Animation DamageC;
	Animation WalkF;
	Animation WalkB;
	Animation Jump;
	Animation Knockdown;
	Animation Wakeup;
	Animation GrabF;
	Animation GrabB;
	Animation Grabbed;
	Animation BlockDmg;
	Animation BlockDmgC;

	//5/6 for far, 4 for close
	//jab + strong for forward throw, strong + fierce for back
	Attack JabC;
	Attack JabF;
	Attack StrongC;
	Attack StrongF;
	Attack FierceC;
	Attack FierceF;
	Attack ShortC;
	Attack ShortF;
	Attack ForwardC;
	Attack ForwardF;
	Attack RoundhouseC;
	Attack RoundhouseF;
	Command fGrab;
	Command bGrab;
	
	Attack P7C;
	Attack P8C;
	Attack P9C;
	Attack K4C;
	Attack K5C;
	Attack K6C;
	
	Attack P7A;
	Attack P8A;
	Attack P9A;
	Attack K4A;
	Attack K5A;
	Attack K6A;
	
	abstract void setupAnims();
	abstract void setupNormals();
	abstract void setupCommands();
	Command[] Commands;
	
	public Character(String name, int jumpsquat, int forwardSpeed, int backSpeed, int airSpeed, int width)
	{
		this.name = name;
		this.jumpsquat = jumpsquat;
		this.forwardSpeed = forwardSpeed;
		this.backSpeed = backSpeed;
		this.airSpeed = airSpeed;
		this.width = width;
		setupAnims();
		setupNormals();
		setupCommands();
	}
	
	static Character read(File f)
	{
		return null;
	}
}
