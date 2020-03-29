package br.bispojr.mastermind.configuracao;

import br.bispojr.mastermind.Principal;
import br.bispojr.mastermind.jogo.models.Cronometro;
import br.bispojr.mastermind.jogo.models.ResultadoModel;
import br.bispojr.mastermind.jogo.viewer.PontuacoesViewer;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConfiguracaoControle
        implements Serializable {
    public ConfiguracaoControle() {
    }

    public static Principal tela = null;
    public static ConfiguracaoModel configuracaoModel = new ConfiguracaoModel();
    public static ResourceBundle bundleMesagens = ResourceBundle.getBundle("messages");
    public static ArrayList<ResultadoModel> resultados;
    public static boolean jogando = false;

    public static void inicializaResultados() {
        resultados = Pontuacoes.deserializaListaResultado();
    }


    public static void setCursor(boolean espera) {
        tela.setCursorEspera(espera);
    }

    public static boolean isJogando() {
        return jogando;
    }

    public static void setJogando(boolean jogando) {
        jogando = jogando;
    }

    public static List<ResultadoModel> getResultados() {
        return resultados;
    }


    public static boolean addResultados(ResultadoModel r) {
        resultados.add(r);
        Collections.sort(resultados, new Comparator() {
            public int compare(Object o1, Object o2) {
                ResultadoModel p1 = (ResultadoModel) o1;
                ResultadoModel p2 = (ResultadoModel) o2;
                return p1.tempo.intValue() > p2.tempo.intValue() ? 1 : p1.tempo.intValue() < p2.tempo.intValue() ? -1 : 0;
            }
        });
        new Pontuacoes().serializaListaResultados(resultados);
        return true;
    }

    public static ConfiguracaoModel getConfiguracaoModel() {
        return configuracaoModel;
    }

    public static boolean isMusica() {
        return configuracaoModel.isMusica();
    }

    public static boolean isEfeito() {
        return configuracaoModel.isEfeitos();
    }

    public static void setMusica(boolean som) {
        System.out.print("Setando ativacao de som musica: ");
        configuracaoModel.setMusica(som);
    }

    public static void setEfeito(boolean som) {
        System.out.print("Setando ativacao de som efeitos: ");
        configuracaoModel.setEfeitos(som);
    }

    public static Locale getLocale() {
        return configuracaoModel.getLocale();
    }

    public static void setLocale(Locale locale) {
        System.out.print("Setando escolha de Linguagem: ");
        bundleMesagens = ResourceBundle.getBundle("messages", locale);

        configuracaoModel.setLocale(locale);
    }

    public static void showPontuacoes() {
        String a = "Nome             Tempo\n";
        for (int i = 0; i < resultados.size(); i++) {
            a = a + ((ResultadoModel) resultados.get(i)).getNome() + "       "
                    + Cronometro.retornaTimer(((ResultadoModel) resultados.get(i)).tempo.intValue()) + "\n";

        }
        new PontuacoesViewer(null);
    }


    public static void timeMSforTimeMM_SS() {
    }


    static void showAjuda() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    new Help("/configuracao/Manual Master Mind.htm").setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ConfiguracaoControle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
}
