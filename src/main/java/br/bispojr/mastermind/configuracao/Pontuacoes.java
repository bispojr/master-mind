package br.bispojr.mastermind.configuracao;

import br.bispojr.mastermind.jogo.models.Cronometro;
import br.bispojr.mastermind.jogo.models.ResultadoModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Pontuacoes
        implements Serializable {
    public static final String ARQUIVO = "pontuacao.mstr";
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public Pontuacoes() {
    }

    public void serializaListaResultados(ArrayList<ResultadoModel> listaTenis) {
        List<Rank> lista = new ArrayList();
        for (int i = 0; i < listaTenis.size(); i++) {
            String nome = ((ResultadoModel) listaTenis.get(i)).getNome();
            Integer tempo = ((ResultadoModel) listaTenis.get(i)).getTempo();
            Integer qntTentativas = listaTenis.get(i).getQuantTentativas();

            Rank rank = new Rank(nome, tempo, qntTentativas);
            lista.add(rank);
        }
        FileOutputStream arq = null;
        out = null;
        try {
            arq = new FileOutputStream("pontuacao.mstr");

            out = new ObjectOutputStream(arq);

            out.writeObject(lista);
            return;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                arq.close();
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static ArrayList<ResultadoModel> deserializaListaResultado() {
        FileInputStream arqLeitura = null;
        in = null;
        ArrayList<Rank> lista = null;
        if (!new File("pontuacao.mstr").exists()) {
            return new ArrayList();
        }
        try {
            arqLeitura = new FileInputStream("pontuacao.mstr");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pontuacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            in = new ObjectInputStream(arqLeitura);
        } catch (IOException ex) {
            Logger.getLogger(Pontuacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            lista = (ArrayList) in.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Pontuacoes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Pontuacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            arqLeitura.close();
        } catch (IOException ex) {
            Logger.getLogger(Pontuacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(Pontuacoes.class.getName()).log(Level.SEVERE, null, ex);
        }


        if (lista == null) {
            return new ArrayList();
        }
        ArrayList<ResultadoModel> r = new ArrayList();
        for (int i = 0; i < lista.size(); i++) {
            r.add(new ResultadoModel(((Rank) lista.get(i)).getNome(), ((Rank) lista.get(i)).getTempoMS(), ((Rank) lista.get(i)).getQntTentativas().intValue()));
        }
        return r;
    }

    class Rank implements Serializable {
        String nome;
        Integer tempoMS;
        Integer qntTentativas;

        public Rank(String nome, Integer tempoMS, Integer qntTentativas) {
            this.nome = nome;
            this.tempoMS = tempoMS;
            this.qntTentativas = qntTentativas;
        }

        public Rank(String nome, Integer tempoMS) {
            this(nome, tempoMS, null);
        }

        public Integer getQntTentativas() {
            return qntTentativas;
        }

        public void setQntTentativas(Integer qntTentativas) {
            this.qntTentativas = qntTentativas;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Integer getTempoMS() {
            return tempoMS;
        }

        public void setTempoMS(Integer tempoMS) {
            this.tempoMS = tempoMS;
        }

        public String returnRelatorio() {
            return null;
        }

        public String toString() {
            return "Nome: " + nome + "\nTempo: " + Cronometro.retornaTimer(tempoMS.intValue()) + "\nQuantidade de Jogadas: " + qntTentativas;
        }
    }
}
