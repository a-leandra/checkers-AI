package sztuczna.inteligencja.checkers.training;

import sztuczna.inteligencja.checkers.Draughtsman;

import java.util.List;

public class NeuralNetwork {

    // Variable Declaration

    // Layers
    static Layer[] layers;

    // Training data
    static TrainingData[] tDataSet;

    static int whiteQueen = 0;
    static int blackQueen = 0;
    static int white = 0;
    static int black = 0;

    public static int Create(List<Draughtsman> draughtsmen) {
        // Set the Min and Max weight value for all Neurons
        Neuron.setRangeWeight(-1,1);

        // Create the layers
        layers = new Layer[3];
        layers[0] = null; // Input Layer 0,2
        layers[1] = new Layer(2,6); // Hidden Layer 2,6
        layers[2] = new Layer(6,1); // Output Layer 6,1

        // Create the training data
        CreateTrainingData(draughtsmen);

        train(10, 0.05f);

        int sum = 0;
        for(int i = 0; i < tDataSet.length; i++) {
            forward(tDataSet[i].data);
            sum += layers[2].neurons[0].value;
        }
        int whiteWon = black + blackQueen;
        int blackWon = white + whiteQueen;
        if(whiteWon == 0)
            return 100;
        else if(blackWon == 0)
            return -100;
        else
            return sum;
    }

    public static void CreateTrainingData(List<Draughtsman> draughtsmen) {
        int[] player2Queen = new int[] {0, 1}; //Expect -2 here
        int[] player2Normal = new int[] {0, 0}; //Expect -1 here
        int[] player1Queen = new int[] {1, 1}; //Expect 2 here
        int[] player1Normal = new int[] {1, 0}; //Expect 1 here

        int[] expectedOutput1 = new int[] {-2};
        int[] expectedOutput2 = new int[] {-1};
        int[] expectedOutput3 = new int[] {2};
        int[] expectedOutput4 = new int[] {1};

        tDataSet = new TrainingData[draughtsmen.size()];
        int i = 0;
        for (Draughtsman d : draughtsmen) {
            if (d.isQueen() && d.getPlayer() == 2) {
                tDataSet[i] = new TrainingData(player2Queen, expectedOutput1);
                blackQueen++;
            }
            else if (!d.isQueen() && d.getPlayer() == 2) {
                tDataSet[i] = new TrainingData(player2Normal, expectedOutput2);
                black++;
            }
            else if (d.isQueen() && d.getPlayer() == 1) {
                tDataSet[i] = new TrainingData(player1Queen, expectedOutput3);
                whiteQueen++;
            }
            else if (!d.isQueen() && d.getPlayer() == 1) {
                tDataSet[i] = new TrainingData(player1Normal, expectedOutput4);
                white++;
            }
            i++;
        }
    }

    public static void forward(int[] inputs) {
        // First bring the inputs into the input layer layers[0]
        layers[0] = new Layer(inputs);

        for(int i = 1; i < layers.length; i++) {
            for(int j = 0; j < layers[i].neurons.length; j++) {
                float sum = 0;
                for(int k = 0; k < layers[i-1].neurons.length; k++) {
                    sum += layers[i-1].neurons[k].value*layers[i].neurons[j].weights[k];
                }
                sum += layers[i].neurons[j].bias;
                layers[i].neurons[j].value = StatUtil.Sigmoid(sum);
            }
        }
    }

    // The idea is that you calculate a gradient and cache the updated weights in the neurons.
    // When ALL the neurons new weight have been calculated we refresh the neurons.
    // Calculate the output layer weights, calculate the hidden layer weight then update all the weights
    public static void backward(float learning_rate,TrainingData tData) {
        int number_layers = layers.length;
        int out_index = number_layers-1;

        // Update the output layers
        // For each output
        for(int i = 0; i < layers[out_index].neurons.length; i++) {
            // and for each of their weights
            float output = layers[out_index].neurons[i].value;
            float target = tData.expectedOutput[i];
            float derivative = output-target;
            float delta = derivative*(output*(1-output));
            layers[out_index].neurons[i].gradient = delta;
            for(int j = 0; j < layers[out_index].neurons[i].weights.length;j++) {
                float previous_output = layers[out_index-1].neurons[j].value;
                float error = delta*previous_output;
                layers[out_index].neurons[i].cache_weights[j] = layers[out_index].neurons[i].weights[j] - learning_rate*error;
            }
        }

        //Update all the subsequent hidden layers
        for(int i = out_index-1; i > 0; i--) {
            // For all neurons in that layers
            for(int j = 0; j < layers[i].neurons.length; j++) {
                float output = layers[i].neurons[j].value;
                float gradient_sum = sumGradient(j,i+1);
                float delta = (gradient_sum)*(output*(1-output));
                layers[i].neurons[j].gradient = delta;
                // And for all their weights
                for(int k = 0; k < layers[i].neurons[j].weights.length; k++) {
                    float previous_output = layers[i-1].neurons[k].value;
                    float error = delta*previous_output;
                    layers[i].neurons[j].cache_weights[k] = layers[i].neurons[j].weights[k] - learning_rate*error;
                }
            }
        }

        // Here we do another pass where we update all the weights
        for(int i = 0; i< layers.length;i++) {
            for(int j = 0; j < layers[i].neurons.length;j++) {
                layers[i].neurons[j].update_weight();
            }
        }

    }

    // This function sums up all the gradient connecting a given neuron in a given layer
    public static float sumGradient(int n_index,int l_index) {
        float gradient_sum = 0;
        Layer current_layer = layers[l_index];
        for(int i = 0; i < current_layer.neurons.length; i++) {
            Neuron current_neuron = current_layer.neurons[i];
            gradient_sum += current_neuron.weights[n_index]*current_neuron.gradient;
        }
        return gradient_sum;
    }

    // This function is used to train being forward and backward.
    public static void train(int training_iterations,float learning_rate) {
        for(int i = 0; i < training_iterations; i++) {
            for(int j = 0; j < tDataSet.length; j++) {
                forward(tDataSet[j].data);
                backward(learning_rate,tDataSet[j]);
            }
        }
    }
}
