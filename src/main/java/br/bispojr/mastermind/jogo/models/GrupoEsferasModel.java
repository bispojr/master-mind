package br.bispojr.mastermind.jogo.models;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GrupoEsferasModel
        extends JComponent
        implements Serializable {
    private final int tamanhoGrupo = 4;


    private List<EsferaModel> esferas = new ArrayList(4);


    private boolean sorteavel;


    public GrupoEsferasModel(boolean sorteavel) {
        this.sorteavel = sorteavel;
        iniciaEsferas();
    }


    public GrupoEsferasModel(List<EsferaModel> esferas) {
        this(false);
        this.esferas = esferas;
    }


    public GrupoEsferasModel() {
        sorteavel = false;
    }


    public List<EsferaModel> getEsferas() {
        return esferas;
    }


    public EsferaModel getEsfera(int index) {
        return (EsferaModel) esferas.get(index);
    }


    public void setEsferas(List<EsferaModel> esferas) {
        this.esferas = esferas;
    }


    public void setEsfera(EsferaModel esfera, int index) {
        esferas.set(index, esfera);
    }


    public boolean isSorteavel() {
        return sorteavel;
    }

    /**
     * @deprecated
     */
    public void setSoreavel(boolean sorteavel) {
        this.sorteavel = sorteavel;
    }


    private void iniciaEsferas() {
        if (sorteavel) {

            esferas = new ArrayList(4);

            for (int i = 0; i < 4; i++) {
                boolean tem = false;

                int temp = (int) (6.0D * Math.random());

                temp++;

                for (int j = 0; j < esferas.size(); j++) {
                    if (((EsferaModel) esferas.get(j)).getCor() == temp) {
                        tem = true;
                    }
                }

                if (tem) {
                    i--;
                } else {
                    esferas.add(i, new EsferaModel(temp));
                }
            }
        } else {
            esferas = new ArrayList(4);
            esferas.add(new EsferaModel(0));
            esferas.add(new EsferaModel(0));
            esferas.add(new EsferaModel(0));
            esferas.add(new EsferaModel(0));
        }
    }


    public String toString() {
        return "Grupo de Esferas:\n Tamanho: 4\n Esferas: " + esferas + "\n Sorteavel:" + sorteavel + "\n--------------------------------\n\n";
    }


    public static void main(String[] a) {
        System.out.print(new GrupoEsferasModel(true));
        System.out.print(new GrupoEsferasModel(true));
        System.out.print(new GrupoEsferasModel(true));
        System.out.print(new GrupoEsferasModel(true));
        System.out.print(new GrupoEsferasModel(true));
        System.out.print(new GrupoEsferasModel(true));
        System.out.print(new GrupoEsferasModel(true));
        System.out.print(new GrupoEsferasModel(true));
        System.out.print(new GrupoEsferasModel(true));
        System.out.print(new GrupoEsferasModel(false));
    }
}
