package app.models.actions;

import app.contracts.Action;
import app.contracts.Targetable;
import app.models.participants.Necromancer;
import app.models.participants.Warrior;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OneVsOneTest {

    private static final String NOT_TWO_PARTICIPANTS = "There should be exactly 2 participants for OneVsOne!";

    private List<Targetable> participants;
    private OneVsOne action;


    @Before
    public void setUp() throws Exception {
        this.action = new OneVsOne();
        this.participants = new ArrayList<>();
    }

    //test for individual count of warriors
    @Test
    public void executeActionShouldFailForInvalidCountOfWarriors() {
        //Arrange

        //Act
        String actual = this.action.executeAction(this.participants);
        String expected = NOT_TWO_PARTICIPANTS;
        //Assert
        Assert.assertEquals(expected, actual);
    }

    // Test for valid count of warriors
    @Test
    public void executeActionShouldSucceedForSecondWarrior() {
        //Arrange
        Warrior victor = Mockito.mock(Warrior.class);
        Necromancer looser = Mockito.mock(Necromancer.class);
        Mockito.doNothing().when(victor).levelUp();
        Mockito.doNothing().when(looser).levelUp();
        Mockito.when(victor.attack(looser)).thenReturn("");
        Mockito.when(looser.attack(victor)).thenReturn("");
        Mockito.when(victor.isAlive()).thenReturn(true);
        Mockito.when(victor.getName()).thenReturn("Victor");
        Mockito.when(looser.isAlive()).thenReturn(false);
        Mockito.when(looser.getName()).thenReturn("Looser");

        this.participants.add(victor);
        this.participants.add(looser);
        //Act
        boolean actual = this.action.executeAction(this.participants).trim().startsWith(victor.getName());
        boolean expected = this.participants.get(0).isAlive();
        //Assert
        Assert.assertEquals(expected ,actual);
    }
}