/**
 * 
 */
package test;

import network.BayesianNetwork;

/**
 * @author harinder
 *
 */
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BayesianNetwork bayesianNetwork = BayesianNetwork.generateNetwork(10, 5);
		System.out.println(bayesianNetwork.getNodes());
	}

}
