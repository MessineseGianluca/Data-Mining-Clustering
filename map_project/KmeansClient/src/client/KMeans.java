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

/**
 * The KMeans class is the basis for the client interface where all the operations
 * can be requested by the user. This class extends the JApplet class in order to
 * use all the methods needed for the creation of the interface. In addition this
 * class encapsulates also methods to establish connection with the server in order
 * to accomplish all the requests.
 */
public class KMeans extends JApplet {
	/**
	 * The serial version ID needed to serialize instances of this
	 * class.
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * The Object Output Stream out is used to print all the output received 
     * from the server after a request.
     */
    private ObjectOutputStream out;
    
    /**
     * The Object Input Stream in is used to send a request to the server
     */
    private ObjectInputStream in;
    
    /**
     * The TabbedPane class is one of the main component of the interface.
     * Because it extends the class JPanel of the JSwing library, it uses
     * methods from JPanel to add inner components such as JButtons and JPanels. 
     * It instantiates all the Action Listener that makes useful the interface.
     * These Action Listeners call methods that connect the client to the server 
     * to solve requests.	
     */
    class TabbedPane extends JPanel {
    	
    	/**
    	 * The serial version ID needed to serialize instances of this
    	 * class.
    	 */
        private static final long serialVersionUID = 1L;
        
        /**
         * PanelWrite is the JPanel where the user can request a computation of
         * k clusters of a defined table which is saved on the DataBase. Furthermore
         * the user have to choose a valid name to save his computation in a file. 
         */
        private JPanelWrite panelWrite;
        
        /**
         * PanelRead is the JPanel where the user can request and visualize 
         * a past computation which is saved in a File. The name of this File 
         * must be given by the user. 
         */
        private JPanelRead panelRead;
        
        
        /**
         * The constructor of TabbedPane initializes the tabbedPane itself, two
         * ActionListeners (One for each possible request), two JPanels (One for
         * each possible request) which are added to the tabbedPane and finally
         * he calls the setTabComponent method (Of JTabbedPane) to set icons
         * to each tab.
         */
        public TabbedPane() {
        	
        	/**
        	 * Construction of a new JTabbedPane object from JSwing
        	 */
            JTabbedPane tabbedPane = new JTabbedPane();
            
            /**
             * actionWrite Action Listener is linked to 
             */
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
                req = new WriteRequest(fileName, numberClusters, table);
                // Send request 
                out.writeObject(req);
                // Write response
                panelWrite.setOutputPanel((String)in.readObject());
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
            panelRead.setOutputPanel(null);
            Request req = null;
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            int port = 8000;
            Socket socket = new Socket(addr, port);
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                String fileName = panelRead.getFile();
                req = new ReadRequest(fileName);
                // Send request 
                out.writeObject(req);
                // Write response
                panelRead.setOutputPanel((String)in.readObject());
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
        frame.setTitle("K-Means Client");
        frame.setSize(900, 345);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        TabbedPane tab = new TabbedPane();
        frame.add(tab);
        frame.setVisible(true);
    }
}