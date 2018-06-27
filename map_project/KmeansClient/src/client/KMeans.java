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
            this.add(tabbedPane);
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
        
        
        class JPanelWrite extends JPanel {
            private static final long serialVersionUID = 1L;
            private JTextField tableText = new JTextField(20);
            private JTextField fileText = new JTextField(20);
            private JTextField kText = new JTextField(10);
            private JTextArea clusterOutput = new JTextArea();
            private JButton executeButton = new JButton();
            private JLabel tableLabel = new JLabel("Table:");
            private JLabel kLabel = new JLabel("Num of clusters: ");
            private JLabel fileLabel = new JLabel("File: ");
            
            JPanelWrite(String buttonName, ActionListener a) { 
                GroupLayout layout = new GroupLayout(this);
                JScrollPane scrollingArea = new JScrollPane(
                    clusterOutput, 
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
                );
                clusterOutput.setEditable(false);
                clusterOutput.setLineWrap(true);
                scrollingArea.setPreferredSize(new Dimension(780, 200));
                scrollingArea.setMaximumSize(new Dimension(780, 200));
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
                        .addComponent(fileLabel)
                        .addComponent(fileText)
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
                        .addComponent(fileLabel)
                        .addComponent(fileText)
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
            
            public int getNumberOfClusters() {
                return Integer.parseInt(kText.getText());
            }
            
            public String getTable() {
                return tableText.getText();
            }
            
            public String getFile() {
                return fileText.getText();
            }
          
            public void setOutputTextArea(String s) {
                clusterOutput.setText(s);
            }
        }
        
        class JPanelRead extends JPanel {
            private static final long serialVersionUID = 1L;
            private JTextField fileText = new JTextField(20);
            private JTextArea clusterOutput = new JTextArea();
            private JButton executeButton = new JButton();
            private JLabel fileLabel = new JLabel("File:");
            
            JPanelRead(String buttonName, ActionListener a) { 
                GroupLayout layout = new GroupLayout(this);
                JScrollPane scrollingArea = new JScrollPane(
                    clusterOutput, 
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
                );
                clusterOutput.setEditable(false);
                clusterOutput.setLineWrap(true);
                scrollingArea.setPreferredSize(new Dimension(780, 200));
                scrollingArea.setMaximumSize(new Dimension(780, 200));
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
                        .addComponent(fileLabel)
                        .addComponent(fileText)
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
                        .addComponent(fileLabel)
                        .addComponent(fileText)
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
            
            public String getFile() {
                return fileText.getText();
            }
            public void setOutputTextArea(String s) {
                clusterOutput.setText(s);
            }
        }
    }
  
    public void init() {
        JFrame frame = new JFrame();
        frame.setTitle("Clustering");
        frame.setSize(800, 345);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        TabbedPane tab = new TabbedPane();
        frame.add(tab);
        frame.setVisible(true);
    }
}