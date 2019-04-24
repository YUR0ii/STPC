package sf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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

	Attack JabCl;
	Attack JabFa;
	Attack StrongCl;
	Attack StrongFa;
	Attack FierceCl;
	Attack FierceFa;
	Attack ShortCl;
	Attack ShortFa;
	Attack ForwardCl;
	Attack ForwardFa;
	Attack RoundhouseCl;
	Attack RoundhouseFa;
	Command fGrab;
	Command bGrab;
	
	Attack JabC;
	Attack StrongC;
	Attack FierceC;
	Attack ShortC;
	Attack ForwardC;
	Attack RoundhouseC;
	
	Attack JabA;
	Attack StrongA;
	Attack FierceA;
	Attack ShortA;
	Attack ForwardA;
	Attack RoundhouseA;
	
	abstract void setupCommands();
	Command[] Commands;
	
	public Character(String characterDirectory)
	{
		dir = characterDirectory;
		setupAnims();
		setupNormals();
		setupCommands();
	}
	
	void setupAnims()
	{
		String animDir = dir + "/baseAnim";
		try
		{
			Stand = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "stand.anim"))).readObject();
			Crouch = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "crouch.anim"))).readObject();
			Damage = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "dmg.anim"))).readObject();
			DamageC = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "dmgC.anim"))).readObject();
			WalkF = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "walkF.anim"))).readObject();
			WalkB = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "walkB.anim"))).readObject();
			Jump = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "jump.anim"))).readObject();
			Knockdown = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "knockdown.anim"))).readObject();
			Wakeup = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "wakeup.anim"))).readObject();
			GrabF = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "grabF.anim"))).readObject();
			GrabB = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "grabB.anim"))).readObject();
			Grabbed = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "grabbed.anim"))).readObject();
			BlockDmg = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "blockDmg.anim"))).readObject();
			BlockDmgC = (Animation) new ObjectInputStream(new FileInputStream(new File(dir + "blockDmgC.anim"))).readObject();
		} catch (Exception e) {e.printStackTrace(System.out);}
	}
	
	void setupNormals()
	{
		String animDir = dir + "/baseAnim";
	}
	
	static Character read(File f)
	{
		return null;
	}
}