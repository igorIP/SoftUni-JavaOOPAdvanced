package app.factory;

import app.contracts.Targetable;
import app.contracts.TargetableFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TargeatbleFactoryImpl implements TargetableFactory {

    private static final String TARGETABLE_PACKAGE_NAME =
            "app.models.participants.";

    public TargeatbleFactoryImpl() {
    }

    @Override
    public Targetable create(String name, String className) {
        Targetable participant = null;

        try {
            Class<?> classTargetable = Class.forName(TARGETABLE_PACKAGE_NAME + className);
            Constructor<?> declaredConstructor = classTargetable.getDeclaredConstructor();
            participant = (Targetable) declaredConstructor.newInstance();
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return participant;
    }
}
