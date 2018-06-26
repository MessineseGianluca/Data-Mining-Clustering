package client;

// <applet code=KMeans.class width=600 height=600>
// </applet>
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.beans.EventHandler;
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
        private JPanelCluster panelDB;
        private JPanelCluster panelFile;
    
        public TabbedPane() {
            JTabbedPane tabbedPane = new JTabbedPane();
            ActionListener actionDB = (ActionListener)EventHandler.create
                (ActionListener.class, tabbedPane, "learningFromDBAction");
            ActionListener actionFILE = (ActionListener)EventHandler.create
                (ActionListener.class, tabbedPane, "learningFromFileAction");
            this.panelDB = new JPanelCluster("DB", actionDB);
            this.panelFile = new JPanelCluster("File", actionFILE);
            tabbedPane.addTab("DB", panelDB);
            tabbedPane.addTab("File", panelFile);  
            this.add(tabbedPane);
            this.setVisible(true);
        }
    
        private void learningFromDBAction() throws IOException, ClassNotFoundException {
            Request req = null;
            String fileName = "data";
            int numberClusters;
            try {
                numberClusters = new Integer(panelDB.kText.getText()).intValue();
                String table = new String(panelDB.tableText.getText());
                req = new WriteRequest(2, fileName, numberClusters, table);
                // Send request 
                out.writeObject(req);
                // Write response
                panelDB.clusterOutput.append((String)in.readObject());
            } catch(NumberFormatException e) { 
                JOptionPane.showMessageDialog(this, e.toString());
            } catch(ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Class not found.");
            }
        }
        
        private void learningFromFileAction() throws IOException, ClassNotFoundException {
            Request req = null;
            String fileName = "data";
            req = new ReadRequest(1, fileName);
            try {
                // Send request 
                out.writeObject(req);
                // Write response
                panelDB.clusterOutput.append((String)in.readObject());
            } catch(ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Class not found.");
            }
        }
        
        class JPanelCluster extends JPanel {
            private static final long serialVersionUID = 1L;
            JTextField tableText = new JTextField(20);
            JTextField kText = new JTextField(10);
            JTextArea clusterOutput = new JTextArea();
            private JButton executeButton = new JButton();
            private JPanel upPanel, centralPanel, downPanel;
            private JLabel tableLabel = new JLabel("k:");
            private JLabel kLabel = new JLabel("Table:");               
            
            JPanelCluster(String buttonName, ActionListener a) {
                this.setLayout(new GridLayout(5, 4, 10, 10)); 
                upPanel = new JPanel();
                upPanel.setLayout(new GridLayout(1, 4, 10, 10));
                upPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT); 
                centralPanel = new JPanel();
                centralPanel.setLayout(new GridLayout(1, 1, 10, 10));
                downPanel = new JPanel();
                downPanel.setLayout(new GridLayout(1 , 1, 10, 10));
                upPanel.add(tableLabel);
                upPanel.add(tableText);
                upPanel.add(kLabel);
                upPanel.add(kText);
                centralPanel.add(clusterOutput);
                executeButton.setText(buttonName);
                executeButton.setPreferredSize(new Dimension(50,100));
                executeButton.addActionListener(a);
                downPanel.add(executeButton);
                this.add(upPanel);
                this.add(new JSeparator(JSeparator.HORIZONTAL));
                this.add(centralPanel);
                this.add(new JSeparator(JSeparator.HORIZONTAL));
                this.add(downPanel);
            }
        }
    }
  
    public void init() {
        Socket socket = null;
        try {
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            int port = 8000;
            socket = new Socket(addr, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            JFrame frame = new JFrame();
            frame.setTitle("Clustering");
            frame.setSize(600,600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            TabbedPane tab = new TabbedPane();
            frame.add(tab);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());  
        }/* finally {
            try {
                if(socket != null) socket.close();
            } catch(IOException e) {
                JOptionPane.showMessageDialog(this, "Socket not closed");
            }
        }*/
    }
}