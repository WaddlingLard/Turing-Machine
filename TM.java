import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TM {

    private LinkedList<Integer> tape;
    private int head; //head's current position 
    private int state; // current state
    // number of states and symbols for validation
    private int numStates;
    private int numSymbols; 
    private Map<String, String> transitions;

    public TM(int numStates, int numSymbols) {
        this.tape = new LinkedList<>();
        this.head =0;
        this.state =0;
        this.transitions = new HashMap<>();
        this.numStates = numStates;
        this.numSymbols = numSymbols;
        
    }
    // constructor for Turing machine with input string 
    public TM(String input, int numStates, int numSymbols) {
        this(numStates, numSymbols);
        for(int i = 0; i<input.length(); i++){
            tape.add(input.charAt(i)-'0');
        }
    }

    public boolean isHalted(){
        return state == numStates;
    }


    

}