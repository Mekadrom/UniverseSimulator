package com.higgs.sim.graphics;

import com.higgs.sim.input.InputListener;
import com.higgs.sim.utils.ResourceLocation;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

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
