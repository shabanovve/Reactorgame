package Reactor;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;




public class Water {

    public Water() {
        try {
            img = ImageIO.read(imageSrc);
        } catch (IOException e) {
            System.out.println("Image could not be read");
            System.exit(1);
        }
        changepic();
    }
    
    final static int maxx =  Startgame.widthscreen;
    final static int maxy =  Startgame.heightscreen+135;

    private BufferedImage canvas = new BufferedImage(maxx, maxy, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage water1 = new BufferedImage(maxx, maxy, BufferedImage.TYPE_INT_ARGB);
    private BufferedImage img= new BufferedImage(maxx, maxy, BufferedImage.TYPE_INT_ARGB);
    float alpha = 0.5f;
    float d_alpha = 0.1f;    
    float[] scales = { 1f, 1f, 1f, alpha };
    float[] offsets = new float[4];
    RescaleOp rop;
    private URL url = getClass().getResource("res/water2.jpg");
    private URL imageSrc = getClass().getResource("res/water1.jpg");
    static String imageFileName = "j:/del/120803/water1.jpg";
    ImageIcon water2 = new ImageIcon(url);    
    private int delay=0;
    Graphics g;
    
    private void definealpha(){
        if ((alpha<0.1)|(alpha>0.9)) d_alpha=-d_alpha;
        alpha = alpha + d_alpha;
        scales[3]=alpha;
        rop = new RescaleOp(scales, offsets, null);
    }
    
    private void changepic(){
        
        Graphics2D g2d = canvas.createGraphics();
        g2d.drawImage(water2.getImage(), 0, 0, new Color(255, 255, 255, 0), null);
        g = water1.createGraphics();
        g.drawImage(img, 0, 0, null);
        definealpha();
        g2d.drawImage(water1, rop, 0, 0);

    }
    
    public void render(Graphics reactgraph){
        delay++;
        if (delay>10){
            changepic();
            delay=0;
        }
        reactgraph.drawImage(canvas, 0, 0, null);
    }
}
