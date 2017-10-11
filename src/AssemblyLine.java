//has oranges in it


import java.util.ArrayList;
import java.util.List;

public class AssemblyLine {
	private final List<Orange> oranges;

	// Initialize the arraylist as a new arraylist for each conveyor belt.
	public AssemblyLine() {
		oranges = new ArrayList<Orange>();      //
	}

	// Add an orange to the ArrayList
	public synchronized void addOrange(Orange o) {//throws InterruptedException {   //adding orange to the pile
		oranges.add(o);
		if (countOranges() == 1) {
			notifyAll();
		}
	}

	// Gets the first orange in the sequence
	public synchronized Orange getOrange() {    //picking up from pile
		while (countOranges() == 0) {
			try {
				
				wait();
			} catch (InterruptedException ignored) {

			}
		}
//		System.out.println("not waiting");
		return oranges.remove(0);
	}

//	public synchronized void removeOrange() {
//		oranges.remove(0);
//	}

	// Count the total number of oranges in the ArrayList
	public synchronized int countOranges() {
		return oranges.size();
	}
}
