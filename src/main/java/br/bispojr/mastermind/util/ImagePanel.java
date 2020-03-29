package br.bispojr.mastermind.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImagePanel extends JComponent {
    private BufferedImage image = null;


    private FillType fillType = FillType.CENTER;


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

    public static enum FillType {
        /**
         * Make the image size equal to the panel size, by resizing it.
         */
        RESIZE {
            @Override
            public void drawImage(JComponent panel, Graphics2D g2d,
                                  BufferedImage image) {
                g2d.drawImage(image, 0, 0, panel.getWidth(), panel.getHeight(),
                        null);
            }
        },
        /**
         * Centers the image on the panel.
         */
        CENTER {
            @Override
            public void drawImage(JComponent panel, Graphics2D g2d,
                                  BufferedImage image) {
                int left = (panel.getHeight() - image.getHeight()) / 2;
                int top = (panel.getWidth() - image.getWidth()) / 2;
                g2d.drawImage(image, top, left, null);
            }
        },
        /**
         * Makes several copies of the image in the panel, putting them side by
         * side.
         */
        SIDE_BY_SIDE {
            @Override
            public void drawImage(JComponent panel, Graphics2D g2d, BufferedImage image) {
                Paint p = new TexturePaint(image, new Rectangle2D.Float(0, 0, image.getWidth(), image.getHeight()));
                g2d.setPaint(p);
                g2d.fillRect(0, 0, panel.getWidth(), panel.getHeight());
            }
        };

        public abstract void drawImage(JComponent panel, Graphics2D g2d, BufferedImage image);
    }
}