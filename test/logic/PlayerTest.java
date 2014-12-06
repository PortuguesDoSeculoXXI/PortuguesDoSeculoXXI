/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

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
public class PlayerTest {
    private Player instance;
    
    public PlayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Player(1, "Carol");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Player.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Player.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Player instance = new Player(1, "Carol");
        String expResult = "Carol";
        String result = instance.getName();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testClass() {
        System.out.println("getClass");
        Player instance = new Player(1, "Carol");
        String expResult = "Id: 1 Name: Carol";
        String result = "Id: " + instance.getId() + " Name: " + instance.getName();
        assertEquals(expResult, result);
    }
    
}
