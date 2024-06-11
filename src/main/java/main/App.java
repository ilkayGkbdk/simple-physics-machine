package main;

import javax.swing.*;

public class App extends JFrame {

    public static void main(String[] args) {
        new App();
    }

    private App(){
        this.setTitle("Engine 1.0");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        WindowPanel wp = new WindowPanel();
        this.add(wp);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        wp.setupEngine();
        wp.startEngine();
    }
}
