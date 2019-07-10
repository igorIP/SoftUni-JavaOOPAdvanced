package hell;

import hell.comparators.HeroComparator;
import hell.entities.heroes.Assassin;
import hell.entities.heroes.Barbarian;
import hell.entities.heroes.Wizard;
import hell.entities.items.CommonItem;
import hell.entities.items.RecipeItem;
import hell.entities.miscellaneous.HeroInventory;
import hell.interfaces.*;
import hell.io.ConsoleReader;
import hell.io.ConsoleWriter;
import hell.repositories.HeroRepository;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static Map<String, Hero> heroes = new LinkedHashMap<>();

    public static void main(String[] args) throws IllegalAccessException {

        InputReader reader = new ConsoleReader();
        OutputWriter writer = new ConsoleWriter();
        Repository<Hero> heroRepository = new HeroRepository();

        while (true) {
            String[] tokens = reader.readLine().split("\\s+");
            String command = tokens[0];
            String[] arguments = Arrays.stream(tokens).skip(1).toArray(String[]::new);

            String result = interpretCommand(command, arguments);

            writer.writeLine(result);

            if (command.equals("Quit"))
                break;
        }
    }

    private static String interpretCommand(String command, String[] arguments) throws IllegalAccessException {
        Hero hero = null;
        switch (command) {
            case "Hero":
                hero = createHero(arguments[0], arguments[1]);
                heroes.put(hero.getName(), hero);
                return String.format("Created %s - %s", hero.getClass().getSimpleName(), hero.getName());
            case "Item":
                Item commonItem = new CommonItem(
                        arguments[0],
                        Integer.parseInt(arguments[2]),
                        Integer.parseInt(arguments[3]),
                        Integer.parseInt(arguments[4]),
                        Integer.parseInt(arguments[5]),
                        Integer.parseInt(arguments[6])
                );

                hero = heroes.get(arguments[1]);
                hero.addItem(commonItem);
                return String.format("Added item - %s to Hero - %s", commonItem.getName(), hero.getName());
            case "Recipe":
                Recipe recipeItem = new RecipeItem(
                        arguments[0],
                        Integer.parseInt(arguments[2]),
                        Integer.parseInt(arguments[3]),
                        Integer.parseInt(arguments[4]),
                        Integer.parseInt(arguments[5]),
                        Integer.parseInt(arguments[6]),
                        Arrays.stream(arguments).skip(7).collect(Collectors.toList())
                );
                hero = heroes.get(arguments[1]);
                hero.addRecipe(recipeItem);
                return String.format("Added recipe - %s to Hero - %s", recipeItem.getName(), hero.getName());
            case "Inspect":
                hero = heroes.get(arguments[0]);
                return inspect(hero);
            case "Quit":
                return quit();
        }
        return null;
    }

    private static Hero createHero(String name, String type) {
        switch (type) {
            case "Barbarian":
                return new Barbarian(name, new HeroInventory());
            case "Assassin":
                return new Assassin(name, new HeroInventory());
            case "Wizard":
                return new Wizard(name, new HeroInventory());
        }
        return null;
    }

    private static String inspect(Hero hero) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();

        sb
                .append(String.format("Hero: %s, Class: %s", hero.getName(), hero.getClass().getSimpleName()))
                .append(System.lineSeparator())
                .append(String.format("HitPoints: %d, Damage: %d", hero.getHitPoints(), hero.getDamage()))
                .append(System.lineSeparator())
                .append(String.format("Strength: %d", hero.getStrength()))
                .append(System.lineSeparator())
                .append(String.format("Agility: %d", hero.getAgility()))
                .append(System.lineSeparator())
                .append(String.format("Intelligence: %d", hero.getIntelligence()))
                .append(System.lineSeparator())
                .append(String.format("Items:%s",
                        hero.getItems().size() == 0 ? " None" : ""))
                .append(System.lineSeparator());

        if (hero.getItems() != null) {
            for (Item item : hero.getItems()) {
                sb.append(item.toString()).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    private static String quit() {
        int counter = 1;
        StringBuilder sb = new StringBuilder();

        List<Hero> heroesCompared = new ArrayList<>(heroes.values());
        heroesCompared.sort(new HeroComparator());

        for (Hero hero : heroesCompared) {
            sb
                    .append(String.format("%d. %s", counter++, hero))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }


}