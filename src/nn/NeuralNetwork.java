package nn;

import java.util.Random;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

public class NeuralNetwork
{
	// TODO: more options for activation functions
	
	private Random prng;
	
	/*
	 * TODO: Later on, having more layers or support for an
	 * arbitrary number of layers could be worth the effort
	 */
	
	public int input_neurons;
	public int hidden_neurons;
	public int output_neurons;
	
	// weights and biases for each layer
	private DoubleMatrix weights1;
	private DoubleMatrix weights2;
	private DoubleMatrix biases1;
	private DoubleMatrix biases2;
	
	public NeuralNetwork()
	{
		// we seed the prng for consistency
		prng = new Random(0);
	}
	
	private DoubleMatrix input_layer;
	private DoubleMatrix hidden_layer;
	private DoubleMatrix output_layer;
	
	public void forward_prop(DoubleMatrix input)
	{
		input_layer = input;
		hidden_layer = MatrixFunctions.tanh(input_layer.mmul(weights1).add(biases1));
		output_layer = MatrixFunctions.tanh(hidden_layer.mmul(weights2).add(biases2));
	}
	
	public void backward_prop()
	{
		
	}
}
