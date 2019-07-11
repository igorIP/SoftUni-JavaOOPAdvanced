package pr03Barracks.core.commands;

import pr03Barracks.annotations.Inject;
import pr03Barracks.contracts.Executable;
import pr03Barracks.contracts.Repository;
import pr03Barracks.contracts.Unit;
import pr03Barracks.contracts.UnitFactory;

public class AddCommand implements Executable {

    @Inject
    private String[] data;
    @Inject
    private Repository repository;
    @Inject
    private UnitFactory unitFactory;


    @Override
    public String execute() {
        String unitType = this.data[1];
        Unit unitToAdd = this.unitFactory.createUnit(unitType);
        this.repository.addUnit(unitToAdd);
        return unitType + " added!";
    }
}
