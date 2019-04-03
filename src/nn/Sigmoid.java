package nn;

public class Sigmoid implements ActivationFunction
{
	private double dcache;
	private double cache;

	@Override
	public double activation(double d)
	{
		dcache = d;
		cache = 1 / (1 + 1 / Math.exp(d));
		return cache;
	}

	@Override
	public double activation_derivative(double d)
	{
		if (d != dcache)
		{
			cache = activation(d);
		}

		return cache * (1 - cache);
	}
}
