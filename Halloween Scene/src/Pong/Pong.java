package Pong;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Pong extends JPanel {

    public Game game;

    public static void main(String[] args) throws IOException, FontFormatException, UnsupportedAudioFileException, LineUnavailableException {

        Pong pong = new Pong();
        pong.game = new Game(Game.GamePlayerType.TwoPlayerLocal);

        JFrame frame = new JFrame("Pong Test");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.add(pong);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(pong.game);


        while (true) {
            pong.repaint();
        }

    }


    @Override
    protected void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        Graphics2D g = (Graphics2D) graph;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        game.draw(g);

        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
