package com.higgs.sim.graphics;

import com.higgs.sim.Universe;
import com.higgs.sim.obj.Actor;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Canvas extends JPanel {
    Canvas() {
        this.setFocusable(true);
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        g.drawImage(this.paint(), 0, 0, null);
    }

    private BufferedImage paint() {
        final BufferedImage imageResult = new BufferedImage(Viewport.SIZE.width, Viewport.SIZE.height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = imageResult.createGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int) Viewport.SIZE.getWidth(), (int) Viewport.SIZE.getHeight());
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        for (final Actor actor : new ArrayList<>(Universe.getInstance().getActors())) {
//            BufferedImage imgNew = new BufferedImage(actor.getImage().getWidth(), actor.getImage().getHeight(), actor.getImage().getType());
//            Graphics2D g2d = (Graphics2D)imgNew.getGraphics();
//            g2d.rotate(actor.getRotation(), actor.getImage().getWidth()/2, actor.getImage().getHeight()/2);
//            g2d.drawImage(actor.getImage(), 0, 0, null);
            g.drawImage(actor.getImage(), (int) (actor.getX() - (actor.getImage().getWidth() / 2)), (int) (actor.getY() - (actor.getImage().getHeight() / 2)), null);
        }

        final String fps = Universe.getInstance().getDisplayFPS() + " Total mass: " + Universe.getInstance().getTotalMass();
        final FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.BLACK);
        g.fillRect(2, 2, fm.stringWidth(fps) + 2, fm.getAscent() + 2);
        g.setColor(Color.WHITE);
        g.drawString(fps, 4, 16);
        g.dispose();

        return imageResult;
    }


    public void screenshot() {
        final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        final Date date = new Date();
        final File outputFile = new File("Screenshot-" + dateFormat.format(date) + ".png");

        try {
            ImageIO.write(this.paint(), "png", outputFile);
            System.out.println("Screenshot saved to: " + outputFile.getName());
        } catch (final IOException e) {
            System.out.println("Could not save screenshot.");
        }
    }
}
