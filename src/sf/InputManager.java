package sf;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class InputManager implements KeyListener
{
	private boolean[] keyUp = new boolean[256];
	private boolean[] keyDown = new boolean[256];
	private boolean[] lastFrame = new boolean[256];
	private int[] keysP1 = new int[4];
	private int[] keysP2 = new int[4];
	private int[] historyP1 = new int[29];
	private int[] historyP2 = new int[29];
	private int dirP1;
	private int dirP2;


	public InputManager(int[] keysP1, int[] keysP2)
	{
		this.keysP1 = keysP1;
		this.keysP2 = keysP2;
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
			lastFrame[e.getKeyCode()] = false;
		}
	}

	public void keyTyped(KeyEvent e){}
	
	int posInHistory(int direction, int start, int range, boolean p1)
	{
		int[] history = historyP2;
		if(p1)
			history = historyP1;
		
		int pos = -1;
		for(int i = start; i < start + range; i++)
		{
			if(history[i] == direction)
				pos = i;
		}
		
		return pos;
	}
	
	public int direction(boolean p1)
	{
		if(p1)
			return dirP1;
		else
			return dirP2;
	}
	
	public void doDirection(boolean p1)
	{
		int dir;
		int[] keys;
		int[] history;
		if(p1)
		{
			keys = keysP1;
			history = historyP1;
		}
		else
		{
			keys = keysP2;
			history = historyP2;
		}
		
		dir = directionCheck(keys);
		
		int holder = history[0];
		int holder2;
		history[0] = dir;
		for(int i = 1; i < 28; i+=2)
		{
			holder2 = history[i];
			history[i] = holder;
			holder = history[i+1];
			history[i+1] = holder2;
		}
		
		if(p1)
			dirP1 = dir;
		else
			dirP2 = dir;
		
//		for(int i = 0; i < 29; i++)
//			System.out.print(history[i]);
//		System.out.println();
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
		if(keyDown[key] && !lastFrame[key])
		{
			lastFrame[key] = true;
			return true;
		}
		else
			return false;
	}
	
	public boolean keyCheck(int key, boolean special)
	{	
		if(keyDown[key] && !lastFrame[key])
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