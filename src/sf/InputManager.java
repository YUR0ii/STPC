package sf;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public abstract class InputManager
{
	public static final int JAB = 4, STRONG = 5, FIERCE = 6;
	public static final int SHORT = 7, FORWARD = 8, ROUNDHOUSE = 9;
	Command[] commands;
	protected int dir;

	public InputManager(Command[] commands) 
	{
		this.commands = commands;
	}
	
	abstract boolean normal(int key);
	abstract boolean command(Command c);
	
	public int getDir(boolean right){return dir;}
	
	abstract void Update();

	
}