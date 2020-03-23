package sf.chars;

import sf.*;

public class Ryu extends sf.Character {

	public Ryu()
	{
		super("characters/ryu");
		name = "Ryu";
		fSpeed = 48;
		bSpeed = 32;
		fASpeed = 32;
		bASpeed = 48;
		jumpHeight = 90;
		//selectIcon

		jabRange = 25;
		strongRange = 31;
		fierceRange = 41;
		shortRange = 0;
		forwardRange = 0;
		roundhouseRange = 56;
	}

	@Override
	protected void setupCommands() {
		Commands = new Command[1];
		Commands[0] = new JHadoken();
//		Commands[1] = new SHadoken();
//		Commands[2] = new FHadoken();
	}
	
	class JHadoken extends Command
	{
//		class Fireball extends Projectile
//		{
//			
//		}
		JHadoken()
		{
			directions = new int[][] {{2,6},{3,6},{6,10}};
			button = InputManager.JAB;
			anim = animFromFile(dir + "/commands/jhadouken.anim");;
			Super = false;
		}
		
		@Override
		public void customEvents(Player parent, int frame)
		{
			if(frame == 12)
			{
				parent.createProjectile(null);
			}
		}
		
	}

	@Override
	protected void setupWinAnims() {

	}
}
