package app.core;

import app.contracts.*;
import app.models.participants.AbstractHero;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class BattlefieldImplementation implements Battlefield {

    private Map<String, Targetable> participants;

    private List<Action> executedActions;
    private Writer writer;
    private TargetableFactory targetableFactory;
    private ActionFactory actionFactory;
    private SpecialFactory specialFactory;

    public BattlefieldImplementation(Writer writer, ActionFactory actionFactory, TargetableFactory targetableFactory, SpecialFactory specialFactory) {
        this.writer = writer;
        this.actionFactory = actionFactory;
        this.targetableFactory = targetableFactory;
        this.specialFactory = specialFactory;

        this.executedActions = new ArrayList<>();
        this.participants = new TreeMap<>();
    }

    @Override
    public void createAction(String actionName, String... participantNames) {
        try {
            Action action = this.actionFactory.create(actionName);

            List<Targetable> actionParticipants = new ArrayList<>();
            for (String name : participantNames) {
                if (this.participants.containsKey(name)) {
                    actionParticipants.add(this.participants.get(name));
                } else {
                    this.writer.writeLine(String.format("%s is not on the battlefield. %s failed.", name, actionName));
                    return;
                }
            }

            writer.writeLine(action.executeAction(actionParticipants));
            checkForDeadParticipants();
            this.executedActions.add(action);
        } catch (Exception e) {
            writer.writeLine("Action does not exist.");
        }
    }

    @Override
    public void createParticipant(String name, String className) {

        if (this.participants.containsKey(name)) {
            System.out.println("AbstractHero with that name already exists.");
            return;
        }

        Targetable targetable = null;
        try {
            targetable = targetableFactory.create(name, className);
            targetable.setName(name);
            this.participants.put(targetable.getName(), targetable);

            System.out.println(
                    String.format("%s %s entered the battlefield.",
                            targetable.getClass().getSimpleName(),
                            targetable.getName()));
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            System.out.println("AbstractHero class does not exist.");
            e.printStackTrace();
        }
    }

    @Override
    public void createSpecial(String name, String specialName) {
        if (!this.participants.containsKey(name)) {
            writer.writeLine("Participant with that name does not exist.");
        }

        Targetable participant = this.participants.get(name);
        List<String> participantInterfacesNames = Arrays.stream(participant.getClass().getSuperclass().getInterfaces()[0].getInterfaces()).map(Class::getSimpleName).collect(Collectors.toList());

        if (participantInterfacesNames.contains("Specialist")) {
            try {
                Specialist specialist = (Specialist) participant;
                Special special = this.specialFactory.create(specialName);
                specialist.setSpecial(special);
            } catch (Exception e) {
                this.writer.writeLine("Special does not exist.");
            }
        } else {
            writer.writeLine("Participant cannot have special abilities.");
        }
    }

    @Override
    public void reportParticipants() {
        if (this.participants.size() < 1) {
            System.out.println("There are no participants on the battlefield.");
            return;
        }

        for (String name : this.participants.keySet()) {
            System.out.println(this.participants.get(name).toString());
            System.out.println("* * * * * * * * * * * * * * * * * * * *");
        }
    }

    @Override
    public void reportActions() {
        if (this.executedActions.size() < 1) {
            System.out.println("There are no actions on the battlefield.");
            return;
        }

        for (Action executedAction : executedActions) {
            System.out.println(executedAction.getClass().getSimpleName());
        }
    }

    private void checkForDeadParticipants() {
        Map<String, Targetable> aliveAbstractHero = new TreeMap<>();

        for (Targetable participant : this.participants.values()) {
            if (!participant.isAlive()) {
                System.out.println(String.format("%s has been removed from the battlefield.", participant));
            } else {
                aliveAbstractHero.put(participant.getName(), participant);
            }
        }

        this.participants = aliveAbstractHero;
    }
}
