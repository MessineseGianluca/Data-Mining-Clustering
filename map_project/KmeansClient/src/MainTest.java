import keyboardinput.Keyboard;
import java.net.Socket;
import java.net.InetAddress;
import java.io.*;

public class MainTest {
    private static int menu() {
        int answer;
        System.out.println("Select an operation");
        do {
            System.out.println("(1) Load cluster from file");
            System.out.println("(2) Load data");
            System.out.print("Answer: ");
            answer = Keyboard.readInt();
        } while(answer <= 0 || answer > 2);
        return answer;
    }
    
    public static void main(String[] args) throws IOException {
        InetAddress addr = InetAddress.getByName("localhost");
        int port = 8080;
        do {
        	Socket socket = null;
        	ObjectOutputStream out;
        	ObjectInputStream in;
            try {
                socket = new Socket(addr, port);       
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
                int menuAnswer = menu(); // print menu()
                Request req = null;
                String fileName = "";
                System.out.print("Archive name: ");
                fileName = Keyboard.readString();

                // Parse request to send
                switch(menuAnswer) {
                    case 1:
                        req = new ReadRequest(menuAnswer, fileName);
                        break;

                    case 2: 
                        int numberClusters;
                        String table;
                        System.out.println("How many clusters do you want to calculate?: ");
                        numberClusters = Keyboard.readInt();
                        System.out.println("What is the name of the table to analyze?: ");
                        table = Keyboard.readString();	// Use "playtennis" as existing table name
                        req = new WriteRequest(menuAnswer, fileName, numberClusters, table);
                        break;
                   default:
                           System.out.println("Invalid operation!");
                }
                try {
                    // Send request 
                    out.writeObject(req);
                    // Write response
                    System.out.println((String)in.readObject());
                } catch(ServerException e) {
                    System.out.println(e.getMessage());
                } catch(ClassNotFoundException e) {
                    System.out.println("Class not found.");
                }
                System.out.print("Do you want to choose another operation from the menu?(y/n): ");
                if(Keyboard.readChar() != 'y') break;   
            } catch(IOException e) {
                System.out.println(e.getMessage());  
            } finally {
            	if(socket != null) socket.close();
            }
        } while(true);
    } 
}
