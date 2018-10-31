package br.bispojr.mastermind.jogo.models;

import br.bispojr.mastermind.jogo.viewer.GrupoEsferasViewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JogoModel {
    public static boolean jogando = false;
    public GrupoEsferasModel sorteado;
    public List<GrupoEsferasModel> tentativas = new ArrayList(10);
    public int quantTentativas;
    public int[] resultado = new int[4];

    public String mesagem;


    public JogoModel() {
    }


    public void iniciaJogo() {
        System.out.println("Validando incio de Jogo");
        if (!jogando) {


            sorteado = new GrupoEsferasModel(true);
            System.out.println("Sorteando senha");

            tentativas = new ArrayList();
            System.out.println("Iniando vetor de tentativas");

            for (int i = 0; i < 10; i++) {
                tentativas.add(new GrupoEsferasModel(false));
            }

            jogando = true;

            quantTentativas = 0;
            System.out.println("Zerando quantidade de tentantivas");
        } else {
            try {
                throw new Exception("Jogo em execução impossivel iniciar novamente", new Throwable("Burrice do Desenvolvedor"));
            } catch (Exception ex) {
                Logger.getLogger(JogoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public void pausaJogo() {
        if (jogando) {
            System.out.println("Zerando todos os valores do modelo");
            sorteado = null;
            tentativas = null;
            jogando = false;
        } else {
            try {
                throw new Exception("Jogo já pausado, Nao tem como parar o que está parado", new Throwable("Burrice do Desenvolvedor"));
            } catch (Exception ex) {
                Logger.getLogger(JogoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isJogando() {
        return jogando;
    }


    public ResultadoModel enviaJogada(GrupoEsferasViewer tentativa) {
        tentativas.add(tentativa);
        ResultadoModel resultadoModel = new ResultadoModel(tentativa, sorteado);
        return resultadoModel;
    }

    public static void main(String[] a) throws IOException {
        JogoModel j = new JogoModel();
        j.iniciaJogo();
        GrupoEsferasViewer g = new GrupoEsferasViewer(false);


        System.out.print(j.enviaJogada(g));
    }
}
