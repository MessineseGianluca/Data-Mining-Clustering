package server;

import mining.KmeansMiner;
import java.io.*;
import java.net.Socket;

public class ServerOneClient extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private KmeansMiner kmeans;
    
    public ServerOneClient(Socket s) throws IOException {
        socket = s;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        start(); 
    }
        
    public void run() {
        try {
            while(true) {
                /* ... */ 
            }
            System.out.println("Closing request...");
        } catch(IOException e) {
            System.err.println("IO Exception");
        } finally {
            try {
                socket.close();
            } catch(IOException e) {
                System.err.println("Socket not closed");
            }  
        }
    }

}