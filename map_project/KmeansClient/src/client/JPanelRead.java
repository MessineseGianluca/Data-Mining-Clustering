package client;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class JPanelRead extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField fileText = new JTextField(20);
    private JTextArea outputPanel = new JTextArea();
    private JButton executeButton = new JButton();
    private JLabel fileLabel = new JLabel("File:");
    
    JPanelRead(String buttonName, ActionListener a) { 
        GroupLayout layout = new GroupLayout(this);
        JScrollPane scrollingArea = new JScrollPane(
            outputPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        outputPanel.setEditable(false);
        outputPanel.setLineWrap(true);
        scrollingArea.setPreferredSize(new Dimension(880, 200));
        scrollingArea.setMaximumSize(new Dimension(880, 200));
        scrollingArea.setVisible(true);
        scrollingArea.setAlignmentX(RIGHT_ALIGNMENT);
        outputPanel.setWrapStyleWord(true);
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
    
    String getFile() {
        return fileText.getText();
    }
    void setOutputPanel(String s) {
        outputPanel.setText(s);
    }
}