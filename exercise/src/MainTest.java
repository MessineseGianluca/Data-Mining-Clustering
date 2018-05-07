import data.Data;
import keyboardinput.Keyboard;
import mining.KmeansMiner;
import data.OutOfRangeSampleSize;

public class MainTest {
    public static void main(String[] args) throws OutOfRangeSampleSize {
        Data data = new Data();
        boolean flag = false;
        while(!flag) {
                System.out.println("How many clusters do you want to create?: ");
                int k = Keyboard.readInt(); // Number of clusters to be built
                System.out.println(data);
            try {
                KmeansMiner kmeans = new KmeansMiner(k);
                int numIter = kmeans.kmeans(data);
                System.out.println("Number of Iterations: " + numIter);
                System.out.println(kmeans.getC().toString(data));
                System.out.println("Do you want to repeat the operation? (Y/N): ");
                char choice = Keyboard.readChar();
                if(choice == 'N' || choice == 'n') {
                    flag = true;
                }
            } catch(OutOfRangeSampleSize oorss) {
                System.err.println("The number of cluster is out of range!");  
            }
        }
    }
}
