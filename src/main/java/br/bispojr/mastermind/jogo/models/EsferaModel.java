package br.bispojr.mastermind.jogo.models;

import javax.swing.*;
import java.io.Serializable;


public class EsferaModel
        extends JComponent
        implements Serializable {
    public static String[] IMAGES = {"/jogo/vazio.png", "/jogo/azul.png", "/jogo/amarelo.png", "/jogo/rosa.png", "/jogo/roxo.png", "/jogo/laranja.png", "/jogo/marrom.png"};


    private static final String[] SONS = {"vazio.mp3", "azul.mp3", "amarelo.mp3", "rosa.mp3", "roxo.mp3", "laranja.mp3", "marrom.mp3"};


    private static final int VAZIO = 0;


    private static final int AZUL = 1;


    private static final int AMARELO = 2;


    private static final int ROSA = 3;


    private static final int ROXO = 4;


    private static final int LARANJA = 5;


    private static final int MARROM = 6;


    private int cor;


    private String img;


    private String som;


    public EsferaModel(int cor) {
        if ((cor >= 0) && (cor <= 6)) {
            this.cor = cor;
            som = SONS[cor];
            img = IMAGES[cor];
        } else {
            throw new NumberFormatException("Cor inexistente!: " + cor);
        }
    }


    /**
     * @deprecated
     */
    public EsferaModel() {
        this(0);
    }


    public void setCor(int cor) {
        img = IMAGES[cor];
        som = SONS[cor];
        this.cor = cor;
    }


    public int getCor() {
        return cor;
    }


    public String[] getCores() {
        return IMAGES;
    }

    public String getPachImg() {
        return img;
    }

    public String getPachSom() {
        return som;
    }

    public int hashCode() {
        int hash = 7;
        return hash;
    }


    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EsferaModel other = (EsferaModel) obj;
        if (cor != cor) {
            return false;
        }
        return true;
    }

    public String toString() {
        if (cor == 0) {
            return cor + " - Vazio";
        }
        if (cor == 1) {
            return cor + " - Azul";
        }
        if (cor == 2) {
            return cor + " - Amarelo";
        }
        if (cor == 3) {
            return cor + " - Rosa";
        }
        if (cor == 4) {
            return cor + " - Roxo";
        }
        if (cor == 5) {
            return cor + " - Laranja";
        }
        if (cor == 6) {
            return cor + " - Cinza";
        }
        return cor + "ERRO DE COR";
    }

    public static void main(String[] a) {
        EsferaModel esf0 = new EsferaModel(0);
        EsferaModel esf1 = new EsferaModel(1);
        EsferaModel esf2 = new EsferaModel(2);
        EsferaModel esf3 = new EsferaModel(3);
        EsferaModel esf4 = new EsferaModel(4);
        EsferaModel esf5 = new EsferaModel(5);
        EsferaModel esf6 = new EsferaModel(6);
        System.out.println(esf0.getPachImg());
    }
}
