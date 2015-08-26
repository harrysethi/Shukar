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
		//BayesianNetwork bayesianNetwork = BayesianNetwork.generateNetwork(10, 2);
		BayesianNetwork bayesianNetwork = IO.readFromFile("data/sample-bn2.txt");
		//IO.writeToFile("data/bn_my.txt", bayesianNetwork);
		//System.out.println(bayesianNetwork.getNodes());
		QueryHelper.processQueries("data/sample-query2.txt", "data/query_out.txt" , bayesianNetwork);
	}

}
