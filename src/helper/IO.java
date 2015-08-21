/**
 * 
 */
package helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import network.BayesianNetwork;

/**
 * @author harinder
 *
 */
public class IO {
	public void writeToFile(String filePath, BayesianNetwork network) throws IOException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				filePath)));
		pw.println(network.getNumOfNodes());
		
		for(int i=0;i<network.getNumOfNodes();i++){
			pw.print(i+1);
			pw.print(" ");
			pw.println(Util.getNodeIDsAsDelimetedStr(network.getNodeByID(i).getChildren()));
		}
		
		pw.close();
	}

	public static BayesianNetwork readFromFile(String filePath)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));

		int numOfNodes = Integer.parseInt(br.readLine());

		BayesianNetwork network = new BayesianNetwork(numOfNodes);

		for (int i = 0; i < numOfNodes; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			int ID = Integer.parseInt(st.nextToken()) - 1;

			for(Integer childID : Util.getIDsFromStr(st.nextToken())){
				network.getNodeByID(ID).addChild(childID);
				network.getNodeByID(childID).addParent(ID);
			}
		}

		br.close();
		return network;
	}
}
