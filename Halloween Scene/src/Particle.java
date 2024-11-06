import Pong.Utils;
import Pong.vec2;

import java.awt.*;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Random;

public class Particle {

    static protected class particleInstance {
        public vec2 position;
        public vec2 velocity;
        public double damping;
        public double remainingLifespan;
        public long last_time;
        public boolean alive;
        public boolean gravity;
        public boolean destroy_off_screen;
        public long rand_seed;

        particleInstance(vec2 position, vec2 velocity, double damping, double lifespan,
        boolean gravity, vec2 gravity_dir, boolean destroy_off_screen) {
            /*
            * @param position: the initial position of the particle
            * @param velocity: the intial velocity of the particle
            * @param damping: the damping to be applied to the velocity every frame
            * @param lifespan: the lifespan of the particle in seconds
            * @param scale: the scale of the particle
            * @param gravity: whether the particle has gravity
            * @param gravity_dir: the direction of gravity
            * */
            this.position = position;

            if (gravity) this.velocity = velocity.add(gravity_dir);
            else this.velocity = velocity;

            this.damping = damping;
            this.remainingLifespan = lifespan;
            this.alive = true;
            this.gravity = gravity;
            this.destroy_off_screen = destroy_off_screen;
            this.rand_seed = (long) (this.position.y + (((damping * 98723456) + this.velocity.x)));


            last_time = System.currentTimeMillis();
        }

        public boolean update() {

            if (remainingLifespan <= 0.0) {
                this.alive = false;
                return false;
            }

            if (destroy_off_screen) {
                if (this.position.x < 0 || this.position.y < 0
                || this.position.x > 800 || this.position.y > 600) {
                    this.alive = false;
                    return false;
                }
            }

            position.x += velocity.x;
            position.y += velocity.y;

            velocity.x *= damping;
            velocity.y *= damping;

            remainingLifespan -= Math.abs(System.currentTimeMillis()-last_time)/1000.0;
            last_time = System.currentTimeMillis();

            return true;
        }

        public String toString() {
            return "Instance<" + this.hashCode() + ">" + "Position: " + position + "Velocity: " + velocity;
        }
    }

    protected final Random r;
    public ParticleSettings.values settings;

    ArrayList<particleInstance> instances;


    public Particle(int random_seed, ParticleSettings settings) {
        r = new Random();
        this.settings = settings.getValues();
        r.setSeed(random_seed);
        instances = new ArrayList<>();
    }

    public Particle(ParticleSettings settings) {
        r = new Random();
        this.settings = settings.getValues();
        instances = new ArrayList<>();
    }

    public Particle() {
        r = new Random();
        settings = new ParticleSettings().getValues();
        instances = new ArrayList<>();
    }


    public int get_random_int(int min, int max) {
        r.setSeed(Clock.systemUTC().millis());
        int ret = r.nextInt();

        return (ret % (max-min))+min;
    }

    public int random_int(int min, int max) {
        int ret = r.nextInt();
        r.setSeed(ret);

        return (ret % (max-min))+min;
    }

    public double random_double(double min, double max) {
        double ret = r.nextDouble();
        r.setSeed(Math.round(ret));
        return (ret % (max-min)) + min;
    }

    public int get_random_int(int min, int max, long seed) {
        r.setSeed(seed);
        int ret = r.nextInt();
        r.setSeed(Clock.systemUTC().instant().toEpochMilli());
        return (ret % (max-min)) + min;
    }

    public float get_random_float() {
        r.setSeed(Clock.systemUTC().millis());
        float ret = r.nextFloat(1);
        int n_seed = r.nextInt();
        r.setSeed(n_seed);

        return ret;
    }

    public double get_random_double(double min, double max) {
        r.setSeed(Clock.systemUTC().millis());
        double ret = r.nextDouble();
        r.setSeed(Math.round(ret));
        return (ret % (max-min)) + min;
    }



    protected void drawPoint(Graphics graphics, int x, int y, particleInstance instance) {
        graphics.setColor(Color.BLACK);
        graphics.fillOval(x, y, 3, 3);
    }

    public void update(Graphics graphics) {

        r.setSeed((long)(r.nextInt() * Math.random()) << 4);

        for (particleInstance instance: instances) {
            drawPoint(graphics, (int) instance.position.x, (int) instance.position.y, instance);
        }

        for (int i = 0; i < instances.size(); i++) {
            if (!instances.get(i).update()) {
                instances.remove(i);
                i--;
            }
        }

        //debug
        /*
        for (particleInstance instance: instances) {
            System.out.println(instance);
        }
        //*/
    }

    public void spawn_particles(int how_many) {
        vec2 position = settings.position;
        vec2 gravity_dir = new vec2(0, 0);
        if (settings.gravity) {
            if (settings.gravity_dir == ParticleSettings.GRAVITY_DOWN) gravity_dir = new vec2(0, 9.8);
            if (settings.gravity_dir == ParticleSettings.GRAVITY_UP) gravity_dir = new vec2(0, -9.8);
            if (settings.gravity_dir == ParticleSettings.GRAVITY_LEFT) gravity_dir = new vec2(-9.8, 0);
            if (settings.gravity_dir == ParticleSettings.GRAVITY_RIGHT) gravity_dir = new vec2(9.8, 0);
        }
        for (int i = 0; i < how_many; i++) {
            instances.add(new particleInstance(new vec2(position.x + random_int(0, settings.max_offset),
                    position.y /*- get_random_int(0, 32)*/),
                    settings.particle_velocity.add(new vec2(random_double(-2, 2), random_double(-2, 2))),
                    Utils.clamp(settings.damping, 0, 1),
                    settings.life_span + random_double(-0.3, 0.3),
                    settings.gravity,
                    gravity_dir, settings.destroy_when_off_screen));
            if (instances.size() == settings.max_particles - 1) {
                return;
            }
            r.setSeed((long) (Math.random() * r.nextInt()));

        }
    }

    public int getParticleCount() {
        return instances.size();
    }

}
