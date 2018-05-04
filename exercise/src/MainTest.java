import data.Data;
import keyboardinput.Keyboard;
import mining.KmeansMiner;

public class MainTest {
	public static void main(String[] args) {
		Data data = new Data();
		boolean flag=false;
		while(!flag) {
			System.out.println("Quanti cluster vuoi costruire? \n");
			int k = Keyboard.readInt(); // Number of clusters to be built
			System.out.println(data);
			KmeansMiner kmeans = new KmeansMiner(k);
			int numIter = kmeans.kmeans(data);
			System.out.println("Numero di Iterazioni: " + numIter);
			System.out.println(kmeans.getC().toString(data));
			System.out.println("Vuoi ripetere l'iterazione con un numero diverso di cluster? (Y/N) \n");
			char choice = Keyboard.readChar();
			if(choice=='N') {
				flag=true;
			}
		}
		
	}
}
