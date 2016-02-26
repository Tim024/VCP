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
    
    private int antLifeTime;
    private Point location;

    public Ant(Point startingPoint,int antLifetime){
        this.location = startingPoint;
        this.antLifeTime = antLifetime;
    }
    
    //careful with borders
    public void moveTo(Point location, Rectangle[] obstacles, double[][] pheromonTable){
        
        int xDiff = location.x - this.location.x;
        int yDiff = location.y - this.location.y;
        
        double pheromonSum = 0;
        for(int x=-1;x<=1;x++){
            for(int y=-1;y<=1;y++){
                pheromonSum += pheromonTable[this.location.x + x][this.location.y + y];
            }
        }
        
    }
    
    public void setLocation(Point p){
        this.location = p;
    }
    
    public Point getLocation(){
        return this.location;
    }
    
}
