package network;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author harinder
 *
 */
public class Node {
	private int ID;
	private List<Integer> parents;
	private List<Integer> children;

	public Node(int ID) {
		this.ID = ID;
		parents = new ArrayList<Integer>();
		children = new ArrayList<Integer>();
	}

	public void addParent(int ID) {
		this.parents.add(ID);
	}

	public void addChild(int ID) {
		this.children.add(ID);
	}

	@Override
	public String toString() {
		return this.ID + ": " + this.parents + "," + this.children;
	}

}
