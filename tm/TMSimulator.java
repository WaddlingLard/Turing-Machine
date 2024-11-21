package tm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

/**
 * Class that creates a Turing Machine and executes it. Follows a certain form in test files to grab data like the number of states,
 * elements in the alphabet, transitions, and if there is an input string.
 * 
 * NOTE: Test files must be added to the /Test folder before calling TMSimulator. That is how this program is setup.
 * 
 * @author Brian Wu
 * @author Max Ma
 */
public class TMSimulator {
    
    public static void main(String[] args) {

        // Getting the starting time
        // long begin = System.currentTimeMillis();

        int numStates = 0, numAlphabet = 0;
        Queue<int[]> transitions = new LinkedList<int[]>();
        String inputString = "";
        
        if (args.length != 1) {
            System.out.println("Invalid Usage of Command. Only one .txt file should be attached");
            return;
        }

        // Grabbing the file
        // Note, test files must be in the /Test dir
        String filename = "./Test/" + args[0];

        File inputFile = new File(filename);

        if (!inputFile.exists()) {
            System.out.println("Cannot find file: " + filename);
            return;
        }

        if (!inputFile.isFile()) {
            System.out.println("Argument provided is not a file.");
            return;
        } else {
            // System.out.println("Reading file success!"); // Used for troubleshooting
        }

        // Reading file
        // Honestly redundant, but you need to catch the exception. Might just delete the several if-statements above.
        try {

            Scanner scan = new Scanner(inputFile);
            String readLine = "";
            int[] transitionTemplate; 
            
            // Number of states
           numStates = scan.nextInt();
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

        TM turingMachine;

        if (inputString == "") { // Empty input
            turingMachine = new TM(numStates, numAlphabet);
        } else {
            turingMachine = new TM(inputString, numStates, numAlphabet);
        }

        for (int i = 0; i < numStates - 1; i++) {

            for (int j = 0; j < numAlphabet + 1; j++) {
                int[] transition = transitions.remove();
                turingMachine.addTransition(transition, i, j);
            }
        }

        turingMachine.execute();
        System.out.println(turingMachine.toString());

        // long end = System.currentTimeMillis();
        // long totalDuration = end - begin;
	    // String milliseconds = String.format("%03d", totalDuration % 1000);
        // System.out.println("Total Time to run operation (S:M): " + (totalDuration / 1000)  + ":" + milliseconds);
    }

    /**
     * Parses through the given string which is a transition segmented by commas.
     * @param transition which carries the elements (Next_State, Write_Symbol, Move[L, R])
     * @return The transition in a String[].
     */
    public static int[] readTransition(String transition) {
        
        int[] transitionTemplate = new int[3]; 

        Scanner transitionReader = new Scanner(transition);
        transitionReader.useDelimiter(",");

        for (int i = 0; i < transitionTemplate.length; i++) {
            // Opting for numbers to reduce conversion inside TM
            if (i == 2) {
                String direction = transitionReader.next();
                if (direction.equals("R")) {
                    transitionTemplate[i] = 0; // 0 == Go Right
                } else {
                    transitionTemplate[i] = 1; // 1 == Go Left
                }
            } else {
                transitionTemplate[i] = Integer.parseInt(transitionReader.next());
            }
        }
        transitionReader.close();

        return transitionTemplate;
    }
}
