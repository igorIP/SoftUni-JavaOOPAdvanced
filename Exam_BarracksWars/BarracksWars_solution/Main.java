package pr03Barracks;

import pr03Barracks.contracts.Repository;
import pr03Barracks.contracts.Runnable;
import pr03Barracks.contracts.UnitFactory;
import pr03Barracks.core.Engine;
import pr03Barracks.core.factories.UnitFactoryImpl;
import pr03Barracks.data.UnitRepository;

public class Main {

    public static void main(String[] args) {
        Repository repository = new UnitRepository();
        UnitFactory unitFactory = new UnitFactoryImpl();
        Runnable engine = new Engine(repository, unitFactory);
        engine.run();
    }
}
