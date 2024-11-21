package tm;

import java.util.ArrayList;

public class TMState {
    
    private int element;

    private ArrayList<int[]> transitions;

    public TMState() {
        this.transitions = new ArrayList<int[]>();
    }

    public TMState(int element) {
        this();
        this.element = element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public void addTransition(int nextState, int[] direction) {
        this.transitions.add(nextState, direction);
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
        }
        return build.toString();
    }
}
