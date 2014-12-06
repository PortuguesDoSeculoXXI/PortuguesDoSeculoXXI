/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Filipe
 */
public class ScoreTest {
    private Score instance;
    
    public ScoreTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Date date = new Date(2015, 01, 01);
        instance = new Score(1, 123, date, 4, Level.MODE_EASY, 1, 1, 1);
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of getDateTime method, of class Score.
     */
    @Test
    public void testGetDateTime() {
        System.out.println("getDateTime");
        instance.getDateTime();
        Date expResult = new Date(2015, 01, 01);
        Date result = instance.getDateTime();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of setDateTime method, of class Score.
     */
    @Test
    public void testSetDateTime() {
        System.out.println("setDateTime");
        Date dateTime = new Date(2015, 01, 02);
        instance.setDateTime(dateTime);
        Date expResult = dateTime;
        Date result = instance.getDateTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDuration method, of class Score.
     */
    @Test
    public void testGetDuration() {
        System.out.println("getDuration");
        int expResult = 4;
        int result = instance.getDuration();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDuration method, of class Score.
     */
    @Test
    public void testSetDuration() {
        System.out.println("setDuration");
        int duration = 6;
        instance.setDuration(duration);
        int expResult = duration;
        int result = instance.getDuration();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLevel method, of class Score.
     */
    @Test
    public void testGetLevel() {
        System.out.println("getLevel");
        Level expResult = Level.MODE_EASY;
        Level result = instance.getLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLevel method, of class Score.
     */
    @Test
    public void testSetLevel() {
        System.out.println("setLevel");
        Level level = Level.MODE_HARD;
        instance.setLevel(level);
        Level expResult = level;
        Level result = instance.getLevel();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getGold method, of class Score.
     */
    @Test
    public void testGetGold() {
        System.out.println("getGold");
        int expResult = 1;
        int result = instance.getGold();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGold method, of class Score.
     */
    @Test
    public void testSetGold() {
        System.out.println("setGold");
        int gold = 0;
        instance.setGold(gold);
        int expResult = gold;
        int result = instance.getGold();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSilver method, of class Score.
     */
    @Test
    public void testGetSilver() {
        System.out.println("getSilver");
        int expResult = 1;
        int result = instance.getSilver();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSilver method, of class Score.
     */
    @Test
    public void testSetSilver() {
        System.out.println("setSilver");
        int silver = 0;
        instance.setSilver(silver);
        int expResult = silver;
        int result = instance.getSilver();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBronze method, of class Score.
     */
    @Test
    public void testGetBronze() {
        System.out.println("getBronze");
        int expResult = 1;
        int result = instance.getBronze();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBronze method, of class Score.
     */
    @Test
    public void testSetBronze() {
        System.out.println("setBronze");
        int bronze = 0;
        instance.setBronze(bronze);
        int expResult = bronze;
        int result = instance.getBronze();
        assertEquals(expResult, result);

    }

    /**
     * Test of getIdPlayer method, of class Score.
     */
    @Test
    public void testGetIdPlayer() {
        System.out.println("getIdPlayer");
        int expResult = 1;
        int result = instance.getIdPlayer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getScore method, of class Score.
     */
    @Test
    public void testGetScore() {
        System.out.println("getScore");
        int expResult = 123;
        int result = instance.getScore();
        assertEquals(expResult, result);
    }
    
}
