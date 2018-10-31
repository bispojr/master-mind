package br.bispojr.mastermind.jogo.models;

public class Cronometro {
    private boolean decrescente;
    private long total;
    private long inicio;
    private long atual;
    private boolean parado;
    private boolean expirado;

    public Cronometro() {
        decrescente = false;
        parado = false;
        total = 0L;
        atual = 0L;
        inicio = System.currentTimeMillis();
    }


    public Cronometro(long milisegundos) {
        decrescente = true;
        parado = false;
        total = milisegundos;
        atual = milisegundos;
        inicio = System.currentTimeMillis();
    }


    public long tempoTotal() {
        if (!parado) {
            atualiza();
        }
        return atual - inicio;
    }


    public long tempoRestante() {
        if (!parado) {
            atualiza();
        }
        return total - atual + inicio;
    }


    public void reseta() {
        parado = false;
        expirado = false;
        inicio = System.currentTimeMillis();
        atual = inicio;
    }


    /**
     * @deprecated
     */
    public void reseta(long milisegundos) {
        parado = false;
        expirado = false;
        total = milisegundos;
        atual = milisegundos;
        inicio = System.currentTimeMillis();
    }


    /**
     * @deprecated
     */
    public boolean expirou() {
        if (!expirado) {
            atualiza();
        } else {
            return true;
        }

        if (decrescente) {
            if (atual - inicio >= total) {
                parado = true;
                expirado = true;
                return true;
            }
            return false;
        }

        return false;
    }


    public void para() {
        if (!parado) {
            atualiza();
        }
        parado = true;
    }


    public void continua() {
        if ((expirado) || (!parado)) {
            return;
        }

        long delta = atual - inicio;

        atual = System.currentTimeMillis();
        inicio = (atual - delta);
        parado = false;
    }


    public void atualiza() {
        atual = System.currentTimeMillis();
    }


    public String retornaTimer() {
        int segundos = (int) (tempoTotal() / 1000L);
        int seg = segundos < 60 ? segundos : segundos % 60;
        int min = segundos / 60;
        return (min >= 10 ? "" : "0") + min + ":" + (seg >= 10 ? "" : "0") + seg;
    }

    public static String retornaTimer(int tempoTotal) {
        int segundos = tempoTotal / 1000;
        int seg = segundos < 60 ? segundos : segundos % 60;
        int min = segundos / 60;
        return (min >= 10 ? "" : "0") + min + ":" + (seg >= 10 ? "" : "0") + seg;
    }


    public static void main(String[] a)
            throws InterruptedException {
        Cronometro c = new Cronometro();
        for (; ; ) {
            Thread.sleep(1000L);
            System.out.println(c.retornaTimer());
        }
    }
}
