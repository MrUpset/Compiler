package com.tyq_code.compiler;

import javax.swing.*;
import java.awt.*;

class Drawer extends JFrame {

    private Graphics g;

    Drawer() {
        Container p = getContentPane();
        setBounds(0, 0, 600, 600);
        setVisible(true);
        p.setBackground(Color.white);
        setLayout(null);
        setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        g = this.getGraphics();
        g.setColor(Color.black);
        g.drawLine(0, 300, 600, 300);
        g.drawLine(300, 0, 300, 600);
    }

    void drawPoint(double x, double y) {
        double temp;
        x = x * Definition.scale_x;
        y = - y * Definition.scale_y; // y反转
        temp = x * Math.cos(Definition.rot) + y * Math.sin(Definition.rot);
        y = y * Math.cos(Definition.rot) - x * Math.sin(Definition.rot);
        x = temp;
        x += Definition.origin_x;
        y += Definition.origin_y;
        g.drawLine((int) x, (int) y, (int) x, (int) y);
    }
}
