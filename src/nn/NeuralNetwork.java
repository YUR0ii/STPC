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
	public DoubleMatrix A2;		// output layer

	// loss
	protected DoubleMatrix loss;

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
		//A1 = MatrixFunctions.tanh(A0.mmul(W0.transpose()).addRowVector(B0));
		//A2 = MatrixFunctions.tanh(A1.mmul(W1.transpose()).addRowVector(B1));
		A1 = MatrixFunctions.tanh(A0.mmul(W0.transpose()));
		A2 = MatrixFunctions.tanh(A1.mmul(W1.transpose()));
	}

	public void backprop(DoubleMatrix desired_output, TrainingHyperparameters thp)
	{
		// implementation of the squared loss function
		DoubleMatrix loss = DoubleMatrix.zeros(A2.rows);
		for (int i = 0; i < output_neurons; i++)
		{
			loss.add(MatrixFunctions.pow(A2.getRow(i).sub(desired_output.getRow(i)), 2));
		}

		// take the derivative of the loss function with respect to the weights
		// TODO: caching and cleanup
		DoubleMatrix d_loss = desired_output.sub(A2).mul(2);
		DoubleMatrix d_loss_d_A2 = d_loss.mul(d_tanh(A2));
		DoubleMatrix d_W1 = A1.transpose().mmul(d_loss_d_A2);
		DoubleMatrix tmp = d_loss_d_A2.mmul(W1);
		DoubleMatrix tmp2 = (tmp).mul(d_tanh(A1));
		DoubleMatrix d_W0 = A0.transpose().mmul(tmp2);

		W1 = W1.add(d_W1.transpose().mul(thp.learning_rate));
		W0 = W0.add(d_W0.transpose().mul(thp.learning_rate));
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

		W0 = DoubleMatrix.randn(hidden_neurons, input_neurons);
		B0 = DoubleMatrix.randn(hidden_neurons);
		W1 = DoubleMatrix.randn(output_neurons, hidden_neurons);
		B1 = DoubleMatrix.randn(output_neurons);

		// main training loop
		for (int i = 0; i < thp.epochs; i++)
		{
			feedforward(input_data);
			backprop(output_data, thp);
		}
	}
}
