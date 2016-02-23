/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package view;

import java.awt.Dimension;
import main.Controler;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author Kashy
 */

class MapRenderer extends JPanel {
    
    private Controler myControler = null;
    private Image bgImage = null;
    private Image antImage = null;
    private Image startImage = null;
    private Image endImage = null;
    private Point antList[] = null;
    private Point startPoint = null;
    private int startPointSize = 30;
    private int endPointSize = 30;
    private int antSize = 30;
    private Point endPoint = null;
    private Dimension dim = null;
    private Rectangle[] obstacles = null;
    
    MapRenderer(Controler c) {
        myControler = c;
        
        //Load all images
        MediaTracker mt = new MediaTracker(this);
        bgImage = c.getMap().getMapImage();
        antImage = Toolkit.getDefaultToolkit().getImage("images/ant.png");
        startImage = Toolkit.getDefaultToolkit().getImage("images/start.png");
        endImage = Toolkit.getDefaultToolkit().getImage("images/end.png");
        
        mt.addImage(bgImage, 0);
        mt.addImage(startImage, 1);
        mt.addImage(endImage, 2);
        mt.addImage(antImage, 3);
        try {
            mt.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void updateMapDimension(Dimension dim){
        this.dim = dim;
    }
    public void updateAnts(Point antList[]){
        this.antList = antList;
    }
    public void updateImage(Image newBgImage){
        this.bgImage = newBgImage;
    }
    
    public void updateStartingPoint(Point newStart){
        this.startPoint = newStart;
    }
    public void updateEndPoint(Point newEnd){
        this.endPoint = newEnd;
    }
    public void updateObstacles(Rectangle[] obstacles) {
        this.obstacles = obstacles; //To change body of generated methods, choose Tools | Templates.
    }
    
    //TODO, if image out of bounds.
    @Override
    protected void paintComponent(Graphics g) {
        //Paint all images
        super.paintComponent(g);
        //background
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
        //obstacles
        for(int i = 0; i < obstacles.length; i++)
        {
            g.fillRect((int) (obstacles[i].x*(getWidth()/(float) dim.width)), 
                    (int) (obstacles[i].y*(getHeight()/(float) dim.height)),
                    (int) (obstacles[i].width*(getWidth()/(float) dim.width)), 
                    (int) (obstacles[i].height*(getHeight()/(float) dim.height)));
        }
        
        //Start and end
        int xStart = (int) ((startPoint.x - startPointSize/2)*(getWidth()/(float) dim.width));
        int yStart = (int) ((startPoint.y - startPointSize/2)*(getHeight()/(float) dim.height));
        g.drawImage(startImage, xStart < 0 ? 0 : xStart ,
                                yStart < 0 ? 0 : yStart ,
                                startPointSize, startPointSize ,null);
        int xEnd = (int) ((endPoint.x - endPointSize/2)*(getWidth()/(float) dim.width));
        int yEnd = (int) ((endPoint.y - endPointSize/2)*(getHeight()/(float) dim.height));
        g.drawImage(endImage,xEnd < 0 ? 0 : xEnd ,
                             yEnd < 0 ? 0 : yEnd ,
                             endPointSize, endPointSize ,null);
        //ants
        //g.drawImage(antImage,250 ,34, 27, 30 ,null);
    }
}
