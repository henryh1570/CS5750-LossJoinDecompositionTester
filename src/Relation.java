import java.util.ArrayList;
import java.util.Arrays;

//Relation R is the set containing all attributes. Also known as universal relation.
public class Relation {

    ArrayList<String> setOfAttributes;

    public Relation() {
        setOfAttributes = new ArrayList<>();
    }

    public Relation(String[] arr) {
        setOfAttributes = new ArrayList<String>(Arrays.asList(arr));
    }

    public String toString() {
        return setOfAttributes.toString();
    }
}
