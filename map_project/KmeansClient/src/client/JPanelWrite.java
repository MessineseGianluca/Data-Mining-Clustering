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

/**
 * The JPanelWrite class has the role to build a JPanel for the clustering calculation interface.
 * Here all the components of the PanelWrite are initialize with their settings. A specific layout
 * manager is initialize in order to organize the components in the best way for the user point of view.
 * 
 * @see JPanel
 */
class JPanelWrite extends JPanel {
	
	/**
	 * The serial version ID needed to serialize instances of this
	 * class.
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Creating a new JTextField for the user input. In this case the TextField
     * receives the name of the server database table to be queried.
     * 
     * @see JTextField
     */
    private JTextField tableText = new JTextField(20);
    
    /**
     * Creating a new JTextField for the user input. In this case the TextField
     * receive the name of the file where clusters are going to be saved
     * 
     * @see JTextField
     */
    private JTextField fileText = new JTextField(20);
    
    /**
     * Creating a new JTextField for the user input. In this case the TextField
     * receive the number of the clusters to be calculated and saved
     * 
     * @see JTextField
     */
    private JTextField kText = new JTextField(10);
    
    /**
     * Creating a new JTextArea for the server output. All the results of the
     * computation will be printed in this TextArea.
     * 
     * @see JTextArea
     */
    private JTextArea outputPanel = new JTextArea();
    
    /**
     * Creating a new JButton so the user can start a new computation.
     * 
     * @see JTextButton
     */
    private JButton executeButton = new JButton();
    
    /**
     * Creating a new JLabel so the user can understand what he has to write
     * in the TextAreas. In this case the JLabel is paired with the TextField
     * tableText.
     * 
     * @see JLabel
     */
    private JLabel tableLabel = new JLabel("Table:");
    
    /**
     * Creating a new JLabel so the user can understand what he has to write
     * in the TextAreas. In this case the JLabel is paired with the TextField
     * kText.
     * 
     * @see JLabel
     */
    private JLabel kLabel = new JLabel("Num of clusters: ");
    
    /**
     * Creating a new JLabel so the user can understand what he has to write
     * in the TextAreas. In this case the JLabel is paired with the TextField
     * fileText.
     * 
     * @see JLabel
     */
    private JLabel fileLabel = new JLabel("File: ");
    
    /**
     * The constructor of the JPanelWrite receives from the caller the string to be attached
     * to the button and the ActionListener also linked to its only JButton. There is also the
     * creation of a new Layout Manager object in order to set a correct position of the components.
     * 
     * 
     * @param buttonName, which is going to be attached to the JButton of the Panel
     * @param a, which is going to be linked to the only JButton of the Panel
     */
    JPanelWrite(String buttonName, ActionListener a) { 
    	
    	/**
    	 * Creating a new layout manager object based on GroupLayout Class,because it can easily manager
    	 * different type of components.
    	 * 
    	 * @see GroupLayout
    	 */
        GroupLayout layout = new GroupLayout(this);
        
        /**
         * Creating a JScrollPane in order to make the output readable. This JScrollPane is attached to the JTextArea
         * outputPanel. The vertical scrollbar is always shown even if there is no output printed in the TextArea.
         * Meanwhile the horizontal scrollbar is shown only if the output goes out of vertical bounds.
         * 
         * @see JScrollPane
         */
        JScrollPane scrollingArea = new JScrollPane(
            outputPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        
        /**
         * 
         */
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
    
    int getNumberOfClusters() {
        return Integer.parseInt(kText.getText());
    }
    
    String getTable() {
        return tableText.getText();
    }
    
    String getFile() {
        return fileText.getText();
    }
  
    void setOutputPanel(String s) {
        outputPanel.setText(s);
    }
}