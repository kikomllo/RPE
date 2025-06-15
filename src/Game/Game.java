package Game;

import Game.players.Neny;
import GameEngine.GameEngine;
import GameEngine.core.InputManager;
import GameEngine.exceptions.GameWindowTooSmallException;
import GameEngine.exceptions.NullUserInterfaceException;
import GameEngine.gui.GameUI;
import GameEngine.interfaces.IGameEngine;
import GameEngine.interfaces.IGameUI;

public class Game {
    private final String name;

    public Game(String name) throws InvalidGameNameException{
        if (name == null) throw new InvalidGameNameException();
        this.name = name;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void start(boolean showColliders) {
        IGameUI ui = null;
        IGameEngine engine = null;

        try {
            //TODO ver o tamanho da janela, pode ser possivel expandir a 000000000000altura
            ui = new GameUI(new InputManager(), 1280, 720, this.name);
            engine = new GameEngine(ui);
        } catch (GameWindowTooSmallException | NullUserInterfaceException e) {
            e.printStackTrace();
        }

        if (ui == null || engine == null) {
            System.err.println("No UI or Engine");
            System.exit(0);
        }

        // OBJECT LOADING HERE

        Neny neny = new Neny(engine);

        ui.showColliders(showColliders);

        engine.run();
    }
}
