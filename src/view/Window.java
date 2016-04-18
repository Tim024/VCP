/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import main.Controler;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class Window extends JFrame {
  
    private MapRenderer mapRenderer = null;
    private Controler myControler = null;
    private UIRenderer uIRenderer = null;
    private JSplitPane contentPanel = null;
    
    public Window(Controler c) {
        

        
      
        myControler = c;
        mapRenderer = new MapRenderer(myControler);
        uIRenderer = new UIRenderer(myControler);
        
        
        //Create a split pane.
        contentPanel = new JSplitPane( JSplitPane.VERTICAL_SPLIT);
        contentPanel.setDividerLocation(500);
        contentPanel.add(mapRenderer);
        JFrame uiRendererWindow = new JFrame();
        uiRendererWindow.add(uIRenderer);
        uiRendererWindow.setSize(870,300);
        //contentPanel.add(uIRenderer);
        
        this.setSize(700, 700);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(contentPanel);
        this.setVisible(true);
        
        
        uiRendererWindow.setVisible(true);
    }

    public void updateView() {
        mapRenderer.updateImage(myControler.getMap().getMapImage());
        mapRenderer.updateEndPoint(myControler.getMap().getEndPoint());
        mapRenderer.updateMapDimension(myControler.getMap().getDimension());
        mapRenderer.updateStartingPoint(myControler.getMap().getStartingPoint());
        mapRenderer.updateObstacles(myControler.getMap().getObstacles());
        mapRenderer.repaint();
    }
    
    public void updateAnts(){
        mapRenderer.updateAnts(myControler.getAlgo().getAnts());
        mapRenderer.repaint();
    }
    
    public void updateText(String s){
        this.uIRenderer.textDisplay(s);
    }
    
    public int getNbAnts(){
        return uIRenderer.getNbAnts();
    }
}
