import java.io.FileNotFoundException;
import java.io.IOException;

import keyboardinput.Keyboard;
import mining.KmeansMiner;
import data.Data;
import data.OutOfRangeSampleSize;
public class MainTest {
	private static int menu() {
		int answer;
		System.out.println("Select an operation");
		do {
			System.out.println("(1) Load cluster from file");
			System.out.println("(2) Load data");
			System.out.print("Answer:");
			answer = Keyboard.readInt();
		} while(answer <= 0 || answer > 2);
		return answer;
	}
	
	static KmeansMiner learningFromFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		String fileName = "";
		System.out.print("Archive name: ");
		fileName = Keyboard.readString();
		return new KmeansMiner(fileName + ".dmp");
		
	}
	public static void main(String[] args) {
		do {
			int menuAnswer = menu();
			switch(menuAnswer) {
				case 1:
					try {
						KmeansMiner kmeans=learningFromFile();
						System.out.println(kmeans);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					break;
				case 2:
					Data data = new Data();
					System.out.println(data);
					char answer = 'y';
					do {
						int k = 1;
						System.out.print("Insert k: ");
						k = Keyboard.readInt();
						KmeansMiner kmeans = new KmeansMiner(k);
						try {
							int numIter = kmeans.kmeans(data);
							System.out.println("Number of iteration: " + numIter);
							System.out.println(kmeans.getC().toString(data));
							System.out.print("Backup file name: ");
							String fileName = Keyboard.readString() + ".dmp";
							System.out.println("Saving in " + fileName);
							try {
								kmeans.save(fileName);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("End of saving operations!");
						} catch(OutOfRangeSampleSize e) {
							System.out.println(e.getMessage());
						}
						System.out.print("Do you want to continue? (y/n): ");
						answer = Keyboard.readChar();
					} while(answer == 'y');
					break;
				default:
					System.out.println("Invalid operation!");
			}
			System.out.print("Do you want to choose another operation from the men�?(y/n): ");
			if(Keyboard.readChar() != 'y') break;
		} while(true);
	}
}
