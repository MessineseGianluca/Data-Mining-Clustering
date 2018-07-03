package server;

import mining.KmeansMiner;
import share.Request;
import share.WriteRequest;
import share.ReadRequest;
import data.Data;
import data.OutOfRangeSampleSize;
import java.sql.SQLException;
import database.DatabaseConnectionException;
import database.DbAccess;
import java.io.*;
import java.net.Socket;

/**
 * ServerOneClient is the base class for all threads created by 
 * a multithreaded server to handle requests coming from 
 * client applications. That's why this class extends Thread class.
 * Each ServerOneClient's object needs a socket to communicate with
 * a specific client and object streams to receive or to send it information.
 * The request handled by the instance of this class is analyzed and then
 * the response is generated.
 * The ServerOneClient thread ends when the asked service is supplied
 * to the client and all data have been transmitted, then 
 * the socket used is closed.
 * 
 * @see Thread
 * @see Socket
 * @see ObjectInputStream
 * @see ObjectOutputStream
 */
public class ServerOneClient extends Thread {
	
	/**
	 * The Socket's object needed to communicate with a client application 
	 * using a Socket-to-Socket communication.
	 * 
	 * @see Socket
	 */
    private Socket socket;
    
    /**
     * The stream used to deserialize data received from the client.
     * 
     * @see ObjectInputStream
     */
    private ObjectInputStream in;
    
    /**
     * The stream used to serialize data to send to the client.
     * 
     * @see ObjectOutputStream
     */
    private ObjectOutputStream out;

    /**
     * Constructs a new ServerOneClient's object by initializing Socket's object,
     * then it starts the execution of the thread.
  	 *
     * @param s a Socket's object used to handle communication with a client.
     * @throws IOException thrown if an I/O error occurs when handling a
     * 		   connection using sockets.
     * @see Socket
     * @see ObjectInputStream
     * @see ObjectOutputStream
     */
    public ServerOneClient(Socket s) throws IOException {
        socket = s;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }
    
    /**
     * Overrides the run() method given by the Thread class to handle 
     * client's requests.
     * First of all, this method analyzes the handled request to establish
     * what type of service the client is asking for.
     * Then it executes the right operation and communicates the result to
     * the client.
     * The connection is closed at the end of data transmission.
     * 
     * @see Request
     * @see Socket
     */
    public void run() {
        try {
            Request req = (Request)in.readObject();   
            if(req instanceof WriteRequest) {
            	System.out.println("Executing WriteRequest...");
                try {
                    DbAccess.initConnection();
                    String table = ((WriteRequest) req).getDBtableName();
                    Data data = new Data(table);
                    int k = ((WriteRequest) req).getNumberOfClusters();
                    KmeansMiner kmeans = new KmeansMiner(k);
                    try {
                        int numIter = kmeans.kmeans(data);
                        System.out.println("Saving in " + req.getFileName() + ".dmp...");
                        try {
                            kmeans.save(req.getFileName() + ".dmp");
                        } catch(FileNotFoundException e) {
                            System.out.println(e.getMessage());
                            out.writeObject(e.getMessage());
                        } catch(IOException e) {
                            e.printStackTrace();
                            out.writeObject("Error from the server, please try again.");
                        }
                        out.writeObject(
                        		data +
                        		"\nNumber of iterations: " +
                        		numIter + "\n" +
                        		kmeans.getC().toString(data)
                        );
                    } catch(OutOfRangeSampleSize e) {
                    	String str = "Invalid number of clusters.";
                        System.out.println(str);
                        out.writeObject(str);
                    } finally {
                    	 DbAccess.closeConnection();
                    }
                } catch(SQLException e) {
                    System.out.println(e.getMessage());
                    out.writeObject(e.getMessage());
                } catch(IllegalAccessException e) {
                    System.out.println(e.getMessage());
                    out.writeObject("Error from the server, please try again.");
                } catch(InstantiationException e) {
                    System.out.println(e.getMessage());
                    out.writeObject("Error from the server, please try again.");
                } catch(DatabaseConnectionException e) {
                    System.out.println(e.getMessage());
                    out.writeObject("Error from the server, please try again.");
                }
            } else if(req instanceof ReadRequest) {
            	System.out.println("Executing ReadRequest...");
                try {
                    KmeansMiner kmeans = new KmeansMiner(req.getFileName() + ".dmp");
                    out.writeObject(kmeans.toString());
                } catch(FileNotFoundException e) {
                    System.out.println(e.getMessage());
                    out.writeObject(e.getMessage());
                } catch(IOException e) {
                    e.printStackTrace();
                    out.writeObject("Error from the server, please try again.");
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                    out.writeObject("Error from the server, please try again.");
                }
            }
            System.out.println("Closing request...");
        } catch(IOException e) {
            e.printStackTrace();
            try {
				out.writeObject("Error from the server, please try again.");
			} catch(IOException e1) {
				e.printStackTrace();
			}
        } catch(ClassNotFoundException e) {
                e.printStackTrace();
                try {
    				out.writeObject("Error from the server, please try again.");
    			} catch(IOException e1) {
    				e.printStackTrace();
    			}
        } finally {
            try {
                socket.close();
            } catch(IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }
}