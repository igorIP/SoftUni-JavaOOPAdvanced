package pr03Barracks.core.commands;

import pr03Barracks.annotations.Inject;
import pr03Barracks.contracts.Executable;
import pr03Barracks.contracts.Repository;


public class ReportCommand implements Executable {

    @Inject
    private Repository repository;

    @Override
    public String execute() {
        return this.repository.getStatistics();
    }
}
