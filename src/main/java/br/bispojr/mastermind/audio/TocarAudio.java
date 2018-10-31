package br.bispojr.mastermind.audio;

import br.bispojr.mastermind.configuracao.ConfiguracaoControle;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class TocarAudio {
    private File mp3;
    private Player player;
    private String nomearquivo;

    public TocarAudio(String p) {
        nomearquivo = p;
    }

    public boolean tocando = true;
    InputStream is;

    boolean isComplet() {
        return player.isComplete();
    }

    BufferedInputStream bis;

    public void play()
            throws FileNotFoundException, JavaLayerException {
        is = getClass().getResourceAsStream(nomearquivo);
        bis = new BufferedInputStream(is);
        player = new Player(bis);
        player.play();
    }

    public void playFundo() throws FileNotFoundException, JavaLayerException {
        if (ConfiguracaoControle.isMusica()) {
            play();
            while (!isComplet()) {
            }
        }
    }


    public void pause() {
        player.close();
    }

    public static void main(String[] a) throws FileNotFoundException, JavaLayerException, InterruptedException {
        String path = "/audio/jogo.mp3";

        TocarAudio musica = new TocarAudio(path);
        musica.playFundo();
    }
}
