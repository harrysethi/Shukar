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
	private boolean isObserved;

	private boolean isMarkedTop;
	private boolean isMarkedBottom;
	private boolean isVisited;

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public Node(int ID) {
		this.ID = ID;
		parents = new ArrayList<Integer>();
		children = new ArrayList<Integer>();
	}

	public int getID() {
		return this.ID;
	}

	public boolean isMarkedTop() {
		return isMarkedTop;
	}

	public void setMarkedTop(boolean isMarkedTop) {
		this.isMarkedTop = isMarkedTop;
	}

	public boolean isMarkedBottom() {
		return isMarkedBottom;
	}

	public void setMarkedBottom(boolean isMarkedBottom) {
		this.isMarkedBottom = isMarkedBottom;
	}

	public boolean isObserved() {
		return isObserved;
	}

	public void setObserved(boolean isObserved) {
		this.isObserved = isObserved;
	}

	public void addParent(int ID) {
		this.parents.add(ID);
	}

	public void addChild(int ID) {
		this.children.add(ID);
	}

	public List<Integer> getParents() {
		return parents;
	}

	public List<Integer> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		return this.ID + ": " + this.parents + "," + this.children;
	}

}
