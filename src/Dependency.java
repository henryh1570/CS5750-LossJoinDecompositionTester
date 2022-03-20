import java.util.ArrayList;
import java.util.Arrays;

//A single dependency contains variable amounts of attributes as the determinant and as the dependent.
//It is a constraint on a relation schema. A set of dependencies may be used in Algorithm 10.
//4 Examples: A -> B, B -> CD, EF -> A, GH->IJK
public class Dependency {

    ArrayList<String> determinants;  //Left-side
    ArrayList<String> dependents;    //Right-side

    public Dependency(String[] left, String[] right) {

        determinants = new ArrayList<String>(Arrays.asList(left));

        dependents = new ArrayList<>(Arrays.asList(right));

    }

    public String toString() {
        String str = "Determinants:";
        str += determinants.toString();
        str += ("Dependents:" + dependents.toString());
        return str;
    }
}
