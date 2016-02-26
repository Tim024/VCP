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
import model.Map;
import view.Window;
/**
 *
 * @author Kashy
 */
public class Controler {
    private Map map = null;
    private Window view = null;
    public boolean startingPointToBePlaced = false;
    public boolean endingPointToBePlaced = false;
    public boolean obstacleToBePlaced = false;
    
    public Controler(){
        map = new Map();//Default
        view = new Window(this);
        
        view.updateView();
    }
    
    public Map getMap(){
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
        map.setStartingPoint(x, y);
        view.updateView();
    }

    public void setEndingPoint(int x, int y) {
        map.setEndingPoint(x, y);
        view.updateView();
    }

    public void setObstacle(int x, int y, int z, int t) {
        map.placeObstacle(x,y,z,t);
        view.updateView();
    }
}
