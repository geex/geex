package nl.tudelft.context.graph;

import junit.framework.TestCase;
import nl.tudelft.context.graph.BaseCounter;
import org.junit.Test;

/**
 * @author Jasper on 6-5-2015.
 * @version 1f
 * @since 6-5-2015
 */
public class BaseCounterTest extends TestCase {

    protected BaseCounter baseCounter1, baseCounter2, baseCounter3, baseCounter4, baseCounter5;

    public void setUp() throws Exception {
        super.setUp();
        baseCounter1 = new BaseCounter("AAAAAAAAAA");
        baseCounter2 = new BaseCounter("ATCGATCGATCG");
        baseCounter3 = new BaseCounter("ATTCGCTCACANNNNNNNNNNNNNATCCCTTTACCCG");
        baseCounter4 = new BaseCounter("");
        baseCounter5 = new BaseCounter("ATCGNZ");
    }

    @Test
    public void testGetIntEmptyString() throws Exception {
        assertEquals(0, baseCounter4.getInt('A'));
        assertEquals(0, baseCounter4.getInt('T'));
        assertEquals(0, baseCounter4.getInt('C'));
        assertEquals(0, baseCounter4.getInt('G'));
        assertEquals(0, baseCounter4.getInt('N'));

    }

    @Test
    public void testGetIntOnOnlyThisBaseInDna() throws Exception {
        assertEquals(10, baseCounter1.getInt('A'));
    }

    @Test
    public void testGetIntNotOccuringInDna() throws Exception {
        assertEquals(0, baseCounter1.getInt('T'));
    }

    @Test
    public void testGetIntCombinedButNotN() throws Exception {
        assertEquals(3, baseCounter2.getInt('A'));
        assertEquals(3, baseCounter2.getInt('T'));
        assertEquals(3, baseCounter2.getInt('C'));
        assertEquals(3, baseCounter2.getInt('G'));
        assertEquals(0, baseCounter2.getInt('N'));

    }

    @Test
    public void testGetIntAllCombined() throws Exception {
        assertEquals(5, baseCounter3.getInt('A'));
        assertEquals(7, baseCounter3.getInt('T'));
        assertEquals(10, baseCounter3.getInt('C'));
        assertEquals(2, baseCounter3.getInt('G'));
        assertEquals(13, baseCounter3.getInt('N'));
    }

    @Test
    public void testGetIntInvalidCharInDNA() throws Exception {
        assertEquals(1, baseCounter5.getInt('A'));
        assertEquals(1, baseCounter5.getInt('T'));
        assertEquals(1, baseCounter5.getInt('C'));
        assertEquals(1, baseCounter5.getInt('G'));
        assertEquals(1, baseCounter5.getInt('N'));
        assertEquals(0, baseCounter5.getInt('Z'));
    }


    @Test
    public void testGetIntNotExistingChar() throws Exception {
        assertEquals(0, baseCounter3.getInt('H'));
    }

    @Test
    public void testgetPercentageEmptyString() throws Exception {
        assertEquals(0f, baseCounter4.getPercentage('A'));
        assertEquals(0f, baseCounter4.getPercentage('T'));
        assertEquals(0f, baseCounter4.getPercentage('C'));
        assertEquals(0f, baseCounter4.getPercentage('G'));
        assertEquals(0f, baseCounter4.getPercentage('N'));

    }

    @Test
    public void testGetPercentageOnOnlyThisBaseInDna() throws Exception {
        assertEquals(100f, baseCounter1.getPercentage('A'));
        assertEquals(0f, baseCounter1.getPercentage('T'));
        assertEquals(0f, baseCounter1.getPercentage('C'));
        assertEquals(0f, baseCounter1.getPercentage('G'));
        assertEquals(0f, baseCounter1.getPercentage('N'));
    }

    @Test
    public void testGetPercentageNotOccuringInDna() throws Exception {
        assertEquals(0f, baseCounter1.getPercentage('T'));
    }

    @Test
    public void testGetPercentageCombinedButNotN() throws Exception {
        assertEquals(25f, baseCounter2.getPercentage('A'));
        assertEquals(25f, baseCounter2.getPercentage('T'));
        assertEquals(25f, baseCounter2.getPercentage('C'));
        assertEquals(25f, baseCounter2.getPercentage('G'));
        assertEquals(0f, baseCounter2.getPercentage('N'));

    }

    @Test
    public void testGetPercentageAllCombined() throws Exception {
        assertEquals(13.513514f, baseCounter3.getPercentage('A'));
        assertEquals(18.918919f, baseCounter3.getPercentage('T'));
        assertEquals(27.027027f, baseCounter3.getPercentage('C'));
        assertEquals(5.4054055f, baseCounter3.getPercentage('G'));
        assertEquals(35.135136f, baseCounter3.getPercentage('N'));
    }


    @Test
    public void testGetPercentageInNotExistingChar() throws Exception {
        assertEquals(0f, baseCounter3.getPercentage('H'));
    }

    @Test
    public void testGetPercentageInvalidCharInDNA() throws Exception {
        assertEquals(20f, baseCounter5.getPercentage('A'));
        assertEquals(20f, baseCounter5.getPercentage('T'));
        assertEquals(20f, baseCounter5.getPercentage('C'));
        assertEquals(20f, baseCounter5.getPercentage('G'));
        assertEquals(20f, baseCounter5.getPercentage('N'));
        assertEquals(0f, baseCounter5.getPercentage('Z'));
    }

    @Test
    public void testGetPercStringRoundNumber() throws Exception {
        assertEquals("20.0", baseCounter5.getPercString('A'));
        assertEquals("20.0", baseCounter5.getPercString('T'));
        assertEquals("20.0", baseCounter5.getPercString('C'));
        assertEquals("20.0", baseCounter5.getPercString('G'));
        assertEquals("20.0", baseCounter5.getPercString('N'));
        assertEquals("0.0", baseCounter5.getPercString('Z'));
    }

    @Test
    public void testGetPercStringRounding() throws Exception {
        assertEquals("13.51", baseCounter3.getPercString('A'));
        assertEquals("18.92", baseCounter3.getPercString('T'));
        assertEquals("27.03", baseCounter3.getPercString('C'));
        assertEquals("5.41", baseCounter3.getPercString('G'));
        assertEquals("35.14", baseCounter3.getPercString('N'));
        assertEquals("0.0", baseCounter3.getPercString('Z'));
    }

}
