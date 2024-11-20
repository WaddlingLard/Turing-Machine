package tm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

/**
 * 
 * @author
 * @author
 */
public class TMSimulator {
    
    public static void main(String[] args) {

        int numStates = -1, numAlphabet = -1, finalState = -1;
        // ArrayList<String[]> transitions = new ArrayList<String[]>();
        Queue<String[]> transitions = new LinkedList<String[]>();
        String inputString = "";
        
        if (args.length != 1) {
            System.out.println("Invalid Usage of Command. Only one .txt file should be attached");
            return;
        }

        // Grabbing the file
        // VV This one is for normal operation
        String filename = "./Test/" + args[0];

        // String filename = "./Test/file0.txt";
        File inputFile = new File(filename);

        if (!inputFile.exists()) {
            System.out.println("Cannot find file: " + filename);
            return;
        }

        if (!inputFile.isFile()) {
            System.out.println("Argument provided is not a file.");
            return;
        } else {
            System.out.println("Reading file success!"); // Used for troubleshooting
        }

        // Reading file
        // Honestly redundant, but you need to catch the exception. Might just delete the several if-statements above.
        try {

            Scanner scan = new Scanner(inputFile);
            String readLine = "";
            String[] transitionTemplate; 
            
            // Number of states
           numStates = scan.nextInt();
           finalState = numStates - 1;
           numAlphabet = scan.nextInt();

            // Grabbing Transitions
            do {

                // Nothing to read, aka no more transitions
                if (!scan.hasNext()) {
                    break;
                }
                readLine = scan.next();

                // readLine is containing the input string
                if (!readLine.contains(",")) {
                    inputString = readLine;
                    break;
                }

                // Retrieving transition in a String[]
                transitionTemplate = readTransition(readLine);
                transitions.add(transitionTemplate);
                                
            } while (scan.hasNextLine());

            scan.close();

        } catch (FileNotFoundException e) {
            System.err.println("File Not Found!");
        } catch (NoSuchElementException e) {
            System.err.println("File missing data!");
            // e.printStackTrace();
        }

        // Checking input is read properly
        // System.out.println("Read:");
        // System.out.println("Number of States: " + numStates);
        // System.out.println("Final State: " + finalState);
        // System.out.println("Number of Alphabet Elements (At Most) : " + numAlphabet);

        // System.out.println("There should be " + ((numStates - 1) * (numAlphabet + 1)) + " transitions.");

        // Creating the tm.TM

        TM turingMachine;

        if (inputString == "") { // Empty input
            turingMachine = new TM(numStates, numAlphabet);
        } else {
            turingMachine = new TM(inputString, numStates, numAlphabet);
        }

        for (int i = 0; i < numStates - 1; i++) {

            for (int j = 0; j < numAlphabet + 1; j++) {
                 String[] transition = transitions.remove();
                turingMachine.addTransition(transition, i, j);

                // System.out.println("J is " + j);
                // StringBuilder output = new StringBuilder();
                
                // output = new StringBuilder();
                // output.append("Transition for State: " + i + ", and on Character: " + j + " ");


                // output.append("[");
                // output.append(transition[0] + ", ");
                // output.append(transition[1] + ", ");
                // output.append(transition[2] + "]");
            
                //System.out.println(output.toString());
            }
        }

        // System.out.println("Input String: " + inputString);

        // Verifying the input is correct
        turingMachine.execute();
        System.out.println("Finished execution!");
        System.out.println(turingMachine.toString());
    }

    /**
     * This method parses through the given line which is a transition segmented by commas.
     * @param transition which carries the elements (Next_State, Write_Symbol, Move[L, R])
     * @return The transition in a String[].
     */
    public static String[] readTransition(String transition) {
        
        String[] transitionTemplate = new String[3]; 

        Scanner transitionReader = new Scanner(transition);
        transitionReader.useDelimiter(",");

        for (int i = 0; i < transitionTemplate.length; i++) {
            transitionTemplate[i] = transitionReader.next();
        }
        transitionReader.close();

        return transitionTemplate;
    }
}
