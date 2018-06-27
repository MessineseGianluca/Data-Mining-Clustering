package client;

// <applet code=KMeans.class width=600 height=600>
// </applet>
import java.awt.Dimension;
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
            this.panelDB = new JPanelCluster("Send", actionDB);
            this.panelFile = new JPanelCluster("Send", actionFILE);
            tabbedPane.addTab("Write to File", panelDB);
            tabbedPane.addTab("Read File", panelFile);
            this.add(tabbedPane);
        }
    
        private void learningFromDBAction() throws IOException, ClassNotFoundException {
        	panelDB.clusterOutput.setText(null);
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
        	panelFile.clusterOutput.setText(null);
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
            private JLabel tableLabel = new JLabel("Table:");
            private JLabel kLabel = new JLabel("Num of clusters: "); 
            
            JPanelCluster(String buttonName, ActionListener a) { 
                GroupLayout layout = new GroupLayout(this);
                JScrollPane scrollingArea = new JScrollPane(
                    clusterOutput, 
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
                );
                clusterOutput.setEditable(false);
                clusterOutput.setLineWrap(true);
                scrollingArea.setPreferredSize(new Dimension(580, 200));
                scrollingArea.setMaximumSize(new Dimension(580, 200));
                scrollingArea.setVisible(true);
                scrollingArea.setAlignmentX(RIGHT_ALIGNMENT);
                clusterOutput.setWrapStyleWord(true);
                executeButton.setText(buttonName);
                executeButton.setSize(new Dimension(50, 100));
                executeButton.addActionListener(a);
                this.setLayout(layout);
                layout.setAutoCreateContainerGaps(true);
                layout.setAutoCreateGaps(true);
                layout.setVerticalGroup(
                    layout.createSequentialGroup()
                    .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tableLabel)
                        .addComponent(tableText)
                        .addComponent(kLabel)
                        .addComponent(kText)
                    )
                    .addGroup(
                        layout.createParallelGroup()
                        .addComponent(
                            scrollingArea,
                            GroupLayout.PREFERRED_SIZE, 
                            GroupLayout.DEFAULT_SIZE, 
                            GroupLayout.PREFERRED_SIZE
                        )
                    )
                    .addComponent(executeButton)
                );
                
                layout.setHorizontalGroup(
                    layout.createParallelGroup()
                    .addGroup(
                        layout.createSequentialGroup()
                        .addComponent(tableLabel)
                        .addComponent(tableText)
                        .addComponent(kLabel)
                        .addComponent(kText)
                    )
                    .addGroup(
                        layout.createSequentialGroup()
                        .addComponent(scrollingArea)
                    )
                    .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(executeButton)
                    )
                );
            }
        }
    }
  
    public void init() {
        JFrame frame = new JFrame();
        frame.setTitle("Clustering");
        frame.setSize(600, 345);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        TabbedPane tab = new TabbedPane();
        frame.add(tab);
        frame.setVisible(true);
    }
}