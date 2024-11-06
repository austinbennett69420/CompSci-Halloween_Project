import Pong.Utils;
import Pong.vec2;

public class ParticleSettings {

    public static final int GRAVITY_DOWN = 0;
    public static final int GRAVITY_LEFT = 1;
    public static final int GRAVITY_UP = 2;
    public static final int GRAVITY_RIGHT = 3;

    private boolean gravity;
    private int gravity_dir;
    private vec2 position;
    private int max_offset;
    private vec2 particle_velocity;
    private double damping;
    private int max_particles;
    private int width;
    private int height;
    private double life_span;
    private boolean destroy_when_off_screen;

    public static class values {
        public boolean gravity;
        public int gravity_dir;
        public vec2 position;
        public int max_offset;
        public vec2 particle_velocity;
        public double damping;
        public int max_particles;
        public double life_span;
        public boolean destroy_when_off_screen;

        public values(boolean gravity,
        int gravity_dir,
         vec2 position,
         int max_offset,
         vec2 particle_velocity,
         double damping,
         int max_particles,
         double life_span,
         boolean destroy_when_off_screen) {
            this.gravity = gravity;
            this.gravity_dir = gravity_dir;
            this.position = position;
            this.max_offset = max_offset;
            this.particle_velocity = particle_velocity;
            this.damping = damping;
            this.max_particles = max_particles;
            this.life_span = life_span;
            this.destroy_when_off_screen = destroy_when_off_screen;
        }
    }


    public ParticleSettings() {
        /*
        * Settings:
        * Gravity
        * Gravity Direction
        * Position
        * Max Offset
        * Particle Velocity
        * Damping
        * Max Particles
        * Width
        * Height
        * Lifespan
        * Destroy when off screen
        * */
        gravity = false;
        gravity_dir = GRAVITY_DOWN;
        position = new vec2(0, 0);
        max_offset = Integer.MAX_VALUE;
        particle_velocity = new vec2(0, 0);
        damping = 1.0;
        max_particles = 32;
        life_span = 1.0;
        destroy_when_off_screen = true;
    }

    public ParticleSettings Gravity(boolean b) {
        gravity = b;
        return this;
    }

    public ParticleSettings gravityDirection(int dir) {
        gravity_dir = (int) Utils.clamp(dir, 0, 3);
        return this;
    }

    public ParticleSettings Position(vec2 pos) {
        position = pos;
        return this;
    }

    public ParticleSettings maxOffset(int max) {
        max_offset = max;
        return this;
    }

    public ParticleSettings particleVelocity(vec2 velocity) {
        particle_velocity = velocity;
        return this;
    }

    public ParticleSettings Damping(double damp) {
        damping = Utils.clamp(damp, 0, 1);
        return this;
    }

    public ParticleSettings maxParticles(int max) {
        max_particles = max;
        return this;
    }

    public ParticleSettings lifeSpan(double life_span) {
        this.life_span = life_span;
        return this;
    }

    public ParticleSettings destroyWhenOffScreen(boolean b) {
        destroy_when_off_screen = b;
        return this;
    }

    public values getValues() {
        return new values(gravity,
          gravity_dir,
          position,
          max_offset,
          particle_velocity,
          damping,
          max_particles,
          life_span,
          destroy_when_off_screen);
    }


}
