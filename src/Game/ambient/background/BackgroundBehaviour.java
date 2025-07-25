package Game.ambient.background;

import Game.ambient.ScreenManager;
import GameEngine.core.Behaviour;
import GameEngine.interfaces.IGameUI;
import GameEngine.interfaces.IInputEvent;
import GameEngine.interfaces.ITransform;

public class BackgroundBehaviour extends Behaviour {
    private static final double intervalInSeconds = 2.0;
    private double accumulatedTime = 0;
    private int width = 0, height = 0;
    private IGameUI ui;
    private ScreenManager screenManager;
    private double dt;

    public BackgroundBehaviour(IGameUI ui, ScreenManager screenManager){
        this.ui = ui;
        this.screenManager = screenManager;
    
        
        this.width = ui.getWidth();
        this.height = ui.getHeight();
    }

    @Override
    public void onUpdate(long deltaTime, IInputEvent ie) {
        this.dt = deltaTime / 1000.0;

        accumulatedTime += this.dt;

        if (accumulatedTime >= intervalInSeconds) {
            accumulatedTime = 0.0;

            this.gameObject().shape().nextFrame();
        }

        screenManager.updateValues();

        ITransform transform = this.gameObject().transform();

        //Move relative to player centroid
        transform.move(screenManager.getOffset(), 0);
        
        //Handle player drag from scaling
        screenManager.setBoardCenter(transform.getPosition());

        //Zoom relative to player spread
        transform.scale(screenManager.getZoom() - transform.scale());
    }
}
