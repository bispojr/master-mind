package br.bispojr.mastermind.jogo.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ResultadoModel
        implements Serializable {
    public GrupoEsferasModel tentativa;
    public List<Integer> bolinhas;
    public boolean vitoria = false;
    public String nome;
    public Integer tempo;
    public Integer quantTentativas;
    public double pontuacao;

    public double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(double pontuaçao) {
        this.pontuacao = pontuaçao;
    }

    public int getQuantTentativas() {
        return quantTentativas.intValue();
    }

    public void setQuantTentativas(int quantTentativas) {
        this.quantTentativas = Integer.valueOf(quantTentativas);
    }

    public GrupoEsferasModel getTentativa() {
        return tentativa;
    }

    public void setTentativa(GrupoEsferasModel tentativa) {
        this.tentativa = tentativa;
    }

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ResultadoModel(GrupoEsferasModel tentativa, GrupoEsferasModel sorteado) {
        this.tentativa = tentativa;
        bolinhas = new ArrayList();
        verificaJogada(sorteado);
    }

    public ResultadoModel(String nome, Integer tempo, int tentativa) {
        this.nome = nome;
        this.tempo = tempo;
        quantTentativas = Integer.valueOf(tentativa);
    }

    private void verificaJogada(GrupoEsferasModel sorteado) {
        List<Integer> comparar = comparar(tentativa, sorteado);
        setVitoria(comparar);
    }


    public static int BOLA_PRETA = 0;


    public static int BOLA_BRANCA = 1;


    public static int BOLA_NULA = 2;


    public List<Integer> comparar(GrupoEsferasModel esferas1, GrupoEsferasModel esferas2) {
        List<EsferaModel> esf1 = esferas1.getEsferas();
        List<EsferaModel> esf2 = esferas2.getEsferas();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (((EsferaModel) esf1.get(i)).getCor() == ((EsferaModel) esf2.get(j)).getCor()) {
                    if (i == j) {
                        bolinhas.add(Integer.valueOf(BOLA_PRETA));

                    } else if (i != j) {
                        bolinhas.add(Integer.valueOf(BOLA_BRANCA));
                    } else {
                        bolinhas.add(Integer.valueOf(BOLA_NULA));
                    }
                }
            }
        }


        Collections.sort(bolinhas);
        return bolinhas;
    }

    public boolean setVitoria(List<Integer> bolinhas) {
        int cont = 0;
        for (int i = 0; i < bolinhas.size(); i++) {
            if (((Integer) bolinhas.get(i)).intValue() == BOLA_PRETA) {
                cont++;
            }
        }
        if (cont == 4) {
            vitoria = true;
        } else {
            vitoria = false;
        }
        return vitoria;
    }

    public boolean isVitoria() {
        return vitoria;
    }

    public String toString() {
        return "nome=" + nome + ", tempo=" + tempo + "\n";
    }

    public List<Integer> getBolinhas() {
        return bolinhas;
    }
}
