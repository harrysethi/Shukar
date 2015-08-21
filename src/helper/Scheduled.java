/**
 * 
 */
package helper;

import network.BayesianNetwork;
import network.Node;

/**
 * @author harinder
 *
 */
public class Scheduled {
	private Node scheduledNode;
	private Direction direction;

	public Scheduled(int scheduledNodeID, Direction direction,
			BayesianNetwork network) {
		this.scheduledNode = network.getNodeByID(scheduledNodeID);
		this.direction = direction;
	}

	/**
	 * @return the scheduledNode
	 */
	public Node getScheduledNode() {
		return scheduledNode;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

}
