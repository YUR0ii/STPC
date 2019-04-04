package nn;

import org.jblas.DoubleMatrix;

public class XOR
{
	public static void main(String[] args) throws InterruptedException
	{
		NeuralNetwork nn = new NeuralNetwork();
		nn.input_neurons = 2;
		nn.hidden_neurons = 3;
		nn.output_neurons = 1;
	}
}
