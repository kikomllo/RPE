package Game.ambient.background;

import Game.ambient.ScreenManager;
import GameEngine.core.Behaviour;
import GameEngine.interfaces.IGameUI;
import GameEngine.interfaces.IInputEvent;

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

        if (this.width != ui.getWidth() || this.height != ui.getHeight()){
            this.width = ui.getWidth();
            this.height = ui.getHeight();

            //ITransform transform = this.gameObject().transform();
            //transform.move(new Point(this.width/2, this.height/2).sub(transform.getPosition()), 0);
        
        }

        screenManager.updateValues();
        this.gameObject().transform().move(screenManager.getOffset(), 0);
    }
}
