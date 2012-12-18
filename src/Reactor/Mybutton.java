/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reactor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 *
 * @author user
 */
public class Mybutton extends JLabel{

    private ImageIcon img;
    private boolean pressed = false;

    public boolean isPressed() {
        return pressed;
    }
    


    public Mybutton(String path) {

        URL url = getClass().getResource(path);
        img = new ImageIcon(url);
        setBackground(new Color(255,0,0,0));

        setAlignmentX(Component.CENTER_ALIGNMENT);
        setIcon(img);
        
        setFocusable(true);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent me) {
                pressed = true;
//                setIcon(null);
            }
        });
        

    }
}
