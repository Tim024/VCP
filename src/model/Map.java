/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Map {
    private Image mapImage = null; //background image
    private Dimension mapDimension = null; //dimension of the map
    private Point startPosition = null; //one point
    private Point endPosition = null; //one point, maybe implement more later
    private Rectangle[] obstacles = null; //list of rectangles
    
    public Map(){
        this.mapDimension = new Dimension(500,500);
        this.endPosition = new Point(400,400);
        this.startPosition = new Point(30,30);
        this.mapImage = Toolkit.getDefaultToolkit().getImage("maps/default.jpg");
        this.obstacles = new Rectangle[1];
        this.obstacles[0] = new Rectangle(89,34,100,100);
    }
    
    public void setImage(BufferedImage i){
        this.mapImage = i;
    }
    public void removeObstacles(){
        this.obstacles = new Rectangle[1];
        this.obstacles[0] = new Rectangle(0,0,0,0);
    }
    public Dimension getDimension(){
        return this.mapDimension;
    }
    public Point getStartingPoint(){
        return this.startPosition;
    }
    public Point getEndPoint(){
        return this.endPosition;
    }
    public Image getMapImage(){
        return this.mapImage;
    }
    public Rectangle[] getObstacles(){
        return this.obstacles;
    }
    public Rectangle getObstacle(int i){
        return this.obstacles[i];
    }
    public void setStartingPoint(int x, int y){
        this.startPosition.x = x;
        this.startPosition.y = y;
    }

    public void setEndingPoint(int x, int y) {
        this.endPosition.x = x;
        this.endPosition.y = y;
    }
    
    public void setDimension(int w, int h){
        this.mapDimension = new Dimension(w,h);
    }
    
    public void placeObstacle(int x, int y, int width, int height){
        Rectangle[] newObstacles = new Rectangle[obstacles.length + 1];
        for(int i = 0; i < obstacles.length; i++)
        {
            newObstacles[i]=obstacles[i];
        }
        newObstacles[obstacles.length] = new Rectangle(x,y,width,height);
        this.obstacles = newObstacles;
       
    }
}
