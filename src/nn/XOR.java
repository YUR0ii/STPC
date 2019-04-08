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
		
		DoubleMatrix inputs = new DoubleMatrix(
			new double[][] {
				{0, 0},
				{0, 1},
				{1, 0},
				{1, 1}
			}
		);
		
		DoubleMatrix outputs = new DoubleMatrix(
			new double[][] {
				{0},
				{1},
				{1},
				{0}
			}
		);
		
		NeuralNetwork.TrainingHyperparameters thp = nn.new TrainingHyperparameters();
		thp.epochs = 40_000;
		thp.learning_rate = 0.1;
		
		long nt = System.nanoTime();
		nn.train(inputs, outputs, thp);
		System.out.println("Finished training in " + (System.nanoTime() - nt) / 1_000_000 + " ms");
	}
}
