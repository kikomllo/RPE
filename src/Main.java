import Game.Game;
import Game.InvalidGameNameException;

public class Main {
    public static void main(String[] args) {
        Game game = null;
        
        try {
            game = new Game("RPE");
        } catch (InvalidGameNameException e) {
            e.printStackTrace();
            System.exit(0);
        }

        game.start(false);
    }
}