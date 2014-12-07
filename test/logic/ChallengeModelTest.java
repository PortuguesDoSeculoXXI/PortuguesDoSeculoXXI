/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.List;
import logic.database.Controller;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import states.WaitAnswer;
import states.WaitConfiguration;
import states.WaitScore;

/**
 *
 * @author Filipe
 */
public class ChallengeModelTest {
    private ChallengeModel instanceCM;
    private Challenge instanceC;
    private Player player;
    private Controller controller;
    
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
        //player = new Player(1, "Carol");
        //controller = new Controller();
        instanceC = new Challenge(null, player);
        instanceCM = new ChallengeModel(instanceC);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setChallenge method, of class ChallengeModel.
     */
//    @Test
//    public void testSetChallenge() {
//        System.out.println("setChallenge");
//        Challenge challenge = new Challenge(null, null);
//        instanceCM.setChallenge(challenge);
//        if(instanceCM.getChallenge().ge)
//       
//    }

    /**
     * Test of sendNotification method, of class ChallengeModel.
     */
//    @Test
//    public void testSendNotification() {
//        System.out.println("sendNotification");
//        ChallengeModel instance = null;
//        instance.sendNotification();
//        //TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of newGame method, of class ChallengeModel.
     */
    @Test
    public void testNewGame() {
        System.out.println("newGame");
        instanceCM.newGame();
        if(!(instanceCM.getChallenge().getCurrentState() instanceof WaitConfiguration))
            fail("Error.");
    }

    /**
     * Test of startGame method, of class ChallengeModel.
     */
//    @Test
//    public void testStartGame() {
//        System.out.println("startGame");
//        List<Challenge.Categories> categoryList = new ArrayList<>();
//        categoryList.add(Challenge.Categories.RANDOM);
//        instanceCM.startGame(categoryList);
//        if(!(instanceCM.getChallenge().getCurrentState() instanceof WaitAnswer))
//            fail("Error.");
//    }

    /**
     * Test of nextAnswer method, of class ChallengeModel.
     */
//    @Test
//    public void testNextAnswer() {
//        System.out.println("nextAnswer");
//        Answer answer = null;
//        ChallengeModel instance = null;
//        instance.nextAnswer(answer);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of quitGame method, of class ChallengeModel.
     */
    @Test
    public void testQuitGame() {
        System.out.println("quitGame");
        instanceCM.quitGame();
        if(!(instanceCM.getChallenge().getCurrentState() instanceof WaitConfiguration)) //nai tenho a certeza se e este estado?!
            fail("Error.");
    }

    /**
     * Test of end method, of class ChallengeModel.
     */
    @Test
    public void testEnd() {
        System.out.println("end");
        instanceCM.end();
        if(!(instanceCM.getChallenge().getCurrentState() instanceof WaitScore)) 
            fail("Error.");
    }

    /**
     * Test of setCurrentProfile method, of class ChallengeModel.
     */
    @Test
    public void testSetCurrentProfile() {
        System.out.println("setCurrentProfile");
        Player player = new Player(2, "John");
        instanceCM.setCurrentProfile(player);
        String expResult = "Id: 2 Name: John";
        String result = "Id: " + instanceCM.challenge.getCurrentProfile().getId() + " Name: " + instanceCM.challenge.getCurrentProfile().getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChallenge method, of class ChallengeModel.
     */
//    @Test
//    public void testGetChallenge() {
//        System.out.println("getChallenge");
//        ChallengeModel instance = null;
//        Challenge expResult = null;
//        Challenge result = instance.getChallenge();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getCurrentPlayer method, of class ChallengeModel.
     */
    @Test
    public void testGetCurrentPlayer() {
        System.out.println("getCurrentPlayer");
        Player player = new Player(2, "John");
        instanceCM.setCurrentProfile(player);
        String expResult = "Id: 2 Name: John";
        String result = "Id: " + instanceCM.challenge.getCurrentProfile().getId() + " Name: " + instanceCM.challenge.getCurrentProfile().getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of backPressed method, of class ChallengeModel.
     */
//    @Test
//    public void testBackPressed() {
//        System.out.println("backPressed");
//        ChallengeModel instance = null;
//        instance.backPressed();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of isScoreWindow method, of class ChallengeModel.
     */
//    @Test
//    public void testIsScoreWindow() {
//        System.out.println("isScoreWindow");
//        ChallengeModel instance = null;
//        boolean expResult = false;
//        boolean result = instance.isScoreWindow();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of setScoreWindow method, of class ChallengeModel.
     */
    @Test
    public void testSetScoreWindow() {
        System.out.println("setScoreWindow");
        boolean scoreWindow = false;
        instanceCM.setScoreWindow(scoreWindow);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChallengeScore method, of class ChallengeModel.
     */
//    @Test
//    public void testGetChallengeScore() {
//        System.out.println("getChallengeScore");
//        long duration = 0L;
//        ChallengeModel instance = null;
//        int expResult = 0;
//        int result = instance.getChallengeScore(duration);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
