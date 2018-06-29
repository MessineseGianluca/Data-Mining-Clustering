package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import share.ReadRequest;
import share.Request;
import share.WriteRequest;
import java.net.InetAddress;
import java.net.Socket;
import java.io.*;

public class KMeans extends JApplet {
    private static final long serialVersionUID = 1L;
    private ObjectOutputStream out;
    private ObjectInputStream in;
  
    class TabbedPane extends JPanel {
        private static final long serialVersionUID = 1L;
        private JPanelWrite panelWrite;
        private JPanelRead panelRead;
    
        public TabbedPane() {
            JTabbedPane tabbedPane = new JTabbedPane();
            ActionListener actionWrite = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        learningFromDBAction();
                    } catch(IOException err) {
                        JOptionPane.showMessageDialog(tabbedPane, err.toString());
                    } catch(ClassNotFoundException err) {
                        JOptionPane.showMessageDialog(tabbedPane, err.toString());
                    }
                }
            };
            ActionListener actionRead = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        learningFromFileAction();
                    } catch(IOException err) {
                        JOptionPane.showMessageDialog(tabbedPane, err.toString());
                    } catch(ClassNotFoundException err) {
                        JOptionPane.showMessageDialog(tabbedPane, err.toString());
                    }  
                }
            };
            this.panelWrite = new JPanelWrite("Send", actionWrite);
            this.panelRead = new JPanelRead("Send", actionRead);
            tabbedPane.addTab("Write to File", panelWrite);
            tabbedPane.addTab("Read File", panelRead);
            try {
                tabbedPane.setTabComponentAt(0, getLabel("WriteToFile", "/icons/db1.png"));
                tabbedPane.setTabComponentAt(1, getLabel("Read File", "/icons/ld1.png"));
            } catch(IOException ex) {
            	ex.printStackTrace();
            }
            this.add(tabbedPane);
        }
    
        public JLabel getLabel(String title, String icon) throws IOException {
        	JLabel label = new JLabel(title);
        	try {
        		label.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(icon))));
        	} catch(IOException ex) {
        		ex.printStackTrace();
        	}
        	return label;
        }
        
        private void learningFromDBAction() throws IOException, ClassNotFoundException {
            Request req = null;
            int numberClusters;
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            int port = 8000;
            Socket socket = new Socket(addr, port);
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                numberClusters = panelWrite.getNumberOfClusters();
                String table = panelWrite.getTable();
                String fileName = panelWrite.getFile();
                req = new WriteRequest(2, fileName, numberClusters, table);
                // Send request 
                out.writeObject(req);
                // Write response
                panelWrite.setOutputTextArea((String)in.readObject());
            } catch(NumberFormatException e) { 
                JOptionPane.showMessageDialog(this, e.toString());
            } catch(ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Class not found.");
            } finally {
                try {
                    if(socket != null) socket.close();
                } catch(IOException e) {
                    JOptionPane.showMessageDialog(this, "Socket not closed");
                }
            }
        }
        
        private void learningFromFileAction() throws IOException, ClassNotFoundException {
            panelRead.clusterOutput.setText(null);
            Request req = null;
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            int port = 8000;
            Socket socket = new Socket(addr, port);
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                String fileName = panelRead.getFile();
                req = new ReadRequest(1, fileName);
                // Send request 
                out.writeObject(req);
                // Write response
                panelRead.setOutputTextArea((String)in.readObject());
            } catch(NumberFormatException e) { 
                JOptionPane.showMessageDialog(this, e.toString());
            } catch(ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Class not found.");
            } finally {
                try {
                    if(socket != null) socket.close();
                } catch(IOException e) {
                    JOptionPane.showMessageDialog(this, "Socket not closed");
                }
            }
        }
    }
  
    public void init() {
        JFrame frame = new JFrame();
        frame.setTitle("Clustering");
        frame.setSize(900, 345);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        TabbedPane tab = new TabbedPane();
        frame.add(tab);
        frame.setVisible(true);
    }
}