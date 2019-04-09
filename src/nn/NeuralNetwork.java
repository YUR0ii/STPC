package nn;

import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

public class NeuralNetwork
{
	/*
	 * TODO: Later on, having more layers or support for an
	 * arbitrary number of layers could be worth the effort
	 */

	// the number of neurons in each layer
	public int input_neurons;
	public int hidden_neurons;
	public int output_neurons;

	// the weights and biases
	protected DoubleMatrix W0;
	protected DoubleMatrix W1;
	protected DoubleMatrix B0;
	protected DoubleMatrix B1;

	// the layers
	protected DoubleMatrix A0;	// input layer
	protected DoubleMatrix A1;	// hidden layer
	protected DoubleMatrix A2;	// output layer

	// applies the derivative of tanh to each item of the DoubleMatrix A
	public static DoubleMatrix d_tanh(DoubleMatrix A)
	{
		// TODO: optimizations and caching
		DoubleMatrix tanh = MatrixFunctions.tanh(A);
		DoubleMatrix ones = DoubleMatrix.ones(A.rows, A.columns);
		return ones.sub(tanh.mul(tanh));
	}

	// loads the input into the first layer and computes all of the other layers
	public void feedforward(DoubleMatrix input)
	{
		A0 = input;
		A1 = MatrixFunctions.tanh(A0.mmul(W0.transpose()).addRowVector(B0));
		A2 = MatrixFunctions.tanh(A1.mmul(W1.transpose()).addRowVector(B1));
	}

	public void backprop()
	{
		// TODO: stub
	}

	// hyperparameters for training
	public class TrainingHyperparameters
	{
		// TODO: add more hyperparameters

		// the default parameter values
		int epochs = 10_000;
		double learning_rate = 0.01;
	}

	// trains the neural network on the given dataset using the given hyperparameters
	public void train(DoubleMatrix input_data, DoubleMatrix output_data, TrainingHyperparameters thp)
	{
		// we seed jblas's prng for consistency
		org.jblas.util.Random.seed(0);

		// generate random weights and biases
		W0 = DoubleMatrix.rand(hidden_neurons, input_neurons);
		B0 = DoubleMatrix.rand(hidden_neurons);
		W1 = DoubleMatrix.rand(output_neurons, hidden_neurons);
		B1 = DoubleMatrix.rand(output_neurons);

		// main training loop
		for (int i = 0; i < thp.epochs; i++)
		{
			feedforward(input_data);
			backprop();
		}
	}
}
