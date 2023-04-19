package edu.pwr.db.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

class JTextAreaWithPlaceholder extends JTextArea {
    private final String placeholder;
    private boolean focused;

    JTextAreaWithPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        focused = false;
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                focused = false;
                repaint();
            }

            @Override
            public void focusGained(FocusEvent e) {
                focused = true;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        if(getText().isEmpty() && !focused) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setColor(Color.lightGray);
            g2.setFont(getFont().deriveFont(Font.BOLD));
            g2.drawString(placeholder, 5, 10);
            g2.dispose();
        }
    }
}