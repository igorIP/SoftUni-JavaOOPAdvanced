package app.models.participants;

import app.constants.Config;
import app.contracts.Targetable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static app.constants.Config.*;
import static org.junit.Assert.*;

public class WarriorTest {

    //object that are needed in order to test the methods
    Targetable target;

    //tested class
    private Warrior warrior;

    @Before
    public void setUp() throws Exception {
        this.warrior = new Warrior();
    }


    // Test for receiveReward()
    @Test
    public void receiveReward() {
        //Arrange
        double rewardToReceive = Config.HERO_START_GOLD;//+200

        //Act
        this.warrior.receiveReward(rewardToReceive);
        double expected = Config.HERO_START_GOLD + HERO_START_GOLD;
        double actual = this.warrior.getGold();

        //Assert
        Assert.assertEquals(expected, actual, Double.MIN_VALUE);
    }


    // Test for levelUp()
    @Test
    public void levelUp() {
        //Arrange
        this.warrior.setDexterity(4);
        this.warrior.setIntelligence(5);

        //Act
        this.warrior.levelUp();
        double expectedHealth = WARRIOR_BASE_STRENGTH  * HERO_HEALTH_MULTIPLIER * LEVEL_UP_MULTIPLIER;
        double actualHealth = this.warrior.getHealth();
        int expectedStrength = WARRIOR_BASE_STRENGTH *  LEVEL_UP_MULTIPLIER;
        int actualStrength = this.warrior.getStrength();
        int expectedDexterity = 4 * LEVEL_UP_MULTIPLIER;
        int actualDexterity = this.warrior.getDexterity();
        int expectedIntelligence = 5 * LEVEL_UP_MULTIPLIER;
        int actualIntelligence = this.warrior.getIntelligence();
        int expectedLevel = 2;
        int actualLevel = this.warrior.getLevel();

        //Assert
        Assert.assertEquals(expectedHealth, actualHealth, Double.MIN_VALUE);
        Assert.assertEquals(expectedDexterity, actualDexterity);
        Assert.assertEquals(expectedIntelligence, actualIntelligence);
        Assert.assertEquals(expectedStrength, actualStrength);
        Assert.assertEquals(expectedLevel, actualLevel);
    }

    // Test for takeDamage()
    @Test
    public void takeDamage() {
        //Arrange
        double demageToReceive = Config.BOSS_DAMAGE;//-5

        //Act
        double expected = Config.HERO_HEALTH_MULTIPLIER * this.warrior.getStrength();//50
        double actual = this.warrior.getHealth();

        boolean expectedIsAlive = true;
        boolean actualIsAlive = this.warrior.isAlive();

        //Assert
        Assert.assertEquals(expected, actual, 2);
        Assert.assertEquals(expectedIsAlive, actualIsAlive);
    }
}