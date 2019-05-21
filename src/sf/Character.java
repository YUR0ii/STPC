package sf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;

public abstract class Character
{
	protected String dir;

	protected String name;
	protected int fSpeed;
	protected int bSpeed;
	protected int fASpeed;
	protected int bASpeed;
	protected int jumpHeight;

	public BufferedImage selectIcon;

	protected Animation Stand;
	protected Animation StandUp;
	protected Animation TurnRL;
	protected Animation TurnLR;
	protected Animation Crouching;
	protected Animation Crouch;
	protected Animation TurnRLC;
	protected Animation TurnLRC;
	protected Animation Damage;
	protected Animation DamageC;
	protected Animation DamageLow;
	protected Animation DamageA;
	protected Animation Sweep;
	protected Animation WalkF;
	protected Animation WalkB;
	protected Animation JumpF;
	protected Animation JumpN;
	protected Animation JumpB;
	protected Animation Wakeup;
	protected Animation GrabF;
	protected Animation GrabB;
	protected Animation Grabbed;
	protected Animation BlockDmg;
	protected Animation BlockDmgC;
	protected Animation Dizzy;

	protected Animation JabCl;
	protected Animation JabFa;
	protected int jabRange;
	protected Animation StrongCl;
	protected Animation StrongFa;
	protected int strongRange;
	protected Animation FierceCl;
	protected Animation FierceFa;
	protected int fierceRange;
	protected Animation ShortCl;
	protected Animation ShortFa;
	protected int shortRange;
	protected Animation ForwardCl;
	protected Animation ForwardFa;
	protected int forwardRange;
	protected Animation RoundhouseCl;
	protected Animation RoundhouseFa;
	protected int roundhouseRange;
	protected Command fGrab;
	protected Command bGrab;

	protected Animation JabC;
	protected Animation StrongC;
	protected Animation FierceC;
	protected Animation ShortC;
	protected Animation ForwardC;
	protected Animation RoundhouseC;

	protected Animation JabA;
	protected Animation JabAD;
	protected Animation StrongA;
	protected Animation StrongAD;
	protected Animation FierceA;
	protected Animation FierceAD;
	protected Animation ShortA;
	protected Animation ShortAD;
	protected Animation ForwardA;
	protected Animation ForwardAD;
	protected Animation RoundhouseA;
	protected Animation RoundhouseAD;

	protected Animation Win;
	protected Animation Lose;

	protected abstract void setupCommands();
	protected Command[] Commands;
	protected abstract void setupWinAnims();
	protected Animation[] winAnims;

	public Character(String dir)
	{
		this.dir = dir;
		setupAnims();
		setupNormals();
		setupCommands();
		setupWinAnims();
	}

	protected Animation animFromFile(String filename)
	{
		try
		{
			System.out.println("loading " + filename);
			return (Animation) new ObjectInputStream(new FileInputStream(new File(filename))).readObject();
		} catch (Exception e)
		{
			System.out.println("failed");
			return Stand;
		}
	}

	void setupAnims()
	{
		String animDir = dir + "/baseAnim/";

		Stand = animFromFile(animDir + "stand.anim");
		StandUp = animFromFile(animDir + "standUp.anim");
		TurnRL = animFromFile(animDir + "turnRL.anim");
		TurnLR = animFromFile(animDir + "turnLR.anim");
		Crouching = animFromFile(animDir + "crouching.anim");
		Crouch = animFromFile(animDir + "crouch.anim");
		TurnRLC = animFromFile(animDir + "turnRLC.anim");
		TurnLRC = animFromFile(animDir + "turnLRC.anim");
		Damage = animFromFile(animDir + "dmg.anim");
		DamageC = animFromFile(animDir + "dmgC.anim");
		DamageLow = animFromFile(animDir + "dmgL.anim");
		DamageA = animFromFile(animDir + "dmgA.anim");
		Sweep = animFromFile(animDir + "sweep.anim");
		WalkF = animFromFile(animDir + "walkF.anim");
		WalkB = animFromFile(animDir + "walkB.anim");
		JumpF = animFromFile(animDir + "jumpF.anim");
		JumpN = animFromFile(animDir + "jumpN.anim");
		JumpB = animFromFile(animDir + "jumpB.anim");
		Wakeup = animFromFile(animDir + "wakeup.anim");
		GrabF = animFromFile(animDir + "grabF.anim");
		GrabB = animFromFile(animDir + "grabB.anim");
		Grabbed = animFromFile(animDir + "grabbed.anim");
		BlockDmg = animFromFile(animDir + "blockDmg.anim");
		BlockDmgC = animFromFile(animDir + "blockDmgC.anim");
		Dizzy = animFromFile(animDir + "dizzy.anim");
		Win = animFromFile(animDir + "win.anim");
		Lose = animFromFile(animDir + "lose.anim");
	}

	void setupNormals()
	{
		String animDir = dir + "/normals/close/";

		JabCl = animFromFile(animDir + "jab.anim");
		StrongCl = animFromFile(animDir + "strong.anim");
		FierceCl = animFromFile(animDir + "fierce.anim");
		ShortCl = animFromFile(animDir + "short.anim");
		ForwardCl = animFromFile(animDir + "forward.anim");
		RoundhouseCl = animFromFile(animDir + "roundhouse.anim");

		animDir = dir + "/normals/far/";
		JabFa = animFromFile(animDir + "jab.anim");
		StrongFa = animFromFile(animDir + "strong.anim");
		FierceFa = animFromFile(animDir + "fierce.anim");
		ShortFa = animFromFile(animDir + "short.anim");
		ForwardFa = animFromFile(animDir + "forward.anim");
		RoundhouseFa = animFromFile(animDir + "roundhouse.anim");

		animDir = dir + "/normals/crouch/";
		JabC = animFromFile(animDir + "jab.anim");
		StrongC = animFromFile(animDir + "strong.anim");
		FierceC = animFromFile(animDir + "fierce.anim");
		ShortC = animFromFile(animDir + "short.anim");
		ForwardC = animFromFile(animDir + "forward.anim");
		RoundhouseC = animFromFile(animDir + "roundhouse.anim");

		animDir = dir + "/normals/jump/";
		JabA = animFromFile(animDir + "jab.anim");
		StrongA = animFromFile(animDir + "strong.anim");
		FierceA = animFromFile(animDir + "fierce.anim");
		ShortA = animFromFile(animDir + "short.anim");
		ForwardA = animFromFile(animDir + "forward.anim");
		RoundhouseA = animFromFile(animDir + "roundhouse.anim");

		animDir = dir + "/normals/diagonal/";
		JabAD = animFromFile(animDir + "jab.anim");
		StrongAD = animFromFile(animDir + "strong.anim");
		FierceAD = animFromFile(animDir + "fierce.anim");
		ShortAD = animFromFile(animDir + "short.anim");
		ForwardAD = animFromFile(animDir + "forward.anim");
		RoundhouseAD = animFromFile(animDir + "roundhouse.anim");
	}

	static Character read(File f)
	{
		return null;
	}
}