package Game.players;

import java.awt.event.KeyEvent;
import java.util.List;

public class NenyBehaviour extends GenericPlayerBehaviour {
    private static final List<Integer> keys = List.of(
            KeyEvent.VK_W,
            KeyEvent.VK_S,
            KeyEvent.VK_A,
            KeyEvent.VK_D);

    private static final List<Byte> frames = List.of(
            (byte) 2,
            (byte) 4
    );

    public NenyBehaviour(PlayerHealth health, int height) {
        super(keys, health, frames, height);
    }
}
