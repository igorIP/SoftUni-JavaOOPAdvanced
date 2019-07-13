package app.models.participants;

import app.constants.Config;
import app.contracts.Targetable;

public class Boss implements Targetable {

    private double health;
    private String name;
    private double damage;
    private boolean isAlive;
    private double gold;

    public Boss() {
        this.setGold(Config.BOSS_GOLD);
        this.setDamage(Config.BOSS_DAMAGE);
        this.setHealth(Config.BOSS_HEALTH);
        this.setName("Boss");
        this.isAlive = true;
    }

    @Override
    public String attack(Targetable target) {

        if (!this.isAlive()) {
            return this.getName() + " is dead! Cannot attack.";
        }

        if (!target.isAlive()) {
            return target.getName() + " is dead! Cannot be attacked.";
        }

        target.takeDamage(this.getDamage());

        String result = this.getName() + " attacked!";
        if (!target.isAlive()) {
            this.levelUp();
            target.giveReward(this);
            result += String.format(" %s has been slain by %s.", target.getName(), this.getName());
        }

        return result;
    }

    @Override
    public void takeDamage(double damage) {
        this.setHealth(this.getHealth() - damage);
        if (this.getHealth() <= 0)
            this.setAlive(false);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getDamage() {
        return this.damage;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public void giveReward(Targetable targetable) {
        targetable.receiveReward(this.getGold());
        this.setGold(0);
    }

    @Override
    public void receiveReward(double reward) {
        this.setGold(this.getGold() + (reward * 0.10));
    }

    @Override
    public void levelUp() {
        this.setHealth(Config.BOSS_HEALTH);
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    private void setDamage(double damage) {
        this.damage = damage;
    }

    private void setAlive(boolean alive) {
        isAlive = alive;
    }

    public double getGold() {
        return gold;
    }

    private void setGold(double gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("  Name: %s | Class: %s%n", this.getName(), this.getClass().getSimpleName()));
        sb.append(String.format("  Health: %.2f | Damage: %.2f | Gold: %.2f", this.getHealth(), this.getDamage(), this.getGold()));

        return sb.toString();
    }
}
