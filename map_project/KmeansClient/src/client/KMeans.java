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
     * 
     * @see ObjectOutputStream
     */
    private ObjectOutputStream out;
    
    /**
     * The Object Input Stream in is used to send a request to the server.
     * 
     * @see ObjectInputStream
     */
    private ObjectInputStream in;
    
    /**
     * The TabbedPane class is one of the main component of the interface.
     * Because it extends the class JPanel of the JSwing library, it uses
     * methods from JPanel to add inner components such as JButtons and JPanels. 
     * It instantiates all the Action Listener that makes useful the interface.
     * These Action Listeners call methods that connect the client to the server 
     * to solve requests.
     * 
     * @see JPanel	
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
         * 
         *  @see JPanelWrite
         */
        private JPanelWrite panelWrite;
        
        /**
         * PanelRead is the JPanel where the user can request and visualize 
         * a past computation which is saved in a File. The name of this File 
         * must be given by the user.
         * 
         *  @see JPanelRead
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
        	 * Construction of a new JTabbedPane object from JSwing.
        	 * 
        	 * @see JTabbedPane
        	 */
            JTabbedPane tabbedPane = new JTabbedPane();
            
            /**
             * actionWrite Action Listener is linked to a JButton of the WriteFile
             * JPannel. It invokes the learningFromDBAction method which establishes a
             * connection with the server in order to get data from the database.
             * If an IOException or a ClassNotFoundException occurs, it will generate
             * a MessageDialog (made from a JOptionPane) which shows the error message.
             * 
             * @see JOptionPane
             * @see ActionListener
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
            
            /**
             * actionRead Action Listener is linked to a JButton of the ReadFile
             * JPannel. It invokes the learningFromFileAction method which establishes a
             * connection with the server in order to get data from a file.
             * If an IOException or a ClassNotFoundException occurs, it will generate
             * a MessageDialog (made from a JOptionPane) which shows the error message.
             * 
             * @see JOptionPane
             * @see ActionListener
             */
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
            
            /**
             * Construct a new JPanelWrite by setting a String for the JButton of the JPanel
             * and an ActionListener linked also to this JButton.
             * 
             * @see JPanelWrite
             */
            this.panelWrite = new JPanelWrite("Send", actionWrite);
            
            /**
             * Construct a new JPanelRead by setting a String for the JButton of the JPanel
             * and an ActionListener linked also to this JButton.
             * 
             * @see JPanelRead
             */
            this.panelRead = new JPanelRead("Send", actionRead);
            
            /**
             * The method addTab of the tabbedPane object adds panelWrite JPanel to the main
             * tabbePane by setting also the label for the corresponding tab.
             */
            tabbedPane.addTab("Write to File", panelWrite);
            
            /**
             * The method addTab of the tabbedPane object adds panelRead JPanel to the main
             * tabbePane by setting also the label for the corresponding tab.
             */
            tabbedPane.addTab("Read File", panelRead);
            try {
            	
            	/**
            	 * The method setTabComponentAt sets a Label to the corresponding tab of the
            	 * first integer parameter. The second parameter contains a reference to
            	 * a JPanel which is returned by the getLabel method.
            	 * 
            	 * @see getLabel
            	 */
                tabbedPane.setTabComponentAt(0, getLabel("WriteToFile", "/icons/db1.png"));
                
                /**
            	 * The method setTabComponentAt sets a Label to the corresponding tab of the
            	 * first integer parameter. The second parameter contains a reference to
            	 * a JPanel which is returned by the getLabel method.
            	 * 
            	 * @see getLabel
            	 */
                tabbedPane.setTabComponentAt(1, getLabel("Read File", "/icons/ld1.png"));
              
              /*
               * This catch block checks if the getLabel method has no IOException during
               * his activity.
               */
            } catch(IOException ex) {
            	ex.printStackTrace();
            }
            
            /**
             * The tabbedPane is added to the main frame of the client interface
             */
            this.add(tabbedPane);
        }
        
        /**
         * This method takes an icon from a file and creates a JLabel with
         * the icon and the string given by the caller.
         * 
         * @param title, it's the string that will compose the label
         * @param icon, it's the file path of the icon that will compose the label
         * @return JLabel which is composed by the icon and the title string
         * @throws IOException, if the given path of the icon doesn't exist
         */
        public JLabel getLabel(String title, String icon) throws IOException {
        	
        	/**
        	 * Creates a new JLabel with the input "title" string.
        	 * 
        	 *  @see JLabel
        	 */
        	JLabel label = new JLabel(title);
        	
        	try {
        		/**
        		 * Reads an image from the icon string which indicates a path for the icon
        		 * to put beside the label title. For a correct use of this method imported
        		 * image must be 20x20 pixels.
        		 */
        		label.setIcon(new ImageIcon(ImageIO.read(getClass().getResource(icon))));
        		
        	/**
        	 * If it catches an IOException from the getResource method, it prints an error
        	 * message.
        	 */
        	} catch(IOException ex) {
        		ex.printStackTrace();
        	}
        	return label;
        }
        
        
        /**
         * This method establishes a connection with the server, by creating a valid socket,
         * in order to send a request to the server itself. If the request is valid then the
         * method handles the response by printing the output on the JTextarea designed for
         * the output.
         * 
         * @throws IOException 
         * @throws ClassNotFoundException
         */
        private void learningFromDBAction() throws IOException, ClassNotFoundException {
            
        	/**
        	 * The request object is initialized.
        	 */
        	Request req = null;
        	
        	/**
        	 * Number of clusters chosen by the user for the computation.
        	 */
            int numberClusters;
            
            /**
             * With getByName method of InetAddress, the method gets the DNS of an IP.
             */
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            
            /**
             * Port where the client makes the request to the server.
             */
            int port = 8000;
            
            /**
             * Creation of a new socket by setting the address and the port of the server.
             */
            Socket socket = new Socket(addr, port);
            try {
            	
            	/**
            	 * Creation of an ObjectOutputStream in order to send the request to the server.
            	 */
                out = new ObjectOutputStream(socket.getOutputStream());
                
                /**
                 * Creation of an ObjectInputStream in order to print the output once the client receives a response.
                 */
                in = new ObjectInputStream(socket.getInputStream());
                
                /**
                 * Getting the number of clusters to produce from the input textArea of the interface.
                 */
                numberClusters = panelWrite.getNumberOfClusters();
                
                /**
                 * Getting the name of the table where the query has place.
                 */
                String table = panelWrite.getTable();
                
                /**
                 * Getting the name of the file where the result of the computation is saved
                 */
                String fileName = panelWrite.getFile();
                
                /**
                 * Building a new request with the chosen file name, number of clusters to produce and the table name where the data is taken
                 */
                req = new WriteRequest(fileName, numberClusters, table);
                
                /**
                 * Sending the request to the server 
                 */
                out.writeObject(req);
                
                /**
                 * Printing on the textArea of the interface the response of the server.
                 */
                panelWrite.setOutputPanel((String)in.readObject());
                
              /**
               * Catching the NumberFormatException if the number of clusters given by the user is not usable for a valid computation 
               * and creates a JOptionPane to print the error message.
               */
            } catch(NumberFormatException e) { 
                JOptionPane.showMessageDialog(this, e.toString());
              /**
               * Catching the ClassNotFoundException if the driver manager of the database connector is not found and creates a JOptionPane
               * to print the error message.
               */
            } catch(ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Class not found.");
            } finally {
            	/**
            	 * It closes the socket even if there were problems during the execution of the request.
            	 */
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