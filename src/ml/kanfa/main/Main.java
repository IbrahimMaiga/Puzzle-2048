package ml.kanfa.main;

import ml.kanfa.ui.GameFrame;

import javax.swing.*;

/**
 * @uthor Ibrahim Maïga.
 */
public class Main {

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new GameFrame() );
    }
}
