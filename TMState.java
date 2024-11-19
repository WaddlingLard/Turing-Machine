import java.util.HashMap;
import java.util.Map;

public class TMState {
    
    private int element;

    // Could use TMState to be stored in HashMap instead
    private Map<Integer, String> transitions;

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

    public String addTransition(int nextState, String direction) {
        return this.transitions.put(nextState, direction);
    }

    public String getTransition() {

        return "";
    }

}
