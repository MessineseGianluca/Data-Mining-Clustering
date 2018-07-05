package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The MultiServer class represents the server designated 
 * for accepting requests coming from a client application.
 * The client must connect to the server through the IP address 
 * and the port on which the latter is waiting for requests.
 * The server will be waiting for a request until a client 
 * application sends it a new one.
 * When a new request arrives, the server instantiates
 * a new thread to handle a specific request and to supply
 * the service the client is asking for.
 * A thread ends its execution when the result of 
 * a request is communicated to the client.
 */
public class MultiServer {
	
	/**
	 * The port used by the server to accept communication from clients.
	 */
    private int PORT;
    
    /**
     * Constructs an instance of MultiServer class by setting information
     * about server port. 
     * This method always executes run() to give server the opportunity 
     * to receive new requests from client applications.
     * 
     * @param port a numeric combination representing the server port.
     * @throws IOException thrown if an I/O error occurs when handling a
     *            connection using sockets.
     */
    public MultiServer(int port) throws IOException {
        this.PORT = port;
        this.run();
    }
  
    /**
     * Represents the core of server activity.
     * This method creates a ServerSocket object that put the server 
     * in waiting state until a new client request arrives.
     * When there is a pending request, a Socket-to-Socket connection
     * is set up by creating a Socket object which will be used 
     * to communicate the result of a request to a client application.
     * Communication with a specific client is handled by a thread
     * instantiated with the Socket object when there is a new request.
     * The server ends the execution when its activity is no longer required.
     * 
     * @throws IOException thrown if an I/O error occurs when handling a
     *            connection using sockets.
     * @see ServerSocket
     * @see Socket
     * @see ServerOneClient
     */
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

    /**
     * Initializes a MultiServer object and set its port to a specific value.
     * @param args the input parameters for main
     * 
     * @throws IOException thrown if an I/O error occurs when handling a
     * 		   connection using sockets.
     */
    public static void main(String[] args) throws IOException {
        int port = 8000;
        new MultiServer(port);
    } 
}