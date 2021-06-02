package com.higgs.sim.input;

import com.higgs.sim.graphics.Viewport;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {
    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        final int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_F12: {
                Viewport.getCanvas().screenshot();
                break;
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }
}