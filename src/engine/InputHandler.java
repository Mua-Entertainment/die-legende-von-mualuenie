package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener {

    private boolean keyDown, keyPressed, keyUp;
    private boolean mouseDown, mousePressed, mouseUp;
    private final SafeList<Integer> pressedKeys = new SafeList<>();

    void update() {
        if (keyDown) keyDown = false;
        if (keyUp) keyUp = false;
        if (mouseDown) mouseDown = false;
        if (mouseUp) mouseUp = false;
    }

    public boolean keyDown(int... keyCodes) {
        return checkKey(keyCodes, keyDown);
    }

    public boolean keyPressed(int... keyCodes) {
        return checkKey(keyCodes, keyPressed);
    }

    public boolean keyUp(int... keyCodes) {
        return checkKey(keyCodes, keyUp);
    }

    private boolean checkKey(int[] keyCodes, boolean condition) {
        if (condition) {
            for (int code : keyCodes) {
                if (pressedKeys.contains(code)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean mouseDown() {
        return mouseDown;
    }

    public boolean mousePressed() {
        return mousePressed;
    }

    public boolean mouseUp() {
        return mouseUp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (!keyPressed) {
            keyDown = keyPressed = true;
        }

        if (!pressedKeys.contains(e.getKeyCode())) {
            pressedKeys.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed = false;
        keyUp = true;

        if (pressedKeys.contains(e.getKeyCode())) {
            pressedKeys.remove((Object) e.getKeyCode());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!mousePressed) {
            mouseDown = true;
            mousePressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        mouseUp = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}