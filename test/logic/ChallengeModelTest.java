/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;
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
public class ChallengeModelTest {
    
    public ChallengeModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setChallenge method, of class ChallengeModel.
     */
    @Test
    public void testSetChallenge() {
        System.out.println("setChallenge");
        Challenge challenge = null;
        ChallengeModel instance = null;
        instance.setChallenge(challenge);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendNotification method, of class ChallengeModel.
     */
    @Test
    public void testSendNotification() {
        System.out.println("sendNotification");
        ChallengeModel instance = null;
        instance.sendNotification();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of newGame method, of class ChallengeModel.
     */
    @Test
    public void testNewGame() {
        System.out.println("newGame");
        ChallengeModel instance = null;
        instance.newGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startGame method, of class ChallengeModel.
     */
    @Test
    public void testStartGame() {
        System.out.println("startGame");
        List<Challenge.Categories> categoryList = null;
        Challenge challengeMode = null;
        ChallengeModel instance = null;
        instance.startGame(categoryList, challengeMode);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextAnswer method, of class ChallengeModel.
     */
    @Test
    public void testNextAnswer() {
        System.out.println("nextAnswer");
        int answer = 0;
        ChallengeModel instance = null;
        instance.nextAnswer(answer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of quitGame method, of class ChallengeModel.
     */
    @Test
    public void testQuitGame() {
        System.out.println("quitGame");
        ChallengeModel instance = null;
        instance.quitGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of end method, of class ChallengeModel.
     */
    @Test
    public void testEnd() {
        System.out.println("end");
        ChallengeModel instance = null;
        instance.end();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurrentProfile method, of class ChallengeModel.
     */
    @Test
    public void testSetCurrentProfile() {
        System.out.println("setCurrentProfile");
        Player player = null;
        ChallengeModel instance = null;
        instance.setCurrentProfile(player);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChallenge method, of class ChallengeModel.
     */
    @Test
    public void testGetChallenge() {
        System.out.println("getChallenge");
        ChallengeModel instance = null;
        Challenge expResult = null;
        Challenge result = instance.getChallenge();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentPlayer method, of class ChallengeModel.
     */
    @Test
    public void testGetCurrentPlayer() {
        System.out.println("getCurrentPlayer");
        ChallengeModel instance = null;
        Player expResult = null;
        Player result = instance.getCurrentPlayer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of backPressed method, of class ChallengeModel.
     */
    @Test
    public void testBackPressed() {
        System.out.println("backPressed");
        ChallengeModel instance = null;
        instance.backPressed();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
