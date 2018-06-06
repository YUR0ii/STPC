package sf;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class InputManager implements KeyListener
{
	private boolean[] keyUp = new boolean[256];
	private boolean[] keyDown = new boolean[256];
	public int[] lastFrame = new int[256];

	private static InputManager instance = new InputManager();

	protected InputManager(){}

	public static InputManager getInstance()
	{
		return instance;
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
			lastFrame[e.getKeyCode()] = 2;
		}
	}

	public void keyTyped(KeyEvent e){}

	public boolean keyCheck(int key)
	{
//		if(keyDown[key] || lastFrame[key] == 0)
//			System.out.println(lastFrame[key] + " " + keyDown[key]);
//		System.out.println(key);
		
		if(keyDown[key] && lastFrame[key] == 0)
		{
			lastFrame[key]++;
			return true;
		}
		else
		{
//			lastFrame[key] = false;
			return false;
		}
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