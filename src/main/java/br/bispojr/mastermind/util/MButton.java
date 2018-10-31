package br.bispojr.mastermind.util;

import javax.swing.*;
import java.awt.*;


public final class MButton
        extends JButton {
    private Icon icon;
    private Icon pressed;
    private Icon rollover;

    public MButton(String icon, String pressed, String rollover) {
        this.icon = new ImageIcon(getClass().getResource(icon));
        this.pressed = new ImageIcon(getClass().getResource(pressed));
        this.rollover = new ImageIcon(getClass().getResource(rollover));
        configuraButton();
    }

    public void configuraButton() {
        setIcon(icon);
        setFocusPainted(false);
        setRolloverEnabled(false);
        setPressedIcon(pressed);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setHorizontalTextPosition(0);
        setVerticalTextPosition(0);
        setCursor(Cursor.getPredefinedCursor(12));
    }

    public MButton(String icon) {
        this(icon, icon, icon);
    }


    public Icon getPressed() {
        return pressed;
    }


    public void setPressedIcon(Icon pressed) {
        super.setPressedIcon(pressed);
    }


    public void setPressedIcon(String pressed) {
        setPressedIcon(new ImageIcon(getClass().getResource(pressed)));
    }


    public Icon getRolloverIcon() {
        return rollover;
    }


    public void setRolloverIcon(Icon rollover) {
        super.setRolloverIcon(rollover);
    }


    public void setRolloverIcon(String rollover) {
        setRolloverIcon(new ImageIcon(getClass().getResource(rollover)));
    }


    public void setIcon(Icon icon) {
        super.setIcon(icon);
    }


    public void setIcon(String icon) {
        setIcon(new ImageIcon(getClass().getResource(icon)));
    }
}
