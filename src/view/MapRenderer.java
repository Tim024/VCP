/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package view;

import java.awt.Dimension;
import javax.swing.BorderFactory; 
import main.Controler;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

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
        
        //Set border
        TitledBorder title = BorderFactory.createTitledBorder("Ant Simulation Display");
        this.setBorder(title);
        
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
        
        this.addMouseListener(new MouseAdapter() {
                int x=0,y=0;
                
                @Override
                public void mousePressed(MouseEvent e) {
                    if(c.startingPointToBePlaced){
                        c.setStartingPoint((int) (e.getX()*((float) dim.width)/getWidth()), 
                                (int) (e.getY()*((float) dim.height)/getHeight()));
                        c.startingPointToBePlaced = false;
                    } else if(c.endingPointToBePlaced){
                        c.setEndingPoint((int) (e.getX()*((float) dim.width)/getWidth()), 
                                (int) (e.getY()*((float) dim.height)/getHeight()));
                        c.endingPointToBePlaced = false;
                    } else if(c.obstacleToBePlaced){
                        x = (int) (e.getX()*((float) dim.width)/getWidth());
                        y = (int) (e.getY()*((float) dim.height)/getHeight());
                    }
                }
                
                @Override
                public void mouseReleased(MouseEvent e){
                    if(c.obstacleToBePlaced){
                        c.setObstacle(x,y,(int) (e.getX()*((float) dim.width)/getWidth()) - x, (int) (e.getY()*((float) dim.height)/getHeight()) - y);
                        c.obstacleToBePlaced = false;
                    }
                }
            });
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
        this.obstacles = obstacles;
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
