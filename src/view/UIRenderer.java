/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
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
    private JButton placeStart = null;
    private JButton placeEnd = null;
    private JButton placeObstacle = null;
    
    public UIRenderer(Controler c){
        
        
        //Set border
        TitledBorder title = BorderFactory.createTitledBorder("Options");
        this.setBorder(title);
        
        //Layout
        this.setLayout(new FlowLayout());
        
        //Button initialisation
        startButton = new JButton("START");
        startButton.setPreferredSize(smallButton);
        this.add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("You clicked the start button"); //To change body of generated methods, choose Tools | Templates.
            }
        }); 
        
        stopButton = new JButton("STOP");
        stopButton.setPreferredSize(smallButton);
        this.add(stopButton);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("You clicked the stop button"); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        saveButton = new JButton("Save configuration");
        saveButton.setPreferredSize(bigButton);
        this.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.saveConfiguration();
                } catch (IOException error) {
                    displaySaveError(error);
                }
            }
        }); 
        
        placeStart = new JButton("Place starting point");
        placeStart.setPreferredSize(bigButton);
        this.add(placeStart);
        placeStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.startingPointToBePlaced = true;
            }
        });
        
        placeEnd = new JButton("Place goal");
        placeEnd.setPreferredSize(bigButton);
        this.add(placeEnd);
        placeEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.endingPointToBePlaced = true;
            }
        });
        
        placeObstacle = new JButton("Place obstacle");
        placeObstacle.setPreferredSize(bigButton);
        this.add(placeObstacle);
        placeObstacle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.obstacleToBePlaced = true;
            }
        });
    }
    
    private void displaySaveError(IOException error){
        JOptionPane.showMessageDialog(this,
                        "File could not be saved : "+error.getMessage(),
                        "Saving error",
                        JOptionPane.ERROR_MESSAGE);
    }
}
