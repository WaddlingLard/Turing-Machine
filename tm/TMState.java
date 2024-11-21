package tm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TMState {
    
    private int element;

    // Could use tm.TMState to be stored in HashMap instead
    private Map<Integer, int[]> transitions;

    public TMState() {
        this.transitions = new HashMap<>();
    }

    public TMState(int element) {
        this();
        this.element = element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public int[] addTransition(int nextState, int[] direction) {
        return this.transitions.put(nextState, direction);
    }

    public int[] getTransition (int charIndex){
        return this.transitions.get(charIndex);
    }

    public String toString() {
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < transitions.size(); i++) {
            build.append("On input (" + i + "):\n");

            int[] transition = getTransition(i);
            build.append("[" + transition[0] + ", " + transition[1] + ", " + transition[2] + "]\n");
//            System.out.println("looking for transition on " + i);
        }
        return build.toString();
    }
}
