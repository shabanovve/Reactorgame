package Reactor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class Finish extends JPanel implements Runnable {
    private static final int maxx=Startgame.widthscreen;
    private static final int maxy=Startgame.heightscreen+135;

    private LayoutManager overlay = new OverlayLayout(this);
    private JPanel glasspanel;
    private static final String path = "res/next.png";
    private Mybutton btn;
    private int buttonpressed = 0;
    private Thread thread;
    private BufferedImage bimg;
    private Backpanel backpanel;
    private Graphics g;
    private int energy;
    private int e=0;//energy count
    private int de=0;//delta energy count
    private MyLabel[] mylbl = new MyLabel[4] ;
    
    private String[] txt = new String[]{"1111","2222","3333","4444"};

    public Finish(int energy) {
        
        txt[0]="Количество";
        txt[1]="выработанной";
        txt[2]="электроэнергии";
        txt[3]="";
        this.energy = energy;
        de = (int) energy / 117;
        glasspanel = new JPanel();
        backpanel = new Backpanel(Backpanel.nologo);
        
        
        setLayout(overlay);
        add(glasspanel, null);
        add(backpanel, null);
        
        glasspanel.setLayout(new BoxLayout(glasspanel, BoxLayout.Y_AXIS));
        
        for (int i = 0; i < 3; i++) {
            mylbl[i] = new MyLabel(txt[i], Color.orange);
            glasspanel.add(mylbl[i]);
            glasspanel.add(Box.createVerticalStrut(10));
        }
        mylbl[3] = new MyLabel(txt[3], Color.white);
        glasspanel.add(mylbl[3]);
        glasspanel.add(Box.createVerticalStrut(10));

        glasspanel.add(Box.createVerticalStrut(50));
        btn = new Mybutton(path);
        glasspanel.add(btn);
        

        glasspanel.setBackground(new Color(255, 0, 0, 0));
                
        bimg = new BufferedImage(maxx, maxy, BufferedImage.TYPE_INT_ARGB);
        g = bimg.createGraphics();

    }
    
    @Override
    public void paint(Graphics gr){
        super.paint(gr);
        if (e < energy) {
            e=e+de;
            mylbl[3].setText(Integer.toString(e));
        } else {
            mylbl[3].setText(Integer.toString(energy));
        }
    }
    

    public int getButtonpressed() {
        if (btn.isPressed()) {
            buttonpressed = 1;
        }
        return buttonpressed;
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
            try {
                Thread.sleep(10);
            } catch (Exception ex) {
                break;
            }
        }
        thread = null;
    }
}
