package network;

import helper.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
			int rand = Util.randInt(1, maxChildren);

			List<Integer> tempList = new ArrayList<Integer>();
			for(int j=i+1;j<numOfNodes;j++){
				tempList.add(j);
			}
			
			for(int j=0;j<rand;j++){
				if (tempList.isEmpty())
					break;
				int randChildIndex = Util.randInt(0, tempList.size()-1);
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
	
	public void resetAllFlags() {
		for(Node node : this.nodes){
			node.setVisited(false);
			node.setMarkedBottom(false);
			node.setMarkedTop(false);
			node.setObserved(false);
		}
	}

	public void writeToFile(String filePath) throws IOException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				filePath)));
		pw.println(this.numOfNodes);
		
		for(int i=0;i<this.numOfNodes;i++){
			pw.print(i+1);
			pw.print(" [");
			
			int childrenLen = this.getNodeByID(i).getChildren().size();
			int childrenIndex = 0;
			for (Integer childID : this.getNodeByID(i).getChildren()){
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

			for(Integer childID : Util.getIDsFromStr(st.nextToken())){
				network.nodes[ID].addChild(childID);
				network.nodes[childID].addParent(ID);
			}
		}

		br.close();
		return network;
	}
}
