package client;

import java.awt.BorderLayout;
// <applet code=KMeans.class width=600 height=600>
// </applet>
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

public class Kmeans extends JApplet {
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
            		try{
            			learningFromDBAction();
            		}catch(IOException err) {
            			JOptionPane.showMessageDialog(tabbedPane, err.toString());
            		}catch(ClassNotFoundException err) {
            			JOptionPane.showMessageDialog(tabbedPane, err.toString());
            		}	
            	}
            };
            ActionListener actionFILE = new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		try{
            			learningFromFileAction();
            		}catch(IOException err) {
            			JOptionPane.showMessageDialog(tabbedPane, err.toString());
            		}catch(ClassNotFoundException err) {
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
            try {
                numberClusters = Integer.parseInt(panelDB.kText.getText());
                String table = panelDB.tableText.getText();
                req = new WriteRequest(2, fileName, numberClusters, table);
                // Send request 
                out.writeObject(req);
                // Write response
                this.panelDB.clusterOutput.append((String)in.readObject());
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
                panelFile.clusterOutput.append((String)in.readObject());
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
            private JLabel tableLabel = new JLabel("Table:");
            private JLabel kLabel = new JLabel("k:"); 
            
            JPanelCluster(String buttonName, ActionListener a) { 
            	GroupLayout layout = new GroupLayout(this);
            	JScrollPane scrollingArea = new JScrollPane(clusterOutput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                //clusterOutput.setPreferredSize(new Dimension(500,200));
                //clusterOutput.setMaximumSize(new Dimension(500,200));
                clusterOutput.setEditable(false);
                clusterOutput.setLineWrap(true);
                scrollingArea.setPreferredSize(new Dimension(500,200));
                scrollingArea.setMaximumSize(new Dimension(500,200));
                scrollingArea.setVisible(true);
                scrollingArea.setAlignmentX(RIGHT_ALIGNMENT);
                clusterOutput.setWrapStyleWord(true);
                //clusterOutput.setColumns(30);
                executeButton.setText(buttonName);
                executeButton.setSize(new Dimension(50,100));
                executeButton.addActionListener(a);
            	this.setLayout(layout);
                layout.setAutoCreateContainerGaps(true);
                layout.setAutoCreateGaps(true);
                layout.setVerticalGroup(
                			layout.createSequentialGroup()
                			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    			.addComponent(tableLabel)
                    			.addComponent(tableText)
                    			.addComponent(kLabel)
                    			.addComponent(kText))
                			.addGroup(layout.createParallelGroup()
                				//.addComponent(clusterOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                				.addComponent(scrollingArea,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                				.addComponent(executeButton)
                );
                
                layout.setHorizontalGroup(
                			layout.createParallelGroup()
                			.addGroup(layout.createSequentialGroup()
                				.addComponent(tableLabel)
                				.addComponent(tableText)
                				.addComponent(kLabel)
                				.addComponent(kText))
                			.addGroup(layout.createSequentialGroup()
                				//.addComponent(clusterOutput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                				.addComponent(scrollingArea))
                			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                				.addComponent(executeButton))
                );
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
            frame.setSize(560,345);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            TabbedPane tab = new TabbedPane();
            frame.add(tab);
            frame.setVisible(true);
            //frame.pack();
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