package Game.objects;

import Game.ambient.ScreenManager;
import GameEngine.core.Behaviour;
import GameEngine.core.utils.Vector;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IInputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class GenericPlayerBehaviour extends Behaviour {
    public class DirectionKey {
        public static final int UP = 0;
        public static final int DOWN = 1;
        public static final int LEFT = 2;
        public static final int RIGHT = 3;
    }
    
    protected static final int MAX_JUMPS = 200;
    protected static final double TIME_BETWEEN_JUMPS = 0.15;
    protected final ObjectManager playerManager; 
    protected final ScreenManager screenManager;
    
    protected final List<Byte> ANIM_FRAMES;
    
    protected final List<Integer> keySet;
    
    protected final double maxSpeed = 250;
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

    protected boolean active = false; 
    //////////////////////////////////////////////////// animacoes

    private double accuTimeIdle = 0;
    private int animIdleCounter = 0;
    private boolean animIdleReset = false;

    private double accuTimeWalking = 0;
    private int animWalkingCounter = 0;

    private double lastSwitched = 0;

    public GenericPlayerBehaviour(ObjectManager playerManager, ScreenManager screenManager, List<Integer> keySet, List<Byte> animFrames) {
        this.playerManager = playerManager;
        this.screenManager = screenManager;

        this.keySet = keySet;

        this.ANIM_FRAMES = animFrames;

        this.lookingLeft = true;
        this.wasLookingLeft = true;
    }

    private double calcAxisMovement(boolean posMovement, boolean negMovement, double velocity){
        //double new_mov = acceleration * dt;
        
        int direction = 1;
        if (posMovement ^ negMovement) {

            if (!posMovement) direction = -1;
            velocity += direction * acceleration * dt;
            if (Math.abs(velocity) > maxSpeed)
                velocity = direction * maxSpeed;
        } else {
            
            if (velocity > 0) { //TODO: Melhorar isto

                velocity -= deceleration * dt;
                if (velocity < 0)
                    velocity = 0;
            } else if (velocity < 0) {

                velocity += deceleration * dt;
                if (velocity > 0)
                    velocity = 0;
            }

            if (Math.abs(velocity) < velocityThreshold) { // TODO threshold valor fixo aqui...
                velocity = 0;
            }
        }
        return velocity;
    }

    public void setActiveStatus(boolean status){
        this.active = status;
    }

    @Override
    public void onUpdate(long deltaTime, IInputEvent ie) {
        
        this.dt = deltaTime / 1000.0;
        this.lastSwitched += this.dt;

        Vector movement = new Vector(0, 0);
        if(lastSwitched > 0.2 && playerManager.getIndex(gameObject()) == 0){
            if (ie.contains(KeyEvent.VK_E)){
                playerManager.activate(1, true);
                lastSwitched = 0;
            }
            if (ie.contains(KeyEvent.VK_Q)){
                playerManager.activate(-1, true);
                lastSwitched = 0;
            }
        }

        if (active){
            boolean[] currentMov = {
                ie.contains(this.keySet.get(DirectionKey.UP)),
                ie.contains(this.keySet.get(DirectionKey.DOWN)),
                ie.contains(this.keySet.get(DirectionKey.LEFT)),
                ie.contains(this.keySet.get(DirectionKey.RIGHT))
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
        } else movement = new Vector(0, 0);
        
        this.gameObject().transform().move(movement, 0);
        this.gameObject().transform().move(screenManager.getOffset(), 0);
        this.gameObject().collider().onUpdate();

        if (Math.abs(movement.getX()) > 0 || Math.abs(movement.getY()) > 0) this.showWalking();
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

            if (this.animIdleCounter >= this.ANIM_FRAMES.get(0)) {
                this.animIdleCounter = 0;
            }

            this.gameObject().shape().setFrame(this.animIdleCounter++, !this.lookingLeft);
            this.animIdleReset = false;
        }
    }

    private void showWalking() {
        final double INTERVAL_IN_SECONDS = 0.20;

        this.accuTimeIdle += this.dt;

        if (this.accuTimeIdle >= INTERVAL_IN_SECONDS || this.animIdleReset) {
            this.accuTimeIdle = 0.0;

            if (this.animIdleCounter >= this.ANIM_FRAMES.get(1)) {
                this.animIdleCounter = 0;
            }

            this.gameObject().shape().setFrame(this.ANIM_FRAMES.get(0) + this.animIdleCounter++, !this.lookingLeft);
            this.animIdleReset = false;
        }
    }
}
