package sf;

import java.util.Random;

public class BogoBoxer extends InputManager
{
    Random rand = new Random();

    public BogoBoxer()
    {
    }

    @Override
    void Update()
    {
        dir = doDir();
    }

    int doDir() {
        double weight = rand.nextDouble();

        if (weight < .3) {
            if (weight < 0.001) {
                return 7;
            }
            if (weight < .2)
                return 1;
            return 4;
        }

        if (.49 < weight && weight < .51)
            return 8;

        if (.51 < weight && weight < .6)
            return 2;

        if (weight > .6) {
            if (weight < .605) {
                return 9;
            }
            return 6;
        }

        return 5;
    }

    @Override
    boolean normal(int key)
    {
        return rand.nextDouble() < .05;
    }

	@Override
	boolean command(Command c) {
		return rand.nextDouble() < .001;
	}
}
