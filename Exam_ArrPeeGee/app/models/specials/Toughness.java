package app.models.specials;

import app.constants.Config;
import app.contracts.Hero;

public class Toughness extends AbstractSpecial {

    @Override
    public void update() {
        Hero owner = super.getOwner();

        if (!super.isActive()) {
            if (owner.getHealth() <= (owner.getStrength() * Config.HERO_HEALTH_MULTIPLIER) * 0.50) {
                super.setActive(true);
                owner.setStrength(owner.getStrength() + owner.getIntelligence());
            }
        }

        if (super.isActive()) {
            if (owner.getHealth() <= (owner.getStrength() * Config.HERO_HEALTH_MULTIPLIER) * 0.50) {
                super.setActive(false);
                owner.setStrength(owner.getStrength() + owner.getIntelligence());
            }
        }
    }

    @Override
    public void refresh() {
        if (super.isActive()) {
            super.getOwner().setStrength(super.getOwner().getStrength() - super.getOwner().getIntelligence());
            this.setActive(false);
        }
    }
}
