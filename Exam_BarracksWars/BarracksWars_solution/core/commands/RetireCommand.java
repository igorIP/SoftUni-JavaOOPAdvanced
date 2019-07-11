package pr03Barracks.core.commands;

import pr03Barracks.annotations.Inject;
import pr03Barracks.contracts.Executable;
import pr03Barracks.contracts.Repository;

public class RetireCommand implements Executable {

    @Inject
    private String[] data;

    @Inject
    private Repository repository;


    @Override
    public String execute() {
        String unitType = this.data[1];
        try {
            this.repository.removeUnit(unitType);
            return unitType + " retired!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
