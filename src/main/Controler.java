/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import model.AntAlgorithm;
import model.Ant;
import model.Map;
import view.Window;
/**
 *
 * @author Kashy
 */
public class Controler {
    private Map map = null;
    private Window view = null;
    private AntAlgorithm algo = null;
    private int speed = 1;

    public boolean startingPointToBePlaced = false;
    public boolean endingPointToBePlaced = false;
    public boolean obstacleToBePlaced = false;
    private boolean running = false;
    
    public Controler(){
        this.speed = 1;
        map = new Map();//Default
        view = new Window(this);
        
        algo = new AntAlgorithm(map);
        
        view.updateView();
    }
    
    public void stopRun(){
        this.running = false;
    }
    public void run(){
        this.running = true;
        new Thread(){
        public void run(){

        while(running){
            try {
                Thread.sleep(4);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
            }
            algo.moveStep(speed, map.getEndPoint(), map.getObstacles());
            view.updateAnts();
        }
        //algo.printPheromnTable();
 
        }}.start();
    }
    
    
    public Map getMap(){//should not be here
        return this.map;
    }
    //If we are running, do not authorize change of values.

    public void saveConfiguration() throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("maps/saved.txt")))) {
              writer.write(Integer.toString(map.getDimension().width)+"\n");
              writer.write(Integer.toString(map.getDimension().height)+"\n");
              writer.write(Integer.toString(map.getStartingPoint().x)+"\n");
              writer.write(Integer.toString(map.getStartingPoint().y)+"\n");
              writer.write(Integer.toString(map.getEndPoint().x)+"\n");
              writer.write(Integer.toString(map.getEndPoint().y)+"\n");
              for(int i = 0; i < map.getObstacles().length; i++)
              {
                writer.write(Integer.toString(map.getObstacle(i).x)+"\n");
                writer.write(Integer.toString(map.getObstacle(i).y)+"\n");
                writer.write(Integer.toString(map.getObstacle(i).width)+"\n");
                writer.write(Integer.toString(map.getObstacle(i).height)+"\n");
              }
            }
    }
    
    public void setStartingPoint(int x, int y){
        if(!running){
            map.setStartingPoint(x, y);
            algo = new AntAlgorithm(map);
            view.updateView();
        }
    }

    public void setEndingPoint(int x, int y) {
        if(!running){
            map.setEndingPoint(x, y);
            algo = new AntAlgorithm(map);
            view.updateView();
        }
    }

    public void setObstacle(int x, int y, int z, int t) {
        if(!running){
            map.placeObstacle(x,y,z,t);
            algo = new AntAlgorithm(map);
            view.updateView();
        }
    }
    
    public AntAlgorithm getAlgo(){
        return this.algo;
    }
}
