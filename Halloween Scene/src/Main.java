import Pong.Game;
import Pong.Utils;
import Pong.vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends JPanel implements KeyListener {

    public long tick = 0;
    public final int y_off = 100;
    public int fade = 0;
    public int fade_dir = 1;
    public Font font1 = new Font("JetBrainsMonoNL Nerd Font", Font.PLAIN, 20);
    public JFrame frame;
    public int flame_offset = 0;
    public int flame_target = 3;
    public int flame_dir = 1;
    public Game pong;
    public ExecutorService executor = Executors.newSingleThreadExecutor();

    public greenBubble test = new greenBubble(0x003218764, new ParticleSettings()
            .Gravity(false)
            .gravityDirection(ParticleSettings.GRAVITY_UP)
            .Position(new vec2(310 + 71, 200+y_off))
            .maxOffset(60).lifeSpan(1)
            .Damping(1.0)
            .particleVelocity(new vec2(0, -3))
            .maxParticles(3));
    public boolean debug = false;

    boolean loading = true;


    public static void main(String[] args) throws InterruptedException, IOException, FontFormatException, UnsupportedAudioFileException, LineUnavailableException {
        Main m = new Main();
        m.frame = new JFrame("Spooky Scary");
        m.frame.setSize(800, 600);
        m.frame.setResizable(false);
        m.frame.add(m);
        m.frame.addKeyListener(m);
        m.frame.setVisible(true);
        m.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (args[0].equalsIgnoreCase("1P")) m.pong = new Game(Game.GamePlayerType.OnePlayer);
        if (args[0].equalsIgnoreCase("2P")) m.pong = new Game(Game.GamePlayerType.TwoPlayerLocal);
        m.frame.addKeyListener(m.pong);





        while (true) {
            if (m.tick > 10) m.loading = false;
            m.repaint();
        }

    }

    @Override
    protected void paintComponent(Graphics graph) {
        super.paintComponent(graph);

        long time_stamp = System.nanoTime();

        Graphics2D g = (Graphics2D) graph;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);



        Point mousepos = getMousePosition();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 600);
        draw_bg(g);



        if (!loading) {
            pong.draw(g);
        }

        long render_delay = System.nanoTime() - time_stamp;



        try {
            Thread.sleep(Math.max((int)(16 - (render_delay / 1000000)), 0));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long delay = System.nanoTime() - time_stamp;

        if (debug) {
            g.setColor(new Color(200, 200, 200, 100));
            g.fillRect(0, 0, 400, 180);
            g.setFont(font1);
            g.setColor(Color.BLACK);
            if (mousepos != null) {
                g.drawString("Mouse {" + (mousepos.x) + ", " + (mousepos.y) + "}", 0, 20);
            } else {
                g.drawString("Mouse {null, null}", 0, 20);
            }
            g.drawString("tick: " + tick, 0, 40);
            g.drawString("fade: " + fade, 0, 60);
            g.drawString("flame_offset: " + flame_offset, 0, 80);
            g.drawString("flame_dir: " + flame_dir, 0, 100);
            g.drawString("flame_target: " + flame_target, 0, 120);
            g.drawString(String.format("FPS: %.2f", (1.0 / ((double)(delay) / 1E9))), 0, 140);
            g.drawString("Ball velocity: " + pong.b.getVelocity(), 0, 160);

        }

        tick++;
        fade += fade_dir;
        if (fade >= 100 || fade <= 0) {
            fade_dir *= -1;
        }
        flame_offset += flame_dir;
        if (flame_offset >= flame_target) {
            flame_dir = -1;
        } else if (flame_offset <= 0) {
            flame_dir = 1;
            flame_target = (int) ((Math.random() * (4)) + 1);
        }

        if (loading) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 800, 600);
            g.setFont(font1);
            g.setColor(Color.WHITE);
            g.drawString("loading...", 40, 40);
        }
    }

    protected void draw_brick(Graphics g, int x, int y) {
        g.setColor(new Color(81, 81, 81));
        g.fillRect(x, y, 60, 30);
        g.setColor(new Color(165, 165, 165));
        g.fillRoundRect(x, y, 56, 26, 3, 3);
    }

    protected void draw_candles(Graphics2D g) {
        g.setColor(new Color(255, 255, 230));
        g.fillRoundRect(532, 451, 15, 60, 4, 8);
        g.setColor(new Color(255, 255, 200));
        g.fillOval(532, 450, 15, 10);
        g.setColor(Color.BLACK);
        g.drawLine(539, 452, 539, 442);

        g.setColor(new Color(255, 255, 230));
        g.fillRoundRect(550, 431, 15, 80, 4, 8);
        g.setColor(new Color(255, 255, 200));
        g.fillOval(550, 430, 15, 10);
        g.setColor(Color.BLACK);
        g.drawLine(557, 432, 557, 422);

        g.setColor(new Color(255, 255, 230));
        g.fillRoundRect(568, 421, 15, 90, 4, 8);
        g.setColor(new Color(255, 255, 200));
        g.fillOval(568, 420, 15, 10);
        g.setColor(Color.BLACK);
        g.drawLine(575, 423, 575, 413);
        //draw flames
        g.setColor(new Color(255, 221, 0, 160));
        g.fillOval(535, 432-flame_offset, 10, 13+flame_offset);
        g.fillOval(552, 412-flame_offset, 10, 13+flame_offset);
        g.fillOval(571, 403-flame_offset, 10, 13+flame_offset);

        g.setColor(new Color(255, 89, 0, 116));
        g.fillOval(537, 435-flame_offset, 5, 10+flame_offset);
        g.fillOval(554, 415-flame_offset, 5, 10+flame_offset);
        g.fillOval(573, 406-flame_offset, 5, 10+flame_offset);
    }

    protected void draw_bg(Graphics2D g) {//spooky scene



        //draw wall
        for (int y = 0; y <= 600; y+=30) {
            for (int x = 0; x <= 800; x+=60) {
                draw_brick(g, x, y);
            }
        }
        //draw floor
        g.setColor(new Color(92, 53, 41));
        g.fillRect(0, 400, 800, 200);



        //draw cauldron

        g.setColor(new Color(30, 30, 30));
        g.fillOval(255, 220+y_off, 250, 200);
        g.fillOval(300, 220+y_off, 163, 30);

        //draw cauldron bubbles

        test.update(g);
        if (tick % 10 == 0) {
            test.spawn_particles(3);
        }
        //draw cauldron light
        //300 - 460
        g.setColor(new Color(9, 255, 0, 87 - (fade/4)));
        int[] x = {304, 458, 522, 236};
        int[] y= {334, 334, 0, 0};
        g.fillPolygon(x, y, 4);

        //draw cauldron filling
        g.setColor(new Color(0, (int) Utils.clamp((double) 255-fade, 0.0, 255.0), 0));
        g.fillOval(305, 225+y_off, 153, 20);
        g.setColor(new Color(0, (int) Utils.clamp((double) 200-fade, 0.0, 255.0), 0));
        g.fillOval(310, 230+y_off, 143, 15);

        //draw candles
        draw_candles(g);


    }




    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F3) {
            debug = !debug;
            pong.setDebug(debug);
        }
    }
}