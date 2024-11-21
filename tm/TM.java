package tm;

import java.util.ArrayList;

public class TM {

    private ArrayList<Integer> tape;
    // private Map<Integer, Integer> test;
    
    private int head; //head's current position 
    private int state; // current state

    // number of states and symbols for validation
    private int numStates;
    private int numSymbols; 

    private TMState[] Q;
    private Integer[] Sigma;

    private TMState q0;

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
        this.numSymbols = numSymbols;
        this.q0 = Q[0];
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

    // Don't believe this needs to exist
    public boolean inSigma(int letter) {
        if (letter == 0) { // 0 is not supposed to be in sigma, but is treated as an element
            return true;
        }

        return letter >= 0 && letter <= this.Sigma.length;
    }

    public boolean addTransition(int[] transition, int stateIndex, int charIndex) {

        if (stateIndex > numStates - 1) {
            System.out.println("Cannot apply transition");
            return false;
        }

        // TMState fromState = getState(stateIndex);
        TMState fromState = Q[stateIndex];
        fromState.addTransition(charIndex, transition);
        
        return true;
    }

    // Obsolete method, since states are indexed you can directly accessed them
    public TMState getState(int stateIndex) {

        int index = 0;
        for(TMState state: Q){
            if(index == stateIndex){
                return state;
            }
            index++;
        }
        return null;
    }  
 
    public void execute() {

        Integer value;
        boolean emptyTile = false;
        String currentSymbol;
        int symbolIndex;

        // If the head is on an empty tile
        if (tape.size() <= head) {
            value = 0;
            currentSymbol = "_";
            emptyTile = true;
        } else {
            value = tape.get(head);
            currentSymbol = value + "";
        }
    
        // Only continues to execute while the state is not in the final state
        while (state != numStates - 1) {

            // Convert symbol to index
            if (currentSymbol == "_") {
                symbolIndex = 0;
            } else {
                symbolIndex = value;
            }
    
            // Get transition
            int[] transition = Q[state].getTransition(symbolIndex);
    
            // Apply transition
            state = transition[0];
            if (emptyTile) {
                emptyTile = false;
                tape.add(head, transition[1]);
            } else {
                tape.set(head, transition[1]);
            }


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

            value = tape.get(head);
            currentSymbol = value + "";
        }
    }
   
    public String toString() {

        StringBuilder build = new StringBuilder();

        //get final output string 
        StringBuilder output = new StringBuilder(); 
        int sum = 0;

        if (tape.size() > 1000) {
            output.append("very large");
            for (Integer value : tape) {
                sum += value;
            }
        } else {
            for (Integer value : tape) {
                output.append(value);
                sum += value;
            }
        }

        //add output statistics
        build.append("Output: " + output.toString()).append("\n");
        build.append("output length: ").append(tape.size()).append("\n");
        build.append("sum of symbols: ").append(sum);

        return build.toString();
    }

}