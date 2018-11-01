package br.bispojr.mastermind.jogo.viewer;

import br.bispojr.mastermind.jogo.Constantes;
import br.bispojr.mastermind.jogo.models.EsferaModel;
import br.bispojr.mastermind.jogo.models.GrupoEsferasModel;
import br.bispojr.mastermind.util.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GrupoEsferasViewer
        extends GrupoEsferasModel {
    private EsferaViewer esfera1;
    private EsferaViewer esfera2;
    private EsferaViewer esfera3;
    private EsferaViewer esfera4;
    private boolean ativo;
    private Status status;
    private ImagePanel fundo;

    public GrupoEsferasViewer(boolean sorteavel, Status status)
            throws IOException {
        super(sorteavel);

        this.status = status;
        setLayout(null);
        iniciaFundo();
        iniciaEsferas();
    }

    public GrupoEsferasViewer(boolean sorteavel) throws IOException {
        this(sorteavel, Status.ESPERA);
    }

    public GrupoEsferasViewer(GrupoEsferasModel g) throws IOException {
        status = Status.ESPERA;
        setLayout(null);
        iniciaFundo();
        iniciaEsferas(g);
    }

    public final void iniciaEsferas(GrupoEsferasModel g) {
        if (status.equals(Status.ATIVO)) {
            ativo = true;
        } else {
            ativo = false;
        }

        esfera1 = new EsferaViewer(g.getEsfera(0).getCor(), ativo);
        esfera1.setBounds(9, 7, 31, 31);
        fundo.add(esfera1);

        esfera2 = new EsferaViewer(g.getEsfera(1).getCor(), ativo);
        esfera2.setBounds(52, 7, 31, 31);
        fundo.add(esfera2);

        esfera3 = new EsferaViewer(g.getEsfera(2).getCor(), ativo);
        esfera3.setBounds(94, 7, 31, 31);
        fundo.add(esfera3);

        esfera4 = new EsferaViewer(g.getEsfera(3).getCor(), ativo);
        esfera4.setBounds(134, 7, 31, 31);
        fundo.add(esfera4);
        setEsferasVisible();
    }

    public final void iniciaEsferas() {
        if (status.equals(Status.ATIVO)) {
            ativo = true;
        } else {
            ativo = false;
        }


        esfera1 = new EsferaViewer(getEsfera(0).getCor(), ativo);
        esfera1.setBounds(10, 7, 31, 31);
        fundo.add(esfera1);

        esfera2 = new EsferaViewer(getEsfera(1).getCor(), ativo);
        esfera2.setBounds(52, 7, 31, 31);
        fundo.add(esfera2);

        esfera3 = new EsferaViewer(getEsfera(2).getCor(), ativo);
        esfera3.setBounds(94, 7, 31, 31);
        fundo.add(esfera3);

        esfera4 = new EsferaViewer(getEsfera(3).getCor(), ativo);
        esfera4.setBounds(134, 7, 31, 31);
        fundo.add(esfera4);
        setEsferasVisible();
    }

    void criaSoerteada() {
    }

    public final void iniciaFundo() throws IOException {
        if (status.equals(Status.ATIVO)) {
            fundo = new ImagePanel("/jogo/esferasAtivo.png");
            ativo = true;
        }

        if (status.equals(Status.ENVIADO)) {
            fundo = new ImagePanel("/jogo/esferasEnviada.png");
            ativo = false;
        }


        if (status.equals(Status.ESPERA)) {
            fundo = new ImagePanel("/jogo/esferasEspera.png");
            ativo = false;
        }
        fundo.setLocation(0, 0);
        fundo.setSize(Constantes.TAMANHO_GRUPO_ESFERA);
        add(fundo);
    }

    public void setStatus(Status status)
            throws IOException {
        this.status = status;
        if (status.equals(Status.ATIVO)) {
            setAtivo(true);
        } else {
            setAtivo(false);
        }
        repaint();
    }

    public Status getStatus() {
        return status;
    }

    public void setAtivo(boolean ativo) throws IOException {
        this.ativo = ativo;
        esfera1.setAtivo(ativo);
        esfera2.setAtivo(ativo);
        esfera3.setAtivo(ativo);
        esfera4.setAtivo(ativo);
        setFundo();
    }

    public void limpaEsferas() {
        esfera1.setCor(0);
        esfera2.setCor(0);
        esfera3.setCor(0);
        esfera4.setCor(0);
    }

    public void setEsferasVisible() {
        if (status.equals(Status.ESPERA)) {
            esfera1.setVisible(false);
            esfera2.setVisible(false);
            esfera3.setVisible(false);
            esfera4.setVisible(false);
        } else {
            esfera1.setVisible(true);
            esfera2.setVisible(true);
            esfera3.setVisible(true);
            esfera4.setVisible(true);
        }
    }

    public List<EsferaModel> getEsferas() {
        atualizaEsferas();
        return super.getEsferas();
    }

    public boolean contemNull() {
        if ((esfera1.getCor() == 0) || (esfera2.getCor() == 0) || (esfera3.getCor() == 0) || (esfera4.getCor() == 0)) {
            return true;
        }
        return false;
    }


    public boolean contemRepetido() {
        int[] cores = new int[4];
        boolean ret = false;
        cores[0] = esfera1.getCor();
        cores[1] = esfera2.getCor();
        cores[2] = esfera3.getCor();
        cores[3] = esfera4.getCor();
        int cont = 0;
        for (int i = 0; i < cores.length; i++) {
            for (int j = 0; j < cores.length; j++) {
                if (cores[i] == cores[j]) {
                    cont++;
                }
            }
        }
        if (cont > cores.length) {
            return true;
        }
        return false;
    }

    private void atualizaEsferas() {
        setEsfera(esfera1.model, 0);
        setEsfera(esfera2.model, 1);
        setEsfera(esfera3.model, 2);
        setEsfera(esfera4.model, 3);
    }

    private void setFundo() throws IOException {
        if (status.equals(Status.ATIVO)) {
            fundo.setImage("/jogo/esferasAtivo.png");
        }
        if (status.equals(Status.ENVIADO)) {
            fundo.setImage("/jogo/esferasEnviada.png");
        }
        if (status.equals(Status.ESPERA)) {
            fundo.setImage("/jogo/esferasEspera.png");
        }
        setEsferasVisible();
    }

    public String toString() {
        return super.toString();
    }

    public void setEsferas(List<EsferaModel> esferas) {
        super.setEsferas(esferas);
        esfera1.setCor(((EsferaModel) esferas.get(0)).getCor());
        esfera2.setCor(((EsferaModel) esferas.get(1)).getCor());
        esfera3.setCor(((EsferaModel) esferas.get(2)).getCor());
        esfera4.setCor(((EsferaModel) esferas.get(3)).getCor());
    }


    public static enum Status {
        ATIVO, ESPERA, ENVIADO;

        private Status() {
        }
    }

    public static void main(String[] args) {
        JFrame tela = new JFrame();
        tela.setLayout(new BorderLayout());
        tela.setSize(180, 80);
        tela.setDefaultCloseOperation(3);
        GrupoEsferasViewer grupoEsferasViewer = null;
        try {
            grupoEsferasViewer = new GrupoEsferasViewer(false);
            tela.getContentPane().add(grupoEsferasViewer, "Center");
        } catch (IOException ex) {
            Logger.getLogger(GrupoEsferasViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        tela.setVisible(true);
        try {
            for (; ; ) {
                try {
                    grupoEsferasViewer.setStatus(Status.ATIVO);
                } catch (IOException ex) {
                    Logger.getLogger(GrupoEsferasViewer.class.getName()).log(Level.SEVERE, null, ex);
                }
                Thread.sleep(5000L);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(GrupoEsferasViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
