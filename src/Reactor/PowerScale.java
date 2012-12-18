/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reactor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class PowerScale extends JPanel{
    
    private int power=0;
    private int energy=0;
    private Graphics g;
    private BufferedImage img;
    private static final int maxx=Startgame.widthscreen;
    protected static final int maxy=40;
    private static final int powerPearNeutron=50;
    private static final int maxpercetns=120;
    protected static final int maxpower=1200;
    
    public void setEnergy() {
        if (power>300) energy = energy+power;
    }

    public int getEnergy() {
        return energy;
    }

    public int getPower() {
        return power;
    }

    public void setpwrzero() {
        power = 0;
    }
    
    //затухание мощности
    public void attenuation(){
        if (power>0) power=power-1;
    }
    
    //затухание мощности
    public void increase(int absorbed_neutrons){
        
        power = power + absorbed_neutrons * powerPearNeutron;
        if (power > maxpower) power = maxpower;
        
    }


        
    private void creatgraph(){
        this.setPreferredSize(new Dimension(maxx,maxy));
//        setBackground(Color.gray);
        img = new BufferedImage(maxx, maxy, BufferedImage.TYPE_INT_ARGB);
        g =  img.createGraphics();
        g.setColor(Color.gray);
        g.fillRect(0, 0, maxx, maxy);

    }
    
    //определение уровня мощности
    private int stage(int pwr){
        int low=30;
        int midle=100;
        int overload=maxpercetns;
        int stg=0;

        if (pwr <= overload) {
            stg = 3;
        }
        if (pwr <= midle) {
            stg = 2;
        }
        if (pwr <= low) {
            stg = 1;
        }
        
        return stg;
    }
    
    private void drawscale(int x0, int y0){
        
        int pwr = (int) (power / 10);
        
        for (int x = 0; x < maxpercetns; x++) {

                int stg = stage(x);
                switch (stg){
                    case 1: g.setColor(Color.yellow);
                            break;
                    case 2: g.setColor(Color.green);
                            break;                        
                    case 3: g.setColor(Color.red);
                            break;                        
                }
            if (pwr < x) g.setColor(Color.black);
            
            g.drawLine(x + x0, y0, x + x0, y0 + 10);
        }
    }
    
    @Override
   public void paint(Graphics gr){
//        removeAll();
        g.setColor(Color.gray);
        g.fillRect(0, 0, maxx, maxy);
        
        drawscale(20, 20);


        g.setColor(Color.DARK_GRAY);
        g.drawRect(5, 15, 150, 20);
        g.drawRect(165, 15, 120, 20);

        String pwrstr= Integer.toString((int)(power/10));
        g.setColor(Color.green);
        g.drawString(pwrstr+"%", 90, 12);
        g.drawString("Power", 40, 12);                
        g.drawString("Energy", 175, 29);
        g.drawString(Integer.toString(energy), 220, 29);
        if (gr!=null) gr.drawImage(img, 0, 0, this);                                       

   }
    
    public PowerScale() {
        creatgraph();
    }
    
}
