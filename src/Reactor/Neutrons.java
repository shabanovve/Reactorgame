package Reactor;

import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;
import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author user
 */
public class Neutrons {



    protected void addone(int i){
        if (amount_ntr < (maxntr-2)) {
            ntr[amount_ntr] = new Neutron();
           
            ntr[amount_ntr].x=ntr[i].x;
            ntr[amount_ntr].y=ntr[i].y;            
            ntr[amount_ntr].dx=-ntr[i].dx;
            
            if (Math.abs(ntr[i].dy)<2) ntr[amount_ntr].dy = -2;
            if (Math.abs(ntr[i].dy)>1) ntr[amount_ntr].dy = -1;
            
            //смещаем нейтрон
            ntr[amount_ntr].y = ntr[amount_ntr].y + ntr[amount_ntr].dy;
            //нужно отвести новый нейтрон от ТВЭЛ, иначе появляются двойные, тройные и т.д. гуппы
            
            if(ntr[amount_ntr].y>(Startgame.heightscreen-diametr)){
                int j=0;
            }
            
            if((ntr[amount_ntr].dx*ntr[i].dx)>0){
                int s=1;
            }
            
            amount_ntr++;                                    
        }

    }

    
    protected void delone(int i){
        if (amount_ntr > 1) {
            absorbed_neutrons++;            
            ntr[i] = null;
            //если это нейтрон в конце массива, то затирать его нечем и незачем
            if (i < (maxntr - 1)) {
                for (int j = i; j < amount_ntr; j++) {
                    ntr[j] = ntr[j + 1];
                }
            }
            amount_ntr--;
        }
        ntr[i].setRodstouched(false);
    }
    
//class Neutron ========================================    
    private class Neutron {

        public Neutron() {
            int w = Startgame.widthscreen;
            int h = Startgame.heightscreen;

            x = (int) (Math.random() * (w - diametr));
            y = (int) (Math.random() * Startgame.heightscreen /3 + Startgame.heightscreen /2);

            x0 = x;
            y0 = y;

            dx = 1;
            dy = 1;

            tveltouched=false;
            rodstouched=false;
            
        }

        public boolean isTveltouched() {
            return tveltouched;
        }

        public void setTveltouched(boolean tveltouched) {
            this.tveltouched = tveltouched;
        }

        
        private int x;
        private int y;
        private int x0;
        private int y0;
        private int dx;
        private int dy;
        private int picnum; // номер картики, которую воспросизводим
        private boolean tveltouched=false;
        private boolean rodstouched=false;

        public boolean isRodstouched() {
            return rodstouched;
        }

        public void setRodstouched(boolean rodstouched) {
            this.rodstouched = rodstouched;
        }

        // прорисовка нейтронов
        public void render(Graphics graphics) {

            graphics.setColor(Color.red);
            graphics.drawOval(x, y, diametr, diametr); // рисует овал


            graphics.drawImage(orange.getImage(), x, y, new Color(255, 255, 255, 0), null);


            /*
             * //задержка для отладки try { Thread.sleep(10); }catch
             * (InterruptedException e){ }
             */
        }

        // определяет касание стенок реактора
        private boolean touchwall() {

            boolean b;
            int w = Startgame.widthscreen;
            int h = Startgame.heightscreen;

            b = ((x < 0) | (x > (w - diametr))) ? true : false;

            return b;

        }

        // определяет касание ТВЭЛ
        private boolean touchtvel() {

            boolean b = false;
            int w = Startgame.widthscreen;
            int h = Startgame.heightscreen;


            if (y > (h - diametr)){
                b = true;
            }
            return b;

        }

        // определяет касание стержней
        private boolean touchrods() {

            boolean b = false;
            int w = Startgame.widthscreen;
            int h = Startgame.heightscreen;


            if (y < getRodsH()){
                b=true;
                setRodstouched(true);
            }
            
            return b;

        }

