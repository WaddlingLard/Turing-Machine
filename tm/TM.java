package tm;

import java.util.ArrayList;

public class TM {

    private ArrayList<Integer> tape;
    
    private int head; //head's current position 
    private int state; // current state

    // number of states and symbols for validation
    private int numStates;

    private TMState[] Q;
    private Integer[] Sigma;

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
        this.Q[element] = newState;
        return newState;
    }

    public void addSigma(int letter) {
        if (letter == 0) {
            return;
        }
        this.Sigma[letter] = letter;
    }

    public boolean addTransition(int[] transition, int stateIndex, int charIndex) {

        TMState fromState = Q[stateIndex];
        fromState.addTransition(charIndex, transition);
        
        return true;
    }
 
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
   
    public String toString() {

        StringBuilder build = new StringBuilder();

        //get final output string 
        StringBuilder output = new StringBuilder(); 
        int sum = 0;

        // if (tape.size() > 1000) {
        //     output.append("very large");
        //     for (Integer value : tape) {
        //         sum += value;
        //     }
        // } else {
        //     for (Integer value : tape) {
        //         output.append(value);
        //         sum += value;
        //     }
        // }

        // Iterator method (kinda ehh)
        // Iterator<Integer> iterator = tape.iterator();
        // while (iterator.hasNext()) {
        //     Integer value = iterator.next();
        //     output.append(value);
        //     sum += value;
        // }

        for (int i = 0; i < tape.size(); i++) {
            // int value = tape.get(i);
            output.append(tape.get(i));
            sum += tape.get(i);
        }

        //add output statistics
        build.append("Output: " + output.toString()).append("\n");
        build.append("output length: ").append(tape.size()).append("\n");
        build.append("sum of symbols: ").append(sum);

        return build.toString();
    }

}