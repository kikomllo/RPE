package Game;

import Game.ambient.ScreenConfig;
import Game.ambient.ScreenManager;
import Game.ambient.background.Background;
import Game.exceptions.InvalidGameNameException;
import Game.objects.ObjectConfig;
import Game.objects.ObjectManager;
import GameEngine.GameEngine;
import GameEngine.core.InputManager;
import GameEngine.exceptions.GameWindowTooSmallException;
import GameEngine.exceptions.NullUserInterfaceException;
import GameEngine.gui.GameUI;
import GameEngine.gui.GameUIConfig;
import GameEngine.interfaces.IGameEngine;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IGameUI;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.google.gson.Gson;

public class Game {
    private final String name;

    public Game(String name) throws InvalidGameNameException{
        if (name == null) throw new InvalidGameNameException();
        this.name = name;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void start(boolean showColliders) {
        Gson gson = new Gson();
        IGameUI ui = null;
        IGameEngine engine = null;
        ScreenManager screenManager = null;
        ObjectManager objectManager = new ObjectManager();

        try {

            GameUIConfig uiConfig = gson.fromJson(new FileReader("./resources/configs/Window.json"), GameUIConfig.class);
            ScreenConfig screenConfig = gson.fromJson(new FileReader("./resources/configs/ScreenBehaviour.json"), ScreenConfig.class);

            ui = new GameUI(new InputManager(), 1980, 1080, this.name, uiConfig);
            screenManager = new ScreenManager(objectManager, ui, screenConfig);
            engine = new GameEngine(ui);
        } catch (GameWindowTooSmallException | NullUserInterfaceException | FileNotFoundException e) {
            e.printStackTrace();
        }

        if (ui == null || engine == null) {
            System.err.println("No UI or Engine");
            System.exit(0);
        }

        //** OBJECT LOADING HERE **//

        Background background = new Background(engine, ui, screenManager);

        ///////////////////////////////////// Globals
        
        GenericObjectLoader loader = new GenericObjectLoader(objectManager, screenManager, engine);
         
        final List<Integer> keySet = List.of(
            KeyEvent.VK_W,
            KeyEvent.VK_S,
            KeyEvent.VK_A,
            KeyEvent.VK_D
        );

        final List<Byte> animFrames = List.of(
            (byte) 2,
            (byte) 4
        );

        ///////////////////////////////////// Neny

        try {
            IGameObject neny = loader.loadObject(gson.fromJson(new FileReader("./resources/configs/Neny.json"), ObjectConfig.class));
        } catch (FileNotFoundException ex) {
        }

        try {
            IGameObject nenyclone = loader.loadObject(gson.fromJson(new FileReader("./resources/configs/Neny.json"), ObjectConfig.class));
        } catch (FileNotFoundException ex) {
        }

        //////////////////////////////////// Neny Clone

        try {
            IGameObject paulinho = loader.loadObject(gson.fromJson(new FileReader("./resources/configs/Paulinho.json"), ObjectConfig.class));
        } catch (FileNotFoundException ex) {
        }

        //////////////////////////////////// Engine Run

        ui.showColliders(showColliders);

        engine.run();
    }

/*
 * 
 * Fix Drag
 *      Possible Solution: Transform spread to board relative
 * 
 * Stat class or sum with gameobject finder for easy use
 * 
 */
}
