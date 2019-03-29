package com.company.editor.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class KeyController {
    private static HashMap<KeyStroke, Action> actionMap = new HashMap<KeyStroke, Action>();

    public static HashMap<KeyStroke, Action> getActionMap() {
        return actionMap;
    }

    public static void setup() {
        KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        kfm.addKeyEventDispatcher( new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(e);
                if ( actionMap.containsKey(keyStroke) ) {
                    final Action a = actionMap.get(keyStroke);
                    final ActionEvent ae = new ActionEvent(e.getSource(), e.getID(), null );
                    SwingUtilities.invokeLater( new Runnable() {
                        @Override
                        public void run() {
                            a.actionPerformed(ae);
                        }
                    } );
                    return true;
                }
                return false;
            }
        });
    }
}
