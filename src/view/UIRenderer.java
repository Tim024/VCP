/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
    private JButton stopButton = null;
    private JButton saveButton = null;
    private JButton loadButton = null;
    private JButton loadIMGButton = null;
    private JButton placeStart = null;
    private JButton placeEnd = null;
    private JButton placeObstacle = null;
    private JButton pheroButton = null;
    private JTextArea text = new JTextArea(5,20);
    
    public UIRenderer(Controler c){
        
        //Set border
        TitledBorder title = BorderFactory.createTitledBorder("Options");
        this.setBorder(title);
        
        //Layout
        this.setLayout(new FlowLayout());
        
        //Set text
        text.setMargin(new Insets(5,5,5,5));
        text.setEditable(false);
        this.add(text);
        
        //Button initialisation
        startButton = new JButton("START");
        startButton.setPreferredSize(smallButton);
        this.add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.run();
            }
        }); 
        
        stopButton = new JButton("STOP");
        stopButton.setPreferredSize(smallButton);
        this.add(stopButton);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.stopRun();
            }
        });
        
        saveButton = new JButton("Save configuration");
        saveButton.setPreferredSize(bigButton);
        this.add(saveButton);
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
        this.add(loadButton);
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
        this.add(loadIMGButton);
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
        this.add(pheroButton);
        pheroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.endingPointToBePlaced = false;
                c.obstacleToBePlaced = false;
                c.startingPointToBePlaced = false;
                c.pheromonVisible = !c.pheromonVisible;
            }
        });
        
        placeStart = new JButton("Place starting point");
        placeStart.setPreferredSize(bigButton);
        this.add(placeStart);
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
        this.add(placeEnd);
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
        this.add(placeObstacle);
        placeObstacle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.obstacleToBePlaced = true;
                c.endingPointToBePlaced = false;
                c.startingPointToBePlaced = false;
            }
        });
    }
    
    private void displayError(String saved, IOException error){
        JOptionPane.showMessageDialog(this,
                        "File could not be "+saved+" : "+error.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
    }
}
