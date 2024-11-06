package Pong;



import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class Game implements KeyListener {

    public HashMap<Integer, Boolean> keyboard;
    public Ball b;
    public Paddle p1;
    public Paddle p2;
    int p1_score;
    int p2_score;
    public Font game_font;
    private boolean game_over = false;
    private Clip ballHit;
    private Clip Score;
    private Clip Win;

    //player one plus alpha
    int p1pla = 0;
    int p2pla = 0;

    public enum GamePlayerType {
        OnePlayer,
        TwoPlayerLocal
    }

    GamePlayerType gameType;



    public Game(GamePlayerType typ) throws IOException, FontFormatException, UnsupportedAudioFileException, LineUnavailableException {
        keyboard = new HashMap<>();
        b = new Ball(new vec2(400, 300), new vec2(-5, 0));
        p1 = new Paddle(new vec2(30, 250));
        p2 = new Paddle(new vec2(730, 250));
        game_font = Font.createFont(Font.PLAIN, new FileInputStream("src/Pong/PressStart.ttf"));
        game_font = game_font.deriveFont(20f);
        gameType = typ;

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("src/Pong/Sounds/BallHit.wav"));
        ballHit = AudioSystem.getClip();
        ballHit.open(audioStream);

        AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(new File("src/Pong/Sounds/Point.wav"));
        Score = AudioSystem.getClip();
        Score.open(audioStream2);

        AudioInputStream audioStream3 = AudioSystem.getAudioInputStream(new File("src/Pong/Sounds/Win.wav"));
        Win = AudioSystem.getClip();
        Win.open(audioStream3);

    }

    public void reset() {
        b.setPosition(new vec2(400, 300));
        b.setVelocity(new vec2((b.getVelocity().x >= 0 ? 1:-1) * 5, 0));
        b.reverse_velocity();
    }

    public void setDebug(boolean value) {
        b.setDebug(value);
        p1.setDebug(value);
        p2.setDebug(value);
    }

    public void draw(Graphics2D g) {


        if (!game_over) {
            if (b.getPosition().x <= 0) {
                Score.setFramePosition(0);
                Score.start();
                p2_score++;
                reset();
                p2pla = 300;
            }
            if (b.getPosition().x >= 790) {
                Score.setFramePosition(0);
                Score.start();
                p1_score++;
                reset();
                p1pla = 300;
            }
            if (b.getPosition().y <= 5) {
                b.reverse_y_velocity();
                ballHit.setFramePosition(0);
                ballHit.start();
            }
            if (b.getPosition().y >= 550) {
                b.reverse_y_velocity();
                ballHit.setFramePosition(0);
                ballHit.start();
            }

            Rect b_rect = b.getCollision().getCopy();
            vec2 nextPos = new vec2(b_rect.x + b.getVelocity().x, b_rect.y + b.getVelocity().y);
            if (nextPos.x < b_rect.x) {
                b_rect.w += b_rect.x - nextPos.x;
                b_rect.x = nextPos.x;
            }
            if (nextPos.y < b_rect.y) {
                b_rect.h += b_rect.y - nextPos.y;
                b_rect.y = nextPos.y;
            }

            if (nextPos.x > b_rect.x) {
                b_rect.w += b_rect.w - ((b_rect.x + b_rect.w) - nextPos.x);
            }
            if (nextPos.x > b_rect.x) {
                b_rect.h += b_rect.h - ((b_rect.y + b_rect.h) - nextPos.y);
            }


            g.setColor(Color.MAGENTA);
            if (b.getDebug()) g.drawRect((int) b_rect.x, (int) b_rect.y, (int) b_rect.w, (int) b_rect.h);
            if (Rect.collideRect(b_rect, p1.getCollision())) {
                b.reverse_velocity();

                if (b.getVelocity().x < 100) b.addVelocity(new vec2(0.25,
                        p1.get_velocity().y * 4.0 / 5.0));
                ballHit.setFramePosition(0);
                ballHit.start();
            }
            if (Rect.collideRect(b_rect, p2.getCollision())) {
                b.reverse_velocity();
                if (b.getVelocity().x > -100) b.addVelocity(new vec2(-0.25,
                        p2.get_velocity().y * 4.0 / 5.0));
                ballHit.setFramePosition(0);
                ballHit.start();
            }

            p1.update(g);
            p2.update(g);
            b.update(g);

            if (gameType == GamePlayerType.OnePlayer) {
                //update player one paddle
                if (keyboard.containsKey(KeyEvent.VK_W) && keyboard.get(KeyEvent.VK_W)) {
                    if (p1.getPosition().y > 0) {
                        p1.move(new vec2(0, -5));
                    }
                }
                if (keyboard.containsKey(KeyEvent.VK_S) && keyboard.get(KeyEvent.VK_S)) {
                    if (p1.getPosition().y + 100 < 560) {
                        p1.move(new vec2(0, 5));
                    }
                }
                //update AI
                if (b.getPosition().y < (p2.getPosition().y + 50)) {
                    if (p2.getPosition().y > 0) p2.move(new vec2(0, -4));
                }

                if (b.getPosition().y > (p2.getPosition().y + 50)) {
                    if (p2.getPosition().y + 100 < 560) p2.move(new vec2(0, 4));
                }
            } else if (gameType == GamePlayerType.TwoPlayerLocal) {
                //update player one paddle
                if (keyboard.containsKey(KeyEvent.VK_W) && keyboard.get(KeyEvent.VK_W)) {
                    if (p1.getPosition().y > 0) {
                        p1.move(new vec2(0, -5));
                    }
                }
                if (keyboard.containsKey(KeyEvent.VK_S) && keyboard.get(KeyEvent.VK_S)) {
                    if (p1.getPosition().y + 100 < 560) {
                        p1.move(new vec2(0, 5));
                    }
                }
                //update player two paddle
                if (keyboard.containsKey(KeyEvent.VK_UP) && keyboard.get(KeyEvent.VK_UP)) {
                    if (p2.getPosition().y > 0) {
                        p2.move(new vec2(0, -5));
                    }
                }
                if (keyboard.containsKey(KeyEvent.VK_DOWN) && keyboard.get(KeyEvent.VK_DOWN)) {
                    if (p2.getPosition().y + 100 < 560) {
                        p2.move(new vec2(0, 5));
                    }
                }
            }
            FontMetrics fm = g.getFontMetrics();
            g.setFont(game_font);
            String text = p1_score + " : " + p2_score;
            int plw = fm.stringWidth("+1");
            int w = fm.stringWidth(text);
            g.setColor(new Color(40, 40, 40, 150));
            g.fillRoundRect(((380 - (w / 2)) - plw - 40), 0, 245,
                    60, 10, 10);


            g.setColor(Color.WHITE);
            g.drawString(text, 400 - (w / 2), 40);

            g.setColor(new Color(0, 200, 0, (int) Utils.clamp(p1pla, 0, 255)));
            g.drawString("+1", (400 - (w / 2)) - plw - 50, 40);

            g.setColor(new Color(0, 200, 0, (int) Utils.clamp(p2pla, 0, 255)));
            g.drawString("+1", (400 + (w / 2)) + plw + 85, 40);
            if (p1pla > 0) p1pla -= 2;
            if (p2pla > 0) p2pla -= 2;
        }
        g.setFont(game_font);

        if (p1_score == 11) {
            Win.start();
            game_over = true;
            FontMetrics fm = g.getFontMetrics();
            int w = fm.stringWidth("Player 1 Wins!");
            int h = fm.getHeight();
            int w2 = fm.stringWidth("Press SPACE to restart");
            g.setColor(new Color(200, 200, 200, 150));
            g.fillRoundRect(175, 235, w2+5, 120, 10, 10);
            g.setColor(Color.BLACK);
            g.drawString("Player 1 Wins!", 400 - (w/2), 300 - (h/2));
            g.drawString("Press SPACE to restart", 400 - (w2/2), 350 - (h/2));
        }
        if (p2_score == 11) {
            Win.start();
            game_over = true;
            FontMetrics fm = g.getFontMetrics();
            int w = fm.stringWidth("Player 2 Wins!");
            int h = fm.getHeight();
            int w2 = fm.stringWidth("Press SPACE to restart");
            g.setColor(new Color(200, 200, 200, 150));
            g.fillRoundRect(175, 235, w2+5, 120, 10, 10);
            g.setColor(Color.BLACK);
            g.drawString("Player 2 Wins!", 400 - (w/2), 300 - (h/2));
            g.drawString("Press SPACE to restart", 400 - (w2/2), 350 - (h/2));
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyboard.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyboard.put(e.getKeyCode(), false);
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (game_over) {
                game_over = false;
                p1_score = 0;
                p2_score = 0;
                p1.setPosition(new vec2(30, 250));
                p2.setPosition(new vec2(730, 250));
                reset();
                Win.setFramePosition(0);
            }
        }
    }
}
