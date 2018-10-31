package br.bispojr.mastermind.configuracao;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.io.IOException;


class MyHyperlinkListener
        implements HyperlinkListener {
    MyHyperlinkListener() {
    }

    public void hyperlinkUpdate(HyperlinkEvent evt) {
        if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            JEditorPane pane = (JEditorPane) evt.getSource();
            try {
                pane.setPage(evt.getURL());
            } catch (IOException e) {
            }
        }
    }
}
