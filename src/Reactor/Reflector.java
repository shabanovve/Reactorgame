/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reactor;

import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author user
 */
public class Reflector {


    public void setX(int x) {
        x0=this.x;//сохраняем текущее значение
        if((x+width)<Startgame.widthscreen) this.x = x;//вылазиет ли рефлектор за экран
                else this.x=(Startgame.widthscreen-width);//если вылазиет то рефлектор перемещаем на край
        movement = this.x-x0;
      
    }

    public int getMovement() {
        return movement;
    }

    public int getHeight() {
        return height;
    }

    public int getStep() {
        return step;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x;
    private int x0;
    private int y;
    private static final int width = 120;
    private static final int height = 20;
    private static final int step = 10;
    private int movement = 0;
    private URL url;
    private ImageIcon concrete; //бетон


    public Reflector(int w, int h) {
        x = w / 2;
        y = h / 2;
        url = getClass().getResource("res/concrete.gif"); 
        concrete = new ImageIcon(url);

    }

 
    public void moveleft() {
        if (x > 0) {
            x = x - step;
            movement=-step;
        }
    }

    public void moveright() {
        if (x < Startgame.widthscreen - width) {
            x = x + step;
            movement=step;
        }
    }
 
    
    public void render(Graphics graphics) {
        graphics.setColor(Color.green);
        graphics.fillRect(x, y, width, height);
        
        
        graphics.setColor(Color.BLACK);
        graphics.drawLine(x, y, x + width, y);        
        graphics.drawLine(x, y + height, x + width, y + height);
        graphics.drawImage(concrete.getImage(), x, y, new Color(255, 255, 255, 0), null);
        
        
    }
}
