package app.factory;

import app.contracts.Action;
import app.contracts.ActionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ActionFactoryImpl implements ActionFactory {

    private static final String ACTION_PACKAGE_NAME =
            "app.models.actions.";

    @Override
    public Action create(String actionName, String... participantNames) {
        Action newAction = null;

        try {
            Class<?> classAction = Class.forName(ACTION_PACKAGE_NAME + actionName);
            Constructor<?> declaredConstructor = classAction.getDeclaredConstructor();
            newAction = (Action) declaredConstructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }



        return newAction;
    }
}
