package app.models.participants;

import app.constants.Config;
import app.contracts.Hero;
import app.contracts.Special;
import app.contracts.Targetable;

public abstract class AbstractHero implements Hero {

    private String name;
    private double damage;
    private double health;
    private double gold;
    private int level;
    private boolean isAlive;

    private int strength;
    private int dexterity;
    private int intelligence;
    private Special special;

    protected AbstractHero() {
        this.isAlive = true;
        this.level = 1;
        this.setGold(Config.HERO_START_GOLD);
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
    public void setHealth(double health) {
        if (health < 0) {
            health = 0;
        }

        if (health > this.getStrength() * Config.HERO_HEALTH_MULTIPLIER) {
            health = this.getStrength() * Config.HERO_HEALTH_MULTIPLIER;
        }
        this.health = health;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public double getGold() {
        return this.gold;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    public void setAlive(boolean str) {
        this.isAlive = str;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Special getSpecial() {
        return this.special;
    }

    public void setSpecial(Special special) {
        this.special = special;
        special.setOwner(this);
    }

    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
        this.setHealth(strength * Config.HERO_HEALTH_MULTIPLIER);
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    @Override
    public void giveReward(Targetable targetable) {
        targetable.receiveReward(this.getGold());
        this.setGold(0);
    }

    @Override
    public void receiveReward(double reward) {
        this.setGold(this.getGold() + reward);
    }

    public void levelUp() {
        this.setHealth(this.getStrength() * Config.HERO_HEALTH_MULTIPLIER);
        this.setStrength(this.getStrength() * 2);
        this.setDexterity(this.getDexterity() * 2);
        this.setIntelligence(this.getIntelligence() * 2);
        this.setLevel(this.getLevel() + 1);
        this.refreshSpecial();
    }

    protected void updateSpecial() {
        Special special = this.getSpecial();
        if (special != null) {
            special.update();
        }
    }

    protected void refreshSpecial() {
        Special special = this.getSpecial();
        if (special != null) {
            special.refresh();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("  Name: %s | Class: %s", this.getName(), this.getClass().getSimpleName()))
                .append(System.lineSeparator())
                .append(String.format("  Health: %.2f | Damage: %.2f", this.getHealth(), this.getDamage()))
                .append(System.lineSeparator())
                .append(String.format("  %d STR | %d DEX | %d INT | %.2f Gold", this.getStrength(), this.getDexterity(), this.getIntelligence(), this.getGold()));

        return sb.toString();
    }

    @Override
    public abstract double getDamage();

    @Override
    public abstract String attack(Targetable target);
}
