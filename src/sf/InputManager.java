package sf;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public abstract class InputManager
{
	//first is progress, second is frames since last input
	private int[][] commandValid;
	protected int dir;

	public InputManager(Command[] commands)
	{
		commandValid = new int[commands.length][2];
	}

	//TODO separate commands for easier computer player
	public int getCommandProgress(int command)
	{
		return commandValid[command][0];
	}
	
	public int getDir(boolean right){return dir;}

	public void checkCommandValid(Command c, int i)
	{
		if(commandValid[i][1] < c.directions[commandValid[i][0]][1])
		{
			commandValid[i][0]++;
			commandValid[i][1] = 0;
		}
	}
	
	abstract void Update();

	abstract boolean buttonCheck(int key);
	
	abstract boolean buttonCheck(int key, boolean special);
}