        //проверка пересечения отрезков
        private boolean intersection(int ax1, int ay1, int ax2, int ay2,
                int bx1, int by1, int bx2, int by2) {

            boolean t;

            double v1 = (bx2 - bx1) * (ay1 - by1) - (by2 - by1) * (ax1 - bx1);
            double v2 = (bx2 - bx1) * (ay2 - by1) - (by2 - by1) * (ax2 - bx1);
            double v3 = (ax2 - ax1) * (by1 - ay1) - (ay2 - ay1) * (bx1 - ax1);
            double v4 = (ax2 - ax1) * (by2 - ay1) - (ay2 - ay1) * (bx2 - ax1);


            int[] x = new int[4];
            int[] y = new int[4];

            //упорядычеваем значение координат, выбирая min
            //первый отрезок
            if (ax1 < ax2) {
                x[0] = ax1;
                x[1] = ax2;
            } else {
                x[0] = ax2;
                x[1] = ax1;
            }

            if (ay1 < ay2) {
                y[0] = ay1;
                y[1] = ay2;
            } else {
                y[0] = ay2;
                y[1] = ay1;
            }

            //упорядычеваем значение координат, выбирая min
            //второй отрезок
            if (bx1 < bx2) {
                x[2] = bx1;
                x[3] = bx2;
            } else {
                x[2] = bx2;
                x[3] = bx1;
            }

            if (by1 < by2) {
                y[2] = by1;
                y[3] = by2;
            } else {
                y[2] = by2;
                y[3] = by1;
            }

            t = ((x[1] >= x[2]) && (x[3] >= x[0]) && (y[1] >= y[2]) && (y[3] >= y[0]));

            return (((v1 * v2) <= 0) && ((v3 * v4) <= 0) && t);
        }

        // определяет касание отражателя сверху или снизу (ud - up down)
        private boolean touchrefl_ud(int reflx, int refly,
                int reflw, int reflh) {
            //reflx, refly - x и y координаты рефлектора
            //reflw, reflh - ширина и высота координаты рефлектора

            boolean b = false;
            int w = Startgame.widthscreen;
            int h = Startgame.heightscreen;

            //проверка касания сверху методом пересечения отрезков
            if (intersection(x0, y0, x, y, reflx, refly, reflx + reflw, refly)) {
                b = true;
            }
            if (intersection(x0 + diametr, y0, x + diametr, y, reflx, refly, reflx + reflw, refly)) {
                b = true;
            }
            if (intersection(x0, y0 + diametr, x, y + diametr, reflx, refly, reflx + reflw, refly)) {
                b = true;
            }
            if (intersection(x0 + diametr, y0 + diametr, x + diametr, y + diametr, reflx, refly, reflx + reflw, refly)) {
                b = true;
            }

            //проверка касания снизу методом пересечения отрезков
            if (intersection(x0, y0, x, y, reflx, refly + reflh, reflx + reflw, refly + reflh)) {
                b = true;
            }
            if (intersection(x0 + diametr, y0, x + diametr, y, reflx, refly + reflh, reflx + reflw, refly + reflh)) {
                b = true;
            }
            if (intersection(x0, y0 + diametr, x, y + diametr, reflx, refly + reflh, reflx + reflw, refly + reflh)) {
                b = true;
            }
            if (intersection(x0 + diametr, y0 + diametr, x + diametr, y + diametr, reflx, refly + reflh, reflx + reflw, refly + reflh)) {
                b = true;
            }

            return b;

        }

        // определяет касание отражателя слева или справа (lr - left right)
        private boolean touchrefl_lr(int reflx, int refly,
                int reflw, int reflh) {
            //reflx, refly - x и y координаты рефлектора
            //reflw, reflh - ширина и высота координаты рефлектора

            boolean b = false;
            int w = Startgame.widthscreen;
            int h = Startgame.heightscreen;


            //проверка касания слева методом пересечения отрезков
            if (intersection(x0, y0, x, y, reflx, refly, reflx, refly + reflh)) {
                b = true;
            }
            if (intersection(x0 + diametr, y0, x + diametr, y, reflx, refly, reflx, refly + reflh)) {
                b = true;
            }
            if (intersection(x0, y0 + diametr, x, y + diametr, reflx, refly, reflx, refly + reflh)) {
                b = true;
            }
            if (intersection(x0 + diametr, y0 + diametr, x + diametr, y + diametr, reflx, refly, reflx, refly + reflh)) {
                b = true;
            }

            //проверка касания справа методом пересечения отрезков
            if (intersection(x0, y0, x, y, reflx + reflw, refly, reflx + reflw, refly + reflh)) {
                b = true;
            }
            if (intersection(x0 + diametr, y0, x + diametr, y, reflx + reflw, refly, reflx + reflw, refly + reflh)) {
                b = true;
            }
            if (intersection(x0, y0 + diametr, x, y + diametr, reflx + reflw, refly, reflx + reflw, refly + reflh)) {
                b = true;
            }
            if (intersection(x0 + diametr, y0 + diametr, x + diametr, y + diametr, reflx + reflw, refly, reflx + reflw, refly + reflh)) {
                b = true;
            }

            return b;

        }

