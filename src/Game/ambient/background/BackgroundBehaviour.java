package Game.ambient.background;

import GameEngine.core.Behaviour;
import GameEngine.interfaces.IInputEvent;

public class BackgroundBehaviour extends Behaviour {
    private static final double intervalInSeconds = 2.0;
    private double accumulatedTime = 0;

    private double dt;

    @Override
    public void onUpdate(long deltaTime, IInputEvent ie) {
        this.dt = deltaTime / 1000.0;

        accumulatedTime += this.dt;

        if (accumulatedTime >= intervalInSeconds) {
            accumulatedTime = 0.0;

            this.gameObject().shape().nextFrame();
        }
    }
}
