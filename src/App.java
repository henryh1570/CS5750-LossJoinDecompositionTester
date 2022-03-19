import java.util.Arrays;
import java.util.HashSet;

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
        Relation R1 = new Relation(new String[]{"Ssn, Ename"});
        Relation R2 = new Relation(new String[]{"Pnumber","Pname","Plocation"});
        Relation R3 = new Relation(new String[]{"Ssn","Pnumber","Hours"});
        HashSet<Relation> derived = new HashSet<>(Arrays.asList(new Relation[]{R1, R2, R3}));
        Decomposition D = new Decomposition(derived);

        //Dependency Initialization
        Dependency F1 = new Dependency(new String[]{"Ssn"}, new String[]{"Ename"});
        Dependency F2 = new Dependency(new String[]{"Pnumber"}, new String[]{"Pname", "Plocation"});
        Dependency F3 = new Dependency(new String[]{"Ssn", "Pnumber"}, new String[]{"Hours"});
        HashSet<Dependency> F = new HashSet<Dependency>(Arrays.asList(new Dependency[]{F1, F2, F3}));

        //Matrix S Initialization
        int numRows = D.setOfDecompositions.size();
        int numCols = R.setOfAttributes.size();
        String[][] S = new String[numRows][numCols];

        //Set all of S to bij
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                S[i][j] = ("b"+i+j);
            }
        }

        //Set some of S to aj
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                // Ri includes the attribute Aj
                Relation Ri = (Relation) D.setOfDecompositions.toArray()[i];

                System.out.println(i+"|"+j+"|"+ Ri.setOfAttributes.toString() + "|" + R.setOfAttributes.toArray()[j]);
                if (Ri.setOfAttributes.contains(R.setOfAttributes.toArray()[j])) {
                    S[i][j] = ("a"+j);
                }
            }
        }

        //Looping the matrix and checking dependencies
        for (Dependency dep : F) {

        }


        //Print
        for (String[] arr : S) {
            for (String str : arr) {
                System.out.print(str);
            }
            System.out.println();
        }

    }

    static HashSet<Dependency> initDependency () {
        return null;
    }
}
