/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reactor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class Tvel extends JPanel{

    private BufferedImage img;
    private Graphics g;
    private static final int maxx=Startgame.widthscreen;
    private static final int maxy=70;
    



    public Tvel() {

        this.setPreferredSize(new Dimension(maxx,70));
        URL url = getClass().getResource("res/tvel.jpg");
        img = new BufferedImage(maxx, 70, BufferedImage.TYPE_INT_ARGB);
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            System.out.println("Image could not be read");
            System.exit(1);
        }
        g = img.createGraphics();

    }
    
    @Override
    public void paint(Graphics g){
        if (g!=null) g.drawImage(img, 0, 0, this);
    }
    
    
}
