/**
 * 
 */
package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

import network.BayesianNetwork;
import network.Node;

/**
 * @author harinder
 *
 */
public class QueryHelper {
	public static void processQueries(String filePath, BayesianNetwork network)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		int numOfQueries = Integer.parseInt(br.readLine());

		for (int i = 0; i < numOfQueries; i++) {
			System.out.println("\n----processing query: " + (i + 1) + "\n");
			network.resetAllFlags();

			Queue<Scheduled> scheduledQ = new LinkedList<Scheduled>();
			readQuery(network, br, scheduledQ);
			processScheduledQ(network, scheduledQ);

			List<Integer> dSeparatedNodes = new ArrayList<Integer>();
			List<Integer> requisiteObsNodes = new ArrayList<Integer>();
			List<Integer> requisiteUnobsNodes = new ArrayList<Integer>();

			setRequiredLists(network, dSeparatedNodes, requisiteObsNodes,
					requisiteUnobsNodes);

			printReqListsOnConsole(dSeparatedNodes, requisiteObsNodes,
					requisiteUnobsNodes);
		}

		br.close();
	}

	private static void printReqListsOnConsole(List<Integer> dSeparatedNodes,
			List<Integer> requisiteObsNodes, List<Integer> requisiteUnobsNodes) {
		System.out.println("\n====D-SeparatedNodes======");
		for (int nodeID : dSeparatedNodes) {
			System.out.print(nodeID + 1);
			System.out.print("|");
		}
		System.out.println();

		System.out.println("\n====RequisiteUnobsNodes======");
		for (int nodeID : requisiteUnobsNodes) {
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

	private static void processScheduledQ(BayesianNetwork network,
			Queue<Scheduled> scheduledQ) {
		while (!scheduledQ.isEmpty()) {
			Scheduled scheduled = scheduledQ.remove();
			scheduled.getScheduledNode().setVisited(true);

			Direction dir = scheduled.getDirection();
			boolean isObs = scheduled.getScheduledNode().isObserved();

			System.out.println("Checking node: "
					+ (scheduled.getScheduledNode().getID() + 1)
					+ " from direction-" + dir);

			if (dir == Direction.CHILD && isObs) {
				// blocked
				continue;
			}

			if (dir == Direction.CHILD && !isObs) {
				sendToParents(network, scheduledQ, scheduled);
				sendToChildren(network, scheduledQ, scheduled);
				continue;
			}

			if (dir == Direction.PARENT && isObs) {
				sendToParents(network, scheduledQ, scheduled);
				continue;
			}

			if (dir == Direction.PARENT && !isObs) {
				sendToChildren(network, scheduledQ, scheduled);
				continue;
			}
		}
	}

	private static void sendToChildren(BayesianNetwork network,
			Queue<Scheduled> scheduledQ, Scheduled scheduled) {
		if (!scheduled.getScheduledNode().isMarkedBottom()) {
			scheduled.getScheduledNode().setMarkedBottom(true);
			sendForward(network, scheduledQ, scheduled.getScheduledNode()
					.getChildren(), Direction.PARENT);
		}
	}

	private static void sendToParents(BayesianNetwork network,
			Queue<Scheduled> scheduledQ, Scheduled scheduled) {
		if (!scheduled.getScheduledNode().isMarkedTop()) {
			scheduled.getScheduledNode().setMarkedTop(true);
			sendForward(network, scheduledQ, scheduled.getScheduledNode()
					.getParents(), Direction.CHILD);
		}
	}

	private static void setRequiredLists(BayesianNetwork network,
			List<Integer> dSeparatedNodes, List<Integer> requisiteObsNodes,
			List<Integer> requisiteUnobsNodes) {
		for (Node node : network.getNodes()) {
			if (!node.isObserved() && !node.isVisited()) {
				dSeparatedNodes.add(node.getID());
			}

			if (!node.isObserved() && node.isMarkedTop()) {
				requisiteUnobsNodes.add(node.getID());
			}

			if (node.isObserved() && node.isVisited()) {
				requisiteObsNodes.add(node.getID());
			}
		}
	}

	private static void sendForward(BayesianNetwork network,
			Queue<Scheduled> scheduledQ, List<Integer> nodeIDs,
			Direction direction) {
		for (int nodeID : nodeIDs) {
			Scheduled newScheduled = new Scheduled(nodeID, direction, network);
			scheduledQ.add(newScheduled);
		}
	}

	private static void readQuery(BayesianNetwork network, BufferedReader br,
			Queue<Scheduled> scheduledQ) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (Integer queryNodeID : Util.getIDsFromStr(st.nextToken())) {
			Scheduled scheduled = new Scheduled(queryNodeID, Direction.CHILD,
					network);
			scheduledQ.add(scheduled);
		}

		for (Integer obsNodeID : Util.getIDsFromStr(st.nextToken())) {
			network.getNodeByID(obsNodeID).setObserved(true);
		}
	}
}
