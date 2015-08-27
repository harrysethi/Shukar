/**
 * 
 */
package test;

import helper.IO;
import helper.QueryHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import network.BayesianNetwork;

/**
 * @author harinder
 *
 */
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("shukar hai jo SHUKAR hai :)");
		System.out.println("Welcome to SHUKAR");
		
		BayesianNetwork bayesianNetwork = null;
		while(true){
			System.out.println("Please let us know how can we help you?");
			System.out.println("1. press 1 for generating a random bayesian network");
			System.out.println("2. press 2 for reading the bayesian network from a file");
			System.out.println("3. press 3 to write the bayesian network to a file");
			System.out.println("4. press 4 to process the query file");
			System.out.println("5. press 5 to get set of dSeparatedPairs");
			System.out.println("0. press any other number to exit");
			
			BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
			
			int option = 0;

			option = Integer.parseInt(br.readLine());
			
				switch(option){
				case 1:
					System.out.println();
					System.out.println("Please enter number of nodes in the network");
					int n = Integer.parseInt(br.readLine());
					System.out.println("Please enter max-number of children for each node");
					int k = Integer.parseInt(br.readLine());
					bayesianNetwork = BayesianNetwork.generateNetwork(n, k);
					System.out.println("Network generated :D--- write to file if want to see");
					System.out.println();
					break;
				case 2:
					System.out.println();
					System.out.println("Enter the path of the file to read from");
					String inputFile = br.readLine();
					bayesianNetwork = IO.readNetworkFromFile(inputFile);
					System.out.println("Network read :D");
					System.out.println();
					break;
				case 3:
					System.out.println();
					if(bayesianNetwork==null){
						System.err.println("Bayesian network === null");
						break;
					}
					System.out.println("Enter the path of the file to write in");
					String outputFile = br.readLine();
					IO.writeToFile(outputFile, bayesianNetwork);
					System.out.println("Network written :D");
					System.out.println();
					break;
				case 4:
					System.out.println();
					if(bayesianNetwork==null){
						System.err.println("Bayesian network === null");
						break;
					}
					System.out.println("Enter the path of the query file to read from");
					String queryInFile = br.readLine();
					System.out.println("Enter the path of the output file to write in");
					String queryOutFile = br.readLine();
					QueryHelper.processQueries(queryInFile, queryOutFile , bayesianNetwork);
					System.out.println("Queries Processed :D");
					System.out.println();
					break;
				case 5:
					System.out.println();
					if(bayesianNetwork==null){
						System.err.println("Bayesian network === null");
						break;
					}
					System.out.println("Enter the path of the file to read observed nodes");
					String obsNodesFile = br.readLine();
					System.out.println("Enter the path of the output file to write in");
					String dSepOutFile = br.readLine();
					bayesianNetwork.getDsepPairs(obsNodesFile, dSepOutFile);
					System.out.println("Got the set of dsep pairs :D...check the output file");
					System.out.println();
					break;
				default:
					System.out.println();
					System.out.println("It was definitely fun...isn't it? :)... Please visit again");
					System.out.println();
					br.close();
					System.exit(0);
					break;
				}
			
		}
	}

}
