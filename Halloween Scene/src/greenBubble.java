import java.awt.*;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Random;

public class greenBubble extends Particle {



    public greenBubble(int random_seed, ParticleSettings settings) {
        super(random_seed, settings);
    }

    public greenBubble(ParticleSettings settings) {
        super(settings);
    }

    public greenBubble() {
        super();
    }

    @Override
    protected void drawPoint(Graphics graphics, int x, int y, particleInstance instance) {
        if (instance.remainingLifespan < 0.1) {
            if (instance.remainingLifespan > 0.066) {
                graphics.setColor(new Color(0, 255, 0));

                graphics.drawLine(x, y, x+3, y+3);//top left corner
                graphics.drawLine(x, y+20, x+3, y+17);//bottom left corner
                graphics.drawLine(x+20, y+20, x+17, y+17);//bottom right corner
                graphics.drawLine(x+20, y, x+17, y+3);//top right corner
                return;
            }
            if (instance.remainingLifespan > 0.033) {
                graphics.setColor(new Color(0, 255, 0));

                graphics.drawLine(x-1, y-1, x+3, y+3);//top left corner
                graphics.drawLine(x-1, y+21, x+3, y+17);//bottom left corner
                graphics.drawLine(x+21, y+21, x+17, y+17);//bottom right corner
                graphics.drawLine(x+21, y-1, x+17, y+3);//top right corner
                return;
            }
            if (instance.remainingLifespan > 0) {
                graphics.setColor(new Color(0, 255, 0));

                graphics.drawLine(x-1, y-1, x, y);//top left corner
                graphics.drawLine(x-1, y+21, x, y+20);//bottom left corner
                graphics.drawLine(x+21, y+21, x+20, y+20);//bottom right corner
                graphics.drawLine(x+21, y-1, x+20, y);//top right corner
                return;
            }
            if (instance.remainingLifespan <= 0) return;

        }

        graphics.setColor(new Color(86, 193, 0));
        graphics.drawOval(x, y, 20, 20);
        graphics.setColor(new Color(86, 193, 0, 84));
        graphics.fillOval(x, y, 20, 20);
        graphics.fillOval(x+get_random_int(0, 5, instance.rand_seed),
                y+get_random_int(0, 5, instance.rand_seed), 15, 15);
        graphics.setColor(new Color(25, 255, 109, 169));
        graphics.fillOval(x+5, y+5, 6, 6);
    }
    
}
