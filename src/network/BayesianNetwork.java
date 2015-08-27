package network;

import helper.Direction;
import helper.IO;
import helper.Pair;
import helper.ProcessedEntry;
import helper.Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
			for (int j = i + 1; j < numOfNodes; j++) {
				tempList.add(j);
			}

			for (int j = 0; j < rand; j++) {
				if (tempList.isEmpty())
					break;
				int randChildIndex = Util.randInt(0, tempList.size() - 1);
				int randChild = tempList.get(randChildIndex);

				network.nodes[i].addChild(randChild);
				network.nodes[randChild].addParent(i);

				tempList.remove(randChildIndex);
			}

			/*
			 * for (int j = i + 1; j <= i + rand; j++) { if (j >= numOfNodes)
			 * break; network.nodes[i].addChild(j);
			 * network.nodes[j].addParent(i); }
			 */
		}

		return network;
	}

	public void getDsepPairs(String obsNodesFilePath, String outFilePath) throws IOException {
		resetAllFlags(); //it is good to reset all flags before beginning :)
		
		List<Integer> obsNodes = IO.readObservedNodes(obsNodesFilePath);
		for (Integer nodeNum : obsNodes) {
			this.getNodeByID(nodeNum).setObserved(true);
		}

		List<ProcessedEntry> processedEntries = new ArrayList<ProcessedEntry>();
		boolean[] isOldyEntry = new boolean[this.numOfNodes];
		
		for (int currNode = 0; currNode < this.numOfNodes; currNode++) {
			if (this.getNodeByID(currNode).getProcessedDetails().isProcessingComplete())
				continue;

			dfs(currNode, Direction.CHILD);
			ProcessedEntry processedEntry = new ProcessedEntry();
			processedEntries.add(processedEntry);
			
			processedEntry.addNewlyEntry(currNode);
			for (Integer nodeNum : this.getNodeByID(currNode)
					.getProcessedDetails().getBrotherNodes(Direction.CHILD)) {
				if(!isOldyEntry[nodeNum]) {
					processedEntry.addNewlyEntry(nodeNum);
					isOldyEntry[nodeNum] = true;
				}
				else{
					processedEntry.addOldyEntry(nodeNum);
				}
			}
		}

		//populating reachability array
		boolean[][] reachabilityArray = new boolean[this.numOfNodes][this.numOfNodes];
		for(ProcessedEntry processedEntry : processedEntries) {
			for(int newlyEntry : processedEntry.getNewlyEntries()){
				for(int reachedEntry : processedEntry.getNewlyEntries()){
					reachabilityArray[newlyEntry][reachedEntry] = true;
				}
				
				for(int reachedEntry : processedEntry.getOldyEntries()){
					reachabilityArray[newlyEntry][reachedEntry] = true;
				}
			}
				
			for(int oldyEntry : processedEntry.getOldyEntries()){
				for(int reachedEntry : processedEntry.getNewlyEntries()){
					reachabilityArray[oldyEntry][reachedEntry] = true;
				}
			}
		}
		
		//getting pairs
		List<Pair> dSepPairs = new ArrayList<Pair>();
		
		for (int i = 0; i < this.numOfNodes; i++) {
			for (int j = i + 1; j < this.numOfNodes; j++) {
				if(!reachabilityArray[i][j] || this.getNodeByID(i).isObserved()){
					dSepPairs.add(new Pair(i+1, j+1));
				}
			}
		}
		
		//printing pairs to file
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outFilePath)));
		for(Pair pair : dSepPairs){
			pw.println(pair);
		}
		pw.close();
		
		System.out.println("Done with getting dSeparated pairs");
	}

	private void dfs(Integer currNodeNum, Direction dir) {
		Node currNode = this.getNodeByID(currNodeNum);
		currNode.getProcessedDetails().setProcessed(true, dir); //I have processed this node
		currNode.getProcessedDetails().setProcessingComplete(true);
		
		boolean isObs = currNode.isObserved();
		currNode.getProcessedDetails().addBrotherNode(currNodeNum, dir);
		
		if (dir == Direction.CHILD && isObs) {
			return;
		}

		if (dir == Direction.CHILD && !isObs) {
			parentsTurn(dir, currNode);
			childsTurn(dir, currNode);
			return;
		}

		if (dir == Direction.PARENT && isObs) {
			parentsTurn(dir, currNode);
			return;
		}

		if (dir == Direction.PARENT && !isObs) {
			childsTurn(dir, currNode);
			return;
		}
	}

	private void childsTurn(Direction dir, Node currNode) {
		if (!currNode.isMarkedBottom()) {
			currNode.setMarkedBottom(true);
			
			for (int nodeID : currNode.getChildren()) {
				ProcessedNode nodeProcessedDetails = this.getNodeByID(nodeID).getProcessedDetails();
				if (!nodeProcessedDetails.isProcessed(Direction.PARENT)) {
					dfs(nodeID, Direction.PARENT);
				}
				
				currNode.getProcessedDetails().addBrotherNodes(nodeProcessedDetails.getBrotherNodes(Direction.PARENT), dir);
			}
		}
	}

	private void parentsTurn(Direction dir, Node currNode) {
		if (!currNode.isMarkedTop()) {
			currNode.setMarkedTop(true);

			for (int nodeID : currNode.getParents()) {
				ProcessedNode nodeProcessedDetails = this.getNodeByID(nodeID).getProcessedDetails();
				if (!nodeProcessedDetails.isProcessed(Direction.CHILD)) {
					dfs(nodeID, Direction.CHILD);
				}
				
				currNode.getProcessedDetails().addBrotherNodes(nodeProcessedDetails.getBrotherNodes(Direction.CHILD), dir);
			}
		}
	}

	public void resetAllFlags() {
		for (Node node : this.nodes) {
			node.setVisited(false);
			node.setMarkedBottom(false);
			node.setMarkedTop(false);
			node.setObserved(false);
			
			node.getProcessedDetails().getBrotherNodes(Direction.CHILD).clear();
			node.getProcessedDetails().getBrotherNodes(Direction.PARENT).clear();
			node.getProcessedDetails().setProcessed(false, Direction.CHILD);
			node.getProcessedDetails().setProcessed(false, Direction.PARENT);
		}
	}
}
