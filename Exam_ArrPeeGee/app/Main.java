package app;

import app.contracts.*;
import app.core.BattlefieldImplementation;
import app.engines.EngineImpl;
import app.factory.ActionFactoryImpl;
import app.factory.SpecialFactoryImpl;
import app.factory.TargeatbleFactoryImpl;
import app.io.ConsoleReader;
import app.io.ConsoleWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Reader reader = new ConsoleReader();
        Writer writer = new ConsoleWriter();

        ActionFactory actionFactory = new ActionFactoryImpl();
        TargetableFactory targetableFactory = new TargeatbleFactoryImpl();
        SpecialFactory specialFactory = new SpecialFactoryImpl();

        Battlefield battleField = new BattlefieldImplementation(writer, actionFactory, targetableFactory, specialFactory);
        Engine engine = new EngineImpl(reader, writer, battleField);

        engine.run();

    }
}
