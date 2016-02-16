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


public class Map {
    private Image mapImage = null;
    private Dimension mapDimension = null;
    private Point startPosition = null;
    private Point endPosition = null;
    
    //Default constructor, need to implement Map(MapReader(filepath));
    public Map(){
        mapDimension = new Dimension(500,500);
        endPosition = new Point(400,400);
        startPosition = new Point(30,30);
        mapImage = Toolkit.getDefaultToolkit().getImage("images/a.png");
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
}
