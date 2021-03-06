package sf;

import sf.Command;
import sf.InputManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HumanInputManager extends InputManager implements KeyListener
{
    private boolean[] keyUp = new boolean[256];
    private boolean[] keyDown = new boolean[256];
    private int[] lastFrame = new int[256];
    private int[] keys = new int[10];
    private int[] dirHistory = new int[60];

    @Override
    public int getDir(boolean right)
    {
        if(!right)
        {
            switch(dir)
            {
                case 1:
                    return 3;
                case 4:
                    return 6;
                case 7:
                    return 9;
                case 3:
                    return 1;
                case 6:
                    return 4;
                case 9:
                    return 7;
                default:
                    return dir;
            }
        }
        else
            return dir;
    }

    public HumanInputManager(int[] keys)
    {
        this.keys = keys;
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


    public void Update()
    {
        dir = directionCheck(keys);
        for(int i = dirHistory.length-1; i > 0; i--)
        {
            dirHistory[i] = dirHistory[i-1];
        }
        dirHistory[0]=dir;
    }

    @Override
    boolean command(Command c)
    {
        if(buttonCheck(c.button, true))
        {
            boolean commandValid = true;
            int lastPlace = 0;
            for (int i = c.directions.length - 1; commandValid && i >= 0; i--)
            {
                commandValid = false;
                for (int j = 0; j < c.directions[i][1]; j++)
                {
                    if (dirHistory[lastPlace + j] == c.directions[i][0])
                    {
                        commandValid = true;
                        lastPlace += j;
                    }
                }
            }
            return commandValid;
        }
        return false;
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

    public boolean normal(int key)
    {
        if(keyDown[keys[key]] && !(lastFrame[keys[key]] == 1))
        {
            lastFrame[keys[key]] = 1;
            return true;
        }
        else
            return false;
    }

    public boolean buttonCheck(int key, boolean special)
    {
//		System.out.println(lastFrame[key]);
        if((keyDown[keys[key]] && !(lastFrame[keys[key]] == 1)) || keyUp[keys[key]] && lastFrame[keys[key]] == 1)
            return true;
        else
            return false;
    }

    public boolean isKeyDown(int key)
    {
        return keyDown[keys[key]];
    }

    public boolean isKeyUp(int key)
    {
        return keyUp[keys[key]];
    }
}
