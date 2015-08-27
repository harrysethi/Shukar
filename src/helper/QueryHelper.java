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
	public static void processQueries(String inFilePath, String outFilePath,
			BayesianNetwork network) throws IOException {
		if(network==null){
			System.err.println("Bayesian network === null");
			System.exit(1);
		}
		
		BufferedReader br = new BufferedReader(new FileReader(inFilePath));
		int numOfQueries = Integer.parseInt(br.readLine());

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				outFilePath)));
		for (int i = 0; i < numOfQueries; i++) {
			System.out.println("\n----processing query: " + (i + 1) + "\n");
			network.resetAllFlags();

			Queue<Scheduled> scheduledQ = new LinkedList<Scheduled>();

			StringTokenizer st = new StringTokenizer(br.readLine());
			String queryNodeIDsStr = st.nextToken();
			String obsNodeIDsStr = st.nextToken();

			readQuery(network, scheduledQ, queryNodeIDsStr, obsNodeIDsStr);
			processScheduledQ(network, scheduledQ);

			List<Integer> dSeparatedNodeIDs = new ArrayList<Integer>();
			List<Integer> requisiteObsNodeIDs = new ArrayList<Integer>();
			List<Integer> requisiteProbNodeIDs = new ArrayList<Integer>();

			setRequiredLists(network, dSeparatedNodeIDs, requisiteObsNodeIDs,
					requisiteProbNodeIDs);

			IO.printReqListsOnConsole(dSeparatedNodeIDs, requisiteObsNodeIDs,
					requisiteProbNodeIDs);

			IO.printReqListsInFile(pw, queryNodeIDsStr, obsNodeIDsStr,
					dSeparatedNodeIDs, requisiteObsNodeIDs,
					requisiteProbNodeIDs);
		}

		br.close();
		pw.close();
	}

	private static void processScheduledQ(BayesianNetwork network,
			Queue<Scheduled> scheduledQ) {
		while (!scheduledQ.isEmpty()) {
			Scheduled scheduled = scheduledQ.remove();
			scheduled.getScheduledNode().setVisited(true);

			Direction dir = scheduled.getDirection();
			boolean isObs = scheduled.getScheduledNode().isObserved();

			/*
			 * System.out.println("Checking node: " +
			 * (scheduled.getScheduledNode().getID() + 1) + " from direction-" +
			 * dir);
			 */

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
			List<Integer> requisiteProbNodes) {
		for (Node node : network.getNodes()) {
			//if (node.isObserved() || !node.isVisited()) {
			if(!node.isMarkedBottom()){
				dSeparatedNodes.add(node.getID());
			}

			if (node.isMarkedTop()) {
				requisiteProbNodes.add(node.getID());
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

	private static void readQuery(BayesianNetwork network,
			Queue<Scheduled> scheduledQ, String queryNodeIDsStr,
			String obsNodeIDsStr) throws IOException {

		for (Integer queryNodeID : Util.getIDsFromStr(queryNodeIDsStr)) {
			Scheduled scheduled = new Scheduled(queryNodeID, Direction.CHILD,
					network);
			scheduledQ.add(scheduled);
		}

		for (Integer obsNodeID : Util.getIDsFromStr(obsNodeIDsStr)) {
			network.getNodeByID(obsNodeID).setObserved(true);
		}
	}
}
