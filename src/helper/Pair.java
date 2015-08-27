/**
 * 
 */
package helper;

/**
 * @author harinder
 *
 */
public class Pair {
	private int node1;
	private int node2;

	public Pair(int node1, int node2) {
		this.node1 = node1;
		this.node2 = node2;
	}

	@Override
	public String toString() {
		return "(" + node1 + "," + node2 + ")";
	}
}