        //проверяем, накрыли ли нейтрон экраном?
        private boolean cover(Reflector refl) {
            boolean cover = false;

            int[] vx = new int[4]; //вершины квадрата влокруг нейтрона
            int[] vy = new int[4];
            int reflw = refl.getWidth();
            int reflh = refl.getHeight();
            int reflx = refl.getX();
            int refly = refl.getY();

            vx[0] = x;
            vx[1] = x + diametr;
            vx[2] = x + diametr;
            vx[3] = x;

            vy[0] = y;
            vy[1] = y;
            vy[2] = y + diametr;
            vy[3] = y + diametr;

            for (int i = 0; i < 4; i++) {
                if ((reflx <= vx[i])
                        && (vx[i] <= (reflx + reflw))
                        && (refly <= vy[i])
                        && (vy[i] <= (refly + reflh))) {
                    cover = true;
                }

            }

            return cover;
        }
        
    // определяем направление движения нейтрона
        public void dirdefine(Reflector refl) {

            int w = Startgame.widthscreen;
            int h = Startgame.heightscreen;



            int reflx = refl.getX();
            int refly = refl.getY();
            int reflw = refl.getWidth();
            int reflh = refl.getHeight();

            if (touchwall()) {
                dx = -dx;
            }
            if (touchtvel()) {
                dy = -dy;
                tveltouched = true;
            }

            if (touchrods()) {
                dy = -dy;
                rodstouched = true;
            }

            boolean rebound = false; //отскок от рефлектора
            if (touchrefl_lr(reflx, refly, reflw, reflh)) {
                dx = -dx;
                x0 = x + dx;//Если нейтрон отскачил, то чтобы избежать залипания
                y0 = y + dy;//нужно страную координату сместить за пределы отражателя
                rebound = true;
            }
            if (touchrefl_ud(reflx, refly, reflw, reflh)) {
                dy = -dy;
                x0 = x + dx;//Если нейтрон отскачил, то чтобы избежать залипания
                y0 = y + dy;//нужно страную координату сместить за пределы отражателя
                rebound = true;
            }


            int mov = refl.getMovement();
            if ((mov != 0) && (!rebound)) {//если рефлектор перемещали и нейтрон не было
                if (cover(refl)) {//и если нейтрон накрыло рефлектором 
                    if ( mov < 0) {//если перемещали влево
                        x = refl.getX() - diametr - Math.abs(dx);//то поместить нейтрон слева от рефлектора
                    }
                    if ( mov > 0) {//если перемещали вправо
                        x = refl.getX() + refl.getWidth() + Math.abs(dx);//то поместить нейтрон справа от рефлектора
                    }

                }
            }
            
            x = x + dx;
            y = y + dy;

            if (!rebound) {//если отскока не было, то нужно запомнить прежнее положение
                x0 = x;
                y0 = y;
            }
    }        
        
        
    }
    //end class Neutron==============================================

    //создаем поток нейтронов
    public Neutrons() {
        
        url = getClass().getResource("res/orange.gif"); 
        orange = new ImageIcon(url);
        
        int w = Startgame.widthscreen;
        int h = Startgame.heightscreen;

        for (int i = 0; i < maxntr; i++) {
            ntr[i] = new Neutron();
        }
    }
    //Поля объекта
    private Neutron[] ntr = new Neutron[maxntr];    
    private static final int maxntr = 100; //максимальное количество нейтронов    
    private int diametr = 20; //диаметр нейтрона (для прорисовки)
    private int amount_ntr = 3;
    private URL url;
    private ImageIcon orange;
    private int absorbed_neutrons=0;
    private Rods r;

    public int getAbsorbed_neutrons() {
        return absorbed_neutrons;
    }
      

    public void showntrs(Graphics graphics) {
        for (int i = 0; i < amount_ntr; i++) {
            ntr[i].render(graphics);
        }
    }
    
    public int getRodsH(){
        return r.getHeight();
    }
    
    public void neutron_control(Reflector refl, Rods rods){

        this.r=rods;
        absorbed_neutrons=0;
        for (int i = 0; i < amount_ntr; i++) {
            ntr[i].dirdefine(refl);
            
            
            if (ntr[i].isTveltouched()){
                addone(i);
                ntr[i].setTveltouched(false);//обработали касание, сбрасываем флажок
            }

            if (ntr[i].isRodstouched()){
                delone(i);

            }

        }
        
    }


}
