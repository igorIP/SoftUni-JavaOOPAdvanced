package app.models.specials;

import app.constants.Config;
import app.contracts.Hero;

public class Swiftness extends AbstractSpecial {

    @Override
    public void update() {
        Hero owner = super.getOwner();
        if (!super.isActive()) {
            if (owner.getHealth() >= (owner.getStrength() * Config.HERO_HEALTH_MULTIPLIER) * 0.5) {
                super.setActive(true);
                owner.setDexterity(owner.getDexterity() + owner.getIntelligence());
            }
        }

        if (super.isActive()) {
            if (owner.getHealth() < (owner.getStrength() * Config.HERO_HEALTH_MULTIPLIER) * 0.5) {
                this.setActive(false);
                owner.setDexterity(owner.getDexterity() - owner.getIntelligence());
            }
        }
    }

    @Override
    public void refresh() {
        if (super.isActive()) {
            super.getOwner().setDexterity(super.getOwner().getDexterity() - super.getOwner().getIntelligence());
            this.setActive(false);
        }
    }
}
