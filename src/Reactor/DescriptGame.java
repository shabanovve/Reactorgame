package Reactor;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class DescriptGame extends JPanel implements Runnable {

    private static final int maxx = Startgame.widthscreen;
    private static final int maxy = Startgame.heightscreen + 135;
    private Thread thread;
    private ImageIcon img;
    private URL url;
    private Graphics gr;
    private boolean finished = false;
    private static final String[] path = {
        "res/desc/1.png",
        "res/desc/2.png",
        "res/desc/3.png",
        "res/desc/4.png",
        "res/desc/5.png"};
    private int numpic=0;
    private static final int max_numpic=4;
        

    public DescriptGame() {
        this.setPreferredSize(new Dimension(maxx, maxy));
        gr = getGraphics();
        nextpic();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                nextpic();
            }
        });
    }
    
    private void nextpic(){
        if (numpic <= max_numpic) {
            url = getClass().getResource(path[numpic]);
            img = new ImageIcon(url);
        }else{
            finished=true;
        }
        numpic++;
    }

    public boolean isFinished() {
        return finished;
    }

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public synchronized void stop() {
        getGraphics().dispose();
        if (thread != null) {
            thread = null;
        }
    }

    @Override
    public void run() {
        Thread me = Thread.currentThread();
        revalidate();

        while (thread == me) {
            repaint();
        }
        thread = null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img.getImage(), 0, 0, this);
    }
}
