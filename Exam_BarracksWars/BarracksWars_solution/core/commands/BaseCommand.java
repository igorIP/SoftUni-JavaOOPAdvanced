package pr03Barracks.core.commands;

import pr03Barracks.contracts.Executable;
import pr03Barracks.contracts.Repository;
import pr03Barracks.contracts.UnitFactory;

public abstract class BaseCommand implements Executable {

    private String[] data;

    //    private Repository repository;
//    private UnitFactory unitFactory;

//    protected BaseCommand(String[] data, Repository repository, UnitFactory unitFactory) {
//        this.data = data;
//        this.repository = repository;
//        this.unitFactory = unitFactory;
//    }
//
//    protected Repository getRepository() {
//        return repository;
//    }
//
//    protected UnitFactory getUnitFactory() {
//        return unitFactory;
//    }

    public String[] getData() {
        return data;
    }
}
