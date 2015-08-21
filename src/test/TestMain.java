/**
 * 
 */
package test;

import helper.IO;
import helper.QueryHelper;

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
		BayesianNetwork bayesianNetwork = IO.readFromFile("data/sample-bn.txt");
		//bayesianNetwork.writeToFile("data/out.txt");
		//System.out.println(bayesianNetwork.getNodes());
		QueryHelper.processQueries("data/sample-query.txt", "data/queryOut.txt" , bayesianNetwork);
	}

}
