/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import model.Map;
import view.Window;
/**
 *
 * @author Kashy
 */
public class Controler {
    private Map map = null;
    private Window view = null;
    
    public Controler(){
        map = new Map();//Default
        view = new Window(this);
        
        view.initView();
    }
    
    public Map getMap(){
        return this.map;
    }
    //If we are running, do not authorize change of values.
}
