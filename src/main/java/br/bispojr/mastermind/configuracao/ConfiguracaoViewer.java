package br.bispojr.mastermind.configuracao;

import br.bispojr.mastermind.audio.TocarAudio;
import br.bispojr.mastermind.jogo.observer.Observado;
import br.bispojr.mastermind.jogo.observer.Observador;
import br.bispojr.mastermind.util.ImagePanel;
import br.bispojr.mastermind.util.MButton;
import br.bispojr.mastermind.util.UpdateLabels;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConfiguracaoViewer
        extends JComponent implements UpdateLabels, Observado, Runnable {
    private ImagePanel panelFundo;
    private MButton btTutorial;
    private MButton btPontuacoes;
    private JCheckBox checkSom;
    private ButtonGroup buttonGroup;
    private JRadioButton radioPortugues;
    private JLabel labelSom;
    private JLabel labelIdiomas;
    private JLabel labelPot;
    private JLabel labelEng;
    private final String IMAGE_FUNDO = "/images/configs/fundoOpcoes.png";
    private final String IMAGE_BTopcoes = "/images/configs/btOpcoes.png";
    private final String IMAGE_BTopcoes2 = "/images/configs/btOpcoes2.png";
    private final JRadioButton radioIngles;
    ConfiguracaoModel a = ConfiguracaoControle.getConfiguracaoModel();
    public List<Observador> obserList = new ArrayList();
    private JLabel labelEfeitos;
    private JCheckBox checkEfeitos;
    private static TocarAudio player = new TocarAudio("/audio/jogo.mp3");

    Thread th;
    Runnable r;

    public ConfiguracaoViewer(boolean ativaSom) {
        r = this;

        setLayout(new BorderLayout());
        setSize(241, 273);

        a.setEfeitos(true);
        a.setMusica(false);
        try {
            panelFundo = new ImagePanel("/images/configs/fundoOpcoes.png");
        } catch (IOException ex) {
            Logger.getLogger(ConfiguracaoViewer.class.getName()).log(Level.SEVERE, null, ex);
        }


        checkSom = new JCheckBox();
        checkSom.setLocation(41, 22);
        checkSom.setSelected(ativaSom);
        checkSom.setSize(20, 20);
        checkSom.setCursor(new Cursor(12));
        checkSom.setMnemonic('m');
        checkSom.setSelected(false);
        checkSom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConfiguracaoControle.setMusica(checkSom.isSelected());
                if (checkSom.isSelected()) {
                    th.start();
                } else {
                    th.stop();
                    th = new Thread(r);
                }

            }
        });
        checkEfeitos = new JCheckBox();
        checkEfeitos.setLocation(40, 53);
        checkEfeitos.setSelected(ativaSom);
        checkEfeitos.setSize(20, 20);
        checkEfeitos.setCursor(new Cursor(12));
        checkEfeitos.setMnemonic('s');
        checkEfeitos.setSelected(true);
        checkEfeitos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConfiguracaoControle.setEfeito(checkEfeitos.isSelected());
                System.out.println(checkSom.isSelected());
            }


        });
        labelSom = new JLabel(ConfiguracaoControle.bundleMesagens.getString("lbMusica"));
        labelSom.setLocation(75, 22);
        labelSom.setSize(100, 20);
        labelSom.setForeground(Color.WHITE);


        labelEfeitos = new JLabel(ConfiguracaoControle.bundleMesagens.getString("lbEfeitos"));
        labelEfeitos.setLocation(75, 52);
        labelEfeitos.setSize(100, 20);
        labelEfeitos.setForeground(Color.WHITE);


        buttonGroup = new ButtonGroup();


        radioPortugues = new JRadioButton();
        radioPortugues.setLocation(41, 117);
        radioPortugues.setSize(20, 20);
        radioPortugues.setName("radioPt");
        radioPortugues.setMnemonic('p');
        radioPortugues.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConfiguracaoControle.setLocale(new Locale("pt", "BR"));
                updateLabels();
                notificar();
                System.out.println("pt_BR");
            }

        });
        radioIngles = new JRadioButton();
        radioIngles.setLocation(41, 145);
        radioIngles.setSize(20, 20);
        radioIngles.setName("radioEn");
        radioIngles.setMnemonic('e');
        radioIngles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConfiguracaoControle.setLocale(new Locale("en", "US"));
                updateLabels();
                notificar();
                System.out.println("en_US");
            }

        });
        buttonGroup.add(radioPortugues);
        buttonGroup.add(radioIngles);
        buttonGroup.setSelected(radioPortugues.getModel(), true);


        labelIdiomas = new JLabel(ConfiguracaoControle.bundleMesagens.getString("lbIdioma"));
        labelIdiomas.setLocation(46, 95);
        labelIdiomas.setSize(100, 20);
        labelIdiomas.setForeground(Color.WHITE);
        labelIdiomas.setFont(new Font("Arial", 1, 15));


        labelPot = new JLabel(ConfiguracaoControle.bundleMesagens.getString("lbIdiomaPt"));
        labelPot.setSize(100, 20);
        labelPot.setLocation(70, 117);
        labelPot.setForeground(Color.WHITE);


        labelEng = new JLabel(ConfiguracaoControle.bundleMesagens.getString("lbIdiomaEn"));
        labelEng.setSize(100, 20);
        labelEng.setLocation(70, 145);
        labelEng.setForeground(Color.WHITE);


        btPontuacoes = new MButton("/images/configs/btOpcoes.png", "/images/configs/btOpcoes.png", "/images/configs/btOpcoes2.png");
        btPontuacoes.setRolloverEnabled(true);
        btPontuacoes.setText(ConfiguracaoControle.bundleMesagens.getString("lbBtPontuacoes"));
        btPontuacoes.setLocation(48, 178);
        btPontuacoes.setSize(136, 27);
        btPontuacoes.setForeground(Color.WHITE);
        btPontuacoes.setCursor(new Cursor(12));
        btPontuacoes.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {
            }


        });
        new Thread(new Runnable() {
            public void run() {
                for (; ; ) {
                    try {
                        Thread.sleep(1L);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ConfiguracaoViewer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    radioIngles.setEnabled(!ConfiguracaoControle.isJogando());
                    labelEng.setEnabled(!ConfiguracaoControle.isJogando());
                    radioPortugues.setEnabled(!ConfiguracaoControle.isJogando());
                    labelPot.setEnabled(!ConfiguracaoControle.isJogando());
                }

            }
        }).start();
        btTutorial = new MButton("/images/configs/btOpcoes.png", "/images/configs/btOpcoes.png", "/images/configs/btOpcoes2.png");
        btTutorial.setRolloverEnabled(true);
        btTutorial.setText(ConfiguracaoControle.bundleMesagens.getString("lbBtAjuda"));
        btTutorial.setLocation(48, 218);
        btTutorial.setSize(136, 27);
        btTutorial.setForeground(Color.WHITE);
        btTutorial.setCursor(new Cursor(12));
        btTutorial.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {
            }


        });
        add(panelFundo);
        panelFundo.add(checkSom);
        panelFundo.add(checkEfeitos);
        panelFundo.add(labelSom);
        panelFundo.add(labelEfeitos);
        panelFundo.add(labelIdiomas);
        panelFundo.add(labelPot);
        panelFundo.add(radioPortugues);
        panelFundo.add(radioIngles);
        panelFundo.add(labelEng);
        panelFundo.add(btPontuacoes);
        panelFundo.add(btTutorial);


        th = new Thread(this);
    }

    public void setIdioma() {
    }

    public static void main(String[] a)
            throws IOException, FileNotFoundException, JavaLayerException {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }


        JFrame janela = new JFrame();
        janela.setLayout(new BorderLayout());
        janela.setSize(400, 400);
        janela.getContentPane().add(new ConfiguracaoViewer(true));
        janela.setDefaultCloseOperation(3);
        janela.setVisible(true);
    }

    public void updateLabels() {
        labelSom.setText(ConfiguracaoControle.bundleMesagens.getString("lbMusica"));
        labelEfeitos.setText(ConfiguracaoControle.bundleMesagens.getString("lbEfeitos"));
        btPontuacoes.setText(ConfiguracaoControle.bundleMesagens.getString("lbBtPontuacoes"));
        btTutorial.setText(ConfiguracaoControle.bundleMesagens.getString("lbBtAjuda"));
        labelIdiomas.setText(ConfiguracaoControle.bundleMesagens.getString("lbIdioma"));
        labelPot.setText(ConfiguracaoControle.bundleMesagens.getString("lbIdiomaPt"));
        labelEng.setText(ConfiguracaoControle.bundleMesagens.getString("lbIdiomaEn"));
    }

    public String getTextosLabels() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void adicionarObservador(Observador o) {
        obserList.add(o);
    }

    public void notificar() {
        Iterator<Observador> i = obserList.iterator();
        while (i.hasNext()) {
            ((Observador) i.next()).atualizar();
        }
    }

    public void run() {
        try {
            for (; ; ) {
                player.playFundo();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfiguracaoViewer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex) {
            Logger.getLogger(ConfiguracaoViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
