package br.bispojr.mastermind.jogo.viewer.util;

import br.bispojr.mastermind.configuracao.ConfiguracaoControle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dialog extends JDialog implements ActionListener, java.awt.event.MouseListener {
    JTextArea jTextArea;
    String nome;

    public Dialog(Component parent, String title, String message) throws IOException {
        this(parent, title, message, false);
    }


    public String getNome() {
        return nome;
    }

    public Dialog(Component parent, String title, String message, boolean insertT) throws IOException {
        nome = null;
        setLocationRelativeTo(parent.getComponentAt(10, 10));
        setModal(true);
        setUndecorated(true);
        setCursor(new Cursor(12));

        JPanel messagePane = new JPanel();
        messagePane.setBackground(Color.white);
        JPanel insetPane = new JPanel();
        insetPane.setBackground(Color.white);
        insetPane.setLayout(new BorderLayout());


        JPanel geral = new JPanel(new BorderLayout());
        geral.setBorder(BorderFactory.createEtchedBorder(5, Color.darkGray, Color.black));


        if (insertT) {
            geral.add(insetPane, "Last");
        }
        jTextArea = new JTextArea("");

        jTextArea.setBorder(BorderFactory.createTitledBorder(ConfiguracaoControle.bundleMesagens.getString("informeNome")));
        jTextArea.setSize(100, 20);
        jTextArea.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }


            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    setVisible(false);
                    nome = jTextArea.getText();
                    dispose();
                }
            }


            public void keyReleased(KeyEvent e) {
            }
        });
        insetPane.add(jTextArea, "Center");
        JEditorPane textArea = new JEditorPane();

        textArea.setContentType("text/html");
        textArea.setText(message);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", 5, 15));

        messagePane.add(textArea);

        getContentPane().add(geral);

        geral.add(messagePane);

        geral.addMouseListener(this);
        textArea.addMouseListener(this);
        setDefaultCloseOperation(2);
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    public static void main(String[] a) {
        Dialog dlg;

        try {
            //FIXME Verificar variavel dlg
            dlg = new Dialog(new JFrame(), "Erro no", "Erro no bagulho");
        } catch (IOException ex) {
            Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mouseClicked(MouseEvent e) {
        setVisible(false);
        dispose();
    }

    public void mousePressed(MouseEvent e) {
        setVisible(false);
        dispose();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
