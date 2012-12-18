package Reactor;

import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class Gamemenu extends JPanel implements Runnable{
//    private static final int maxx=Startgame.widthscreen;
//    private static final int maxy=Startgame.heightscreen+135;
    private LayoutManager overlay = new OverlayLayout(this);
    private JPanel glasspanel;
    private Backpanel backpanel;
    private Mybutton[] btn = new Mybutton[3];
    private static final String[] path = {"res/go.png","res/description.png","res/go.png"};
    private int buttonpressed = 0;
    private Thread thread;
    private static final int amount_btn=2;
    

    public Gamemenu(){
        backpanel = new Backpanel();
        glasspanel = new JPanel();
        setLayout(overlay);
        add(glasspanel,null);        
        add(backpanel,null);        
        glasspanel.setLayout(new BoxLayout(glasspanel, BoxLayout.Y_AXIS));
        glasspanel.add(Box.createVerticalStrut(150));
        for (int i = 0; i < amount_btn; i++) {
            btn[i] = new Mybutton(path[i]);
            glasspanel.add(btn[i]);
            glasspanel.add(Box.createVerticalStrut(10));

        }        
        glasspanel.setBackground(new Color(255,0,0,0));
    }
    
    
    public int getButtonpressed() {
        for (int i=0;i<amount_btn;i++){
           if (btn[i].isPressed()){
               buttonpressed=i+1;
               break;
           } 
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
            if (thread!=null) thread = null;
        }


    @Override
        public void run() {
            Thread me = Thread.currentThread();
            revalidate();

            while (thread == me) {
//                repaint();
//                this.paint(getGraphics());
                
                repaint();                
//                try {
//                    thread.sleep(100);
//                } catch (Exception e) { break; }
            }
            thread = null;
        }
    
    

}
