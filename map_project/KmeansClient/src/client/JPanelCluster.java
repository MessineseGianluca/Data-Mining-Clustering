package client;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.*;
public class JPanelCluster extends JPanel{
	      private static final long serialVersionUID = -2718248592095430930L;
    	  JTextField tableText = new JTextField(20);
          JTextField kText = new JTextField(10);
          private JTextArea clusterOutput = new JTextArea();
          private JButton executeButton = new JButton();
          private JPanel upPanel, centralPanel, downPanel, Panel1, Panel2, Panel3;
          private JLabel tableLabel = new JLabel("k:");
          private JLabel kLabel = new JLabel("Table:");          
          
          JPanelCluster(String buttonName) {
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
              this.executeButton.setText(buttonName);
              this.executeButton.setPreferredSize(new Dimension(50,100));
              downPanel.add(executeButton);
              this.add(upPanel);
              this.add(new JSeparator(JSeparator.HORIZONTAL));
              this.add(centralPanel);
              this.add(new JSeparator(JSeparator.HORIZONTAL));
              this.add(downPanel);
          }
}
