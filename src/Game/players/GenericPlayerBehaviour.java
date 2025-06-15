package Game.players;

import GameEngine.core.Behaviour;
import GameEngine.core.utils.Vector;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IInputEvent;
import java.util.List;

public abstract class GenericPlayerBehaviour extends Behaviour {
    public class DirectionKey {
        public static final int UP = 0;
        public static final int DOWN = 1;
        public static final int LEFT = 2;
        public static final int RIGHT = 3;
    }

    protected static final int MAX_JUMPS = 200;
    protected static final double TIME_BETWEEN_JUMPS = 0.15;

    protected final int HEIGHT;

    protected final List<Byte> SHAPE_MAP;

    protected final List<Integer> KEYS;

    protected final PlayerHealth health;

    protected final double maxSpeed = 400;
    protected final double velocityThreshold = 5;
    protected double velocityY = 0;
    protected double velocityX = 0;

    protected double acceleration = 3000;
    protected double deceleration = 1500;

    protected double dt;

    protected boolean lookingLeft;
    protected boolean wasLookingLeft;

    protected boolean lastLookedLeft;

    protected static final double intervalInSeconds = 0.25;
    protected double accumulatedTime = 0;

    //////////////////////////////////////////////////// animacoes

    private double accuTimeIdle = 0;
    private int animIdleCounter = 0;
    private boolean animIdleReset = false;

    private double accuTimeWalking = 0;
    private int animWalkingCounter = 0;

    public GenericPlayerBehaviour(List<Integer> keys, PlayerHealth health, List<Byte> shapeMap, int height) {

        this.KEYS = keys;

        this.HEIGHT = height;

        this.health = health;

        this.SHAPE_MAP = shapeMap;

        this.lookingLeft = true;
        this.wasLookingLeft = true;
    }

    private double calcAxisMovement(boolean posMovement, boolean negMovement, double velocity){
        //double new_mov = acceleration * dt;
        
        if (posMovement && !negMovement) {
            velocity += acceleration * dt;
            if (velocity > maxSpeed)
                velocity = maxSpeed;
        } else if (negMovement && !posMovement) {
            velocity -= acceleration * dt;
            if (velocity < -maxSpeed)
                velocity = -maxSpeed;
        } else {
            if (velocity > 0) {

                velocity -= deceleration * dt;
                if (velocity < 0)
                    velocity = 0;
            } else if (velocity < 0) {

                velocity += deceleration * dt;
                if (velocity > 0)
                    velocity = 0;
            }

            if (Math.abs(velocity) < velocityThreshold) { // TODO threshold aqui
                velocity = 0;
            }
        }
        return velocity;
    }

    @Override
    public void onUpdate(long deltaTime, IInputEvent ie) {
        this.dt = deltaTime / 1000.0;

        Vector movement = new Vector(0, 0);

        boolean[] currentMov = {
            ie.contains(this.KEYS.get(DirectionKey.UP)),
            ie.contains(this.KEYS.get(DirectionKey.DOWN)),
            ie.contains(this.KEYS.get(DirectionKey.LEFT)),
            ie.contains(this.KEYS.get(DirectionKey.RIGHT))
        };

        this.wasLookingLeft = this.lookingLeft;

        if (currentMov[DirectionKey.LEFT] != currentMov[DirectionKey.RIGHT])
            this.lookingLeft = currentMov[DirectionKey.LEFT];

        boolean changedDirection = this.lookingLeft != this.wasLookingLeft;

        if (changedDirection) {
            this.animIdleCounter = 0;
            this.animWalkingCounter = 0;

            this.animIdleReset = true;
        }

        velocityY = calcAxisMovement(currentMov[DirectionKey.DOWN], currentMov[DirectionKey.UP], velocityY);
        velocityX = calcAxisMovement(currentMov[DirectionKey.RIGHT], currentMov[DirectionKey.LEFT], velocityX);

        movement.add(new Vector(velocityX * dt, velocityY * dt));

        this.gameObject().transform().move(movement, 0);
        this.gameObject().collider().onUpdate();

        //TODO: NEED TO IMPLEMENT WALKING ANIM
        if (Math.abs(velocityX) > 0 || Math.abs(velocityY) > 0) this.showWalking(changedDirection);
        else this.showIdle();
        
        
    }

    @Override
    public void onCollision(List<IGameObject> gol) {

    }

    private void showIdle() {
        final double INTERVAL_IN_SECONDS = 0.25;

        this.accuTimeIdle += this.dt;

        if (this.accuTimeIdle >= INTERVAL_IN_SECONDS || this.animIdleReset) {
            this.accuTimeIdle = 0.0;

            if (this.animIdleCounter >= this.SHAPE_MAP.get(0)) {
                this.animIdleCounter = 0;
            }

            this.gameObject().shape().setFrame(this.animIdleCounter++, !this.lookingLeft);
            this.animIdleReset = false;
        }
    }

    @SuppressWarnings("unused")
    private void showWalking(boolean walkDirection) {
        final double INTERVAL_IN_SECONDS = 0.25;

        this.accuTimeIdle += this.dt;

        if (this.accuTimeIdle >= INTERVAL_IN_SECONDS || this.animIdleReset) {
            this.accuTimeIdle = 0.0;

            if (this.animIdleCounter >= this.SHAPE_MAP.get(1)) {
                this.animIdleCounter = 0;
            }

            this.gameObject().shape().setFrame(this.SHAPE_MAP.get(0) + this.animIdleCounter++, !this.lookingLeft);
            this.animIdleReset = false;
        }
    }
}
