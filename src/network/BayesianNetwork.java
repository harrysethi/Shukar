package network;
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
	private int maxChildren;
	private Node[] nodes;
	
	private BayesianNetwork(int numOfNodes, int maxChildren){
		this.numOfNodes = numOfNodes;
		this.maxChildren = maxChildren;
		nodes = new Node[numOfNodes];
		
		for(int i=0;i<numOfNodes;i++){
			nodes[i] = new Node(i);
		}
	}
	
	public Node getNodeByID(int ID){
		return nodes[ID];
	}
	
	public Node[] getNodes(){
		return this.nodes;
	}
	
	public static BayesianNetwork generateNetwork(int numOfNodes, int maxChildren) {
		BayesianNetwork network = new BayesianNetwork(numOfNodes, maxChildren);
		
		for(int i=0;i<numOfNodes;i++){
			int rand = util.randInt(1, maxChildren);
			
			for(int j=i+1;j<=i+rand;j++){
				if(j>=numOfNodes) break;
				network.nodes[i].addChild(j);
				network.nodes[j].addParent(i);
			}
		}
		
		return network;
	}
}
