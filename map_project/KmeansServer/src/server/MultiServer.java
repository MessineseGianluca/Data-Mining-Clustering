package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
    private int PORT = 8080;
    
    public MultiServer(int port) throws IOException {
        this.PORT = port;
        this.run();
    }
  
    public void run() throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server Started");
        try {
            while(true) {
                Socket socket = s.accept();
                try {
                    new ServerOneClient(socket);
                } catch(IOException e) {
                    socket.close();
                }
            }
        } finally {
            s.close();
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 8080;
        new MultiServer(port);
    } 
}