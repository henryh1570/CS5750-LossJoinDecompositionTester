import java.util.HashSet;

//A single dependency contains variable amounts of attributes as the determinant and as the dependent.
//It is a constraint on a relation schema. A set of dependencies may be used in Algorithm 10.
//4 Examples: A -> B, B -> CD, EF -> A, GH->IJK
public class Dependency {

    HashSet<String> determinants;  //Left-side
    HashSet<String> dependents;    //Right-side

    public Dependency(String[] left, String[] right) {

        determinants = new HashSet<>();
        for (String det : left) {
            determinants.add(det);
        }

        dependents = new HashSet<>();
        for(String dep : right) {
            dependents.add(dep);
        }

    }

    public String toString() {
        String str = "Determinants:";
        str += determinants.toString();
        str += ("Dependents:" + dependents.toString());
        return str;
    }
}
