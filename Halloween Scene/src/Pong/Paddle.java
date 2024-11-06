package Pong;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Paddle {
    private vec2 position;//position of the paddle's top left corner
    private Rect collision;

    private vec2 last_pos;
    private BufferedImage texture;
    private boolean debug = false;

    public Paddle(vec2 position) throws IOException {
        this.position = position;
        collision = new Rect((int) position.x, (int) position.y, 20, 100);
        texture = ImageIO.read(new File("src/Pong/bone.png"));
    }

    public Rect getCollision() {
        return collision;
    }

    public vec2 getPosition() {
        return position;
    }

    public void move(vec2 movement) {
        position = position.add(movement);
        collision.x += (int) movement.x;
        collision.y += (int) movement.y;
    }

    public void update(Graphics2D g) {
        g.drawImage(texture, (int) position.x, (int) position.y, null);
        g.setColor(Color.BLACK);
        if (debug) g.drawRect((int) collision.x, (int) collision.y, (int) collision.w, (int) collision.h);
        last_pos = position;
    }

    public void setDebug(boolean value) {
        debug = value;
    }

    public void setPosition(vec2 n_pos) {
        position = n_pos;
        collision.x = n_pos.x;
        collision.y = n_pos.y;
    }

    public vec2 get_velocity() {
        //run before running *.update()
        return new vec2(position.x - last_pos.x, position.y - last_pos.y);
    }

}
