package Game.players;

public class PlayerHealth {
    private int maxHealth;
    private int health;

    public PlayerHealth(int maxHealth) {
        //TODO exception quando <= 0
        this.maxHealth = maxHealth;
        this.health = this.maxHealth;
    }

    public void damage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void damage() {
        this.damage(1);
    }

    public boolean isDead() {
        return this.health == 0;
    }

    public int getCurrentHealth() {
        return this.health;
    }

    @Override
    public String toString() {
        return "" + this.health; //TODO bonito: sim
    }
}
