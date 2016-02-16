/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import main.Controler;
import javax.swing.JFrame;
import model.Map;

public class Window extends JFrame {
  
    private MapRenderer mapRenderer = null ;
    private Controler myControler = null;
    
    public Window(Controler c) {
      
        myControler = c;
        mapRenderer = new MapRenderer(myControler);
        
        this.setSize(500, 300);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mapRenderer);
        this.setVisible(true);
    }

    public void initView() {
        mapRenderer.updateImage(myControler.getMap().getMapImage());
        mapRenderer.updateEndPoint(myControler.getMap().getEndPoint());
        mapRenderer.updateMapDimension(myControler.getMap().getDimension());
        mapRenderer.updateStartingPoint(myControler.getMap().getStartingPoint());
    }
}


  /*private JPanel container = new JPanel();
   
  String[] tab_string = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "=", "C", "+", "-", "*", "/"};
  JButton[] tab_button = new JButton[tab_string.length];
   
  private JLabel ecran = new JLabel();
  private Dimension dim = new Dimension(50, 40);
  private Dimension dim2 = new Dimension(50, 31);
  private double chiffre1;
  private boolean clicOperateur = false, update = false;
  private String operateur = "";

  /*public Window(/*controler){                
    this.setSize(240, 260);
    this.setTitle("Calculette");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    init();                
    //this.controler = controler;                
    this.setContentPane(container);
    this.setVisible(true);
  }

  /*private void init(){
    Font police = new Font("Arial", Font.BOLD, 20);
    ecran = new JLabel("0");
    ecran.setFont(police);
    ecran.setHorizontalAlignment(JLabel.RIGHT);
    ecran.setPreferredSize(new Dimension(220, 20));

    JPanel operateur = new JPanel();        
    operateur.setPreferredSize(new Dimension(55, 225));
    JPanel chiffre = new JPanel();
    chiffre.setPreferredSize(new Dimension(165, 225));
    JPanel panEcran = new JPanel();
    panEcran.setPreferredSize(new Dimension(220, 30));
          
    panEcran.add(ecran);
    panEcran.setBorder(BorderFactory.createLineBorder(Color.black));
    container.add(panEcran, BorderLayout.NORTH);
    container.add(chiffre, BorderLayout.CENTER);
    container.add(operateur, BorderLayout.EAST);                
  }   

  /*Les listeners pour nos boutons
  class ChiffreListener implements ActionListener{ 
    public void actionPerformed(ActionEvent e) {
      //On affiche le chiffre en plus dans le label
      String str = ((JButton)e.getSource()).getText();
      if(!ecran.getText().equals("0"))
        str = ecran.getText() + str;

      controler.setNombre(((JButton)e.getSource()).getText());
    }                
  }

  class OperateurListener implements ActionListener{
    public void actionPerformed(ActionEvent e) {
      controler.setOperateur(((JButton)e.getSource()).getText());
    }           
  }
   
  class ResetListener implements ActionListener{
    public void actionPerformed(ActionEvent arg0) {
      controler.reset();
    }               
  }
   
  //Implémentation du pattern observer
  public void update(String str) {
    ecran.setText(str);
  }
*/
