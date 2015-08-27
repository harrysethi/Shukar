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
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import network.BayesianNetwork;

/**
 * @author harinder
 *
 */
public class IO {
	public static void printReqListsInFile(PrintWriter pw,
			String queryNodeIDsStr, String obsNodeIDsStr,
			List<Integer> dSeparatedNodeIDs, List<Integer> requisiteObsNodeIDs,
			List<Integer> requisiteProbNodeIDs) {
		pw.print("query:");
		pw.print(queryNodeIDsStr);
		pw.print(" ");
		pw.print("obs:");
		pw.print(obsNodeIDsStr);
		pw.print(" ");
		pw.print("dsep:");
		pw.print(Util.getNodeIDsAsDelimetedStr(dSeparatedNodeIDs));
		pw.print(" ");
		pw.print("req-prob:");
		pw.print(Util.getNodeIDsAsDelimetedStr(requisiteProbNodeIDs));
		pw.print(" ");
		pw.print("req-obs:");
		pw.print(Util.getNodeIDsAsDelimetedStr(requisiteObsNodeIDs));
		pw.println();
	}

	public static void printReqListsOnConsole(List<Integer> dSeparatedNodes,
			List<Integer> requisiteObsNodes, List<Integer> requisiteProbNodes) {
		System.out.println("\n====D-SeparatedNodes======");
		for (int nodeID : dSeparatedNodes) {
			System.out.print(nodeID + 1);
			System.out.print("|");
		}
		System.out.println();

		System.out.println("\n====RequisiteProbNodes======");
		for (int nodeID : requisiteProbNodes) {
			System.out.print(nodeID + 1);
			System.out.print("|");
		}
		System.out.println();

		System.out.println("\n====RequisiteObsNodes======");
		for (int nodeID : requisiteObsNodes) {
			System.out.print(nodeID + 1);
			System.out.print("|");
		}
		System.out.println();
	}
	
	public static void writeToFile(String filePath, BayesianNetwork network)
			throws IOException {
		if(network==null){
			System.err.println("Bayesian network === null");
			System.exit(1);
		}
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				filePath)));
		pw.println(network.getNumOfNodes());

		for (int i = 0; i < network.getNumOfNodes(); i++) {
			pw.print(i + 1);
			pw.print(" ");
			Collections.sort(network.getNodeByID(i)
					.getChildren());
			pw.println(Util.getNodeIDsAsDelimetedStr(network.getNodeByID(i)
					.getChildren()));
		}

		pw.close();
	}

	public static BayesianNetwork readNetworkFromFile(String filePath)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));

		int numOfNodes = Integer.parseInt(br.readLine());

		BayesianNetwork network = new BayesianNetwork(numOfNodes);

		for (int i = 0; i < numOfNodes; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			int ID = Integer.parseInt(st.nextToken()) - 1;

			for (Integer childID : Util.getIDsFromStr(st.nextToken())) {
				network.getNodeByID(ID).addChild(childID);
				network.getNodeByID(childID).addParent(ID);
			}
		}

		br.close();
		return network;
	}

	public static List<Integer> readObservedNodes(String filePath)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));

		String obsNodesStr = br.readLine();
		List<Integer> obsNodes = Util.getIDsFromStr("[" + obsNodesStr + "]");

		br.close();

		return obsNodes;
	}
}
