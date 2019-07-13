package app.models.specials;

import app.contracts.Hero;
import app.contracts.Special;

public abstract class AbstractSpecial implements Special {

    private Hero owner;
    private boolean isActive;

    protected AbstractSpecial() {
        this.isActive = false;
    }

    @Override
    public void setOwner(Hero her0) {
        this.owner = her0;
        this.isActive = true;
    }

    public Hero getOwner() {
        return owner;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
