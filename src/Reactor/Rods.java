/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reactor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author user
 */
public class Rods {

    private static final int width = 10;
    private int height = 0;
    private static final int step = 1;
    private static final int maxx=Startgame.widthscreen;
    private static final int maxy=Startgame.heightscreen;
    private boolean dropted = false;
    private Font font;
    private String[] txt = new String[3];

    public Rods() {
        font = new Font("Serif", Font.PLAIN, 28);
        txt[0]="Аварийная защита.";
        txt[1]="Сброс стержней";
        txt[2]="в активную зону";

    }

    public int getHeight() {
        return height;
    }
    
    
    
    public void down() {
        if (height < maxy) {
            height = height + step;
        } else {
            height = maxy;
            dropted = true;
        }

    }

    public boolean isDropted() {
        return dropted;
    }
 
    public void render(Graphics graphics) {
        graphics.setColor(Color.black);
        for (int i = 1; i < 11; i++) {
            graphics.fillRect(25*i, 0, width, height);
        }
        
        graphics.setColor(new Color(255, 255, 0, 100));
        graphics.fillRect(30, 100, 230, 120);
        
        
        graphics.setColor(Color.white);
        graphics.setFont(font);
        for (int i = 1; i < 4; i++) {
            graphics.drawString(txt[i-1], 33, 30*i+100);
        }
    }
}
