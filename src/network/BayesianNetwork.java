package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import util.util;

/**
 * 
 */

/**
 * @author harinder
 *
 */
public class BayesianNetwork {
	private int numOfNodes;
	private Node[] nodes;

	private BayesianNetwork(int numOfNodes) {
		this.numOfNodes = numOfNodes;
		nodes = new Node[numOfNodes];

		for (int i = 0; i < numOfNodes; i++) {
			nodes[i] = new Node(i);
		}
	}

	public Node getNodeByID(int ID) {
		return nodes[ID];
	}

	public Node[] getNodes() {
		return this.nodes;
	}

	public static BayesianNetwork generateNetwork(int numOfNodes,
			int maxChildren) {
		BayesianNetwork network = new BayesianNetwork(numOfNodes);

		for (int i = 0; i < numOfNodes; i++) {
			int rand = util.randInt(1, maxChildren);

			List<Integer> tempList = new ArrayList<Integer>();
			for(int j=i+1;j<numOfNodes;j++){
				tempList.add(j);
			}
			
			for(int j=0;j<rand;j++){
				if (tempList.isEmpty())
					break;
				int randChildIndex = util.randInt(0, tempList.size()-1);
				int randChild = tempList.get(randChildIndex);
						
				network.nodes[i].addChild(randChild);
				network.nodes[randChild].addParent(i);
				
				tempList.remove(randChildIndex);
			}
			
			/*for (int j = i + 1; j <= i + rand; j++) {
				if (j >= numOfNodes)
					break;
				network.nodes[i].addChild(j);
				network.nodes[j].addParent(i);
			}*/
		}

		return network;
	}

	public static void writeToFile(String filePath, BayesianNetwork network) throws IOException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				filePath)));
		pw.println(network.numOfNodes);
		
		for(int i=0;i<network.numOfNodes;i++){
			pw.print(i+1);
			pw.print(" [");
			
			int childrenLen = network.getNodeByID(i).getChildren().size();
			int childrenIndex = 0;
			for (Integer childID : network.getNodeByID(i).getChildren()){
				pw.print(childID+1);
				childrenIndex++;
				if(childrenIndex != childrenLen) pw.print(",");
			}
			pw.println("]");
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

			int index = 1;
			char[] children = st.nextToken().toCharArray();

			while (index < children.length - 1) {
				int childID = children[index++] - '0' - 1;
				network.nodes[ID].addChild(childID);
				network.nodes[childID].addParent(ID);
				index++;
			}
		}

		br.close();
		return network;
	}
}
