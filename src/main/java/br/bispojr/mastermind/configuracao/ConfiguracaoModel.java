package br.bispojr.mastermind.configuracao;

import java.util.Locale;


public class ConfiguracaoModel {
    private boolean musica;
    private boolean efeitos;
    private Locale locale;

    public ConfiguracaoModel(boolean som, Locale locale) {
        musica = som;
        efeitos = som;
        this.locale = locale;
    }

    public ConfiguracaoModel() {
        this(true, new Locale("pt", "BR"));
    }

    public boolean isMusica() {
        return musica;
    }

    public void setMusica(boolean musica) {
        this.musica = musica;
    }

    public boolean isEfeitos() {
        return efeitos;
    }

    public void setEfeitos(boolean efeitos) {
        this.efeitos = efeitos;
    }


    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
