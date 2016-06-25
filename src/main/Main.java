package main;

import views.GameFrame;

import javax.swing.*;

/**
 * @uthor Kanfa.
 */
public class Main {

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new GameFrame() );
    }
}
