package server;

import mining.KmeansMiner;
import share.Request;
import share.WriteRequest;
import data.Data;
import data.OutOfRangeSampleSize;
import java.sql.SQLException;
import database.DatabaseConnectionException;
import database.DbAccess;
import java.io.*;
import java.net.Socket;

public class ServerOneClient extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    public ServerOneClient(Socket s) throws IOException {
        socket = s;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        start(); 
    }
        
    public void run() {
        try {
            Request req = (Request)in.readObject();
            System.out.println("Here");

            switch (req.getMenuChoice()) {
                case 1:
                    try {
                    	
                        KmeansMiner kmeans = new KmeansMiner(req.getFileName() + ".dmp");
                        
                        out.writeObject(kmeans.toString());
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case 2:
                    try { 
                        DbAccess.initConnection();
                        String table = ((WriteRequest) req).getDBtableName();
                        Data data = new Data(table);  
                        System.out.println(data);
                        int k = ((WriteRequest) req).getNumberOfClusters();                        
                        KmeansMiner kmeans = new KmeansMiner(k);
                        try {
                            int numIter = kmeans.kmeans(data);
                            System.out.println("Number of iterations: " + numIter);
                            System.out.println("Saving in " + req.getFileName());
                            try {
                                kmeans.save(req.getFileName());
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("End of saving operations!");
                            out.writeObject(kmeans.getC().toString(data));
                        } catch(OutOfRangeSampleSize e) {
                            System.out.println(e.getMessage());
                        }
                        DbAccess.closeConnection();
                    } catch(SQLException e) {
                        System.out.println(e.getMessage());
                    } catch(IllegalAccessException e) {
                        System.out.println(e.getMessage());
                    } catch(InstantiationException e) {
                        System.out.println(e.getMessage());
                    } catch(DatabaseConnectionException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            }
            System.out.println("Closing request...");
        } catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch(IOException e) {
                System.err.println("Socket not closed");
            }  
        }
    }
}