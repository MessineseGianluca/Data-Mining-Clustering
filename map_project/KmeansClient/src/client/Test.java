package client;

import javax.swing.*;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;

public class Test{ 
    public static void main(String args[]) {
        JPanelCluster panelDB = new JPanelCluster("DB");
        JPanelCluster panelFile = new JPanelCluster("File");
        JFrame frame = new JFrame();
        frame.setTitle("Clustering");
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("DB", panelDB);
        tabbedPane.addTab("File", panelFile);
        frame.add(tabbedPane);
    }
}
	