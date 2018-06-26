package client;

// <applet code=KMeans.class width=600 height=600>
// </applet>
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        private JPanelCluster panelDB;
        private JPanelCluster panelFile;
    
        public TabbedPane() {
            JTabbedPane tabbedPane = new JTabbedPane();
            ActionListener actionDB = new ActionListener() {
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
            ActionListener actionFILE = new ActionListener() {
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
            this.panelDB = new JPanelCluster("DB", actionDB);
            this.panelFile = new JPanelCluster("File", actionFILE);
            tabbedPane.addTab("DB", panelDB);
            tabbedPane.addTab("File", panelFile);
            this.add(tabbedPane);
        }
    
        private void learningFromDBAction() throws IOException, ClassNotFoundException {
            Request req = null;
            String fileName = "data";
            int numberClusters;
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            int port = 8000;
            Socket socket = new Socket(addr, port);
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                numberClusters = Integer.parseInt(panelDB.kText.getText());
                String table = panelDB.tableText.getText();
                req = new WriteRequest(2, fileName, numberClusters, table);
                // Send request 
                out.writeObject(req);
                // Write response
                panelDB.clusterOutput.append((String)in.readObject());
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
            Request req = null;
            String fileName = "data";
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            int port = 8000;
            Socket socket = new Socket(addr, port);
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                req = new ReadRequest(1, fileName);
                // Send request 
                out.writeObject(req);
                // Write response
                panelFile.clusterOutput.append((String)in.readObject());
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
        
        class JPanelCluster extends JPanel {
            private static final long serialVersionUID = 1L;
            JTextField tableText = new JTextField(20);
            JTextField kText = new JTextField(10);
            JTextArea clusterOutput = new JTextArea();
            private JButton executeButton = new JButton();
            private JPanel upPanel, centralPanel, downPanel;
            private JLabel tableLabel = new JLabel("Table:");
            private JLabel kLabel = new JLabel("k");               
            
            JPanelCluster(String buttonName, ActionListener a) {
                setLayout(new GridLayout(5, 4)); 
                upPanel = new JPanel();
                upPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT); 
                centralPanel = new JPanel();
                downPanel = new JPanel();
                upPanel.add(tableLabel);
                upPanel.add(tableText);
                upPanel.add(kLabel);
                upPanel.add(kText);
                centralPanel.add(clusterOutput);
                executeButton.setText(buttonName);
                executeButton.setSize(new Dimension(10, 20));
                executeButton.addActionListener(a);
                downPanel.add(executeButton);
                add(upPanel);
                add(new JSeparator(JSeparator.HORIZONTAL));
                add(centralPanel);
                add(new JSeparator(JSeparator.HORIZONTAL));
                add(downPanel);
            }
        }
    }
  
    public void init() {
        JFrame frame = new JFrame();
        frame.setTitle("Clustering");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        TabbedPane tab = new TabbedPane();
        frame.add(tab);
        tab.setVisible(true);
    }
}