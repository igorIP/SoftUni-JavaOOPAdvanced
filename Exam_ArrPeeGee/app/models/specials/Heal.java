package app.models.specials;

import app.constants.Config;
import app.contracts.Hero;

public class Heal extends AbstractSpecial {

    private int flag;

    public Heal() {
    }

    //• Heal - the hero gains health equal to his points of intelligence every time the ability is triggered.
    // Trigger happens if the hero's health is below or equal to 50%.

    @Override
    public void update() {
        Hero owner = super.getOwner();
        if (!super.isActive()) {
            if (owner.isAlive() &&
                    owner.getHealth() <= (owner.getStrength() * Config.HERO_HEALTH_MULTIPLIER) * 0.50) {
                super.setActive(true);
                this.flag = 1;
            }
        }

        if (super.isActive()) {
            owner.setHealth(owner.getHealth() + owner.getIntelligence());
            flag--;
            if (flag <= 0) {
                super.setActive(false);
            }
        }
    }


    @Override
    public void refresh() {
        if (this.isActive()) {
            this.setActive(false);
            this.flag = 1;
        }
    }

}
