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
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Tim van der Lee
 */
public class UIRenderer extends JPanel {
    
    private Dimension buttonSize = new Dimension(80, 40);
    private JButton startButton = null;
    private JButton stopButton = null;
    
    public UIRenderer(){
        
        this.setLayout(new FlowLayout());
        
        startButton = new JButton("START");
        startButton.setPreferredSize(buttonSize);
        this.add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("You clicked the start button"); //To change body of generated methods, choose Tools | Templates.
            }
        }); 
        
        stopButton = new JButton("STOP");
        stopButton.setPreferredSize(buttonSize);
        this.add(stopButton);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("You clicked the stop button"); //To change body of generated methods, choose Tools | Templates.
            }
        }); 
    }
}
