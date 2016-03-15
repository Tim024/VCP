/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import model.AntAlgorithm;
import model.Ant;
import model.Map;
import view.Window;

/**
 *
 * @author Kashy
 */
public class Controler {

    private Map map = null;
    private Window view = null;
    private AntAlgorithm algo = null;
    private int speed = 1;

    public boolean startingPointToBePlaced = false;
    public boolean endingPointToBePlaced = false;
    public boolean obstacleToBePlaced = false;
    private boolean running = false;

    private JFileChooser fc = new JFileChooser();

    public Controler() {
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setCurrentDirectory(workingDirectory);

        this.speed = 1;
        map = new Map();//Default
        view = new Window(this);

        algo = new AntAlgorithm(map);

        view.updateView();
    }

    public void stopRun() {
        this.running = false;
    }

    public void run() {
        this.running = true;
        new Thread() {
            public void run() {

                while (running) {
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    algo.moveStep(speed, map.getEndPoint(), map.getObstacles());
                    view.updateAnts();
                }
                //algo.printPheromnTable();

            }
        }.start();
    }

    public Map getMap() {//should not be here
        return this.map;
    }
    //If we are running, do not authorize change of values.

    public String saveConfiguration() throws IOException {

        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            String fname = file.getAbsolutePath();
            if (!fname.endsWith(".map")) {
                file = new File(fname + ".map");
            }
            //Now we ok
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file)))) {
                writer.write("//Map Dimension (w,h)\n");
                writer.write(Integer.toString(map.getDimension().width) + "\n");
                writer.write(Integer.toString(map.getDimension().height) + "\n");
                writer.write("//Map Starting Point (x,y)\n");
                writer.write(Integer.toString(map.getStartingPoint().x) + "\n");
                writer.write(Integer.toString(map.getStartingPoint().y) + "\n");
                writer.write("//Map EndingPoint (x,y)\n");
                writer.write(Integer.toString(map.getEndPoint().x) + "\n");
                writer.write(Integer.toString(map.getEndPoint().y) + "\n");
                for (int i = 0; i < map.getObstacles().length; i++) {
                    writer.write("//Obstacle (x,y,w,h)\n");
                    writer.write(Integer.toString(map.getObstacle(i).x) + "\n");
                    writer.write(Integer.toString(map.getObstacle(i).y) + "\n");
                    writer.write(Integer.toString(map.getObstacle(i).width) + "\n");
                    writer.write(Integer.toString(map.getObstacle(i).height) + "\n");
                }
            }

            return "Saving: " + file.getName() + "." + "\n";
        } else {
            return "Save command cancelled by user." + "\n";
        }
    }

    public String loadConfiguration() throws IOException {
        if (running)
            return "Stop simulation first. \n";
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            String fname = file.getAbsolutePath();
            if (!fname.endsWith(".map")) {
                return "You need to open a .map file. \n";
            }

            map.removeObstacles();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = "";
                int lineCounter = 0;
                int buffer[] = new int[4];

                while ((line = br.readLine()) != null) {
                    if (line.startsWith("//")) {
                        continue;
                    }
                    lineCounter++;
                    switch (lineCounter) {
                        case 1:
                            buffer[0] = Integer.parseInt(line);
                            break;
                        case 2:
                            buffer[1] = Integer.parseInt(line);
                            map.setDimension(buffer[0], buffer[1]);
                            //System.out.println("loading: " + buffer[0] + " " + buffer[1]);
                            break;
                        case 3:
                            buffer[0] = Integer.parseInt(line);
                            break;
                        case 4:
                            buffer[1] = Integer.parseInt(line);
                            map.setStartingPoint(buffer[0], buffer[1]);
                            //System.out.println("loading: " + buffer[0] + " " + buffer[1]);
                            break;
                        case 5:
                            buffer[0] = Integer.parseInt(line);
                            break;
                        case 6:
                            buffer[1] = Integer.parseInt(line);
                            map.setEndingPoint(buffer[0], buffer[1]);
                            //System.out.println("loading: " + buffer[0] + " " + buffer[1]);
                            break;
                        default:
                            switch (lineCounter % 4) {
                                case 3:
                                    buffer[0] = Integer.parseInt(line);
                                    break;
                                case 0:
                                    buffer[1] = Integer.parseInt(line);
                                    break;
                                case 1:
                                    buffer[2] = Integer.parseInt(line);
                                    break;
                                case 2:
                                    buffer[3] = Integer.parseInt(line);
                                    map.placeObstacle(buffer[0], buffer[1], buffer[2], buffer[3]);
                                    //System.out.println("loading: " + buffer[0] + " " + buffer[1] + " " + buffer[2] + " " + buffer[3]);
                                    break;
                            }
                            break;
                    }
                }

            }
            algo = new AntAlgorithm(map);
            view.updateView();
            return "Opening: " + file.getName() + "." + "\n";
        } else {
            return "Open command cancelled by user." + "\n";
        }
    }

    public String setStartingPoint(int x, int y) {
        if (!running) {
            map.setStartingPoint(x, y);
            algo = new AntAlgorithm(map);
            view.updateView();
            return "Starting point added : (" + x + "," + y + ").";
        } else {
            return "Algorithm is running !";
        }
    }

    public String setEndingPoint(int x, int y) {
        if (!running) {
            map.setEndingPoint(x, y);
            algo = new AntAlgorithm(map);
            view.updateView();
            return "Goal added : (" + x + "," + y + ").";
        } else {
            return "Algorithm is running !";
        }
    }

    public String setObstacle(int x, int y, int z, int t) {
        if (!running) {
            map.placeObstacle(x, y, z, t);
            algo = new AntAlgorithm(map);
            view.updateView();
            return "Obstacle added : (" + x + "," + y + "," + z + "," + t + ").";
        } else {
            return "Algorithm is running !";
        }
    }

    public AntAlgorithm getAlgo() {
        return this.algo;
    }

    public String loadIMG() throws IOException {
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            BufferedImage img = ImageIO.read(file);
            map.setImage(img);
            view.updateView();
            return "Image successfully loaded. \n";
        } else {
            return "Image loading cancelled by user. \n";
        }
    }

}
