package sf;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public final class InputManager implements KeyListener
{
	private boolean[] keyUp = new boolean[256];
	private boolean[] keyDown = new boolean[256];
	private int[] lastFrame = new int[256];
	private int[] keys = new int[4];

	//first is progress, second is frames since last input
	int[][] commandValid;

	private int dir;


	public InputManager(int[] keys, Command[] commands)
	{
		this.keys = keys;
		commandValid = new int[commands.length][2];
	}

	public int getCommandProgress(int command)
	{
		return commandValid[command][0];
	}

	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() >= 0 && e.getKeyCode() < 256)
		{
			keyDown[e.getKeyCode()] = true;
			keyUp[e.getKeyCode()] = false;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() >= 0 && e.getKeyCode() < 256)
		{
			keyUp[e.getKeyCode()] = true;
			keyDown[e.getKeyCode()] = false;
			lastFrame[e.getKeyCode()]++;
			if(lastFrame[e.getKeyCode()] == 2)
				lastFrame[e.getKeyCode()] = 0;
		}
	}

	public void keyTyped(KeyEvent e){}
	
	public int getDir()
	{
		return dir;
	}

	public void checkCommandValid(Command c, int i)
	{
		if(commandValid[i][1] < c.directions[commandValid[i][0]][1])
		{
			commandValid[i][0]++;
			commandValid[i][1] = 0;
		}
	}
	
	public void Update()
	{
		dir = directionCheck(keys);
		for(int i = 0; i < commandValid.length; i++)
			commandValid[i][1]++;
	}
	
	private int directionCheck(int[] keys)
	{
		int vert = 0;
		
		if(keyDown[keys[0]])
			vert = 1;
		if(keyDown[keys[2]])
			vert = -1;
		
		if(keyDown[keys[1]])
		{
			if(vert == 1)
				return 7;
			else if(vert == -1)
				return 1;
			else
				return 4;
		}
		else if(keyDown[keys[3]])
		{
			if(vert == 1)
				return 9;
			else if(vert == -1)
				return 3;
			else
				return 6;
		}
		else if(vert == 1)
			return 8;
		else if(vert == -1)
			return 2;
		else
			return 5;
	}

	public boolean keyCheck(int key)
	{	
		if(keyDown[key] && !(lastFrame[key] == 1))
		{
			lastFrame[key] = 1;
			return true;
		}
		else
			return false;
	}
	
	public boolean keyCheck(int key, boolean special)
	{
//		System.out.println(lastFrame[key]);
		if((keyDown[key] && !(lastFrame[key] == 1)) || keyUp[key] && lastFrame[key] == 1)
			return true;
		else
			return false;
	}
	
	public boolean isKeyDown(int key)
	{
		return keyDown[key];
	}

	public boolean isKeyUp(int key)
	{
		return keyUp[key];
	}
}