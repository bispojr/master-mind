package br.bispojr.mastermind.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImagePanel
        extends JComponent {
    private BufferedImage image = null;


    private FillType fillType = FillType.DEFAULT;


    public ImagePanel(BufferedImage img) {
        setImage(img);
    }


    public ImagePanel(File imgSrc)
            throws IOException {
        this(ImageIO.read(imgSrc));
    }


    public ImagePanel(String fileName)
            throws IOException {
        setImage(ImageIO.read(getClass().getResource(fileName)));
    }


    public final void setImage(BufferedImage img) {
        if (img == null) {
            throw new NullPointerException("Sem imagem para processar!");
        }
        image = img;
        invalidate();
        updateUI();
    }


    public void setImage(File img)
            throws IOException {
        setImage(ImageIO.read(img));
    }


    public void setImage(String fileName)
            throws IOException {
        setImage(ImageIO.read(getClass().getResource(fileName)));
    }


    public BufferedImage getImage() {
        return image;
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        fillType.drawImage(this, g2d, image);
        g2d.dispose();
    }


    public FillType getFillType() {
        return fillType;
    }


    public void setFillType(FillType fillType) {
        if (fillType == null) {
            throw new IllegalArgumentException("Invalid fill type!");
        }
        this.fillType = fillType;
        invalidate();
    }


    /**
     * FIXME Verificar porque nao aceita abstract (Ver versão do Java)
     */

    public static abstract enum FillType {
        DEFAULT, RESIZE, CENTER, SIDE_BY_SIDE, ASPECT_RATIO, ASPECT_RATIO_CENTER;

        private FillType() {
        }

        public abstract void drawImage(JComponent paramJComponent, Graphics2D paramGraphics2D, BufferedImage paramBufferedImage);
    }
}