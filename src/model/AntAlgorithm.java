/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Tim
 */
public class AntAlgorithm {
    
    private int numberOfAnts; //number of Ants
    private int antLifetime; //number Of Iteration before they die
    private double pheromonDrop; //pheromon they drop
    private double pheromonEvaporation; //amount of pheromon they loose
    
    private Ant[] antList = null;
    
    private Map map = null;
    
    private double[][] pheromonTable;
    
    public AntAlgorithm(Map map){
        this.numberOfAnts = 10;
        this.antLifetime = 20000;
        this.pheromonDrop = 5000;
        this.pheromonEvaporation = 0.002;
        this.map = map;
        
        this.pheromonTable = new double[map.getDimension().width][map.getDimension().height];
        for(int x=0;x<map.getDimension().width;x++){
            for(int y=0;y<map.getDimension().height;y++){
                pheromonTable[x][y] = 0;
            }
        }
        antList = new Ant[numberOfAnts];
        for(int i = 0; i < numberOfAnts; i ++){
            antList[i] = new Ant(map.getStartingPoint(), antLifetime);
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
                pheromonTable[x][y] = pheromonTable[x][y] * pheromonEvaporation < 0.00000000000000000001 ? 0 : (pheromonTable[x][y] * pheromonEvaporation);
            }
        }
    }

    public Ant[] getAnts() {
        return this.antList;
    }

    public void printPheromnTable() {
        for(int x=000;x<200;x++){
            for(int y=000;y<200;y++){
                System.out.print(pheromonTable[x][y]);

            }
                            System.out.println("");
        }
    }
}
