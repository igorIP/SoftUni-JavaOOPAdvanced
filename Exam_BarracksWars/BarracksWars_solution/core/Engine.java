package pr03Barracks.core;

import pr03Barracks.annotations.Inject;
import pr03Barracks.contracts.*;
import pr03Barracks.contracts.Runnable;
import pr03Barracks.contracts.UnitFactory;
import pr03Barracks.contracts.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class Engine implements Runnable {

    private static final String COMMAND_PATH = "pr03Barracks.core.commands.";
    private static final String COMMAND_SUFFIX_NAME = "Command";

    private String[] data;
    private Repository repository;
    private UnitFactory unitFactory;

    public Engine(Repository repository, UnitFactory unitFactory) {
        this.repository = repository;
        this.unitFactory = unitFactory;
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        while (true) {
            try {
                String input = reader.readLine();
                String[] data = input.split("\\s+");
                String commandName = data[0];
                String result = interpretedCommand(data, commandName);
                if (result.equals("fight")) {
                    break;
                }
                System.out.println(result);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String interpretedCommand(String[] data, String commandName) {
        this.data = data;
        try {
            String realCommandClassName =
                    Character.toUpperCase(commandName.charAt(0)) +
                            commandName.substring(1);
            Class<?> CommandClass = Class.forName(COMMAND_PATH + realCommandClassName + COMMAND_SUFFIX_NAME);

            Constructor<?> classCommandConstructor = CommandClass.getDeclaredConstructor();
            Executable command = (Executable) classCommandConstructor.newInstance();
            this.injectDependencies(command);

            return command.execute();

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid Command!");
        }
    }

    private <T> void injectDependencies(T command) throws IllegalAccessException {
        Field[] commandFields = command.getClass().getDeclaredFields();
        Field[] enginedFields = this.getClass().getDeclaredFields();

        for (Field commandField : commandFields) {
            commandField.setAccessible(true);
            if (commandField.isAnnotationPresent(Inject.class)) {
                for (Field enginedField : enginedFields) {
                    enginedField.setAccessible(true);
                    if (commandField.getType().getSimpleName().equals(enginedField.getType().getSimpleName()) &&
                            commandField.getType().equals(enginedField.getType())) {
                        commandField.set(command, enginedField.get(this));
                    }
                }
            }
        }
    }
}
