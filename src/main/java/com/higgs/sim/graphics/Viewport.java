package com.higgs.sim.graphics;

import com.higgs.sim.input.InputListener;
import com.higgs.sim.utils.ResourceLocation;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class Viewport extends JFrame {
    public static Dimension SIZE = /*Toolkit.getDefaultToolkit().getScreenSize();*/new Dimension(1366, 820);
    private static Canvas panel;

    public Viewport() {
        this.setTitle("Universe Simulation");
        this.setSize(Viewport.SIZE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setFocusable(true);
        this.addKeyListener(new InputListener());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        Viewport.panel = new Canvas();
        this.getContentPane().add(Viewport.panel);
        this.setVisible(true);

        this.setIconImage(ResourceLocation.getImage("icons/icon.png"));
    }

    public static Canvas getCanvas() {
        return Viewport.panel;
    }

    public void render() {
        Viewport.panel.repaint();
    }
}
