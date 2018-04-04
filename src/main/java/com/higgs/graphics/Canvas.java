package main.java.com.higgs.graphics;

import main.java.com.higgs.Universe;
import main.java.com.higgs.obj.Actor;
import main.java.com.higgs.obj.Particle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Canvas extends JPanel {
    BufferedImage lastImage;

    Canvas() {
        setFocusable(true);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(paint(), 0, 0, null);
    }

    private BufferedImage paint() {
        BufferedImage imageResult = new BufferedImage(Viewport.SIZE.width, Viewport.SIZE.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = imageResult.createGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int)Viewport.SIZE.getWidth(), (int)Viewport.SIZE.getHeight());
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        for(Actor actor : new ArrayList<>(Universe.getInstance().getActors())) {
            g.drawImage(actor.getImage(), (int)(actor.getX() - (actor.getImage().getWidth() / 2)), (int)(actor.getY() - (actor.getImage().getHeight() / 2)), null);
        }

        String fps = String.valueOf(Universe.getInstance().getDisplayFPS() + " Total mass: " + Universe.getInstance().getTotalMass());
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.BLACK);
        g.fillRect(2, 2, fm.stringWidth(fps) + 2, fm.getAscent() + 2);
        g.setColor(Color.WHITE);
        g.drawString(fps, 4, 16);
        g.dispose();

        lastImage = imageResult;
        return imageResult;
    }


    public void screenshot() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        File outputFile = new File("Screenshot-" + dateFormat.format(date) + ".png");

        try {
            ImageIO.write(lastImage, "png", outputFile);
            System.out.println("Screenshot saved to: " + outputFile.getName());
        } catch(IOException e) {
            System.out.println("Could not save screenshot.");
        }
    }
}
