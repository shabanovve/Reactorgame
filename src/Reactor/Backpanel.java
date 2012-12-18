package Reactor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Backpanel extends JPanel {

    private static final int maxx=Startgame.widthscreen;
    private static final int maxy=Startgame.heightscreen+135;
    private Graphics g;
    private BufferedImage img, logoimg, water;
    private Water wtr = new Water();
    protected final static boolean nologo=true;
    protected boolean logo=true;
    
    private void creatgraph() {
        this.setPreferredSize(new Dimension(maxx, maxy));
        img = new BufferedImage(maxx, maxy, BufferedImage.TYPE_INT_ARGB);
        g = img.createGraphics();
    }

    @Override
    public void paint(Graphics gr) {
        wtr.render(g);
//        g.drawImage(water, 0, 0, null);
        if (logo) {
            int x = (int) (maxx - logoimg.getWidth()) / 2;
            g.drawImage(logoimg, x, 50, null);
        }
        gr.drawImage(img, 0, 0, null);
    }

    private void getlogo() {
        URL url = getClass().getResource("res/logo.png");
        try {
            logoimg = ImageIO.read(url);
        } catch (IOException e) {
            System.out.println("Image could not be read");
            System.exit(1);
        }
    }

    private void getbkgrnd() {
        URL url = getClass().getResource("res/water1.jpg");
        try {
            water = ImageIO.read(url);
        } catch (IOException e) {
            System.out.println("Image could not be read");
            System.exit(1);
        }
    }

    public Backpanel() {
        creatgraph();
        getlogo();
        getbkgrnd();
    }
    
    public Backpanel(boolean nologo) {
        creatgraph();
        getbkgrnd();
        logo = false;
    }
    
}
