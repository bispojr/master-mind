package br.bispojr.mastermind.jogo.viewer;

import br.bispojr.mastermind.configuracao.ConfiguracaoControle;
import br.bispojr.mastermind.jogo.models.ResultadoModel;
import br.bispojr.mastermind.util.ImagePanel;
import br.bispojr.mastermind.util.UpdateLabels;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public final class ResultadoViewer
  extends JComponent
  implements UpdateLabels
{
  private ImagePanel preta;
  private ImagePanel branca;
  
  public ResultadoViewer(List<Integer> bolinhas)
    throws IOException
  {
    setLayout(null);
    setSize(74, 27);
    

    preta = new ImagePanel("/jogo/preta.png");
    branca = new ImagePanel("/jogo/branca.png");
    
    preta.setSize(18, 18);
    preta.setLocation(5, 4);
    branca.setSize(18, 18);
    branca.setLocation(5, 4);
    
    adicionaResposta(bolinhas);
    setToolTipText(setToll());
  }
  
  public String setToll() {
    String corCorreta = ConfiguracaoControle.bundleMesagens.getString("msgToll_CorCorreta");
    String corCorretaPcorreta = ConfiguracaoControle.bundleMesagens.getString("msgToll_CorPCorreta");
    return "<html>" + corCorreta + (corp + corb) + "<br>" + corCorretaPcorreta + corp; }
  
  int corb = 0;
  int corp = 0;
  
  ImagePanel criaBolinha(int cor, int x, int y) throws IOException {
    ImagePanel b = null;
    if (cor == 0) {
      b = new ImagePanel("/jogo/preta.png");
      corp += 1;
    }
    if (cor == 1) {
      b = new ImagePanel("/jogo/branca.png");
      corb += 1;
    }
    b.setSize(18, 18);
    b.setLocation(5 + x, y + 5);
    return b;
  }
  
  public void adicionaResposta(List<Integer> bolinhas) throws IOException {
    int p = 0;
    int b = 0;
    for (int i = 0; i < bolinhas.size(); i++)
    {
      if (((Integer)bolinhas.get(i)).equals(Integer.valueOf(ResultadoModel.BOLA_BRANCA))) {
        if (i == 0) {
          add(criaBolinha(1, 0, 0));
        } else {
          add(criaBolinha(1, 17 * i, 0));
        }
        b++;
      }
      if (((Integer)bolinhas.get(i)).equals(Integer.valueOf(ResultadoModel.BOLA_PRETA))) {
        ImagePanel t = preta;
        if (i == 0) {
          add(criaBolinha(0, 0, 0));
        } else {
          add(criaBolinha(0, 17 * i, 0));
        }
        p++;
      }
    }
  }
  
  public static void main(String[] a) throws IOException {
    JFrame j = new JFrame();
    j.setSize(500, 600);
    j.setLayout(null);
    ArrayList<Integer> lista = new ArrayList();
    lista.add(Integer.valueOf(0));
    lista.add(Integer.valueOf(0));
    lista.add(Integer.valueOf(1));
    lista.add(Integer.valueOf(1));
    ResultadoViewer p = new ResultadoViewer(lista);
    

    j.getContentPane().add(p);
    j.setDefaultCloseOperation(3);
    j.setVisible(true);
  }
  
  public void updateLabels()
  {
    setToolTipText(setToll());
  }
  
  public String getTextosLabels()
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
