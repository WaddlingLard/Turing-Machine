import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class TM {

    private LinkedList<Integer> tape;
    private int head; //head's current position 
    private int state; // current state
    // number of states and symbols for validation
    private int numStates;
    private int numSymbols; 

    private HashSet<TMState> Q;
    private HashSet<Integer> Sigma;
    private TMState q0;

    public TM(int numStates, int numSymbols) {
        this.tape = new LinkedList<>();
        this.Q = new HashSet<>();
        this.Sigma = new HashSet<>();

        for (int i = 0; i < numStates; i++) {
            addState(i);
        }

        for (int j = 0; j <= numSymbols; j++) {
            addSigma(j);
        }
        
        this.head = 0;
        this.state = 0;
        this.numStates = numStates;
        this.numSymbols = numSymbols;
        this.q0 = getState(0);
    }
    
    // constructor for Turing machine with input string 
    public TM(String input, int numStates, int numSymbols) {
        this(numStates, numSymbols);

        // Setting up input tape
        for(int i = 0; i < input.length(); i++){
            this.tape.add((Integer.parseInt(input.charAt(i) + "")));
        }
    }

    public TMState addState(int element) {
        TMState newState = new TMState(element);
        Q.add(newState);
        return newState;
    }

    public void addSigma(int letter) {
        if (letter == 0) {
            return;
        }
        Sigma.add(letter);
    }

    public boolean inSigma(int letter) {
        if (letter == 0) { // 0 is not supposed to be in sigma, but is treated as an element
            return true;
        }
    
        return Sigma.contains(letter);
    }

    // Unsure if needed
    public boolean setInitialState() {
        
        return false;
    }

    public boolean addTransition(String[] transition, int stateIndex, int charIndex) {

        // Ex: numStates 2 (0, 1, 2), but you cannot have transitions on State 2
        if (stateIndex > numStates - 1 || !inSigma(charIndex)) {
            System.out.println("Cannot apply transition");
            return false;
        }

        TMState fromState = getState(stateIndex);
        fromState.addTransition(charIndex, transition);
        
        return false;
    }

    public TMState getState(int stateIndex) {

        // Iterator<TMState> iterate = Q.iterator(); 

        int index = 0;
        for(TMState state: Q){
            if(index == stateIndex){
                return state;
            }
            index++;
        }
        return null;
    }
        
        // int index = 0;
        // while (iterate.hasNext()) {
        //     TMState grabbed = iterate.next();
        //     if (index == stateIndex) {
        //         return grabbed;
        //     }
        //     index++;
        // }// // // 

    // //     return null;
    // // }

    public boolean isHalted(){
        return state == numStates;
    }
   
    public String toString() {

        StringBuilder build = new StringBuilder();

        int stateIndex = 0;
        for (TMState state: Q) {
            build.append("State " + stateIndex++ + ":\n");
            build.append(state.toString());
        }

        build.append("\nSigma: {");
        for (int num: Sigma) {
            build.append(num + ", ");
        }
        build.append("}\n");
        build.append("Current tape: " + tape.toString());

        build.append("\nInitial State:\n");
        build.append(this.q0.toString());

        return build.toString();
    }

}