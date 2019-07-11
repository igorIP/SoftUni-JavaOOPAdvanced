package pr03Barracks.core.commands;

import pr03Barracks.contracts.Executable;

public class FightCommand implements Executable {

    @Override
    public String execute() {
        return "fight";
    }
}
