/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import main.Controler;

/**
 *
 * @author Kashy
 */
public class UIAlgo extends JPanel {
    
    private Controler myControler = null;
    
    private JSlider speed = null;
    private JLabel speedText = new JLabel();
    private JSlider pheros = null;
    private JLabel pherot = new JLabel();
    
    private JSlider rands = null;
    private JLabel randt = new JLabel();
    
    private JSlider pheromaxs = null;
    private JLabel pheromaxt = new JLabel();
    
    private JSlider pheroDrops = null;
    private JLabel pheroDropt = new JLabel();
    
    private JSlider nbAnts = null;
    private JLabel nbAntt = new JLabel();
    
    public UIAlgo(Controler c){
        myControler = c;
        
        //Set border
        TitledBorder title = BorderFactory.createTitledBorder("Algorithm Options");
        this.setBorder(title);
        
        //Layout
        this.setLayout(new GridLayout(6,4));
        
        //slider
        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new FlowLayout());
        speed = new JSlider(JSlider.HORIZONTAL, 1, 200, 1);
        speedText.setText("Speed : "+Integer.toString(speed.getValue()));
        speed.addChangeListener(e -> sliderChanged() );
        speedPanel.add(speedText);
        speedPanel.add(speed);
        this.add(speedPanel);
        
        //slider
        JPanel phero = new JPanel();
        phero.setLayout(new FlowLayout());
        pheros = new JSlider(JSlider.HORIZONTAL, 1, 1000, 500);
        pherot.setText("Pheromone evaporation rate : 0.005");
        pheros.addChangeListener(e -> sliderChangedphero() );
        phero.add(pherot);
        phero.add(pheros);
        this.add(phero);
        
        //slider
        JPanel rand = new JPanel();
        rand.setLayout(new FlowLayout());
        rands = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);
        randt.setText("Pheromone Min value : 0.001");
        rands.addChangeListener(e -> sliderChangedrand() );
        rand.add(randt);
        rand.add(rands);
        this.add(rand);
        
        //slider
        JPanel pheromax = new JPanel();
        pheromax.setLayout(new FlowLayout());
        pheromaxs = new JSlider(JSlider.HORIZONTAL, 10, 10000, 1000);
        pheromaxt.setText("Pheromone Max value : 1000");
        pheromaxs.addChangeListener(e -> sliderChangedpheromax() );
        pheromax.add(pheromaxt);
        pheromax.add(pheromaxs);
        this.add(pheromax);
        
        //slider
        JPanel pheroDrop = new JPanel();
        pheroDrop.setLayout(new FlowLayout());
        pheroDrops = new JSlider(JSlider.HORIZONTAL, 1, 100, 50);
        pheroDropt.setText("Pheromone drop value : 50");
        pheroDrops.addChangeListener(e -> sliderChangedpheroDrop() );
        pheroDrop.add(pheroDropt);
        pheroDrop.add(pheroDrops);
        this.add(pheroDrop);
        
        //slider
        JPanel nbAnt = new JPanel();
        nbAnt.setLayout(new FlowLayout());
        nbAnts = new JSlider(JSlider.HORIZONTAL, 10, 2000, 1000);
        nbAntt.setText("Number of Ants : 1000");
        nbAnts.addChangeListener(e -> sliderChangednbAnts() );
        nbAnt.add(nbAntt);
        nbAnt.add(nbAnts);
        this.add(nbAnt);
        
    }
    
    public void sliderChanged()
    {
        speedText.setText("Speed : "+Integer.toString(speed.getValue()));
        myControler.setSpeed(speed.getValue());
    }
    
    public void sliderChangedphero()
    {
        double k = ((double) pheros.getValue())/100000+0.99;
        //System.out.println(k);
        DecimalFormat df = new DecimalFormat("#.######");
        pherot.setText("Pheromone evaporation rate : "+df.format(1-k));
        myControler.getAlgo().editPheromone(k);
    }
    
        public void sliderChangedrand()
    {
        double k = ((double) rands.getValue())/1000;
        //System.out.println(k);
        randt.setText("Pheromone Min value : "+k);
        myControler.getAlgo().setMinPheromon(k);
    }
        public void sliderChangedpheromax()
    {
        double k = ((double) pheromaxs.getValue());
        //System.out.println(k);
        pheromaxt.setText("Pheromone Max value : "+k);
        myControler.getAlgo().setMaxPheromon(k);
    }
        
        public void sliderChangedpheroDrop()
    {
        double k = ((double) pheroDrops.getValue());
        //System.out.println(k);
        pheroDropt.setText("Pheromone drop value : "+k);
        myControler.getAlgo().setPheromoneDrop(k);
    }
        public void sliderChangednbAnts()
    {
        int k = (nbAnts.getValue());
        //System.out.println(k);
        nbAntt.setText("Number of Ant : "+k);
        myControler.getAlgo().setNbOfAnt(k);
    }
        
    public int getNbAnts(){
        return nbAnts.getValue();
    }

    
}
