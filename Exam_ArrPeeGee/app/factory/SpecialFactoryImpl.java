package app.factory;

import app.contracts.SpecialFactory;
import app.contracts.Special;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SpecialFactoryImpl implements SpecialFactory {


    private static final String SPECIAL_PACKAGE_NAME = "app.models.specials.";

    public SpecialFactoryImpl() {}

    @Override
    public Special create(String specialName) {
        Special special = null;

        try {
            Class<?> classSpecial = Class.forName(SPECIAL_PACKAGE_NAME + specialName);
            Constructor<?> declaredConstructor = classSpecial.getDeclaredConstructor();
            special = (Special) declaredConstructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return special;
    }
}
