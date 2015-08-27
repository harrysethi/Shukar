/**
 * 
 */
package helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author harinder
 *
 */
public class ProcessedEntry {
	private Set<Integer> newlyEntries;
	private Set<Integer> oldyEntries;

	private List<Integer> newlyEntriesList;
	private List<Integer> oldyEntriesList;

	private boolean isInsertionMode;

	public ProcessedEntry() {
		this.newlyEntries = new HashSet<Integer>();
		this.oldyEntries = new HashSet<Integer>();
		this.isInsertionMode = true;
	}

	public void addOldyEntry(int nodeId) {
		checkIfInsertionMode();
		this.oldyEntries.add(nodeId);
	}
	
	public void addNewlyEntry(int nodeId) {
		checkIfInsertionMode();
		this.newlyEntries.add(nodeId);
	}

	private void checkIfInsertionMode() {
		if (!isInsertionMode) {
			System.err.println("ProcessedEntry: Not in Insertion Mode");
			System.exit(1);
		}
	}

	private void changingToNonInsertionMode() {
		if (this.isInsertionMode) {
			this.isInsertionMode = false;
			this.oldyEntriesList = new ArrayList<Integer>(this.oldyEntries);
			this.newlyEntriesList = new ArrayList<Integer>(this.newlyEntries);
		}
	}

	public List<Integer> getOldyEntries() {
		changingToNonInsertionMode();
		return this.oldyEntriesList;
	}

	public List<Integer> getNewlyEntries() {
		changingToNonInsertionMode();
		return this.newlyEntriesList;
	}
	
	@Override
	public String toString(){
		return this.oldyEntries + ":" + this.newlyEntries;
	}
}
