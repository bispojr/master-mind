package br.bispojr.mastermind.jogo.viewer;

import br.bispojr.mastermind.configuracao.ConfiguracaoControle;
import br.bispojr.mastermind.jogo.models.Cronometro;
import br.bispojr.mastermind.jogo.models.ResultadoModel;
import br.bispojr.mastermind.util.ImagePanel;
import br.bispojr.mastermind.util.MButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PontuacoesViewer
        extends JDialog {
    public ImagePanel fundoGeral;
    public MButton btFexar;
    public static int TAM_RANNK = 10;
    int temp = 0;
    public ImagePanel fundoRank;

    public PontuacoesViewer(JComponent t) {
        setLayout(new BorderLayout());
        setLocationRelativeTo(t);

        setLocation(getX() - 211, getY() - 175);
        setUndecorated(true);
        setSize(421, 351);
        setModal(true);
        try {
            fundoGeral = new ImagePanel("/images/configs/fundoPontuacoes.png");
            fundoGeral.setLayout(null);
        } catch (IOException ex) {
            Logger.getLogger(PontuacoesViewer.class.getName()).log(Level.SEVERE, null, ex);
        }


        btFexar = new MButton("/images/configs/btfecha.png", "/images/configs/btfecha.png", "/images/configs/btfecha2.png");
        btFexar.setSize(33, 35);
        btFexar.setRolloverEnabled(true);
        btFexar.setLocation(369, 13);
        btFexar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        try {
            organizaRank(ConfiguracaoControle.resultados);
        } catch (Exception ex) {
            Logger.getLogger(PontuacoesViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        getContentPane().add(fundoGeral, "Center");
        fundoGeral.add(btFexar);
        fundoGeral.add(fundoRank);
        setDefaultCloseOperation(2);
        setVisible(true);
    }


    public ArrayList<ImagePanel> fundoNomes = new ArrayList();
    public ArrayList<ImagePanel> fundoTempo = new ArrayList();
    public ArrayList<ImagePanel> fundoPosic = new ArrayList();

    public void organizaRank(ArrayList<ResultadoModel> r) throws IOException {
        Font f = new Font("Lucida", temp, 15);
        fundoRank = new ImagePanel("/images/configs/fundoRank.png");
        fundoRank.setSize(351, 281);
        fundoRank.setLocation(35, 57);
        fundoRank.setLayout(null);

        Point p = new Point(63, 11);
        for (int i = 0; (i < 5) && (i < r.size()); i++) {
            fundoNomes.add(new ImagePanel("/images/configs/fundonome.png"));
            ((ImagePanel) fundoNomes.get(i)).setSize(197, 46);
            ((ImagePanel) fundoNomes.get(i)).setLocation(63, 12 + 53 * i);
            ((ImagePanel) fundoNomes.get(i)).setLayout(new BorderLayout());
            String nome = ((ResultadoModel) r.get(i)).getNome();
            if (nome == null) {
                nome = ConfiguracaoControle.bundleMesagens.getString("nomeNone");
            }
            JLabel jLabel = new JLabel(nome);
            jLabel.setFont(f);
            jLabel.setHorizontalAlignment(0);
            jLabel.setVerticalAlignment(0);
            ((ImagePanel) fundoNomes.get(i)).add(jLabel);

            fundoRank.add((Component) fundoNomes.get(i));
        }
        for (int i = 0; (i < 5) && (i < r.size()); i++) {
            fundoPosic.add(new ImagePanel("/images/configs/fundoposicao.png"));
            ((ImagePanel) fundoPosic.get(i)).setSize(51, 46);
            ((ImagePanel) fundoPosic.get(i)).setLocation(10, 12 + 53 * i);
            ((ImagePanel) fundoPosic.get(i)).setLayout(new BorderLayout());
            JLabel jLabel = new JLabel(i + 1 + "");
            jLabel.setFont(f);
            jLabel.setHorizontalAlignment(0);
            jLabel.setVerticalAlignment(0);
            ((ImagePanel) fundoPosic.get(i)).add(jLabel);
            fundoRank.add((Component) fundoPosic.get(i));
        }
        for (int i = 0; (i < 5) && (i < r.size()); i++) {
            fundoTempo.add(new ImagePanel("/images/configs/fundopontos.png"));
            ((ImagePanel) fundoTempo.get(i)).setSize(81, 46);
            ((ImagePanel) fundoTempo.get(i)).setLocation(260, 12 + 53 * i);
            ((ImagePanel) fundoTempo.get(i)).setLayout(new BorderLayout());
            JLabel jLabel = new JLabel(Cronometro.retornaTimer(((ResultadoModel) r.get(i)).getTempo().intValue()));
            jLabel.setFont(f);
            jLabel.setHorizontalAlignment(0);
            jLabel.setVerticalAlignment(0);
            ((ImagePanel) fundoTempo.get(i)).add(jLabel);
            fundoRank.add((Component) fundoTempo.get(i));
        }
    }

    public static void main(String[] a) {
        new PontuacoesViewer(null);
    }
}
