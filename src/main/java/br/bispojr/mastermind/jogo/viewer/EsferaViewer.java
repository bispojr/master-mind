package br.bispojr.mastermind.jogo.viewer;

import br.bispojr.mastermind.jogo.Constantes;
import br.bispojr.mastermind.jogo.models.EsferaModel;
import br.bispojr.mastermind.util.MButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class EsferaViewer
        extends JComponent
        implements ActionListener
{
    EsferaModel model;
    private MButton btesfera;
    private boolean ativo;

    public EsferaViewer(int cor, boolean ativo)
    {
        setSize(31, 31);
        this.model = new EsferaModel(cor);
        this.ativo = ativo;
        this.btesfera = new MButton(this.model.getPachImg(), this.model.getPachImg(), this.model.getPachImg());
        this.btesfera.setSize(Constantes.TAMANHO_ESFERA);

        this.btesfera.setRolloverEnabled(false);
        if (ativo) {
            this.btesfera.setCursor(new Cursor(12));
        } else {
            this.btesfera.setCursor(new Cursor(0));
        }
        this.btesfera.addMouseListener(new MouseListener()
        {
            public void mouseClicked(MouseEvent e)
            {
                if ((e.getButton() == 1) &&
                        (EsferaViewer.this.isAtivo())) {
                    if (EsferaViewer.this.model.getCor() != 6) {
                        EsferaViewer.this.setCor(EsferaViewer.this.model.getCor() + 1);
                    } else {
                        EsferaViewer.this.setCor(1);
                    }
                }
                if ((e.getButton() == 3) &&
                        (EsferaViewer.this.isAtivo()))
                {
                    EscolheCor escolheCor = new EscolheCor(EsferaViewer.this.btesfera);

                    EsferaViewer.this.setCor(escolheCor.getCor());
                }
            }

            public void mousePressed(MouseEvent e) {}

            public void mouseReleased(MouseEvent e) {}

            public void mouseEntered(MouseEvent e) {}

            public void mouseExited(MouseEvent e) {}
        });
        add(this.btesfera);
    }

    public EsferaViewer(boolean ativo)
    {
        this(0, ativo);
    }

    public boolean isAtivo()
    {
        return this.ativo;
    }

    public void setAtivo(boolean ativo)
    {
        if (ativo == true) {
            this.btesfera.setCursor(new Cursor(12));
        } else {
            this.btesfera.setCursor(new Cursor(0));
        }
        this.ativo = ativo;
    }

    public void setCor(int cor)
    {
        this.model.setCor(cor);
        this.btesfera.setIcon(this.model.getPachImg());
        this.btesfera.setPressedIcon(this.model.getPachImg());
    }

    public int getCor()
    {
        return this.model.getCor();
    }

    public static void main(String[] args)
    {
        JFrame tela = new JFrame();
        tela.setLayout(new BorderLayout());
        tela.setSize(100, 100);
        tela.setDefaultCloseOperation(3);
        tela.getContentPane().add(new EsferaViewer(2, true), "Center");
        tela.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if ((e.getSource().equals(this.btesfera)) &&
                (this.ativo)) {
            if (this.model.getCor() != 6) {
                setCor(this.model.getCor() + 1);
            } else {
                setCor(1);
            }
        }
    }
}
