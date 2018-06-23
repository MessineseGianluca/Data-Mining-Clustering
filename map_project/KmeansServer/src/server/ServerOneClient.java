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
            switch (req.getMenuChoice()) {
                case 1:
                	System.out.println("Executing ReadRequest...");
                    try {
                        KmeansMiner kmeans = new KmeansMiner(req.getFileName() + ".dmp");
                        out.writeObject(kmeans.toString());
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                        out.writeObject(e.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                        out.writeObject("Error from the server, please try again.");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        out.writeObject("Error from the server, please try again.");
                    }
                    break;
                case 2:
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
                            } catch (FileNotFoundException e) {
                                System.out.println(e.getMessage());
                                out.writeObject(e.getMessage());
                            } catch (IOException e) {
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
                    break;
            }
            System.out.println("Closing request...");
        } catch(IOException e) {
            e.printStackTrace();
            try {
				out.writeObject("Error from the server, please try again.");
			} catch (IOException e1) {
				e.printStackTrace();
			}
        } catch (ClassNotFoundException e) {
                e.printStackTrace();
                try {
    				out.writeObject("Error from the server, please try again.");
    			} catch (IOException e1) {
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
