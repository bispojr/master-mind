package br.bispojr.mastermind.jogo.viewer;


import br.bispojr.mastermind.audio.TocarAudio;
import br.bispojr.mastermind.configuracao.ConfiguracaoControle;
import br.bispojr.mastermind.jogo.models.Cronometro;
import br.bispojr.mastermind.jogo.models.EsferaModel;
import br.bispojr.mastermind.jogo.models.JogoModel;
import br.bispojr.mastermind.jogo.models.ResultadoModel;
import br.bispojr.mastermind.jogo.observer.Observador;
import br.bispojr.mastermind.jogo.viewer.util.Dialog;
import br.bispojr.mastermind.util.ImagePanel;
import br.bispojr.mastermind.util.MButton;
import br.bispojr.mastermind.util.UpdateLabels;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class PainelJogoViewer
        extends JComponent
        implements UpdateLabels, Runnable, Observador {
    public JogoModel jogoModel;
    List<GrupoEsferasViewer> grupos;
    private ImagePanel fundoEnviar;
    MButton enviar;
    int jogada = 0;
    public ImagePanel fundoPainel;
    public Cronometro timer;
    public static final Point POSICAO_ENVIAR = new Point(9, 424);
    public static final int DESLOCAMENTO_ENTRE_GRUPOS = 45;
    public static final Dimension SIZE_PANEL = new Dimension(317, 545);
    private List<ResultadoViewer> lista;
    public MButton btJogar;
    public ImagePanel fundoTimer;
    private JLabel lbTimer;
    public GrupoEsferasViewer gSenha;
    Thread t = new Thread();
    public JLabel labelSenha;
    public GrupoEsferasViewer grupoSenha;
    TocarAudio tocar;

    public PainelJogoViewer() throws IOException {
        setSize(318, 549);

        setLayout(null);
        fundoPainel = new ImagePanel("/images/jogo/fundoJogadas.png");
        fundoPainel.setLayout(null);
        fundoPainel.setSize(SIZE_PANEL);
        fundoPainel.setLocation(0, 0);
        add(fundoPainel, "Center");
        jogoModel = new JogoModel();

        btJogar = new MButton("/images/jogo/btNovoJogo.png", "/images/jogo/btNovoJogo.png", "/images/jogo/btNovoJogo2.png");
        btJogar.setForeground(Color.white);
        btJogar.setRolloverEnabled(true);
        btJogar.setFont(new Font("Arial", 1, 20));
        btJogar.setText(ConfiguracaoControle.bundleMesagens.getString("lbNovoJogo"));
        setBotaoInicia();
        btJogar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!jogoModel.isJogando()) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                System.out.println("Iniciando jogo");
                                iniciaJogo();
                            } catch (IOException ex) {
                                Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }).start();
                } else {
                    try {
                        System.out.println("Parando o jogo");
                        paraJogo();
                    } catch (IOException ex) {
                        Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        timer = new Cronometro();
        timer.para();


        fundoTimer = new ImagePanel("/images/jogo/fundoTempo.png");
        fundoTimer.setLocation(81, 1);
        fundoTimer.setSize(150, 54);
        fundoPainel.add(fundoTimer);

        lbTimer = new JLabel();
        lbTimer.setHorizontalTextPosition(0);
        lbTimer.setVerticalTextPosition(0);
        lbTimer.setSize(100, 54);
        lbTimer.setFont(new Font("Arial", 1, 30));
        lbTimer.setText(getTimer());
        lbTimer.setLocation(35, 0);


        fundoTimer.add(lbTimer);

        fundoEnviar = new ImagePanel("/images/jogo/fundoEnviar.png");
        fundoEnviar.setLocation(185, 59);
        fundoEnviar.setSize(115, 502);

        enviar = new MButton("/images/jogo/btEnviar1.png", "/images/jogo/btEnviar1.png", "/images/jogo/btEnviar2.png");
        enviar.setRolloverEnabled(true);
        enviar.setSize(88, 33);
        enviar.setText(ConfiguracaoControle.bundleMesagens.getString("btEnviar"));
        enviar.setForeground(Color.WHITE);
        enviar.setHorizontalTextPosition(0);
        enviar.setVerticalTextPosition(0);
        enviar.setLocation(POSICAO_ENVIAR);
        enviar.setVisible(false);
        enviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    try {
                        enviar();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JavaLayerException ex) {
                        Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        labelSenha = new JLabel("<html><center>" + ConfiguracaoControle.bundleMesagens.getString("labelSenha") + "</html>");
        labelSenha.setFont(new Font("Arial", 1, 20));
        labelSenha.setLocation(20, 550);
        labelSenha.setSize(100, 30);
        labelSenha.setVisible(false);
        labelSenha.setHorizontalTextPosition(0);
        labelSenha.setVerticalTextPosition(0);

        grupoSenha = new GrupoEsferasViewer(false);
        grupoSenha.setStatus(GrupoEsferasViewer.Status.ESPERA);
        grupoSenha.setLocation(80, 540);
        grupoSenha.setSize(179, 46);
        grupoSenha.setVisible(false);

        t = new Thread(this);
        t.start();
        fundoPainel.add(fundoEnviar);
        fundoEnviar.add(enviar);
        iniciaGrupos();
    }

    public void setBotaoInicia() {
        if (jogoModel.isJogando()) {
            btJogar.setIcon("/images/jogo/btDesistir.png");
            btJogar.setRolloverIcon("/images/jogo/btDesistir2.png");
            btJogar.setRolloverEnabled(false);
            btJogar.setText(ConfiguracaoControle.bundleMesagens.getString("lbDesistirJogo"));
        } else {
            btJogar.setIcon("/images/jogo/btNovoJogo.png");
            btJogar.setRolloverIcon("/images/jogo/btNovoJogo2.png");
            btJogar.setText(ConfiguracaoControle.bundleMesagens.getString("lbNovoJogo"));
        }
    }

    public MButton getBtJogar() {
        return btJogar;
    }

    private void iniciaGrupos() throws IOException {
        grupos = new ArrayList();
        grupos = new ArrayList();
        for (int i = 0; i < 10; i++) {
            GrupoEsferasViewer g = new GrupoEsferasViewer(false, GrupoEsferasViewer.Status.ESPERA);
            g.setLocation(10, 477 - 45 * i);
            g.setSize(179, 48);
            grupos.add(g);
            fundoPainel.add(g);
        }
    }

    public void iniciaJogo() throws IOException {
        System.out.println("Liberando grupo de respostas");
        try {
            for (int i = 0; i < lista.size(); i++) {
                ((ResultadoViewer) lista.get(i)).setVisible(false);
            }
        } catch (Exception e) {
        }

        lista = new ArrayList();
        System.out.println("Iniciando o model de jogo para rodar");

        jogoModel.iniciaJogo();

        System.out.println("Preparando interface para para o  modelo");

        grupoSenha.setEsferas(jogoModel.sorteado.getEsferas());
        grupoSenha.setStatus(GrupoEsferasViewer.Status.ESPERA);
        grupoSenha.repaint();
        new Thread(new Runnable() {
            public void run() {
                ConfiguracaoControle.tela.setCursorEspera(true);

                for (int i = grupos.size() - 1; i > -1; i--) {
                    try {
                        ((GrupoEsferasViewer) grupos.get(i)).setStatus(GrupoEsferasViewer.Status.ESPERA);
                        try {
                            ((GrupoEsferasViewer) grupos.get(0)).setStatus(GrupoEsferasViewer.Status.ATIVO);
                        } catch (IOException ex) {
                            Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ((GrupoEsferasViewer) grupos.get(i)).limpaEsferas();
                }


                enviar.setVisible(true);
                timer.reseta();
                ConfiguracaoControle.tela.setCursorEspera(false);


            }


        }).start();
        setBotaoInicia();

        labelSenha.setVisible(true);
        grupoSenha.setVisible(true);

        System.out.println("Iniciando o Timer");
        ConfiguracaoControle.setJogando(jogoModel.isJogando());
    }


    public void paraJogo()
            throws IOException {
        jogoModel.pausaJogo();
        System.out.println("Entrando no modelo\n Parando timer");
        timer.para();
        enviar.setVisible(false);
        System.out.println("Desabilitando botao enviar");

        System.out.println("");
        ((GrupoEsferasViewer) grupos.get(jogada)).setStatus(GrupoEsferasViewer.Status.ENVIADO);
        enviar.setVisible(false);
        enviar.setLocation(POSICAO_ENVIAR);
        jogada = 0;
        setBotaoInicia();


        grupoSenha.setStatus(GrupoEsferasViewer.Status.ENVIADO);
        ConfiguracaoControle.setJogando(jogoModel.isJogando());
    }

    public String getTimer() {
        return timer.retornaTimer();
    }

    private boolean validaJogada() throws IOException, FileNotFoundException, JavaLayerException {
        Dialog dialog;

        System.out.println("Validando Jogada");
        if (((GrupoEsferasViewer) grupos.get(jogada)).contemNull()) {
            System.out.println("Contem valor vazio");

            if (ConfiguracaoControle.isEfeito()) {
                tocar = new TocarAudio("/audio/erro.mp3");
                tocar(tocar);
            }
            try {
                //FIXME Verificar variavel
                dialog = new Dialog(enviar, "Contém valores vazios", ConfiguracaoControle.bundleMesagens.getString("coresVasias"));
            } catch (IOException ex) {
                Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
        if (((GrupoEsferasViewer) grupos.get(jogada)).contemRepetido()) {
            System.out.println("Contem valor repetido");

            if (ConfiguracaoControle.isEfeito()) {
                tocar = new TocarAudio("/audio/erro.mp3");
                tocar(tocar);
            }
            try {
                dialog = new Dialog(enviar, "Contém valores repedidos", ConfiguracaoControle.bundleMesagens.getString("coresRepetidas"));
            } catch (IOException ex) {
                Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
            }

            return false;
        }
        System.out.println("Jogada valida");

        return true;
    }

    public void enviar() throws IOException, FileNotFoundException, JavaLayerException {
        ResultadoModel resultado = null;
        System.out.println("Precionado enviar");

        if (validaJogada()) {
            System.out.println("");
            Rectangle bounds = enviar.getBounds();

            if (jogada == 9) {
                System.out.println("Ultima chance");

                resultado = jogoModel.enviaJogada((GrupoEsferasViewer) grupos.get(jogada));
                ((GrupoEsferasViewer) grupos.get(jogada)).setStatus(GrupoEsferasViewer.Status.ENVIADO);
                insereBolinhas(bounds, resultado);
                if (resultado.isVitoria()) {
                    mostraSenha();
                    processaVitoria(resultado, jogada);
                } else {
                    System.out.println("PERDEU");
                    mostraSenha();
                    if (ConfiguracaoControle.isEfeito()) {
                        tocar = new TocarAudio("/audio/perdeu.mp3");
                        tocar(tocar);
                    }
                    //FIXME Verifficar esse construtur
                    Dialog dialog = new Dialog(enviar, "", ConfiguracaoControle.bundleMesagens.getString("perdeu"));
                    ((GrupoEsferasViewer) grupos.get(jogada)).setStatus(GrupoEsferasViewer.Status.ENVIADO);
                }
                paraJogo();

            } else {

                resultado = jogoModel.enviaJogada((GrupoEsferasViewer) grupos.get(jogada));

                if (resultado.isVitoria()) {
                    insereBolinhas(bounds, resultado);

                    ((GrupoEsferasViewer) grupos.get(jogada)).setStatus(GrupoEsferasViewer.Status.ENVIADO);
                    int jog = jogada;
                    mostraSenha();
                    paraJogo();
                    processaVitoria(resultado, jog);
                } else {
                    insereBolinhas(bounds, resultado);
                    System.out.println("Liberando nova tentativa");

                    ((GrupoEsferasViewer) grupos.get(jogada)).setStatus(GrupoEsferasViewer.Status.ENVIADO);
                    ((GrupoEsferasViewer) grupos.get(jogada + 1)).setStatus(GrupoEsferasViewer.Status.ATIVO);
                    jogada += 1;
                    enviar.setLocation(enviar.getX(), enviar.getY() - 45);
                }
            }
        }
    }


    public void tocar(final TocarAudio tocar) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    tocar.play();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JavaLayerException ex) {
                    Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public void mostraSenha() throws IOException {
        List<EsferaModel> sorteado = jogoModel.sorteado.getEsferas();
        System.out.print(jogoModel.sorteado.getEsferas());
        System.out.println("Libera senha? " + sorteado);
    }

    public void processaVitoria(ResultadoModel resultado, int tentativas) throws IOException {
        String msg = ConfiguracaoControle.bundleMesagens.getString("vitoria");
        tocar = new TocarAudio("/audio/Vitoria.mp3");
        tocar(tocar);
        System.out.println("Ganohu");

        String nome = new Dialog(enviar, msg, msg, true).getNome();
        System.out.println("Pegou o nome do Jogador: " + nome);

        resultado.setNome(nome);
        resultado.setTempo(Integer.valueOf((int) timer.tempoTotal()));
        resultado.setQuantTentativas(tentativas + 1);
        System.out.println("Liberando resultado de vitoria " + resultado);


        ConfiguracaoControle.addResultados(resultado);
    }

    public void insereBolinhas(Rectangle bounds, ResultadoModel resultado) throws IOException {
        ResultadoViewer resp = new ResultadoViewer(resultado.getBolinhas());
        System.out.println("Gerando resultado grafico");

        resp.setBounds(bounds);
        lista.add(resp);
        fundoEnviar.add(resp);
    }

    public boolean isJogando() {
        return jogoModel.isJogando();
    }

    public static void main(String[] a) {
        JFrame j = new JFrame();
        j.setSize(600, 600);
        j.setLayout(null);

        PainelJogoViewer p = null;
        try {
            p = new PainelJogoViewer();
            p.iniciaJogo();
        } catch (IOException ex) {
            Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
        }

        j.getContentPane().add(p);
        j.setDefaultCloseOperation(3);
        j.setVisible(true);
    }

    public void updateLabels() {
        enviar.setText(ConfiguracaoControle.bundleMesagens.getString("btEnviar"));
        fundoEnviar.repaint();
    }

    public String getTextosLabels() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void run() {
        try {
            for (; ; ) {
                lbTimer.setText(getTimer());
                Thread.sleep(1L);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(PainelJogoViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void atualizar() {
        setBotaoInicia();


        enviar.setText(ConfiguracaoControle.bundleMesagens.getString("btEnviar"));
        labelSenha.setText("<html><center>" + ConfiguracaoControle.bundleMesagens.getString("labelSenha") + "</html>");
    }
}
