/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Tim
 */
public class AntAlgorithm {
    
    private int numberOfAnts; //number of Ants
    private int antLifetime; //number Of Iteration before they die
    private double pheromonDrop; //pheromon they drop
    private double pheromonEvaporation; //amount of pheromon they loose
    private double minPheromon;
    
    private Ant[] antList = null;
    
    private Map map = null;
    
    private double[][] pheromonTable;
    
    public AntAlgorithm(Map map){
        this.minPheromon = 0.001;
        this.numberOfAnts = 1000;
        this.antLifetime = Math.max(map.getDimension().height,map.getDimension().width);
        this.pheromonDrop = 50;
        this.pheromonEvaporation = 0.998;//0.998maze.map ;//for 100x100 0.995; //should be higher for bigger map
        this.map = map;
        
        this.pheromonTable = new double[map.getDimension().width][map.getDimension().height];
        for(int x=0;x<map.getDimension().width;x++){
            for(int y=0;y<map.getDimension().height;y++){
                pheromonTable[x][y] = 0;
            }
        }
        antList = new Ant[numberOfAnts];
        Random rand = new Random();
        for(int i = 0; i < numberOfAnts; i ++){
            antList[i] = new Ant(map.getStartingPoint(), rand.nextInt(5*antLifetime)+5*antLifetime);
        }
    }
    
    private void moveNextStep(Point location, Rectangle[] obstacles){
        for(int i = 0; i < numberOfAnts; i ++){
            antList[i].move(location, obstacles, pheromonTable, map.getDimension());
        }
        this.updatePheromon();
    }
    
    public void moveStep(int k, Point location, Rectangle[] obstacles){
        for(int i = 0; i < k; i ++){
            this.moveNextStep(location, obstacles);
        }
    }
    
    
    private void updatePheromon(){
        //pheromon drop
        for(int i = 0; i < numberOfAnts; i ++){
            if(antList[i].hasFoundFood()){
                pheromonTable[antList[i].getLocation().x][antList[i].getLocation().y] += pheromonDrop;
                }
            }
        //and evaporation
        for(int x=0;x<map.getDimension().width;x++){
            for(int y=0;y<map.getDimension().height;y++){
                pheromonTable[x][y] = pheromonTable[x][y] * pheromonEvaporation < minPheromon ? minPheromon : (pheromonTable[x][y] * pheromonEvaporation);
            }
        }
    }

    public Ant[] getAnts() {
        return this.antList;
    }
    
    public double[][] getPheromons(){
        return this.pheromonTable;
    }

    public void printPheromnTable() {
        for(int x=000;x<100;x++){
            for(int y=000;y<100;y++){
                System.out.print(pheromonTable[x][y]);

            }
                            System.out.println("");
        }
    }
}
