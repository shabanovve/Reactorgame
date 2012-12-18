package Reactor;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;


public class Startgame extends JApplet {
    
   protected static final int widthscreen = 300; //ширина окна
   protected static final int heightscreen = 500; //высота окна
   
   static Demo demo;
   public PowerScale pwrScl = new PowerScale();    
   public Tvel tvel = new Tvel();
   static Gamemenu gmenu;
   public static boolean gameover = false;
   private Finish finish;
   private DescriptGame descr;
   private int energy = 0;//для отображения результата
   private int choice;
   private Container pane;
   
   
        
    private void addbind() {
        JRootPane rp = getRootPane() ;
        Action pressf2 = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("F2 pressed\n");
            }
        };
        rp.getInputMap().put(KeyStroke.getKeyStroke("F2"), "pressf2");
        rp.getActionMap().put("pressf2", pressf2);
    }
   
   public void go(){
        Container pane = getContentPane();
        pane.removeAll();
                
        pwrScl = new PowerScale();
        tvel = new Tvel();
        
        pane.add("Center",demo = new Demo(pwrScl,tvel));
        addbind();
        pane.add("North", pwrScl);
        pane.add("South", tvel);
        demo.start();
    }
    
    public void stage_gmenu() {
        gmenu = new Gamemenu();
        getContentPane().removeAll();
        getContentPane().add("Center", gmenu);
        gmenu.start();
        choice=0;
        while (choice == 0) {
            choice=gmenu.getButtonpressed();
        }
        gmenu.stop();
        gmenu = null;
    }

    public void stage_game() {
        go();
        revalidate();
        getContentPane().update(getGraphics());
        while (!demo.isGameover()) {
        }
    }
    
    
    public void stage_descr() {
        pane = getContentPane();
        pane.removeAll();
        
        descr = new DescriptGame();
        pane.add(descr);
        descr.start();
        revalidate();
        getContentPane().update(getGraphics());
        while (!descr.isFinished()) {
        }
        descr.stop();
        descr = null;
    }
    
    public void stage_final() {
        energy = pwrScl.getEnergy();
        finish = new Finish(energy);
        getContentPane().removeAll();
        revalidate();
        getContentPane().add("Center", finish);
        finish.start();
        getContentPane().update(getGraphics());
        while (finish.getButtonpressed() == 0) {
        }
    }
    
    @Override
    public void init() {
    }

    @Override
    public void start() {

        while (true) {
            stage_gmenu();
            switch (choice){
                case 1:{
                    stage_game();
                    stage_final();
                    break;
                }                    
                case 2:{
                    stage_descr();
                    break;
                }                    
            }
        }
    }

    @Override
    public void stop() {
        demo.stop();
        System.exit(0);
    }
    
    public static void main(String argv[]) {

        final Startgame reactor = new Startgame();
        
        reactor.init();
        Frame f = new Frame("Reactor game");
        f.setResizable(false);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {System.exit(0);}
            @Override
            public void windowDeiconified(WindowEvent e) { reactor.start(); }
            @Override
            public void windowIconified(WindowEvent e) { reactor.stop(); }
        });


        //игра
        Dimension d = new Dimension(widthscreen, heightscreen + 135);        
        while(true){
            gameover=false;
            gmenu = new Gamemenu();
            f.add(gmenu);
            f.pack();
            
            
            f.setSize(d);
            f.setVisible(true);

            while (gmenu.getButtonpressed() == 0) {
            }
            f.removeAll();
            
            reactor.go();
            f.add(reactor);
            f.pack();
            f.setSize(d);
            f.setVisible(true);
            while(!demo.isGameover()){}
            demo.stop();
            demo=null;
            f.removeAll();
        }
    }
}
