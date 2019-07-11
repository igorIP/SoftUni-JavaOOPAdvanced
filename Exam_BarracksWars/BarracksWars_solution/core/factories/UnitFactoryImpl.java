package pr03Barracks.core.factories;

import pr03Barracks.contracts.Unit;
import pr03Barracks.contracts.UnitFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class UnitFactoryImpl implements UnitFactory {

    private static final String UNITS_PACKAGE_NAME =
            "pr03Barracks.models.units.";

    @Override
    public Unit createUnit(String unitType) {
        Unit unit = null;
        try {
            Class<?> aClassUnitType = Class.forName(UNITS_PACKAGE_NAME + unitType);
            Constructor<?> declaredConstructor = aClassUnitType.getDeclaredConstructor();
            unit = (Unit) declaredConstructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return unit;
    }
}
