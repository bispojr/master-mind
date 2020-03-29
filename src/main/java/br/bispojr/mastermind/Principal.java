package br.bispojr.mastermind;

import br.bispojr.mastermind.configuracao.ConfiguracaoControle;
import br.bispojr.mastermind.configuracao.ConfiguracaoViewer;
import br.bispojr.mastermind.jogo.viewer.PainelJogoViewer;
import br.bispojr.mastermind.util.ImagePanel;
import br.bispojr.mastermind.util.MButton;
import br.bispojr.mastermind.util.UpdateLabels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Principal
        extends JFrame
        implements Runnable, UpdateLabels {
    private ImagePanel fundo;
    private ConfiguracaoViewer config;
    private MButton btFexar;
    private MButton btJogar;
    private final Container tela;
    private PainelJogoViewer jogo;
    private MButton logo;
    private final MButton btMin;

    public Principal()
            throws IOException {
        setLayout(new BorderLayout());
        setUndecorated(true);
        setSize(674, 611);
        setIconImage(getToolkit().getImage(getClass().getResource("/jogo/icone.png")));

        logo = new MButton("/jogo/logo.png");
        logo.setSize(337, 125);
        logo.setLocation(10, 40);
        logo.setCursor(new Cursor(0));
        tela = getContentPane();

        fundo = new ImagePanel("/jogo/fundo.png");
        fundo.setLayout(null);
        fundo.setBorder(BorderFactory.createRaisedBevelBorder());

        btFexar = new MButton("/jogo/fechar.png", "/jogo/fechar.png", "/jogo/fechar2.png");
        btFexar.setRolloverEnabled(true);
        btFexar.setLocation(649, 4);
        btFexar.setSize(20, 20);
        btFexar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.print("Salvar estatus do Jogo e sair");
                System.exit(0);
            }

        });
        btMin = new MButton("/jogo/min.png", "/jogo/min.png", "/jogo/min2.png");
        btMin.setRolloverEnabled(true);
        btMin.setLocation(619, 4);
        btMin.setSize(21, 21);
        btMin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setExtendedState(1);

            }


        });
        jogo = new PainelJogoViewer();
        jogo.setLocation(350, 54);


        jogo.btJogar.setSize(194, 48);
        jogo.btJogar.setLocation(48, 180);


        config = new ConfiguracaoViewer(false);
        config.setLocation(25, 260);

        config.adicionarObservador(jogo);

        tela.add(fundo, "Center");
        fundo.add(logo);
        fundo.add(config);
        fundo.add(jogo);
        fundo.add(btFexar);
        fundo.add(btMin);
        fundo.add(jogo.btJogar);
        fundo.add(jogo.labelSenha);
        fundo.add(jogo.grupoSenha);

        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
    }

    public void setCursorEspera(boolean espera) {
        if (espera) {
            getContentPane().setCursor(new Cursor(3));
        } else {
            getContentPane().setCursor(new Cursor(0));
        }
    }


    public static void main(String[] a)
            throws InterruptedException, IOException {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }


        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ConfiguracaoControle.inicializaResultados();
                    Principal p = new Principal();
                    ConfiguracaoControle.tela = p;
                    p.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    boolean jogando = false;


    public void run() {
    }


    public void updateLabels() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getTextosLabels() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
