package Game;

import Game.ambient.background.Background;
import Game.exceptions.InvalidGameNameException;
import Game.players.Neny;
import Game.players.NenyBehaviour;
import Game.players.PlayerHealth;
import GameEngine.GameEngine;
import GameEngine.core.InputManager;
import GameEngine.core.utils.Point;
import GameEngine.exceptions.GameWindowTooSmallException;
import GameEngine.exceptions.NullUserInterfaceException;
import GameEngine.gui.GameUI;
import GameEngine.interfaces.IBehaviour;
import GameEngine.interfaces.IGameEngine;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IGameUI;
import java.util.List;

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
            //TODO ver o tamanho da janela, pode ser possivel expandir a altura
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

        Background background = new Background(engine, ui);

        // Neny

        String name = "Neny";
        Point position = new Point(0, 0);
        int[] size = {
            40, 64
        };
        int[] values = {
            0, 0, 
            0, size[0],
            size[1], size[0],
            size[1], 0
        };
        List<String> spritesPath = List.of(
                "./resources/sprites/neny/default/0.png",
                "./resources/sprites/neny/default/1.png",
                "./resources/sprites/neny/default/Neny_Walking_11.png",
                "./resources/sprites/neny/default/Neny_Walking_12.png",
                "./resources/sprites/neny/default/Neny_Walking_13.png",
                "./resources/sprites/neny/default/Neny_Walking_14.png");
        IBehaviour playerBehaviour = new NenyBehaviour(new PlayerHealth(1), size[1]);
        IGameObject neny = GenericObjectLoader.loadObject(
            name, position, size, values, spritesPath, playerBehaviour, engine);

        ui.showColliders(showColliders);

        engine.run();
    }
}
