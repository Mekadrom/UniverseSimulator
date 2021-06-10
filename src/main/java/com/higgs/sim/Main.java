package com.higgs.sim;

import com.higgs.staged.StagedPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

public final class Main {
    private static final Dimension SIZE = new Dimension(1366, 768);

    public static void main(final String[] args) {
        final JFrame frame = new JFrame("Universe Simulator");

        final JPanel contentPane = new JPanel(new BorderLayout());

        frame.setContentPane(contentPane);

        frame.setSize(Main.SIZE);
        contentPane.setSize(Main.SIZE);

        final Universe universe = new Universe(contentPane);

        final StagedPanel panel = new StagedPanel(universe, false);

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.setEnabled(true);
        frame.setVisible(true);

        panel.start();
    }
}
