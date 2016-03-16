/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Dimension;
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
    private int initAnt;
    private Point location;

    public Ant(Point startingPoint, int antLifetime) {
        this.foodFound = false;
        this.location = new Point(startingPoint.x, startingPoint.y);
        this.antLifeTime = antLifetime;
        this.initAnt = antLifeTime;
        this.memoryPos = 0;
        this.memory = new Point[antLifeTime + 1];
    }

    public void move(Point location, Rectangle[] obstacles, double[][] pheromonTable, Dimension d) {
        //System.out.println(antLifeTime);
        //if(foodFound)System.out.println("foud");
        //if on the border we go back
        if (foodFound || antLifeTime == 0 || (this.location.x <= 1) || this.location.y <= 1 || this.location.x >= d.width - 1 || this.location.y >= d.height - 1) {
            moveFromMemory();
        } else {
            this.antLifeTime--;
            moveTo(location, obstacles, pheromonTable, d);
        }
    }

    private void moveFromMemory() {
        this.memoryPos--;
        if (this.memoryPos < 1) {
            foodFound = false;
            this.antLifeTime = initAnt;
        }

        this.location = this.memory[memoryPos];
    }

    //careful with borders
    private void moveTo(Point location, Rectangle[] obstacles, double[][] pheromonTable, Dimension d) {

        //System.out.println(this.location);
        int xDiff = location.x - this.location.x;
        int yDiff = location.y - this.location.y;

        double[] attractiveness = new double[4];
        
        double pheromonSum = 0;
        pheromonSum += pheromonTable[this.location.x + 1][this.location.y]==0?0:pheromonTable[this.location.x + 1][this.location.y];
        pheromonSum += pheromonTable[this.location.x - 1][this.location.y]==0?0:pheromonTable[this.location.x - 1][this.location.y];
        pheromonSum += pheromonTable[this.location.x][this.location.y + 1]==0?0:pheromonTable[this.location.x][this.location.y + 1];
        pheromonSum += pheromonTable[this.location.x][this.location.y - 1]==0?1:pheromonTable[this.location.x][this.location.y - 1];
        
        
        //pheromon attractiveness
        attractiveness[0] = pheromonTable[this.location.x - 1][this.location.y]/pheromonSum;
        attractiveness[1] = pheromonTable[this.location.x + 1][this.location.y]/pheromonSum;
        attractiveness[2] = pheromonTable[this.location.x][this.location.y - 1]/pheromonSum;
        attractiveness[3] = pheromonTable[this.location.x][this.location.y + 1]/pheromonSum;
         /*
        attractiveness[0] = pheromonTable[this.location.x - 1][this.location.y];
        attractiveness[1] = pheromonTable[this.location.x + 1][this.location.y];
        attractiveness[2] = pheromonTable[this.location.x][this.location.y - 1];
        attractiveness[3] = pheromonTable[this.location.x][this.location.y + 1];
*/
        //direction attractiveness
        if(xDiff >= 0) attractiveness[1] += 0.000004+0.00009*xDiff/d.width;
        if(xDiff <= 0) attractiveness[0] += 0.000004+0.00009*Math.abs(xDiff)/d.width;
        if(yDiff >= 0) attractiveness[3] += 0.000004+0.00009*yDiff/d.height;
        if(yDiff <= 0) attractiveness[2] += 0.000004+0.00009*Math.abs(yDiff)/d.height;
        //System.out.println(attractiveness[0]+","+attractiveness[1]+","+attractiveness[2]+","+attractiveness[3]);
        //randomness
        attractiveness[0] += 0.0005;
        attractiveness[1] += 0.0005;
        attractiveness[2] += 0.0005;
        attractiveness[3] += 0.0005;
        

        //Non attractiveness of the previous pos :
        // 2 
        //0X1
        // 3
        if (memoryPos > 10) {
            for (int k = 2; k < 10; k++) {
                Point previousPos = memory[memoryPos - k];
                //System.out.println(this.location);
                //System.out.println(previousPos.x + " "+previousPos.y);
                if (this.location.x + 1 == previousPos.x) {
                    //System.out.println("Don't go right");
                    attractiveness[1] *= 0.5;
                    attractiveness[2] *= 0.7;
                    attractiveness[3] *= 0.7;
                } else if (this.location.x - 1 == previousPos.x) {
                    //System.out.println("Don't go left");
                    attractiveness[2] *= 0.7;
                    attractiveness[0] *= 0.5;
                    attractiveness[3] *= 0.7;
                } else if (this.location.y + 1 == previousPos.y) {
                    //System.out.println("Dont go down");
                    attractiveness[1] *= 0.7;
                    attractiveness[3] *= 0.5;
                    attractiveness[0] *= 0.7;
                } else if (this.location.y - 1 == previousPos.y) {
                    //System.out.println("Dont go up");
                    attractiveness[1] *= 0.7;
                    attractiveness[0] *= 0.7;
                    attractiveness[2] *= 0.5;
                }
            }
        }

        //obstacles non attractiveness
        for (Rectangle obst : obstacles) {
            if (this.location.x + 1 == obst.x && this.location.y > obst.y && this.location.y < obst.y + obst.height) {
                attractiveness[1] = 0;
            }
            if (this.location.x - 1 == obst.x + obst.width && this.location.y > obst.y && this.location.y < obst.y + obst.height) {
                attractiveness[0] = 0;
            }
            if (this.location.y + 1 == obst.y && this.location.x > obst.x && this.location.x < obst.x + obst.width) {
                attractiveness[3] = 0;
            }
            if (this.location.y - 1 == obst.y + obst.height && this.location.x > obst.x && this.location.x < obst.x + obst.width) {
                attractiveness[2] = 0;
            }
        }
        //System.out.println(attractiveness[0] + "," + attractiveness[1] + "," + attractiveness[2] + "," + attractiveness[3]);

        // 2 
        //0 1
        // 3
        //Choose a location
        switch (choose(attractiveness[0], attractiveness[1], attractiveness[2], attractiveness[3])) {
            case 1:
                this.location.x = this.location.x - 1;
                break;
            case 2:
                this.location.x = this.location.x + 1;
                break;
            case 3:
                this.location.y = this.location.y - 1;
                break;
            case 4:
                this.location.y = this.location.y + 1;
                break;
        }

        updateMemory();
        if ( Math.abs(xDiff) < 2 && Math.abs(yDiff) < 2) {
            //System.out.print("FoodFound!+");
            this.antLifeTime = this.initAnt;
            foodFound = true;
        }

    }

    //return 1, 2, 3 or 4
    private int choose(double p1, double p2, double p3, double p4) {
        double sum = p1 + p2 + p3 + p4;
        double random = Math.random() * sum;
        if (random > 0 && random < p1) {
            return 1;
        } else if (random > p1 && random < p1 + p2) {
            return 2;
        } else if (random > p1 + p2 && random < p1 + p2 + p3) {
            return 3;
        } else {
            return 4;
        }
    }

    private void updateMemory() {
        this.memory[memoryPos] = new Point(this.location);
        memoryPos++;
    }

    public void setLocation(Point p) {
        this.location = p;
    }

    public Point getLocation() {
        return this.location;
    }

    public boolean hasFoundFood() {
        return this.foodFound;
    }

}
