package tm;

import java.util.ArrayList;

/**
 * A TMState object used by the TM class.
 * @author Brian Wu
 * @author Max Ma
 */
public class TMState {
    
    private int element;
    private ArrayList<int[]> transitions;

    /**
     * The default constructor. It initializes a transitions ArrayList to store with an undefined element.
     */
    public TMState() {
        this.transitions = new ArrayList<int[]>();
    }

    /**
     * Constructor that assigns the element with a value.
     * @param element
     */
    public TMState(int element) {
        this();
        this.element = element;
    }

    /**
     * Sets the element for the TMState
     * @param element an int value
     */
    public void setElement(int element) {
        this.element = element;
    }

    /**
     * Adds a transition to the transitions ArrayList. Due to indexing and how testfile.txt structure is, it is perfectly acceptable to use this method.
     * @param nextState
     * @param direction
     */
    public void addTransition(int nextState, int[] direction) {
        this.transitions.add(nextState, direction);
    }

    /**
     * Gets the transition given on a certain input
     * @param charIndex and int that represents the character input
     * @return int[] that contains (next_state, write_element, direction[0=Right, 1=Left])
     */
    public int[] getTransition (int charIndex){
        return this.transitions.get(charIndex);
    }

    /**
     * Grabs the output of all transitions in the transitions ArrayList. Used primarily for troubleshooting purposes.
     * @return a String containing all transitions with the given input
     */
    public String toString() {
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < transitions.size(); i++) {
            build.append("On input (" + i + "):\n");

            int[] transition = getTransition(i);
            build.append("[" + transition[0] + ", " + transition[1] + ", " + transition[2] + "]\n");
        }
        return build.toString();
    }
}
