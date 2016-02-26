/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Tim
 */
public class AntAlgorithm {
    
    private int numberOfAnts; //number of Ants
    private int antLifetime; //number Of Iteration before they die
    private double pheromonStrength; //pheromon they drop
    private double pheromonEvaporation; //amount of pheromon they loose
    
    private Ant[] antList = null;
    
    private Map map = null;
    
    private double[][] pheromonTable;
    
    public AntAlgorithm(Map map){
        this.numberOfAnts = 5;
        this.antLifetime = 100;
        this.pheromonStrength = 5;
        this.pheromonEvaporation = 0.2;
        this.map = map;
        
        this.pheromonTable = new double[map.getDimension().width][map.getDimension().height];
        for(int x=0;x<map.getDimension().width;x++){
            for(int y=0;y<map.getDimension().height;y++){
                pheromonTable[x][y] = 0.1;
            }
        }
        antList = new Ant[numberOfAnts];
        for(int i = 0; i < numberOfAnts; i ++){
            antList[i] = new Ant(map.getStartingPoint(), antLifetime);
        }
    }
    
    
    /*private void updatePheromon(){
        //pheromon drop
        for(int i = 0; i < numberOfAnts; i ++){
            pheromonTable[antList[i].getLocation().x][antList[i].getLocation().y] += pheromonStrength;
        }
        //and evaporation
        for(int x=0;x<map.getDimension().width;x++){
            for(int y=0;y<map.getDimension().height;y++){
                pheromonTable[x][y] = pheromonTable[x][y] < 0 ? 0 : (pheromonTable[x][y] * pheromonEvaporation);
            }
        }
    }*/
    
    public void computeNextState(){
        
    }
    
}
