package sf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;

public abstract class Character
{
	String dir;
	
	String name;
	int fSpeed;
	int bSpeed;
	int fASpeed;
	int bASpeed;
	
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

	Animation JabCl;
	Animation JabFa;
	int jabRange;
	Animation StrongCl;
	Animation StrongFa;
	int strongRange;
	Animation FierceCl;
	Animation FierceFa;
	int fierceRange;
	Animation ShortCl;
	Animation ShortFa;
	int shortRange;
	Animation ForwardCl;
	Animation ForwardFa;
	int forwardRange;
	Animation RoundhouseCl;
	Animation RoundhouseFa;
	int roundhouseRange;
	Command fGrab;
	Command bGrab;
	int grabRange;
	
	Animation JabC;
	Animation StrongC;
	Animation FierceC;
	Animation ShortC;
	Animation ForwardC;
	Animation RoundhouseC;
	
	Animation JabA;
	Animation StrongA;
	Animation FierceA;
	Animation ShortA;
	Animation ForwardA;
	Animation RoundhouseA;
	
	abstract void setupCommands();
	Command[] Commands;
	
	public Character(String characterDirectory)
	{
		dir = characterDirectory;
		setupAnims();
		setupNormals();
		setupCommands();
	}
	
	Animation animFromFile(String filename)
	{
		try
		{
			return (Animation) new ObjectInputStream(new FileInputStream(new File(filename))).readObject();
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	void setupAnims()
	{
		String animDir = dir + "/baseAnim";
		try
		{
			Stand = animFromFile(animDir + "stand.anim");
			Crouch = animFromFile(animDir + "crouch.anim");
			Damage = animFromFile(animDir + "dmg.anim");
			DamageC = animFromFile(animDir + "dmgC.anim");
			WalkF = animFromFile(animDir + "walkF.anim");
			WalkB = animFromFile(animDir + "walkB.anim");
			Jump = animFromFile(animDir + "jump.anim");
			Knockdown = animFromFile(animDir + "knockdown.anim");
			Wakeup = animFromFile(animDir + "wakeup.anim");
			GrabF = animFromFile(animDir + "grabF.anim");
			GrabB = animFromFile(animDir + "grabB.anim");
			Grabbed = animFromFile(animDir + "grabbed.anim");
			BlockDmg = animFromFile(animDir + "blockDmg.anim");
			BlockDmgC = animFromFile(animDir + "blockDmgC.anim");
		} catch (Exception e) {e.printStackTrace(System.out);}
	}
	
	void setupNormals()
	{
		String animDir = dir + "/normals/close";
		
		JabCl = animFromFile(animDir + "jab.anim");
		StrongCl = animFromFile(animDir + "strong.anim");
		FierceCl = animFromFile(animDir + "fierce.anim");
		ShortCl = animFromFile(animDir + "short.anim");
		ForwardCl = animFromFile(animDir + "forward.anim");
		RoundhouseCl = animFromFile(animDir + "roundhouse.anim");
		
		animDir = dir + "/normals/far";
		JabFa = animFromFile(animDir + "jab.anim");
		StrongFa = animFromFile(animDir + "strong.anim");
		FierceFa = animFromFile(animDir + "fierce.anim");
		ShortFa = animFromFile(animDir + "short.anim");
		ForwardFa = animFromFile(animDir + "forward.anim");
		RoundhouseFa = animFromFile(animDir + "roundhouse.anim");
		
		animDir = dir + "/normals/crouch";
		JabC = animFromFile(animDir + "jab.anim");
		StrongC = animFromFile(animDir + "strong.anim");
		FierceC = animFromFile(animDir + "fierce.anim");
		ShortC = animFromFile(animDir + "short.anim");
		ForwardC = animFromFile(animDir + "forward.anim");
		RoundhouseC = animFromFile(animDir + "roundhouse.anim");
		
		animDir = dir + "/normals/jump";
		JabA = animFromFile(animDir + "jab.anim");
		StrongA = animFromFile(animDir + "strong.anim");
		FierceA = animFromFile(animDir + "fierce.anim");
		ShortA = animFromFile(animDir + "short.anim");
		ForwardA = animFromFile(animDir + "forward.anim");
		RoundhouseA = animFromFile(animDir + "roundhouse.anim");
	}
	
	static Character read(File f)
	{
		return null;
	}
}