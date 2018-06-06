package sf;

import java.io.File;

public abstract class Character
{
	String name;
	int jumpsquat;
	int forwardSpeed;
	int backSpeed;
	int airSpeed;
	
	abstract Animation Stand();
	abstract Animation Crouch();
	abstract Animation Block();
	abstract Animation BlockC();
	abstract Animation Damage();
	abstract Animation DamageC();
	abstract Animation WalkF();
	abstract Animation WalkB();
	abstract Animation Jump();
	abstract Animation Knockdown();
	abstract Animation Wakeup();
	abstract Animation GrabF();
	abstract Animation GrabB();
	abstract Animation Grabbed();
	abstract Animation BlockDmg();
	abstract Animation BlockDmgC();
	
	abstract Attack P7();
	abstract Attack P8();
	abstract Attack P9();
	abstract Attack K4();
	abstract Attack K5();
	abstract Attack K6();
	abstract Attack G1();
	abstract Attack G2();
	abstract Attack S3();
	
	abstract Attack P7C();
	abstract Attack P8C();
	abstract Attack P9C();
	abstract Attack K4C();
	abstract Attack K5C();
	abstract Attack K6C();
	
	abstract Attack P7A();
	abstract Attack P8A();
	abstract Attack P9A();
	abstract Attack K4A();
	abstract Attack K5A();
	abstract Attack K6A();
	
	public Character(String name, int jumpsquat, int forwardSpeed, int backSpeed, int airSpeed)
	{
		this.name = name;
		this.jumpsquat = jumpsquat;
		this.forwardSpeed = forwardSpeed;
		this.backSpeed = backSpeed;
		this.airSpeed = airSpeed;
	}
	
	static Character read(File f)
	{
		return null;
	}
}
