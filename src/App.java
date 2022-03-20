import java.util.ArrayList;
import java.util.Arrays;

public class App {
    // This is the Main Class for Algorithm Ten
    // General Algorithm for testing Lossless Join Decomposition
    public static void main(String[] args) {

        //Input: Relation R, Decomposition D, Set of Dependencies F
        //Input: Attributes are provided to form R, D, F.

        //Universal Relation Initialization
        String[] attributes = {"Ssn", "Ename", "Pnumber", "Pname", "Plocation", "Hours"};
        Relation R = new Relation(attributes);

        //Decomposition Initialization
        Relation R1 = new Relation(new String[]{"Ssn", "Ename"});
        Relation R2 = new Relation(new String[]{"Pnumber", "Pname", "Plocation"});
        Relation R3 = new Relation(new String[]{"Ssn", "Pnumber", "Hours"});
        ArrayList<Relation> derived = new ArrayList<>(Arrays.asList(new Relation[]{R1, R2, R3}));
        Decomposition D = new Decomposition(derived);

        //Dependency Initialization
        Dependency F1 = new Dependency(new String[]{"Ssn"}, new String[]{"Ename"});
        Dependency F2 = new Dependency(new String[]{"Pnumber"}, new String[]{"Pname", "Plocation"});
        Dependency F3 = new Dependency(new String[]{"Ssn", "Pnumber"}, new String[]{"Hours"});
        ArrayList<Dependency> F = new ArrayList<>(Arrays.asList(new Dependency[]{F1, F2, F3}));

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

        //STEP 4: Looping the matrix and checking dependencies X -> Y
        step4:
        {
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
                            //Hard set to b(1,*)
                            S[a][index] = ("b" + (0 + 1) + (index + 1));
                        }
                    }
                }
            }
        }

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
    }
}
