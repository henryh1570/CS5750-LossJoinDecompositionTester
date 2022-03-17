import java.util.HashSet;

public class App {
    // This is the Main Class for Algorithm Ten
    // General Algorithm for testing Lossless Join Decomposition
    public static void main(String[] args) {

        String[] attributes = {"SSN", "name", "age"};
        String[] attributes2 = {"major", "birthdate", "gender"};
        AttributeSet A = new AttributeSet(attributes);
        AttributeSet B = new AttributeSet(attributes2);

        Relation R = new Relation(new AttributeSet[]{A, B});

        System.out.println(R.toString());


        System.out.println("it works!");
    }
}
