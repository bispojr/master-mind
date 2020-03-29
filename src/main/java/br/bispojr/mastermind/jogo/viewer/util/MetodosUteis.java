package br.bispojr.mastermind.jogo.viewer.util;

import javax.swing.*;
import java.awt.Dialog;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetodosUteis {

    public static void configureNimbus() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(java.awt.Dialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(java.awt.Dialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(java.awt.Dialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
