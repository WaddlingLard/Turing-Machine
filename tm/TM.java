package tm;

import java.util.HashSet;
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
    private TMState[] states;


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

        // Iterator<tm.TMState> iterate = Q.iterator();

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
        //     tm.TMState grabbed = iterate.next();
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
    
 
    public void execute() {
        if (tape.isEmpty()) {
            return;
        }
    
        while (state != numStates - 1) {
            // Get current symbol
            Integer value = tape.get(head);
            char currentSymbol = value.toString().charAt(0);
            int symbolIndex;
            
            // Convert symbol to index
            if (currentSymbol == '_') {
                symbolIndex = numSymbols;
            } else {
                symbolIndex = Character.getNumericValue(currentSymbol);
            }
    
            // Get transition
            String[] transition = states[state].getTransition(symbolIndex);
            if (transition == null) {
                System.out.println("No transition found. Machine halted.");
                return;
            }
    
            // Apply transition
            state = Integer.parseInt(transition[0]);
            tape.set(head, Integer.valueOf(transition[1]));
    
            // Handle head movement
            if (transition[2].equals("R")) {
                head++;
                if (head == tape.size()) {
                    tape.add(Integer.valueOf(numSymbols));
                }
            } else if (transition[2].equals("L")) {
                head--;
                if (head < 0) {
                    tape.addFirst(Integer.valueOf(numSymbols));
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

        for (Integer value : tape) {
            if (value != numSymbols) {
                output.append(value);
                sum += value;
            }
        }

        //add output statistcs
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