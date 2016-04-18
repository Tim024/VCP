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

//TODO Size of the path that we found
//TODO restart button
public class AntAlgorithm {
    
    private int numberOfAnts; //number of Ants
    private int antLifetime; //number Of Iteration before they die
    private double pheromonDrop; //pheromon they drop
    private double pheromonEvaporation; //amount of pheromon they loose
    private double minPheromon;
    
    private Ant[] antList = null;
    
    private Map map = null;
    
    private double[][] pheromonTable;
    
    private double maxPheromon;
    //maxphero = minphero / (evap*shortest path length)
    
    public AntAlgorithm(Map map){
        this.minPheromon = 0.001;
        this.numberOfAnts = 1000;
        this.antLifetime = 2*Math.max(map.getDimension().height,map.getDimension().width);
        this.pheromonDrop = 50;
        this.pheromonEvaporation = 0.994;//0.998maze.map ;//for 100x100 0.995; //should be higher for bigger map
        this.map = map;
        this.maxPheromon = 1000000000;
        
        this.pheromonTable = new double[map.getDimension().width][map.getDimension().height];
        for(int x=0;x<map.getDimension().width;x++){
            for(int y=0;y<map.getDimension().height;y++){
                pheromonTable[x][y] = 0;
            }
        }
        antList = new Ant[numberOfAnts];
        Random rand = new Random();
        for(int i = 0; i < numberOfAnts; i ++){
            antList[i] = new Ant(map.getStartingPoint(), rand.nextInt(6*antLifetime)+5*antLifetime);
        }
    }
    
     public AntAlgorithm(Map map, int nbOfAnts){
        this(map);
        this.numberOfAnts=nbOfAnts;
        antList = new Ant[numberOfAnts];
        Random rand = new Random();
        for(int i = 0; i < numberOfAnts; i ++){
            antList[i] = new Ant(map.getStartingPoint(), rand.nextInt(6*antLifetime)+5*antLifetime);
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
                pheromonTable[antList[i].getLocation().x][antList[i].getLocation().y] += pheromonDrop*((double) antList[i].getMemoryPos()/(double)antList[i].getLength());
                //Now we check that it is not more than the max:
                if(pheromonTable[antList[i].getLocation().x][antList[i].getLocation().y]>this.maxPheromon)
                    pheromonTable[antList[i].getLocation().x][antList[i].getLocation().y]=this.maxPheromon;
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
    
    public double getPheromoneDrop(){
        return this.pheromonDrop;
    }

    public void printPheromnTable() {
        for(int x=000;x<100;x++){
            for(int y=000;y<100;y++){
                System.out.print(pheromonTable[x][y]);

            }
                            System.out.println("");
        }
    }
    
    public void setMinPheromon(double k){
        this.minPheromon = k;
    }
    
    public void setMaxPheromon(double k){
        this.maxPheromon = k;
    }
    
    public void editPheromone(double k){
        this.pheromonEvaporation = k;
    }

    int minValue = 10000000;
    Point[] myBestPath = null;
    public String getMinPath() {
        int value = minValue;
        Ant candidate = null;
        for(Ant a : antList){
            if (a.hasFoundFood()){
                if(value >= a.getLength()){
                    value = a.getLength();
                    candidate = a;
                }
                
            }
        }
        if(value < minValue) {
            //System.out.println(value);System.out.println(minValue);
            minValue = value;
            myBestPath = candidate.getMemory().clone();                  
            return Integer.toString(minValue);
        }
        return null;
    }

    public void setPheromoneDrop(double k) {
        this.pheromonDrop=k;
    }

    public Point[] getBestAnt() {
        return this.myBestPath;
    }

    public void setNbOfAnt(int k) {
        this.numberOfAnts = k;
    }
}
