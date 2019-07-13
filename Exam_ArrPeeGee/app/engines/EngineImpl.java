package app.engines;

import app.constants.InputCommands;
import app.constants.TextsOutput;
import app.contracts.*;
import java.io.IOException;
import java.util.Arrays;

public class EngineImpl implements Engine {

    private Reader reader;
    private Writer writer;
    private Battlefield battleField;
    //private ActionFactory actionFactory;
    // private TargetableFactory targeableFactory;
    //private InputCommands inputCommands;

    public EngineImpl(Reader reader, Writer writer, Battlefield battleField) {
        this.reader = reader;
        this.writer = writer;
        this.battleField = battleField;
        // this.actionFactory = actionFactory;
        //this.targetableFactory = targetableFactory;
        //this.inputCommands = inputCommands;
    }

    @Override
    public void run() throws IOException {
        String line = reader.readLine();
        while (!InputCommands.TERMINATE.equals(line)) {
            String[] lineTokens = line.split(TextsOutput.SPLITTER_COMMAND);

            switch (lineTokens[0]) {
                case InputCommands.CREATE_PARTICIPANT:
                    battleField.createParticipant(lineTokens[1], lineTokens[2]);
                    break;
                case InputCommands.CREATE_ACTION:
                    battleField.createAction(lineTokens[1],
                            Arrays.copyOf(Arrays.stream(lineTokens).skip(2).toArray(),
                                    Arrays.stream(lineTokens).skip(2).toArray().length,
                                    String[].class));
                    break;
                case InputCommands.CREATE_SPECIAL:
                    battleField.createSpecial(lineTokens[1], lineTokens[2]);
                    break;
                case InputCommands.STAT_ACTION:
                    battleField.reportActions();
                    break;
                case InputCommands.STAT_PARTICIPANT:
                    battleField.reportParticipants();
                    break;
                default:
                    this.writer.writeLine(InputCommands.INVALID_COMMAND);
                    break;
            }

            line = reader.readLine();
        }
    }
}
