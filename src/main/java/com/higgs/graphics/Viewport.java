package main.java.com.higgs.graphics;

import main.java.com.higgs.input.InputListener;
import main.java.com.higgs.utils.ResourceLocation;

import javax.swing.*;
import java.awt.*;

public class Viewport extends JFrame {
    public static Dimension SIZE = /**Toolkit.getDefaultToolkit().getScreenSize();**/new Dimension(1366, 820);
    private static Canvas panel;

    public Viewport() {
        setTitle("Universe Simulation");
        setSize(SIZE);
        setLocationRelativeTo(null);
        setResizable(false);
        setFocusable(true);
        addKeyListener(new InputListener());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        panel = new Canvas();
        getContentPane().add(panel);
        setVisible(true);

        setIconImage(ResourceLocation.getImage("icons/icon.png"));
    }

    public static Canvas getCanvas() {
        return panel;
    }

    public void render() {
        panel.repaint();
    }
}
