package Pong;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ball {
    private vec2 velocity;
    private vec2 position;//position of the balls center
    private Rect collision;
    private BufferedImage texture;
    private boolean debug = false;

    public Ball(vec2 init_pos, vec2 init_velocity) throws IOException {
        position = init_pos;
        velocity = init_velocity;
        collision = new Rect((int) (init_pos.x - 10), (int) (init_pos.y - 10), 20, 20);
        texture = ImageIO.read(new File("src/Pong/pumpkin.png"));
    }

    public void update(Graphics2D g) {




        g.drawImage(texture, (int) position.x-10, (int) position.y-10, null);
        g.setColor(Color.BLACK);
        if (debug) g.drawRect((int) collision.x, (int) collision.y, (int) collision.w, (int) collision.h);

        position = position.add(velocity);
        collision.x += velocity.x;
        collision.y += velocity.y;
    }

    public void setDebug(boolean value) {
        debug = value;
    }

    public boolean getDebug() {
        return debug;
    }

    public void move(vec2 movement) {
        position = position.add(movement);
        collision.x += movement.x;
        collision.y += movement.y;
    }

    public vec2 getPosition() {
        return position;
    }

    public void setPosition(vec2 new_pos) {
        position = new_pos;
        collision.x = new_pos.x-10;
        collision.y = new_pos.y-10;
    }

    public void setVelocity(vec2 velocity) {
        this.velocity = velocity;
    }

    public vec2 getVelocity() {
        return velocity;
    }

    public void addVelocity(vec2 addition) {
        velocity = velocity.add(addition);
    }

    public void reverse_velocity() {
        velocity.x = -velocity.x;
        velocity.y = -velocity.y;
    }

    public void reverse_y_velocity() {
        velocity.y = -velocity.y;
    }

    public Rect getCollision() {
        return collision;
    }
}
