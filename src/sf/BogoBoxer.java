package sf;

import java.util.Random;

public class BogoBoxer extends InputManager
{
    public BogoBoxer(Command[] commands)
    {
        super(commands);
    }

    @Override
    int getDir()
    {
        return 0;
    }

    Random rand = new Random();

    @Override
    void Update()
    {

    }

    @Override
    boolean buttonCheck(int key)
    {
        return false;
    }

    @Override
    boolean buttonCheck(int key, boolean special)
    {
        return false;
    }
}
