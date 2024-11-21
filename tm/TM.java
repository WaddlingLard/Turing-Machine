package tm;

import java.util.ArrayList;

/**
 * A class that represents a Turing Machine.
 * @author Brian Wu
 * @author Max Ma
 */
public class TM {

    private ArrayList<Integer> tape;
    
    private int head; //head's current position 
    private int state; // current state

    // number of states and symbols for validation
    private int numStates;

    private TMState[] Q;
    private Integer[] Sigma;

    /**
     * The default constructor for a TM. It requires 2 values in order to know the transition structure with a given test file
     * @param numStates The number of states a TM will have (final state will have no transitions)
     * @param numSymbols The number of characters in the alphabet (0 is not included)
     */
    public TM(int numStates, int numSymbols) {
        this.tape = new ArrayList<Integer>();
        this.Q = new TMState[numStates];
        this.Sigma = new Integer[numSymbols + 1]; // This is intentional as Sigma[0] will not be populated as '0' is not a character of the alphabet

        for (int i = 0; i < numStates; i++) {
            addState(i);
        }

        for (int j = 0; j <= numSymbols; j++) {
            addSigma(j);
        }
        
        this.head = 0;
        this.state = 0;
           
        this.numStates = numStates;
    }
    
    /**
     * Overloaded constructor that takes in a string as the starting input for the TM tape
     * @param input Starting string for the tape
     * @param numStates The number of states a TM will have (final state will have no transitions)
     * @param numSymbols The number of characters in the alphabet (0 is not included)
     */
    public TM(String input, int numStates, int numSymbols) {
        this(numStates, numSymbols);

        // Setting up input tape
        for(int i = 0; i < input.length(); i++){
            this.tape.add((Integer.parseInt(input.charAt(i) + "")));
        }
    }

    /**
     * Adds a new state to the TM
     * @param element int value that is assigned to the TMState
     * @return The TM state that was added
     */
    public TMState addState(int element) {
        TMState newState = new TMState(element);
        this.Q[element] = newState;
        return newState;
    }

    /**
     * Adds a new character to the TM
     * @param letter int value that represents the character for an alphabet
     */
    public void addSigma(int letter) {
        if (letter == 0) {
            return;
        }
        this.Sigma[letter] = letter;
    }

    /**
     * Adds a transition on a provided TMState
     * @param transition Transition information containing the (next_state, write_element, direction)
     * @param stateIndex Index of the given state
     * @param charIndex Index of the given input character from the alphabet
     * @return boolean, but now that I think about it there is not much boolean checking going on here, disregard this
     */
    public boolean addTransition(int[] transition, int stateIndex, int charIndex) {

        TMState fromState = Q[stateIndex];
        fromState.addTransition(charIndex, transition);
        
        return true;
    }
 
    /**
     * The bulk of the TM's work is here. This method executes the TM while keeping in mind the transitions from one state to
     * another and maintaining concurrency with the tape and halts when it reaches the accept state.
     */
    public void execute() {

        Integer value;

        // If the head is on an empty tile
        if (tape.size() <= head) {
            tape.add(0);
            value = 0;
        } else {
            value = tape.get(head);
        }

        // Only continues to execute while the state is not in the final state
        while (state != numStates - 1) {
    
            // Get transition
            int[] transition = Q[state].getTransition(value);
    
            // Apply transition
            state = transition[0];
            tape.set(head, transition[1]);

            // Move tape header
            if (transition[2] == 0) {
                head++;
                if (head == tape.size()) {
                    tape.add(0);
                }
            } else {
                head--;
                if (head < 0) {
                    tape.add(0, 0);
                    head = 0;
                }
            }

            // Maintain value
            value = tape.get(head);
        }
    }
   
    /**
     * Returns the output of the tapes current state. This method is ideally used after calling execute() as the TM is allowed to process
     * through the given data and arrive to a new, modified tape.
     * @return String containing the tape status, its length, and the sum of all elements.
     */
    public String toString() {

        StringBuilder build = new StringBuilder();

        //get final output string 
        StringBuilder output = new StringBuilder(); 
        int sum = 0;

        for (int i = 0; i < tape.size(); i++) {
            int value = tape.get(i);
            output.append(value);
            sum += value;
        }

        //add output statistics
        build.append("Output: " + output.toString()).append("\n");
        build.append("output length: ").append(tape.size()).append("\n");
        build.append("sum of symbols: ").append(sum);

        return build.toString();
    }

}