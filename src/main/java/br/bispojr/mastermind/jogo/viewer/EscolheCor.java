package br.bispojr.mastermind.jogo.viewer;

import br.bispojr.mastermind.jogo.models.EsferaModel;
import br.bispojr.mastermind.util.MButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class EscolheCor
        extends JDialog
        implements ActionListener
{
    int cor = 2;
    List<EsferaViewer> es = new ArrayList();
    MButton bt1;
    MButton bt2;
    MButton bt3;
    MButton bt4;
    MButton bt5;
    MButton bt6;
    private final Container tela;

    public EscolheCor(JButton owner)
    {
        setModal(true);
        setSize(40, 190);
        setUndecorated(true);
        setLayout(new BorderLayout());
        setLocation((int)owner.getLocationOnScreen().getX() - 5, (int)owner.getLocationOnScreen().getY() - getSize().height);
        JPanel p = new JPanel(new GridLayout(6, 1));
        p.setBackground(Color.WHITE);
        this.tela = getContentPane();
        p.setBorder(BorderFactory.createEtchedBorder(Color.lightGray, Color.BLACK));

        this.tela.add(p);
        this.bt1 = new MButton(EsferaModel.IMAGES[1]);
        this.bt2 = new MButton(EsferaModel.IMAGES[2]);
        this.bt3 = new MButton(EsferaModel.IMAGES[3]);
        this.bt4 = new MButton(EsferaModel.IMAGES[4]);
        this.bt5 = new MButton(EsferaModel.IMAGES[5]);
        this.bt6 = new MButton(EsferaModel.IMAGES[6]);

        this.bt2.setRolloverEnabled(true);
        this.bt3.setRolloverEnabled(true);
        this.bt4.setRolloverEnabled(true);
        this.bt5.setRolloverEnabled(true);
        this.bt6.setRolloverEnabled(true);
        this.bt2.setRolloverEnabled(true);

        this.bt1.addActionListener(this);
        this.bt2.addActionListener(this);
        this.bt3.addActionListener(this);
        this.bt4.addActionListener(this);
        this.bt5.addActionListener(this);
        this.bt6.addActionListener(this);

        p.add(this.bt1);
        p.add(this.bt2);
        p.add(this.bt3);
        p.add(this.bt4);
        p.add(this.bt5);
        p.add(this.bt6);

        setVisible(true);
    }

    final void movimenta()
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                while (EscolheCor.this.getSize().height < 190) {
                    try
                    {
                        EscolheCor.this.setSize(EscolheCor.this.getSize().width, EscolheCor.this.getSize().height + 1);
                        Thread.sleep(100L);
                        EscolheCor.this.repaint();
                    }
                    catch (InterruptedException ex)
                    {
                        Logger.getLogger(EscolheCor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    public int getCor()
    {
        return this.cor;
    }

    public void setCor(int cor)
    {
        this.cor = cor;
    }

    public static void main(String[] a) {}

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.bt1) {
            this.cor = 1;
        }
        if (e.getSource() == this.bt2) {
            this.cor = 2;
        }
        if (e.getSource() == this.bt3) {
            this.cor = 3;
        }
        if (e.getSource() == this.bt4) {
            this.cor = 4;
        }
        if (e.getSource() == this.bt5) {
            this.cor = 5;
        }
        if (e.getSource() == this.bt6) {
            this.cor = 6;
        }
        dispose();
    }
}
