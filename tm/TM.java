package tm;

import java.util.HashSet;
import java.util.LinkedList;

public class TM {

    private LinkedList<Integer> tape;

    
    private int head; //head's current position 

    // Should state be TMState?
    private int state; // current state

    // number of states and symbols for validation
    private int numStates;
    private int numSymbols; 

    // private HashSet<TMState> Q;
    // private HashSet<Integer> Sigma;
    private TMState[] Q;
    private Integer[] Sigma;

    private TMState q0;
    // private TMState[] states;


    public TM(int numStates, int numSymbols) {
        this.tape = new LinkedList<>();

        // this.Q = new HashSet<>();
        // this.Sigma = new HashSet<>();
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
        // this.q0 = getState(0);
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
        // Q.add(newState);
        this.Q[element] = newState;
        return newState;
    }

    public void addSigma(int letter) {
        if (letter == 0) {
            return;
        }
        // Sigma.add(letter);
        this.Sigma[letter] = letter;
    }

    // Don't believe this needs to exist
    public boolean inSigma(int letter) {
        if (letter == 0) { // 0 is not supposed to be in sigma, but is treated as an element
            return true;
        }

        return letter >= 0 && letter <= this.Sigma.length;
    
        // return Sigma.contains(letter);
    }

    public boolean addTransition(String[] transition, int stateIndex, int charIndex) {

        // Ex: numStates 2 (0, 1, 2), but you cannot have transitions on State 2
        // if (stateIndex > numStates - 1 || !inSigma(charIndex)) {
        //     System.out.println("Cannot apply transition");
        //     return false;
        // }

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

    public boolean isHalted(){
        return state == numStates;
    }
    
 
    public void execute() {

        // Uncertain about this line

        // if (tape.isEmpty()) { 
        //     return;
        // }


        // if (tape.size() <= head) {
            
        // }
    
        // Only continues to execute while the state is not in the final state
        while (state != numStates - 1) {
            // Get current symbol

            Integer value;
            boolean emptyTile;
            String currentSymbol;

            // If the head is on an empty tile
            if (tape.size() <= head || head < 0) {
                value = 0;
                emptyTile = true;
                currentSymbol = "_";
            } else {
                value = tape.get(head);
                emptyTile = false;
                currentSymbol = value + "";
            }

            int symbolIndex;
            
            // Convert symbol to index
            if (currentSymbol == "_") {
                // symbolIndex = numSymbols;
                symbolIndex = 0;
            } else {
                symbolIndex = value;
            }
    
            // Get transition
            String[] transition = Q[state].getTransition(symbolIndex);
            
            // I believe this is obsolete
            // if (transition == null) {
            //     System.out.println("No transition found. Machine halted.");
            //     return;
            // }
    
            // Apply transition
            state = Integer.parseInt(transition[0]);
            if (emptyTile) {
                tape.add(head, Integer.valueOf(transition[1]));
            } else {
                tape.set(head, Integer.valueOf(transition[1]));
            }
    
            // Handle head movement
            if (transition[2].equals("R")) {
                head++;
                if (head == tape.size()) {
                    // tape.add(Integer.valueOf(numSymbols));
                    tape.add(0);
                }
            } else if (transition[2].equals("L")) {
                head--;
                if (head < 0) {
                    // tape.addFirst(Integer.valueOf(numSymbols));
                    tape.addFirst(0);
                    head = 0;
                }
            }
        }
    }
   
    public String toString() {

        StringBuilder build = new StringBuilder();

        //get final output string 
        StringBuilder output = new StringBuilder(); 
        int sum =0;

        if (tape.size() > 1000) {
            output.append("very large");
            for (Integer value : tape) {

                // if (value != numSymbols) {
                //     output.append(value);
                //     sum += value;
                // }
    
                // output.append(value);
                sum += value;
            }
        } else {
            for (Integer value : tape) {

                // if (value != numSymbols) {
                //     output.append(value);
                //     sum += value;
                // }
    
                output.append(value);
                sum += value;
            }
        }

        // for (Integer value : tape) {

        //     // if (value != numSymbols) {
        //     //     output.append(value);
        //     //     sum += value;
        //     // }

        //     // output.append(value);
        //     sum += value;
        // }

        //add output statistics
        build.append("Output: " + output.toString()).append("\n");
        build.append("output length: ").append(output.length()).append("\n");
        build.append("sum of symbols: ").append(sum);

        return build.toString();
   
        // int stateIndex = 0;
        // for (TMState state: Q) {
        //     build.append("State " + stateIndex++ + ":\n");
        //     build.append(state.toString());
        // }

        // build.append("\nSigma: {");
        // for (int num: Sigma) {
        //     build.append(num + ", ");
        // }
        // build.append("}\n");
        // build.append("Current tape: " + tape.toString());

        // build.append("\nInitial State:\n");
        // build.append(this.q0.toString());

        // return build.toString();
    }

}