package br.bispojr.mastermind.configuracao;

import br.bispojr.mastermind.jogo.viewer.util.MetodosUteis;
import br.bispojr.mastermind.util.ImagePanel;
import br.bispojr.mastermind.util.MButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Help extends JDialog {
    private ImagePanel fundo;
    private MButton btFexar;

    public Help(String pachHelpIndex) throws IOException {
        setModal(true);

        setLayout(new java.awt.BorderLayout());
        fundo = new ImagePanel("/images/configs/fundoHelp.png");
        fundo.setLayout(null);
        btFexar = new MButton("/images/configs/btfecha.png", "/images/configs/btfecha.png", "/images/configs/btfecha2.png");
        btFexar.setRolloverEnabled(true);
        btFexar.setSize(33, 35);
        btFexar.setLocation(470, 20);
        btFexar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }

        });
        btFexar.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }


            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 27) {
                    System.exit(0);
                }
            }


            public void keyReleased(KeyEvent e) {
            }
        });
        setSize(521, 638);
        setLocationRelativeTo(null);
        setUndecorated(true);
        JScrollPane pane = new JScrollPane();
        pane.setSize(435, 510);
        pane.setLocation(43, 90);
        pane.setBorder(null);
        pane.setVerticalScrollBarPolicy(20);
        pane.setHorizontalScrollBarPolicy(30);
        URL u = getClass().getResource(pachHelpIndex);

        JEditorPane editorPane = new JEditorPane(u);
        editorPane.setBorder(BorderFactory.createEtchedBorder());
        editorPane.setEditable(false);

        editorPane.addHyperlinkListener(new MyHyperlinkListener());
        getContentPane().add(fundo, "Center");

        fundo.add(pane);
        fundo.add(btFexar);
        pane.setViewportView(editorPane);
    }

    /**
     * FIXME Avaliar esse método
     *
     * @param argv
     * @throws Exception
     */
    public static void main(String[] argv) throws Exception {
        MetodosUteis.configureNimbus();


        new Help("MasterMind.htm").setVisible(true);
    }
}
