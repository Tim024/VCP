/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import main.Controler;

/**
 *
 * @author Tim van der Lee
 */
public class UIRenderer extends JPanel {
    
    private Dimension smallButton = new Dimension(72, 35);
    private Dimension bigButton = new Dimension(140,35);
    private JButton startButton = null;
    private JButton restartButton = null;
    private JButton stopButton = null;
    private JButton saveButton = null;
    private JButton loadButton = null;
    private JButton loadIMGButton = null;
    private JButton placeStart = null;
    private JButton placeEnd = null;
    private JButton placeObstacle = null;
    private JButton pheroButton = null;
    private JButton displayPath = null;
    private JTextArea text = new JTextArea(5,20);
    private JScrollPane scrollPane = new JScrollPane( text );
    private UIAlgo uialgo = null;
    
    private JPanel eastPanel = new JPanel();
    private JPanel westPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    
    
    public UIRenderer(Controler c){
        
        eastPanel.setLayout(new GridLayout(1,1));
        westPanel.setLayout(new GridLayout(5,2));
        southPanel.setLayout(new FlowLayout());
        
        //Set border
        TitledBorder title = BorderFactory.createTitledBorder("Options");
        this.setBorder(title);
        
        //Layout
        this.setLayout(new BorderLayout());
        
        //uialgo
        uialgo = new UIAlgo(c);
        eastPanel.add(uialgo);
        
        //Set text
        text.setMargin(new Insets(5,5,5,5));
        text.setEditable(false);
        this.add(scrollPane, BorderLayout.CENTER);
        
        //Button initialisation
        startButton = new JButton("START");
        startButton.setPreferredSize(smallButton);
        southPanel.add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.run();
            }
        });
        
        restartButton = new JButton("RESTART");
        restartButton.setPreferredSize(smallButton);
        southPanel.add(restartButton);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.restart();
            }
        });
        
        stopButton = new JButton("STOP");
        stopButton.setPreferredSize(smallButton);
        southPanel.add(stopButton);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.stopRun();
            }
        });
        
        saveButton = new JButton("Save configuration");
        saveButton.setPreferredSize(bigButton);
        southPanel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    text.append(c.saveConfiguration());
                } catch (IOException ex) {
                    displayError("saved",ex);
                }
            }
        });
        
        loadButton = new JButton("Load configuration");
        loadButton.setPreferredSize(bigButton);
        southPanel.add(loadButton);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    text.append(c.loadConfiguration());
                } catch (IOException ex) {
                    displayError("opened",ex);
                }
            }
        });
        
        loadIMGButton = new JButton("Load image");
        loadIMGButton.setPreferredSize(bigButton);
        southPanel.add(loadIMGButton);
        loadIMGButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    text.append(c.loadIMG());
                } catch (IOException ex) {
                    displayError("opened",ex);
                }
            }
        });
        
        pheroButton = new JButton("See Pheromon");
        pheroButton.setPreferredSize(bigButton);
        westPanel.add(pheroButton);
        pheroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.endingPointToBePlaced = false;
                c.obstacleToBePlaced = false;
                c.startingPointToBePlaced = false;
                c.pheromonVisible = !c.pheromonVisible;
            }
        });
        
        displayPath = new JButton("Display Best Path");
        displayPath.setPreferredSize(smallButton);
        westPanel.add(displayPath);
        displayPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.endingPointToBePlaced = false;
                c.obstacleToBePlaced = false;
                c.startingPointToBePlaced = false;
                c.pheromonVisible = false;
                c.displayPath = !c.displayPath;
            }
        });
        
        placeStart = new JButton("Place starting point");
        placeStart.setPreferredSize(bigButton);
        westPanel.add(placeStart);
        placeStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.startingPointToBePlaced = true;
                c.endingPointToBePlaced = false;
                c.obstacleToBePlaced = false;
            }
        });
        
        placeEnd = new JButton("Place goal");
        placeEnd.setPreferredSize(bigButton);
        westPanel.add(placeEnd);
        placeEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.endingPointToBePlaced = true;
                c.obstacleToBePlaced = false;
                c.startingPointToBePlaced = false;
            }
        });
        
        placeObstacle = new JButton("Place obstacle");
        placeObstacle.setPreferredSize(bigButton);
        westPanel.add(placeObstacle);
        placeObstacle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.obstacleToBePlaced = true;
                c.endingPointToBePlaced = false;
                c.startingPointToBePlaced = false;
            }
        });
        
        this.add(eastPanel, BorderLayout.EAST);
        this.add(westPanel, BorderLayout.WEST);
        this.add(southPanel, BorderLayout.SOUTH);
    }
    
    private void displayError(String saved, IOException error){
        JOptionPane.showMessageDialog(this,
                        "File could not be "+saved+" : "+error.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
    }
    
    public void textDisplay(String s){
        if(s!=null) text.append(s);
    }
    public int getNbAnts(){
        return uialgo.getNbAnts();
    }
}
