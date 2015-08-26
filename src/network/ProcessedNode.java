/**
 * 
 */
package network;

import helper.Direction;

import java.util.HashSet;
import java.util.Set;

/**
 * @author harinder
 *
 */
public class ProcessedNode {
	private Set<Integer> brotherNodesFromChild = new HashSet<Integer>();
	private Set<Integer> brotherNodesFromParent = new HashSet<Integer>();

	public void addBrotherNode(Integer nodeNum, Direction dir) {
		switch (dir) {
		case CHILD:
			this.brotherNodesFromChild.add(nodeNum);
			break;
		case PARENT:
			this.brotherNodesFromParent.add(nodeNum);
			break;
		}
	}

	public Set<Integer> getBrotherNodes(Direction dir) {
		switch (dir) {
		case CHILD:
			return this.brotherNodesFromChild;
		case PARENT:
			return this.brotherNodesFromParent;
		}

		return null;
	}
}
