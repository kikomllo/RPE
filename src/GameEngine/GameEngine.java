package GameEngine;

import GameEngine.exceptions.NullUserInterfaceException;
import GameEngine.interfaces.IGameEngine;
import GameEngine.interfaces.IGameObject;
import GameEngine.interfaces.IGameUI;
import GameEngine.interfaces.IInputEvent;
import GameEngine.interfaces.IInputManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Classe que representa um {@code GameEngine} e que implementa a interface
 * {@code IGameEngine}. Esta classe é responsável por gerir o ciclo de vida
 * dos objetos do jogo, incluindo a sua atualização e deteção de colisões.
 *
 * @author Raquel Nunes, a83883
 * @author David Silvestre, a83938
 * @author Francisco Melo, a84085
 *
 * @version 10-05-2025
 */
public class GameEngine implements IGameEngine {
    private final List<IGameObject> enabled;
    private final List<IGameObject> disabled;

    private IGameUI gui;

    private boolean running;

    private long delta;

    private long lastLoop;

    public GameEngine(IGameUI ui) throws NullUserInterfaceException {
        this.enabled = new CopyOnWriteArrayList<>(); // pode causar problemas de desempenho
        this.disabled = new CopyOnWriteArrayList<>(); // talvez posso haver outra maneira? idk

        if (ui == null) {
            throw new NullUserInterfaceException();
        }

        this.gui = ui;

        this.running = false;
        this.delta = 13;
        this.lastLoop = -1;
    }

    @Override
    public void addEnabled(IGameObject go) {
        this.enabled.add(go);

        go.behaviour().onInit();
    }

    @Override
    public void addDisabled(IGameObject go) {
        this.disabled.add(go);

        go.behaviour().onInit();
    }

    @Override
    public void enable(IGameObject go) {
        this.disabled.remove(go);
        this.enabled.add(go);

        go.behaviour().onEnable();
    }

    @Override
    public void disable(IGameObject go) {
        this.enabled.remove(go);
        this.disabled.add(go);

        go.behaviour().onDisable();
    }

    @Override
    public void destroy(IGameObject go) {
        this.enabled.remove(go);
        this.disabled.remove(go);

        go.behaviour().onDestroy();
    }

    @Override
    public boolean isEnabled(IGameObject go) {
        return this.enabled.contains(go);
    }

    @Override
    public boolean isDisabled(IGameObject go) {
        return this.disabled.contains(go);
    }

    @Override
    public List<IGameObject> getEnabled() {
        return this.enabled;
    }

    @Override
    public List<IGameObject> getDisabled() {
        return this.disabled;
    }

    private Map<IGameObject, List<IGameObject>> getCollisionMap() {
        int n = this.enabled.size();

        Map<IGameObject, List<IGameObject>> collisionMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            IGameObject goA = this.enabled.get(i);

            for (int j = i + 1; j < n; j++) {
                IGameObject goB = this.enabled.get(j);

                if (goA.isColliding(goB)) {
                    collisionMap.computeIfAbsent(goA, ignored -> new ArrayList<>()).add(goB);
                    collisionMap.computeIfAbsent(goB, ignored -> new ArrayList<>()).add(goA);
                }
            }
        }

        return collisionMap;
    }

    private void notifyCollisions(Map<IGameObject, List<IGameObject>> collisionMap) {
        for (Map.Entry<IGameObject, List<IGameObject>> entry : collisionMap.entrySet()) {
            entry.getKey().behaviour().onCollision(entry.getValue());
        }
    }

    private IInputEvent getUserInput() {
        return this.gui.getInput();
    }

    private IInputManager getUserInputManager() {
        return this.gui.getInputManager();
    }

    @Override
    public void run() {
        this.running = true;

        IInputEvent input;

        Map<IGameObject, List<IGameObject>> collisions;

        long start;

        while (this.running) {
            start = System.currentTimeMillis();

            input = this.getUserInput();

            for (IGameObject go : this.enabled) {
                go.behaviour().onUpdate(this.lastLoop, input);
                go.collider().onUpdate();
            }

            collisions = getCollisionMap();
            this.notifyCollisions(collisions);

            if (this.gui != null) {
                this.gui.setObjects(this.sortedObjects());
                this.gui.render();
            }

            if (this.delta > 0) {
                this.delay(this.delta);
            }

            this.getUserInputManager().clearRelease();

            this.lastLoop = System.currentTimeMillis() - start;
        }
    }

    public void stop() {
        this.running = false;
    }

    @Override
    public void setDelta(long deltaTime) {
        this.delta = deltaTime;
    }

    private void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private List<IGameObject> sortedObjects() {
        List<IGameObject> sorted = new ArrayList<>(this.enabled);
        sorted.sort((a, b) -> Integer.compare(a.transform().layer(), b.transform().layer()));
        return sorted;
    }
}
