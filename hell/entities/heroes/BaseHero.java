package hell.entities.heroes;

import hell.entities.miscellaneous.ItemCollection;
import hell.interfaces.Hero;
import hell.interfaces.Inventory;
import hell.interfaces.Item;
import hell.interfaces.Recipe;


import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseHero implements Hero {
    private String name;
    private int strength;
    private int agility;
    private int intelligence;
    private int hitPoints;
    private int damage;
    private Inventory inventory;
    private Item item;


    protected BaseHero(String name, int strength, int agility, int intelligence, int hitPoints, int damage, Inventory inventory) {
        this.name = name;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.hitPoints = hitPoints;
        this.damage = damage;
        this.inventory = inventory;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getStrength() {
        return this.strength + this.inventory.getTotalStrengthBonus();
    }

    @Override
    public long getAgility() {
        return this.agility + this.inventory.getTotalAgilityBonus();
    }

    @Override
    public long getIntelligence() {
        return this.intelligence + this.inventory.getTotalIntelligenceBonus();
    }

    @Override
    public long getHitPoints() {
        return this.hitPoints + this.inventory.getTotalHitPointsBonus();
    }

    @Override
    public long getDamage() {
        return this.damage + this.inventory.getTotalDamageBonus();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Item> getItems() throws IllegalAccessException {
        Collection<Item> items = null;
        Field[] inventoryFields = this.inventory.getClass().getDeclaredFields();
        for (Field inventoryField : inventoryFields) {
            if (inventoryField.isAnnotationPresent(ItemCollection.class)) {
                inventoryField.setAccessible(true);
                Map<String, Item> itemMap = (Map<String, Item>) inventoryField.get(this.inventory);
                items = itemMap.values();
            }
        }
        return items;
    }

    @Override
    public void addItem(Item item) {
        this.inventory.addCommonItem(item);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        this.inventory.addRecipeItem(recipe);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        try {
            sb
                    .append(String.format("%s: %s", this.getClass().getSimpleName(), this.getName()))
                    .append(System.lineSeparator())
                    .append(String.format("HitPoints: %d", this.getHitPoints()))
                    .append(System.lineSeparator())
                    .append(String.format("Damage: %d", this.getDamage()))
                    .append(System.lineSeparator())
                    .append(String.format("Strength: %d", this.getStrength()))
                    .append(System.lineSeparator())
                    .append(String.format("Agility: %d", this.getAgility()))
                    .append(System.lineSeparator())
                    .append(String.format("Intelligence: %d", this.getIntelligence()))
                    .append(System.lineSeparator())
                    .append(String.format("Items:%s",
                            this.getItems().size() == 0 ? " None"
                                    : this.getItems().stream()
                                    .map(Item::getName).collect(Collectors.joining(", "))))
                    .append(System.lineSeparator());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString().trim();
    }
}
