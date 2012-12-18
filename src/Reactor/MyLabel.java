package Reactor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;

public class MyLabel extends JLabel{

    public MyLabel(String text, Color clr) {
        super(text);
        Font font = new Font("Arial", Font.PLAIN, 22);
        setForeground(clr);
        setBackground(new Color(255, 0, 0, 0));
        setFont(font);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
}
