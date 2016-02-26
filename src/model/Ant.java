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
 * @author Tim van der Lee
 */
public class Ant {
    
    private boolean foodFound;
    
    private int memoryPos;
    private Point[] memory;
    
    private int antLifeTime;
    private Point location;

    public Ant(Point startingPoint,int antLifetime){
        this.foodFound = false;
        this.location = startingPoint;
        this.antLifeTime = antLifetime;
        this.memoryPos = 0;
        this.memory = new Point[antLifeTime];
    }
    
    public void move(Point location, Rectangle[] obstacles, double[][] pheromonTable){
        
        
        
        if(foodFound)
            moveFromMemory();
        else
        {
            this.antLifeTime --;
            moveTo(location, obstacles, pheromonTable);
        }
    }
    
    private void moveFromMemory(){
        this.memoryPos--;
        if(memoryPos == 0)
            foodFound = false;
        this.location = this.memory[memoryPos];
    }
    
    //careful with borders
    private void moveTo(Point location, Rectangle[] obstacles, double[][] pheromonTable){
        
        
        int xDiff = location.x - this.location.x;
        int yDiff = location.y - this.location.y;
        
        double[] attractiveness = new double[4];
        
        double pheromonSum = pheromonTable[this.location.x + 1][this.location.y];
        pheromonSum += pheromonTable[this.location.x - 1][this.location.y];
        pheromonSum += pheromonTable[this.location.x][this.location.y + 1];
        pheromonSum += pheromonTable[this.location.x][this.location.y - 1];
        
        //pheromon attractiveness
        attractiveness[0] = pheromonTable[this.location.x - 1][this.location.y]/pheromonSum;
        attractiveness[1] = pheromonTable[this.location.x + 1][this.location.y]/pheromonSum;
        attractiveness[2] = pheromonTable[this.location.x][this.location.y - 1]/pheromonSum;
        attractiveness[3] = pheromonTable[this.location.x][this.location.y + 1]/pheromonSum;
        
        //direction attractiveness
        if(xDiff > 0) attractiveness[1] += 0.2;
        if(xDiff < 0) attractiveness[0] += 0.2;
        if(yDiff > 0) attractiveness[3] += 0.2;
        if(yDiff < 0) attractiveness[2] += 0.2;
        
        //obstacles non attractiveness
        for (Rectangle obst : obstacles){
            if(this.location.x + 1 >= obst.x)
                attractiveness[1] = 0;
            if(this.location.x - 1 <= obst.x+obst.width)
                attractiveness[0] = 0;
            if(this.location.y + 1 >= obst.y)
                attractiveness[3] = 0;
            if(this.location.y - 1 <= obst.y+obst.height)
                attractiveness[2] = 0;
        }
        
        switch (choose(attractiveness[0],attractiveness[1],attractiveness[2],attractiveness[3])) {
            case 1:  this.location.x = this.location.x - 1;
                break;
            case 2:  this.location.x = this.location.x + 1;
                break;
            case 3:  this.location.y = this.location.y - 1;
                break;
            case 4:  this.location.y = this.location.y + 1;
                break;
        }
        
        updateMemory();
        if(this.location == location)
            foodFound = true;
        
    }
    
    //return 1, 2, 3 or 4
    private int choose(double p1, double p2, double p3, double p4){
        double sum = p1+p2+p3+p4;
        double random = Math.random()*sum;
        if(random > 0 && random < p1)
            return 1;
        else if(random > p1 && random < p2)
            return 2;
        else if(random > p2 && random < p3)
            return 3;
        else
            return 4;
    }
    
    private void updateMemory(){
        memoryPos ++;
        this.memory[memoryPos] = this.location;
    }
    
    public void setLocation(Point p){
        this.location = p;
    }
    
    public Point getLocation(){
        return this.location;
    }
    
    public boolean isAlive(){
        return this.antLifeTime>0;
    }
    
}
