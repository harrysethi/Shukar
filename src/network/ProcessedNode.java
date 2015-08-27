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
	private boolean isProcessedChild;
	private boolean isProcessedParent;

	private boolean isProcessingComplete;

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

	public void addBrotherNodes(Set<Integer> nodes, Direction dir) {
		switch (dir) {
		case CHILD:
			for (Integer node : nodes)
				this.brotherNodesFromChild.add(node);
			break;
		case PARENT:
			for (Integer node : nodes)
				this.brotherNodesFromParent.add(node);
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

	public boolean isProcessed(Direction dir) {
		switch (dir) {
		case CHILD:
			return this.isProcessedChild;
		case PARENT:
			return this.isProcessedParent;
		}

		return false;
	}

	public void setProcessed(boolean isProcessed, Direction dir) {
		switch (dir) {
		case CHILD:
			isProcessedChild = isProcessed;
		case PARENT:
			isProcessedParent = isProcessed;
	}
	}

	public boolean isProcessingComplete() {
		return isProcessingComplete;
	}

	public void setProcessingComplete(boolean isProcessingComplete) {
		this.isProcessingComplete = isProcessingComplete;
	}
}
