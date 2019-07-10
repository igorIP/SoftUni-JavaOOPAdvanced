package panzer.models.miscellaneous;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import panzer.contracts.*;
import panzer.models.parts.ArsenalPart;
import panzer.models.parts.EndurancePart;
import panzer.models.parts.ShellPart;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class VehicleAssemblerTest {

    private VehicleAssembler vehicleAssembler;

    private ArsenalPart attackModifyingPart;
    private ShellPart defenseModifyingPart;
    private EndurancePart hitPointsModifyingPart;

    @Before
    public void setUp() throws Exception {
        //every test this will be a new instance of the class that we are testing
        this.vehicleAssembler = new VehicleAssembler();

        //every test new dummy objects for the class that we are testing so that
        //    she can play(add them to her methods, modify them,... and make Mockito.when().then() with some of them.)
        this.attackModifyingPart = Mockito.mock(ArsenalPart.class);
        this.defenseModifyingPart = Mockito.mock(ShellPart.class);
        this.hitPointsModifyingPart = Mockito.mock(EndurancePart.class);


        //Here I take the TESTED CLASS constructor then
        //THIS CLASS FIELD to POINT TO the that one constructor.
        Constructor<? extends Assembler> declaredConstructor = this.vehicleAssembler.getClass().getDeclaredConstructor();
        this.vehicleAssembler = (VehicleAssembler) declaredConstructor.newInstance();

        Field arsenalPartsField = vehicleAssembler.getClass().getDeclaredField("arsenalParts");
        arsenalPartsField.setAccessible(true);
        List<AttackModifyingPart> arsenalPartsList = (List<AttackModifyingPart>) arsenalPartsField.get(vehicleAssembler);
        List<DefenseModifyingPart> shellPartsList = (List<DefenseModifyingPart>) arsenalPartsField.get(vehicleAssembler);
        List<HitPointsModifyingPart> defensePartsList = (List<HitPointsModifyingPart>) arsenalPartsField.get(vehicleAssembler);

        //Not necessary but her i use a method from the new instance EVERY-TIME i test, and
        //     add the dummy objects from the code 4 lines above to the new instance fields->pointer to where it points to.
        this.vehicleAssembler.addArsenalPart(attackModifyingPart);
        this.vehicleAssembler.addShellPart(defenseModifyingPart);
        this.vehicleAssembler.addEndurancePart(hitPointsModifyingPart);

    }

    @Test
    public void getTotalWeight() {
        //arrange
        Mockito.when(this.attackModifyingPart.getWeight()).thenReturn(1.0);
        Mockito.when(this.defenseModifyingPart.getWeight()).thenReturn(1.0);
        Mockito.when(this.hitPointsModifyingPart.getWeight()).thenReturn(1.0);
        //act
        double totalWeight = this.attackModifyingPart.getWeight() + this.defenseModifyingPart.getWeight() + this.hitPointsModifyingPart.getWeight();
        double expectedTotalWeight = 3.0;
        //assert
        Assert.assertEquals(expectedTotalWeight, totalWeight, 0.01);
    }

    @Test
    public void getTotalPrice() {
        //arrange
        Mockito.when(attackModifyingPart.getPrice()).thenReturn(new BigDecimal(BigInteger.TEN));
        Mockito.when(defenseModifyingPart.getPrice()).thenReturn(new BigDecimal(BigInteger.TWO));
        Mockito.when(hitPointsModifyingPart.getPrice()).thenReturn(new BigDecimal(BigInteger.ONE));

        //act
        BigDecimal actualTotalPrice = this.vehicleAssembler.getTotalPrice();
        BigDecimal excepted = BigDecimal.valueOf(13);
        //assert
        Assert.assertEquals(excepted, actualTotalPrice);
    }

    @Test
    public void getTotalAttackModification() {
        Mockito.when(this.attackModifyingPart.getAttackModifier()).thenReturn(50);
        AttackModifyingPart attackModifyingPart = Mockito.mock(AttackModifyingPart.class);
        Mockito.when(attackModifyingPart.getAttackModifier()).thenReturn(120);

        this.vehicleAssembler.addArsenalPart(attackModifyingPart);
        long totalAttackModification = this.vehicleAssembler.getTotalAttackModification();
        long expectedTotaAttackModification = 170;

        Assert.assertEquals(expectedTotaAttackModification, totalAttackModification);
    }

    @Test
    public void getTotalDefenseModification() {
        Mockito.when(this.defenseModifyingPart.getDefenseModifier()).thenReturn(50);
        DefenseModifyingPart shellPart = Mockito.mock(DefenseModifyingPart.class);
        Mockito.when(shellPart.getDefenseModifier()).thenReturn(100);
        this.vehicleAssembler.addShellPart(shellPart);

        Assert.assertEquals(150, this.vehicleAssembler.getTotalDefenseModification());
    }

    @Test
    public void getTotalHitPointModification() {
    }

    @Test
    public void addArsenalPartTest() {

        Mockito.when(attackModifyingPart.getAttackModifier()).thenReturn(10);

        vehicleAssembler.addArsenalPart(attackModifyingPart);


        long totalAttackModification = vehicleAssembler.getTotalAttackModification();
        long expectedtotalModification = 20;

        Assert.assertEquals(expectedtotalModification, totalAttackModification);
    }

    @Test
    public void addShellPart() {
    }

    @Test
    public void addEndurancePart() {
    }
}
