package Reactor;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.Action;
import javax.swing.JPanel;

    /**
     * The Demo class performs the animation and the painting.
     */
    public class Demo extends JPanel implements Runnable{
        private Thread thread;        
        private BufferedImage bimg;
        final static int widthscreen=Startgame.widthscreen;
        final static int heightscreen=Startgame.heightscreen;    


        private PowerScale pwrScl;
        private Tvel tvel;
        private Reflector refl = new Reflector(widthscreen,heightscreen);
        private Neutrons neutrons = new Neutrons();
        private Water wtr = new Water();
        private Rods rods = new Rods();
        private boolean gameover = false;
        private boolean overload = false;

        
        //  solid line stoke
//        protected BasicStroke solid = new BasicStroke(10.0f,
//                            BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND);
        //  dashed line stroke
//        protected BasicStroke dashed = new BasicStroke(10.0f,
//           BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10, new float[] {5}, 0);
        

        public int amount_ntr=0;
               
                

        // indicates whether or not to fill shape
        protected boolean doFill = true;

        // indicates whether or not to draw shape
        protected boolean doDraw = true;
        protected GradientPaint gradient;
//        protected BasicStroke stroke;

        HashMap<Object, Action> actions;

        
        public Demo(PowerScale pwrSlc0, Tvel tvel0) {
            
            this.pwrScl = pwrSlc0;
            this.tvel = tvel0;


            setFocusable(true);
            
            
//            addbind();
//            addKeyListener(new KeyAdapter() {
//
//            @Override
//                public void keyPressed(KeyEvent e) {
//                    keys(e);
//                }
//            });
            
            addMouseMotionListener(new MouseAdapter() {

            @Override
                public void mouseMoved(MouseEvent me) {
                    mouse(me);
                }
            });
        }

        public boolean isGameover() {
            return gameover;
        }
        
        public void mouse(MouseEvent me){
            int x = me.getX();
            refl.setX(x);

        }        
        
        public void keys(KeyEvent e) { //обрабатывает нажатие клавиш            

            int k = e.getKeyCode();
            switch (k) {
                case KeyEvent.VK_RIGHT:
                    refl.moveright();
                    break;
                case KeyEvent.VK_LEFT:
                    refl.moveleft();
                    break;
                case KeyEvent.VK_F12:
                    refl.moveleft();
                    break;
            }
        }

        // calls animate for every point in animpts
        public void step(int w, int h) {
            if (overload) {
                rods.down();
                neutrons.neutron_control(refl,rods);
                if(rods.isDropted()) gameover=true;
            } else {
                neutrons.neutron_control(refl,rods);

                //увеличение мощности
                int absorb = neutrons.getAbsorbed_neutrons();
                if (absorb > 0) {
                    pwrScl.increase(absorb);
                }

                //затухание мощности
                if (pwrScl.getPower() >= PowerScale.maxpower) {
//                if (pwrScl.getPower() >= 12) {
                    overload = true;
                }
                pwrScl.attenuation();
                pwrScl.setEnergy();
            }
        }
        

    @Override
        public void paint(Graphics g) {
            Dimension d = getSize(); //здесь определяется высота и ширина экрана
            step(widthscreen, heightscreen);
            Graphics2D g2 = createGraphics2D(widthscreen, heightscreen);
            if (overload) {
                droprods(widthscreen, heightscreen, g2);
            }
            if (!overload) {
                drawDemo(widthscreen, heightscreen, g2);
            }
            g2.dispose();
            if (bimg != null) {
                g.drawImage(bimg, 0, 0, this);
            }
        }
        
        private void droprods(int w, int h, Graphics2D g2){
            Graphics graphics=bimg.getGraphics();

            pwrScl.paint(pwrScl.getGraphics());
            tvel.paint(tvel.getGraphics());
            wtr.render(graphics);//рисуем воду с эффектом Вавилова-Черенкова
            neutrons.showntrs(graphics);//рисуем поток нейтронов
            rods.render(graphics);
        }

        // sets the points of the path and draws and fills the path
        public void drawDemo(int w, int h, Graphics2D g2) {
            Graphics graphics=bimg.getGraphics();
            
            pwrScl.paint(pwrScl.getGraphics());
            tvel.paint(tvel.getGraphics());
            wtr.render(graphics);//рисуем воду с эффектом Вавилова-Черенкова
            refl.render(graphics);// рисуем отражатель
            neutrons.showntrs(graphics);//рисуем поток нейтронов
        }


        public Graphics2D createGraphics2D(int w, int h) {
            Graphics2D g2;
            if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h) {
                bimg = (BufferedImage) createImage(w, h);
            }
            g2 = bimg.createGraphics();
            g2.setBackground(getBackground());
            g2.clearRect(0, 0, w, h);
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            return g2;
        }


        public void start() {
            thread = new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        }



        public synchronized void stop() {
            thread = null;
        }


    @Override
        public void run() {
            Thread me = Thread.currentThread();
            while (thread == me) {
                repaint();
                try {
                    Thread.sleep(10);
                } catch (Exception e) { break; }
            }
            thread = null;
        }


    } // End Demo class