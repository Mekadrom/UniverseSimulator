package main.java.com.higgs.input;

import main.java.com.higgs.graphics.Viewport;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch(code) {
            case KeyEvent.VK_S: {
                Viewport.getCanvas().screenshot();
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
