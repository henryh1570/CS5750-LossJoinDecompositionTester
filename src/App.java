import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class App {
    // This is the Main Class for Algorithm Ten
    // General Algorithm for testing Lossless Join Decomposition

    public static void main(String[] args) {

        ArrayList<String> listOfRelation = new ArrayList<>();
        ArrayList<String> listOfDecomposition = new ArrayList<>();
        ArrayList<String> listOfDependency = new ArrayList<>();

        //Read inputs: Relation R, Decomposition D, Set of Dependencies F from file.
        try {
            File f = new File(args[0]);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String next = br.readLine();
            ArrayList<String> currentList = listOfRelation;

            do {
                String line = next.toLowerCase();

                //Check for Newlines and pound character.
                if (!line.isEmpty()) {
                    if (line.contains("#")) {
                        // Switch currently parsing list
                        if (line.contains("relation")) {
                            currentList = listOfRelation;
                        }
                        if (line.contains("decomposition")) {
                            currentList = listOfDecomposition;
                        }
                        if (line.contains("dependency") || line.contains("dependencies")) {
                            currentList = listOfDependency;
                        }
                        // Otherwise add the input line
                    } else {
                        currentList.add(next);
                    }
                }
                next = br.readLine();
            } while(next != null);
        } catch (Exception e) {
            System.err.println(e + " was found, exiting program now.");
            System.err.println("Please run the program with the input text file as argument 1.");
            System.exit(5);
        }

        long startTime = new Date().getTime();

        //Universal Relation Initialization
        String[] R_Attributes = listOfRelation.get(0).split(",");
        Relation R = new Relation(R_Attributes);

        //Decomposition Initialization
        ArrayList<Relation> derived = new ArrayList<>();
        for (String line : listOfDecomposition) {
            String[] D_Attributes = line.split(",");
            Relation Ri = new Relation(D_Attributes);
            derived.add(Ri);
        }
        Decomposition D = new Decomposition(derived);

        //Dependency Initialization
        ArrayList<Dependency> F = new ArrayList<>();
        for (String line : listOfDependency) {
            String[] sides = line.split("->");
            String[] leftSide = sides[0].split(",");
            String[] rightSide = sides[1].split(",");
            Dependency Fi = new Dependency(leftSide, rightSide);
            F.add(Fi);
        }

        //STEP 1: Matrix S Initialization
        int numRows = D.setOfDecompositions.size();
        int numCols = R.setOfAttributes.size();
        String[][] S = new String[numRows][numCols];

        //STEP 2: Set all of S to bij
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                S[i][j] = ("b" + (i + 1) + (j + 1));
            }
        }

        //STEP 3: Set the corresponding rows that contain Aj as aj
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                // Ri includes the attribute Aj
                Relation Ri = (Relation) D.setOfDecompositions.toArray()[i];
                String Aj = R.setOfAttributes.get(j);
                if (Ri.setOfAttributes.contains(Aj)) {
                    S[i][j] = ("a" + (j + 1));
                }
            }
        }

        //Print the initial matrix S
        System.out.println("Initial Matrix:");
        for (String[] arr : S) {
            for (String str : arr) {
                if (str.indexOf("a") != -1) {
                    System.out.print("[" + str + " ]");
                } else {
                    System.out.print("[" + str + "]");
                }
            }
            System.out.println();
        }

        //STEP 4: Looping the matrix and checking dependencies X -> Y. Until no changes are made.
        //Sometimes necessary to loop more than once because dependencies are checked sequentially.
        boolean noChanges = false;
        int numOfLoops = 0;
        step4:
        while (noChanges == false) {
            noChanges = true;
            numOfLoops++;
            for (Dependency dep : F) {
                ArrayList<String> X = dep.determinants;
                ArrayList<String> Y = dep.dependents;
                boolean anyRowHasAllX = false;

                //Check every Ri as a row in S, to see if they have 'a' in all columns of X.
                for (int i = 0; i < numRows; i++) {
                    String[] row = S[i];
                    boolean currentRowHasAllX = true;

                    //Check current row if it has all the X dependents
                    for (String attribute : X) {
                        int index = R.setOfAttributes.indexOf(attribute);
                        if (row[index].contains("b")) {
                            currentRowHasAllX = false;
                        }
                    }

                    //STEP 4a: Update all rows that have X. Change their Y columns to "a".
                    if (currentRowHasAllX) {
                        anyRowHasAllX = true;
                        for (String attribute : Y) {
                            int index = R.setOfAttributes.indexOf(attribute);
                            //an element with already an a does not count as a change
                            if (!row[index].contains("a")) {
                                noChanges = false;
                            }
                            row[index] = ("a" + (index + 1));
                        }

                        //Check if any row has all "a". We can exit prematurely.
                        boolean completeRowExists = true;
                        for (int j = 0; j < numCols; j++) {
                            if (row[j].contains("b")) {
                                completeRowExists = false;
                            }
                        }

                        if (completeRowExists) {
                            break step4;
                        }
                    }
                }

                //STEP 4b: None of the Ri rows fulfilled the current dependency.
                //Pick a 'b' symbol from one of the rows and copy to all other rows in that column.
                if (!anyRowHasAllX) {
                    for (String attribute : X) {
                        int index = R.setOfAttributes.indexOf(attribute);
                        for (int a = 0; a < numRows; a++) {
                            //an element already with b does not count as a change
                            if (!S[a][index].contains("b")) {
                                noChanges = false;
                            }
                            //Hard set to b(1,*)
                            S[a][index] = ("b" + (0 + 1) + (index + 1));
                        }
                    }
                }
            }
        }

        System.out.println(numOfLoops + " loops made over matrix S.");
        //STEP 5: Print the final matrix S and the result.
        System.out.println("Final Matrix:");
        boolean isLossless = false;
        for (String[] arr : S) {
            boolean isRowLossless = true;
            for (String str : arr) {
                if (str.indexOf("a") != -1) {
                    System.out.print("[" + str + " ]");
                } else {
                    isRowLossless = false;
                    System.out.print("[" + str + "]");
                }
            }
            if (isRowLossless) {
                isLossless = true;
            }
            System.out.println();
        }
        if (isLossless) {
            System.out.println("The Decomposition is LOSSLESS");
        } else {
            System.out.println("The Decomposition is LOSSY");
        }

        long endTime = new Date().getTime();
        System.out.println("time in milliseconds: " + (endTime-startTime));

    }
}
