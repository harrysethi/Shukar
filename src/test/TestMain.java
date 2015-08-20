/**
 * 
 */
package test;

import java.io.IOException;

import network.BayesianNetwork;

/**
 * @author harinder
 *
 */
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		//BayesianNetwork bayesianNetwork = BayesianNetwork.generateNetwork(10, 5);
		BayesianNetwork bayesianNetwork = BayesianNetwork.readFromFile("data/sample-bn.txt");
		BayesianNetwork.writeToFile("data/out.txt",bayesianNetwork);
		//System.out.println(bayesianNetwork.getNodes());
	}

}
