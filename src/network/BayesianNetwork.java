package network;

import helper.Direction;
import helper.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.directory.DirContext;

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

	public BayesianNetwork(int numOfNodes) {
		this.numOfNodes = numOfNodes;
		nodes = new Node[numOfNodes];

		for (int i = 0; i < numOfNodes; i++) {
			nodes[i] = new Node(i);
		}
	}
	
	public int getNumOfNodes() {
		return this.numOfNodes;
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
	
	public void getDsepPairs() {
		boolean[] processedArray = new boolean[this.numOfNodes];
		
		List<Set<Integer>> listOfProcessedSets = new ArrayList<Set<Integer>>();
		for(int i=0;i<this.numOfNodes;i++) {
			if(processedArray[i]) continue;
			
			Set<Integer> processedSet = dfs(i, Direction.CHILD);
			listOfProcessedSets.add(processedSet);
		}
		
	}
	
	private Set<Integer> dfs(Integer nodeNum, Direction dir){
		Set<Integer> processedSet = new HashSet<Integer>();
		processedSet.
		
		return processedSet;
	}
	
	public void resetAllFlags() {
		for(Node node : this.nodes){
			node.setVisited(false);
			node.setMarkedBottom(false);
			node.setMarkedTop(false);
			node.setObserved(false);
		}
	}
}
